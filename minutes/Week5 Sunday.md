# Meeting Minutes Week 5 Sunday Elderberry
Time: 8:30pm - 9:30pm
Location:        Online

## AGENDA
  1. Talk about UML Design, what is good/bad (get everyone on same page)
  2. Delegate starting tasks for the project

#### Attendance
* Kellen Liew
* Matthew Brian
* Matthew Johnstone
* Yara Attia

## Discussion Points

Talked about and edited below:
UML diagram: https://lucid.app/lucidchart/c929fdc0-0d66-4e46-ad9d-2753e262f42c/edit?invitationId=inv_734cdfd6-47e5-4c3b-8116-ad66b7371d7c&page=0_0#

- Dungeon=Game - named to be inline with Response objects
- Options for how to check adjacent squares (just check all entities maybe)
- Items/Buildables will first be implemented as entities, but could make an inventory-like secondary description if needed
- Entity State for removal of 'dead' entities
- Goal implementation (check at the end of each tick - special goal object that can evaluate itself) (defined inside Dungeon? subclass?)
- Difference between updates method / when to use components or the abstract method instead
- Communication between components
- Interfaces vs components
- Inventory/player structuring
- Other minor points


### Actionable Items: 
* Next meeting: Thursday 21st 12:00pm
* Everyone: Start work on tasks being created on GitHub tasks board