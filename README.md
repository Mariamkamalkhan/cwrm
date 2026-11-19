# Community Waste and Recycling Manager (CWRM)

SDG 11 (Target 11.6) group project for CSC61204 - Software Construction.
Residents book recycling/bulky-waste pickups, collectors log completed
collections, and residents earn reward points. Uses the Builder pattern
to construct pickup requests and the Strategy pattern for reward-point
rules.

## Requirements

- Java 17+
- Maven 3.9+

## Build

```bash
mvn clean package
```

This compiles the project, runs all JUnit 5 tests, generates the JaCoCo
coverage report (`target/site/jacoco/index.html`), and produces an
executable jar at `target/cwrm.jar`.

## Run

```bash
java -jar target/cwrm.jar
```

## Run tests only

```bash
mvn test
```

## Project structure

```
src/main/java/com/cwrm/
├── model/        Domain classes: User, Resident, WasteCollector, PickupRequest...
├── strategy/      Strategy pattern: RewardStrategy and implementations
├── service/      Business logic services
├── api/          OpenStreetMap Nominatim API client
├── controller/   Input validation and GUI <-> service wiring
└── view/         Swing GUI screens
src/test/java/com/cwrm/   Mirrors the above, JUnit 5 tests
```

## Team

| Member | Responsibility |
|---|---|
| 1 | Domain model + Builder pattern (pickup requests) |
| 2 | Strategy pattern  + GUI/MVC |
| 3 | Core business logic, Strategy implementation, unit testing, JaCoCo |
| 4 | OpenStreetMap Nominatim API integration |

