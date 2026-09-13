# Space Colony Simulation

A Java simulation with the exact same class structure, relationships, and
design patterns as the original "Ecosystem Simulation" UML diagram — just
re-themed from plants/animals to a mining colony on an asteroid field.

## Concept mapping

| Original (Ecosystem)      | This project (Space Colony)      | Role                                   |
|----------------------------|-----------------------------------|-----------------------------------------|
| `Locatable`                | `Locatable`                        | interface — has a grid position         |
| `Edible`                   | `Refuelable`                       | interface — can be consumed for energy/fuel |
| `MovementStrategy`         | `NavigationStrategy`               | interface — pluggable movement behaviour |
| `Organism`                 | `Unit`                             | abstract root of everything on the grid |
| `Plant`                    | `ResourceNode`                     | stationary, spreads over time           |
| `Animal`                   | `Vehicle`                          | abstract mobile unit, consumes fuel     |
| `Herbivore`                | `Harvester`                        | mines resource nodes                    |
| `Carnivore`                | `Raider`                           | preys on harvesters                     |
| `RandomWalkStrategy`       | `WanderStrategy`                   | random-step movement                    |
| `FleeOrSeekStrategy`       | `PursueOrEvadeStrategy`            | flee threats / chase prey               |
| `OrganismFactory`          | `UnitFactory`                      | static factory for all unit types       |
| `World`                    | `Colony`                           | owns the grid, ticks the simulation     |
| `CircularBuffer<T>`        | `RingLog<T>`                       | fixed-size `Iterable<T>` ring buffer    |
| `SimulationException`      | `ColonyException`                  | abstract checked exception root         |
| `InvalidPositionException` | `InvalidSectorException`           | bad/occupied grid cell                  |
| `OverpopulationException`  | `ResourceExhaustionException`      | too many units alive at once            |

Every generalisation, realisation, and dependency arrow from the original
diagram has a direct counterpart here (e.g. `Unit` implements `Locatable`
and `Refuelable` the same way `Organism` implemented `Locatable` and
`Edible`; `Vehicle.act()` is `final` and delegates to abstract
`scavenge()` / `tryUpgrade()` hooks the same way `Animal.act()` delegated
to `forage()` / `tryReproduce()`).

## Build & run

Requires a JDK (11+).

```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out com.spacecolony.sim.Main
```

This seeds a 20x20 colony with resource nodes, harvesters, and raiders,
runs 50 simulation cycles, and prints a population snapshot every 10
cycles plus a rolling log of the last 10 events.

## Package layout

```
src/main/java/com/spacecolony/sim/
├── Position.java                 # grid coordinate value type
├── Locatable.java                # interface
├── Refuelable.java                # interface
├── NavigationStrategy.java       # interface
├── Unit.java                     # abstract base class
├── ResourceNode.java             # concrete Unit
├── Vehicle.java                  # abstract Unit
├── Harvester.java                # concrete Vehicle
├── Raider.java                   # concrete Vehicle
├── WanderStrategy.java           # concrete NavigationStrategy
├── PursueOrEvadeStrategy.java    # concrete NavigationStrategy
├── UnitFactory.java              # static factory
├── Colony.java                   # the world/grid
├── RingLog.java                  # generic circular buffer
├── ColonyException.java          # abstract checked exception
├── InvalidSectorException.java
├── ResourceExhaustionException.java
└── Main.java                     # demo entry point
```
