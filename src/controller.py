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
    intents=intents
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

@bot.command()
async def addopt(ctx, name, url):
    pet_id = ctx.guild.id
    pet_time = ctx.message.created_at
    PetService.create_pet(pet_id, pet_time, name, url)
    await ctx.send("🐾 Pet adotado com sucesso!")

@bot.command()
async def check(ctx):
    pet_id = ctx.guild.id
    pet = PetService.get_pet_callback(pet_id)

    embed = discord.Embed(
        title=f"{pet.name}",
        description="Status atual do pet",
        color=discord.Color.blue()
    )
    embed.add_field(name="⚡ Energia",  value=f"{pet.energy}/100",              inline=True)
    embed.add_field(name="🍖 Fome",     value=f"{pet.hunger}/100",              inline=True)
    embed.add_field(name="😴 Dormindo", value="Sim" if pet.sleeping else "Não", inline=True)
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
    user = UserService.get_user_callback(user_id, guild_id)

    if user is None or user.balance < 10:
        await ctx.send("⚠️ Dinheiro insuficiente! Acumule 10 moedas para comprar comida.")
        return

    pet = PetService.get_pet_callback(guild_id)
    UserService.increase_wealth(user_id, guild_id, -10)
    food, rarity = gen_food()
    raw_xp = int(rarity["mult"] * food["value"])
    await ctx.send(
        f"🍽️ Gerando um(a) **{food['name']}** de raridade **{rarity['rarity']}**! "
        f"Vale **{raw_xp}** pontos!"
    )
    xp = PetService.give_food(guild_id, raw_xp)
    UserService.increase_xp(user_id, guild_id, xp)


bot.run(TOKEN)