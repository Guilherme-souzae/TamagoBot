import re
import random

from exceptions import InvalidNameError, InvalidUrlError

DISCORD_IMAGE_REGEX = re.compile(
    r"^https://(?:cdn\.discordapp\.com|media\.discordapp\.net)"
    r"/attachments/\d+/\d+/[^/?]+\.(png|jpg|jpeg|gif|webp)$",
    re.IGNORECASE
)

def validate_name(name):
    pass

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
            "value": 30
        },
        {
            "name": "banana",
            "value": 35
        },
        {
            "name": "orange",
            "value": 40
        },
        {
            "name": "grape",
            "value": 45
        },
        {
            "name": "watermelon",
            "value": 50
        },
        {
            "name": "strawberry",
            "value": 55
        },
        {
            "name": "bread",
            "value": 60
        },
        {
            "name": "cheese",
            "value": 75
        },
        {
            "name": "hamburger",
            "value": 100
        },
        {
            "name": "pizza",
            "value": 120
        },
        {
            "name": "sushi",
            "value": 140
        },
        {
            "name": "steak",
            "value": 160
        },
        {
            "name": "cake",
            "value": 180
        },
        {
            "name": "golden apple",
            "value": 250
        },
        {
            "name": "dragon fruit",
            "value": 300
        },
        {
            "name": "phoenix meat",
            "value": 500
        },
        {
            "name": "celestial pudding",
            "value": 1000
        }
    )

    food = random.choice(foods)

    rarity = random.choices(
        population=rarities,
        weights=[r["weight"] for r in rarities],
        k=1
    )[0]

    return food, rarity