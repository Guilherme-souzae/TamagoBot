import discord
from discord.ext import commands
from dotenv import load_dotenv
import os

from model.pet.pet_entity import Pet
from model.pet.pet_service import PetService
from model.user.user_entity import User
from model.user.user_service import UserService

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
    pass

# --- COMANDOS ---

@bot.command()
async def addopt(ctx, name, url):
    pet_id = ctx.guild.id
    pet_time = ctx.message.created_at
    PetService.create_pet(pet_id, pet_time, name, url)
    await ctx.send("Pet adotado com sucesso")

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

@bot.command()
async def lullaby(ctx):
    pet_id = ctx.guild.id
    PetService.set_sleeping(pet_id, True)

@bot.command()
async def shout(ctx):
    pet_id = ctx.guild.id
    PetService.set_sleeping(pet_id, False)

@bot.command()
async def status(ctx):
    user_id = ctx.author.id
    guild_id = ctx.guild.id
    user = UserService.get_user_callback(user_id, guild_id)

    embed = discord.Embed(
        title=f"{ctx.author.name}",
        description=f"XP: {user.xp}",
        color=discord.Color.blue()
    )

    embed.add_field(name="Dinheiro: ", value=f"{user.balance}", inline=True)

    await ctx.send(embed=embed)

@bot.command()
async def treat(ctx):
    food, rarity = gen_food()
    await ctx.send(f"Generating a {rarity["rarity"]} {food["name"]}! Making {int(rarity["mult"] * food["value"])} points!")


bot.run(TOKEN)