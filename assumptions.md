
Portals
- Portals teleport entities directly onto their corresponding portal
- Only two portals of a specific colour can exist at any time (as in the Portal games)
- Portals read in as JSON will always come in pairs, solo portals will not be fed in


Shield:
- Deflect x amount damage taken to a player. Rather than removing a % of the damage. Specifically 2 damage in this case. 


Player
- There exists a single player on the map at all times

Potions
- Players cannot consume more than one potion a tick
- Players can possess multiple of the same potion type
- When consuming a potion, players don't select an individual potion, but any potion of the requested type.

Health Potion
- The health restoration takes place in the same tick as consumption

Invisible Potion
- The effect of the potion lasts 10 ticks starting from the consumption tick
- Players can walk through enemies without initiating a battle
- Zombie and spider will continue the same movement pattern. Hostile mercenaries will move randomly, similar to zombie to simulate confusion

Invincible Potion
- The effect of the potion lasts 10 ticks starting from the consumption tick
- Players will still initiate a battle, but it will be auto-resolved. Players can therefore still receive rare items when invincible
- Zombie and Mercenary will run away, Spider will keep its circling pattern

Bomb
- The player receives no damage from exploding bombs
- Blast range is a 5x5 grid surrounding the bomb position

The One True Ring
- Revives the player in the same position
- If the enemy hasn't moved on, this means that the battle will still continue

Mercenary
- The player requires one coin to bribe them
- The mercenary can be bribed through any entity, as long as they are within range

Battle Component
- The player starts with 100hp and 20dmg
- The mercenary starts with 30hp and 10dmg
- The spider starts with 5hp and 5dmg
- The zombietoast starts with 10hp and 10dmg
- Enemies attack first
- Players cannot use items during battle except for one true ring (yet to be implemented)
- Ally mercs will not share player bonuses like shields and swords