import shelve

def create_pet(guild, name, img_link):
    with shelve.open("petshop") as db:
        db['guild'] = {'name': name, 'img_link': img_link}

def read_pet(guild):
    with shelve.open("petshop") as db:
        pet = db['guild']
        pet_name = pet['name']
        img_link = pet['img_link']
        return pet_name, img_link