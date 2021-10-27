# Design choices

## Dungeon
#### inventory
- Items can only be used if they are in the character's inventory. Keeping a separate list seems to be  appropriate as when creating a ItemResponse this separate list does not need to be filtered and other Entities do not need to keep track of their inventory status.
- Since all items must have a unique id it makes sense to store each item separately. Rather than is only a list and a frequency. 
- Since the resolution of the map is so small and currently the frequency of items is quite low, keeping a single list with O(n) search times seems sufficient for when accessing the user's inventory.
#### buildables


#### goals


#### gameMode

#### goalCondition
