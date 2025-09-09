import pytest
from src import msg_processor
from unittest.mock import AsyncMock, patch

@pytest.mark.asyncio
async def test_adopt_success():
    ctx = AsyncMock()
    ctx.guild = "fake_guild"

    with patch("src.msg_processor.main_processing.process_adopt") as mock_process:
        mock_process.return_value = None
        await msg_processor.adopt(ctx, content="some content")

    ctx.send.assert_awaited_once_with("Adopted")