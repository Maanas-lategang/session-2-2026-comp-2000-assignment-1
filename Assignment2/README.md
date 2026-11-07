# COMP2000 Assignment 2 — Space Mining Simulation

A Java simulation of a space environment where mining ships collect resources from asteroids and pirate ships pursue miners. The behaviour of these entities changes as resources and fuel become limited.

## Simulation features

- **Mining ships** search for asteroids and collect resources.
- **Pirate ships** pursue mining ships.
- **Movement strategies** separate movement decisions from ship behaviour using the Strategy design pattern.
- **Simulation statistics** use Java streams and lambda expressions to calculate information about the environment.
- **Space events** include solar storms and new resource discoveries.
- **Graphical display** visualises the entities and shows simulation information.

## Java design

The simulation uses object-oriented programming concepts including classes, inheritance, interfaces and polymorphism. `MovementStrategy` defines movement behaviour, with `SeekResourcesStrategy` and `HuntMinersStrategy` providing different implementations. `SimulationStats` calculates statistics using streams and lambdas. `SpaceEvents` introduces occasional environmental changes.

## Files

The project contains 14 Java source files. `Main.java` is the application entry point. All source files use the standard Java library.

## Run

Compile the source files in the project directory:

```bash
javac *.java
```

Run the simulation:

```bash
java Main
```
