# Meeting Minutes Week 6 Sunday Elderberry
Time: 8:30pm @ 9:30pm
Location:        Online

## AGENDA
  1. Recap and summarise progress so far

#### Attendance
* Kellen Liew
* Matthew Brian
* Matthew Johnstone
* Yara Attia

## Discussion Points
- Saved games are now files are saved in json format
- tick -> ItemState(item, direction) -> processInput for each entity -> update each entity
- Do we trust the map json files given to us? e.g. Don't contain 2 players
- possible error from double comparison in ```getEntitiesInRadius()```
- Components effect its Entity which can effect other Components within the Entity

### Actionable Items: 
* Next meeting: Thursday
* Reschedule both weekly meetings in the tut 
* Hayley: Goal and game Exit related features
* Kellen: Static items and usable items
* Matthew B: Movable map features e.g. Portal, Floor switch, boulder
* Matthew J: buildables to coordinate with Kellen + dungeon tests
* Yara: Door + Key + ZombieToast