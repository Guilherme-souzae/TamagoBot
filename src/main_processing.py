from src import db_manager

def process_adopt(guild, content):
    parts = content.split()

    if parts[-1].startswith("http"):
        img_link = parts[-1]
        name = " ".join(parts[:-1])
        db_manager.create_pet(guild, name, img_link)
    else:
        raise Exception