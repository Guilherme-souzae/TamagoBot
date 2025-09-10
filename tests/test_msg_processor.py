import pytest
from src import msg_processor
from unittest.mock import AsyncMock, patch

@pytest.mark.asyncio
async def test_adopt_process_success():
    ctx = AsyncMock()
    ctx.guild = "fake_guild"

    with patch("src.main_processing.process_adopt") as mock_process:
        await msg_processor.adopt(ctx, content="some content")

    mock_process.assert_called_once_with(ctx.guild, "some content")
    ctx.send.assert_awaited_once_with("Adopted")


@pytest.mark.asyncio
async def test_adopt_process_failure():
    ctx = AsyncMock()
    ctx.guild = "fake_guild"
    error_message = "Erro na url"

    with patch("src.main_processing.process_adopt") as mock_process:
        mock_process.side_effect = Exception(error_message)
        await msg_processor.adopt(ctx, content="some content")

    mock_process.assert_called_once_with(ctx.guild, "some content")
    ctx.send.assert_awaited_once_with(f"Error: {error_message}")