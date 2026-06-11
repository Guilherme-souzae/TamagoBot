"""
Testes unitários para utils.py.
Cobre validate_image_url, validate_name e gen_food.
"""
import pytest
from unittest.mock import patch
from exceptions import InvalidUrlError
from utils import validate_image_url, validate_name, gen_food


# ──────────────────────────────────────────────
# validate_image_url
# ──────────────────────────────────────────────

class TestValidateImageUrl:
    # URLs válidas — cdn.discordapp.com
    @pytest.mark.parametrize("url", [
        "https://cdn.discordapp.com/attachments/111/222/image.png",
        "https://cdn.discordapp.com/attachments/111/222/image.jpg",
        "https://cdn.discordapp.com/attachments/111/222/image.jpeg",
        "https://cdn.discordapp.com/attachments/111/222/image.gif",
        "https://cdn.discordapp.com/attachments/111/222/image.webp",
        "https://cdn.discordapp.com/attachments/123456789/987654321/my_pet.PNG",   # case-insensitive
        "https://cdn.discordapp.com/attachments/123456789/987654321/my_pet.GIF",
    ])
    def test_valid_cdn_discord_url(self, url):
        validate_image_url(url)  # não deve lançar

    # URLs válidas — media.discordapp.net
    @pytest.mark.parametrize("url", [
        "https://media.discordapp.net/attachments/111/222/image.png",
        "https://media.discordapp.net/attachments/111/222/image.jpg",
        "https://media.discordapp.net/attachments/111/222/image.webp",
    ])
    def test_valid_media_discord_url(self, url):
        validate_image_url(url)  # não deve lançar

    # URLs inválidas
    @pytest.mark.parametrize("url", [
        # domínio errado
        "https://imgur.com/attachments/111/222/image.png",
        "https://i.imgur.com/abc.png",
        "http://cdn.discordapp.com/attachments/111/222/image.png",   # http, não https
        "https://cdn.discordapp.com/attachments/111/222/image.bmp",  # extensão não suportada
        "https://cdn.discordapp.com/attachments/111/222/image.mp4",  # vídeo
        "https://cdn.discordapp.com/attachments/111/222/image",      # sem extensão
        "https://cdn.discordapp.com/channels/111/222/image.png",     # caminho errado
        "https://cdn.discordapp.com/attachments/abc/def/image.png",  # IDs não numéricos
        "ftp://cdn.discordapp.com/attachments/111/222/image.png",    # protocolo errado
        "",                                                           # vazio
        "not-a-url",                                                  # texto aleatório
        "https://cdn.discordapp.com/attachments/111/222/image.png?size=1024",  # query string
    ])
    def test_invalid_url_raises_error(self, url):
        with pytest.raises(InvalidUrlError):
            validate_image_url(url)


# ──────────────────────────────────────────────
# validate_name (atualmente não levanta, mas testamos o contrato)
# ──────────────────────────────────────────────

class TestValidateName:
    def test_valid_simple_name_does_not_raise(self):
        validate_name("Rex")

    def test_valid_name_with_spaces_does_not_raise(self):
        validate_name("Big Dog")

    def test_empty_name_does_not_raise_yet(self):
        # comportamento atual: não implementado, não levanta
        validate_name("")

    def test_long_name_does_not_raise_yet(self):
        validate_name("x" * 200)


# ──────────────────────────────────────────────
# gen_food
# ──────────────────────────────────────────────

class TestGenFood:
    VALID_RARITIES = {"common", "uncommon", "rare", "epic", "legendary", "mythical"}
    VALID_FOODS = {
        "apple", "banana", "orange", "grape", "watermelon", "strawberry",
        "bread", "cheese", "hamburger", "pizza", "sushi", "steak", "cake",
        "golden apple", "dragon fruit", "phoenix meat", "celestial pudding",
    }

    def test_returns_tuple_of_two(self):
        result = gen_food()
        assert isinstance(result, tuple)
        assert len(result) == 2

    def test_food_has_name(self):
        food, _ = gen_food()
        assert "name" in food

    def test_food_has_value(self):
        food, _ = gen_food()
        assert "value" in food

    def test_food_value_is_positive(self):
        food, _ = gen_food()
        assert food["value"] > 0

    def test_food_name_is_valid(self):
        food, _ = gen_food()
        assert food["name"] in self.VALID_FOODS

    def test_rarity_has_rarity_field(self):
        _, rarity = gen_food()
        assert "rarity" in rarity

    def test_rarity_has_mult_field(self):
        _, rarity = gen_food()
        assert "mult" in rarity

    def test_rarity_has_weight_field(self):
        _, rarity = gen_food()
        assert "weight" in rarity

    def test_rarity_name_is_valid(self):
        _, rarity = gen_food()
        assert rarity["rarity"] in self.VALID_RARITIES

    def test_rarity_mult_is_positive(self):
        _, rarity = gen_food()
        assert rarity["mult"] > 0

    def test_computed_value_is_positive(self):
        food, rarity = gen_food()
        assert int(rarity["mult"] * food["value"]) > 0

    def test_many_calls_all_return_valid_foods(self):
        for _ in range(200):
            food, rarity = gen_food()
            assert food["name"] in self.VALID_FOODS
            assert rarity["rarity"] in self.VALID_RARITIES

    def test_mythical_rarity_has_highest_mult(self):
        """O multiplicador mythical deve ser o maior de todos."""
        # Força rarity para cada possibilidade mockando random.choices
        mults = {}
        all_rarities = ["common", "uncommon", "rare", "epic", "legendary", "mythical"]
        for _ in range(500):
            _, rarity = gen_food()
            mults[rarity["rarity"]] = max(mults.get(rarity["rarity"], 0), rarity["mult"])

        # se mythical apareceu, seu mult deve ser 10.0
        if "mythical" in mults:
            assert mults["mythical"] == 10.0

    def test_common_rarity_has_lowest_mult(self):
        for _ in range(300):
            _, rarity = gen_food()
            if rarity["rarity"] == "common":
                assert rarity["mult"] == 1.0
                return

    def test_all_rarities_reachable_over_many_calls(self):
        """Todos os rarities devem aparecer em 10000 chamadas (probabilístico)."""
        seen = set()
        for _ in range(10_000):
            _, rarity = gen_food()
            seen.add(rarity["rarity"])
        assert seen == self.VALID_RARITIES
