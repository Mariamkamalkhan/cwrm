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

Expected result: all tests pass except one.
`RewardStrategyTest.calculatePoints_smallPaperQuantity_awardsAtLeastOnePoint`
fails on purpose to document a known limitation: rounding down means a
very small recycling amount (e.g. 0.3 kg of paper) earns 0 points. The
build is configured (`testFailureIgnore` in `pom.xml`) so this expected
failure does not stop the build.

## Project structure
``
src/main/java/com/cwrm/
├── model/        Domain classes: User, Resident, WasteCollector, Administrator, PickupRequest, PickupRequestBuilder, CollectionStatus
├── strategy/     Strategy pattern: RewardStrategy, MaterialRewardStrategy, CampaignRewardStrategy
├── service/      Business logic: CollectionService (completes pickups and awards points)
├── api/          OpenStreetMap Nominatim API client
├── controller/   Input validation and GUI <-> service wiring
└── view/         Swing GUI screens
src/test/java/com/cwrm/   JUnit 5 tests mirroring the packages above
``
## Team

| Member | Responsibility |
|---|---|
| 1 | Domain model + Builder pattern (pickup requests) |
| 2 | Strategy pattern + GUI/MVC |
| 3 | Core business logic, Strategy implementation, unit testing, JaCoCo |
| 4 | OpenStreetMap Nominatim API integration |
