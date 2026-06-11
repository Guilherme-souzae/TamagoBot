class User:
    def __init__(self, id, guild_id, balance=0, xp=0):
        self.id = id
        self.guild_id = guild_id
        self.balance = balance
        self.xp = xp