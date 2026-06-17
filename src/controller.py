import discord
from discord.ext import commands
from dotenv import load_dotenv
import os

from model.pet.pet_entity import Pet
from model.pet.pet_service import PetService
from model.user.user_entity import User
from model.user.user_service import UserService

from exceptions import (
    UnregisteredPetError,
    AlreadyAddoptedPetError,
    InsuficientMoneyError,
    InvalidNameError,
    InvalidUrlError,
)
from utils import gen_food

load_dotenv()

TOKEN = os.getenv("DISCORD_TOKEN")

intents = discord.Intents.default()
intents.message_content = True

bot = commands.Bot(
    command_prefix="!",
    intents=intents,
    help_command=None
)

# --- EVENTOS  ---

@bot.event
async def on_ready():
    print(f"Logado como {bot.user}")

@bot.event
async def on_message(ctx):
    if ctx.author.bot:
        return

    user_id = ctx.author.id
    guild_id = ctx.guild.id
    UserService.increase_wealth(user_id, guild_id)
    await bot.process_commands(ctx)

# --- ERROS    ---

@bot.event
async def on_command_error(ctx, error):
    # Erros de uso do comando (argumento faltando, etc.)
    if isinstance(error, commands.MissingRequiredArgument):
        await ctx.send(f"⚠️ Argumento obrigatório faltando: `{error.param.name}`.")
    elif isinstance(error, commands.CommandNotFound):
        await ctx.send("❓ Comando não encontrado.")
    else:
        # Repassa exceções de domínio que vieram embrulhadas pelo discord.py
        cause = getattr(error, "original", error)
        if isinstance(cause, UnregisteredPetError):
            await ctx.send("🐾 Nenhum pet encontrado neste servidor. Use `!addopt` para adotar um!")
        elif isinstance(cause, AlreadyAddoptedPetError):
            await ctx.send("🐾 Este servidor já possui um pet. Use `!abandon` antes de adotar outro.")
        elif isinstance(cause, InsuficientMoneyError):
            await ctx.send("💸 Você não tem dinheiro suficiente para isso.")
        elif isinstance(cause, InvalidNameError):
            await ctx.send("✏️ Nome inválido. Use apenas letras, números e espaços (máx. 32 caracteres).")
        elif isinstance(cause, InvalidUrlError):
            await ctx.send("🔗 URL inválida. Envie um link de imagem do Discord (png, jpg, gif ou webp).")
        else:
            await ctx.send("❌ Ocorreu um erro inesperado. Tente novamente mais tarde.")
            raise error  # Registra no console para depuração

# --- COMANDOS ---

@bot.command(name="help")
async def help_command(ctx):
    embed = discord.Embed(
        title="🐾 TamagoBot — Guia de Comandos",
        description=(
            "TamagoBot é um bot de tamagotchi para Discord! "
            "Cada servidor tem **um pet coletivo** que todos os membros podem cuidar juntos. "
            "Mande mensagens no servidor para acumular moedas e use-as para alimentar o pet!"
        ),
        color=discord.Color.purple()
    )

    # --- Pet ---
    embed.add_field(
        name="🐣 Adoção",
        value=(
            "**`!addopt <nome> <url>`**\n"
            "Adota um pet para o servidor. O `<nome>` deve ter entre 1 e 20 caracteres "
            "(letras, números, `-` ou `_`, sem espaços). "
            "A `<url>` deve ser um link de imagem enviada pelo Discord "
            "o link deve terminar com a extensão (`.png`, `.jpg`, `.gif` ou `.webp`).\n"
            "*Exemplo: `!addopt Rex https://cdn.discordapp.com/.../pet.png`*"
        ),
        inline=False
    )

    embed.add_field(
        name="📊 Status do Pet",
        value=(
            "**`!check`**\n"
            "Exibe o status atual do pet: energia ⚡, fome 🍖 e se está dormindo 😴. "
            "Ambos os atributos vão de 0 a 100 e diminuem com o tempo quando o pet está acordado."
        ),
        inline=False
    )

    embed.add_field(
        name="😴 Dormir & Acordar",
        value=(
            "**`!lullaby`** — Coloca o pet para dormir.\n"
            "**`!shout`** — Acorda o pet.\n\n"
            "Enquanto dorme, o pet recupera energia **2× mais rápido**, "
            "mas também fica com fome **2× mais rápido**. "
            "Use com sabedoria!"
        ),
        inline=False
    )

    embed.add_field(
        name="🍽️ Alimentar o Pet",
        value=(
            "**`!treat`** — Gasta **10 moedas** para comprar uma comida aleatória.\n"
            "A comida tem raridade variável (comum → mítica) que multiplica seu valor. "
            "Alimentar o pet também concede XP ao seu perfil!"
            "Se o pet não estiver saudável, você receberá somente metade do XP."
            "Alimentar o pet quando o mesmo está com fome garante mais XP!"
            "Se o pet já estiver com fome cheia, o excedente vira XP bônus."
        ),
        inline=False
    )

    embed.add_field(
        name="💔 Abandonar",
        value=(
            "**`!abandon`**\n"
            "Remove o pet do servidor permanentemente. "
            "Após isso, qualquer membro pode adotar um novo com `!addopt`."
        ),
        inline=False
    )

    # --- Usuário ---
    embed.add_field(
        name="👤 Seu Perfil",
        value=(
            "**`!status`**\n"
            "Mostra seu saldo de moedas 💰 e XP acumulado. "
            "Seu perfil é criado automaticamente ao enviar a primeira mensagem no servidor."
        ),
        inline=False
    )

    # --- Economia ---
    embed.add_field(
        name="💰 Como ganhar moedas?",
        value=(
            "Você ganha **1 moeda** a cada mensagem enviada no servidor. "
            "Acumule moedas para comprar comida com `!treat`."
        ),
        inline=False
    )

    # --- Mecânicas de tempo ---
    embed.add_field(
        name="⏱️ Mecânicas de Tempo",
        value=(
            "O pet perde atributos passivamente com o tempo:\n"
            "• **Fome** cai 1 ponto a cada **10 minutos** (acordado) ou 2 pontos (dormindo)\n"
            "• **Energia** cai 1 ponto a cada **20 minutos** (acordado) ou sobe 2 pontos (dormindo)\n"
            "Mantenha o pet bem alimentado e descansado! 🐾"
        ),
        inline=False
    )

    embed.set_footer(text="TamagoBot • Cuide bem do seu pet!")

    await ctx.send(embed=embed)


@bot.command()
async def addopt(ctx, name, url):
    pet_id = ctx.guild.id
    pet_time = ctx.message.created_at
    PetService.create_pet(pet_id, pet_time, name, url)
    await ctx.send("🐾 Pet adotado com sucesso!")

@bot.command()
async def check(ctx):
    pet_id = ctx.guild.id
    curr_time = ctx.message.created_at
    PetService.apply_time(pet_id, curr_time)
    pet = PetService.get_pet_callback(pet_id)

    embed = discord.Embed(
        title=f"{pet.name}",
        description="Status atual do pet",
        color=discord.Color.blue()
    )
    embed.add_field(name="⚡ Energia",  value=f"{pet.energy}/100",              inline=True)
    embed.add_field(name="🍖 Fome",     value=f"{pet.hunger}/100",              inline=True)
    embed.add_field(name="😴 Dormindo", value="Sim" if pet.sleeping else "Não", inline=True)
    embed.add_field(name="💚 Omeostase", value="Sim" if pet.omeostasis else "Não", inline=True)
    embed.set_image(url=pet.url)

    await ctx.send(embed=embed)

@bot.command()
async def abandon(ctx):
    pet_id = ctx.guild.id
    PetService.delete_pet_callback(pet_id)
    await ctx.send("💔 Pet abandonado.")

@bot.command()
async def lullaby(ctx):
    pet_id = ctx.guild.id
    PetService.set_sleeping(pet_id, True)
    await ctx.send("😴 Seu pet está dormindo agora.")

@bot.command()
async def shout(ctx):
    pet_id = ctx.guild.id
    PetService.set_sleeping(pet_id, False)
    await ctx.send("📢 Seu pet foi acordado!")

@bot.command()
async def status(ctx):
    user_id = ctx.author.id
    guild_id = ctx.guild.id
    user = UserService.get_user_callback(user_id, guild_id)

    if user is None:
        await ctx.send("⚠️ Você ainda não possui um perfil. Mande uma mensagem para criar um!")
        return

    embed = discord.Embed(
        title=f"{ctx.author.name}",
        description=f"XP: {user.xp}",
        color=discord.Color.blue()
    )
    embed.add_field(name="Dinheiro:", value=f"{user.balance}", inline=True)

    await ctx.send(embed=embed)

@bot.command()
async def treat(ctx):
    user_id = ctx.author.id
    guild_id = ctx.guild.id
    curr_time = ctx.message.created_at
    PetService.apply_time(guild_id, curr_time)
    pet = PetService.get_pet_callback(guild_id)
    user = UserService.get_user_callback(user_id, guild_id)

    if user.balance < 10:
        await ctx.send("⚠️ Dinheiro insuficiente! Acumule 10 moedas para comprar comida.")
        return
    
    if pet == None:
        await ctx.send("⚠️ Nenhum pet cadastrado!.")
        return

    UserService.increase_wealth(user_id, guild_id, -10)
    food, rarity = gen_food()
    raw_xp = int(rarity["mult"] * food["value"])
    await ctx.send(
        f"🍽️ Gerando um(a) **{food['name']}** de raridade **{rarity['rarity']}**! "
        f"Vale **{int(rarity['mult'] * food['value'])}** pontos!"
    )
    xp = PetService.give_food(guild_id, raw_xp)

    if pet.omeostasis == False:
        xp = int(xp / 2)

    UserService.increase_xp(user_id, guild_id, xp)


bot.run(TOKEN)