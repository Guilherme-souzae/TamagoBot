import shelve

def create_pet(guild, name, img_link):
    with shelve.open("../petshop") as db:
        db['guild'] = {'name': name, 'img_link': img_link}


def update_pet_name(guild, name):
    with shelve.open("../petshop") as db:
        db['guild']['name'] = name

def update_pet_img_link(guild, name):
    with shelve.open("../petshop") as db:
        db['guild']['img_link'] = name


def read_pet(guild):
    with shelve.open("../petshop") as db:
        return db['guild']


def delete_pet(guild):
    with shelve.open("../petshop") as db:
        del db['guild']