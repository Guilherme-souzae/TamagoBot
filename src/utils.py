import re
import random

from exceptions import InvalidNameError, InvalidUrlError

DISCORD_IMAGE_REGEX = re.compile(
    r"^https://(?:cdn\.discordapp\.com|media\.discordapp\.net)"
    r"/attachments/\d+/\d+/[^/?]+\.(png|jpg|jpeg|gif|webp)$",
    re.IGNORECASE
)

PET_NAME_REGEX = r"^[a-zA-Z0-9_-]{1,20}$"

def validate_name(name):
    if re.match(PET_NAME_REGEX, name) == False:
        raise InvalidNameError

def validate_image_url(url):
     if bool(DISCORD_IMAGE_REGEX.match(url)) == False:
         raise InvalidUrlError

def gen_food():
    rarities = (
        {
            "rarity": "common",
            "mult": 1.0,
            "weight": 100
        },
        {
            "rarity": "uncommon",
            "mult": 1.5,
            "weight": 70
        },
        {
            "rarity": "rare",
            "mult": 2.0,
            "weight": 50
        },
        {
            "rarity": "epic",
            "mult": 3.0,
            "weight": 30
        },
        {
            "rarity": "legendary",
            "mult": 4.0,
            "weight": 10
        },
        {
            "rarity": "mythical",
            "mult": 10.0,
            "weight": 1
        }
    )

    foods = (
        {
            "name": "apple",
            "value": 5
        },
        {
            "name": "banana",
            "value": 5
        },
        {
            "name": "orange",
            "value": 10
        },
        {
            "name": "grape",
            "value": 1
        },
        {
            "name": "watermelon",
            "value": 15
        },
        {
            "name": "strawberry",
            "value": 3
        },
        {
            "name": "bread",
            "value": 10
        },
        {
            "name": "cheese",
            "value": 20
        },
        {
            "name": "hamburger",
            "value": 25
        },
        {
            "name": "pizza",
            "value": 30
        },
        {
            "name": "sushi",
            "value": 25
        },
        {
            "name": "steak",
            "value": 25
        },
        {
            "name": "cake",
            "value": 30
        },
        {
            "name": "enchanted golden apple",
            "value": 100
        },
        {
            "name": "ultimeatum",
            "value": 100
        }
    )

    food = random.choice(foods)

    rarity = random.choices(
        population=rarities,
        weights=[r["weight"] for r in rarities],
        k=1
    )[0]

    return food, rarity