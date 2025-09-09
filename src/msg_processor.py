import os
import discord
from src import db_manager
from src import main_processing
from discord.ext import commands
from dotenv import load_dotenv


load_dotenv()
TOKEN = os.getenv("TOKEN")


intents = discord.Intents.default()
intents.message_content = True

bot = commands.Bot(command_prefix="!", intents=discord.Intents.all())


@bot.event
async def on_ready():
    print(f"{bot.user} is on the line!")

@bot.command(name="adopt")
async def adopt(ctx, *, content: str):
    guild = ctx.guild
    try:
        main_processing.process_adopt(guild, content)
        await ctx.send(f"Adopted")
    except Exception as e:
        await ctx.send("Invalid argument")

@bot.command(name="check")
async def check(ctx):
    guild = ctx.guild
    pet_name = db_manager.read_pet(guild)
    if pet_name:
        await ctx.send(f"{pet_name} is online")

@bot.command(name="free")
async def delete(ctx):
    guild = ctx.guild
    db_manager.delete_pet(guild)
    await ctx.send(f"Deleted")

@bot.command(name="rename")
async def rename(ctx, new_name):
    guild = ctx.guild

if __name__ == "main":
    bot.run(TOKEN)
