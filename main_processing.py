petshop = {}

def process_adopt(guild, content):
    parts = content.split()

    if parts[-1].startswith("http"):
        img_link = parts[-1]
        name = " ".join(parts[:-1])
        pet = {'name': name, 'img_link': img_link}
        petshop[guild] = pet
    else:
        raise Exception

def process_check(guild):
    pet = petshop.get(guild)
    if pet:
        return pet['name']
    else:
        return None