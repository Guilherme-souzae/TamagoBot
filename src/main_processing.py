from discord import Embed
from src import db_manager

def process_adopt(guild, content):
    parts = content.split()

    if parts[-1].startswith("http"):
        img_link = parts[-1]
        name = " ".join(parts[:-1])
        db_manager.create_pet(guild, name, img_link)
    else:
        raise Exception

def process_check(guild):
    pet = db_manager.read_pet(guild)
    embed = Embed(
        title=f"{pet['name']}",
        description="teste",
        color=0x00ff00
    )
    embed.set_image(url=pet['img_link'])
    return embed