import os
import discord
import main_processing
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
    pet_name = main_processing.process_check(guild)
    if pet_name:
        await ctx.send(f"{pet_name} is online")

bot.run(TOKEN)
