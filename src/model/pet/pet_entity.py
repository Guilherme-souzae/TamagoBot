class Pet:
    def __init__(self, id, time, name, url, energy=100, hunger=100, sleeping=False):
        self.id = id
        self.name = name
        self.url = url
        self.energy = energy
        self.hunger = hunger
        self.time = time
        self.sleeping = sleeping