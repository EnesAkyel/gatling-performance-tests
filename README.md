# Gatling Performance Tests

![Gatling](https://img.shields.io/badge/Gatling-FF9E2A?style=for-the-badge&logo=gatling&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)

A production-grade performance testing framework built with **Gatling** and **Java**, demonstrating senior-level SDET practices including load, stress, spike, and soak testing with threshold-based assertions and CI/CD integration.

---

## Table of Contents

- [Framework Architecture](#framework-architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Simulation Types](#simulation-types)
- [Getting Started](#getting-started)
- [Running Simulations](#running-simulations)
- [Reports](#reports)
- [CI/CD](#cicd)
- [Design Patterns](#design-patterns)

---

## Framework Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Simulation Layer                        │
│  BasicSimulation │ LoadSimulation │ StressSimulation        │
│  SpikeSimulation │ SoakSimulation                           │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                     Scenario Layer                          │
│         Reusable ScenarioBuilders (PostScenarios)           │
│         HTTP actions, checks, pauses, flows                 │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                     Config Layer                            │
│    Base URLs │ Load profiles │ Thresholds │ Assertions      │
└─────────────────────────────────────────────────────────────┘
```

---

## Tech Stack

| Tool                                                                                               | Purpose                         |
|----------------------------------------------------------------------------------------------------|---------------------------------|
| [Gatling](https://gatling.io)                                                                      | Performance testing engine      |
| [Java 25](https://openjdk.org)                                                                     | Primary language                |
| [Maven](https://maven.apache.org)                                                                  | Build and dependency management |
| [Gatling Maven Plugin](https://gatling.io/docs/gatling/reference/current/extensions/maven_plugin/) | Run simulations via Maven       |
| [GitHub Actions](https://github.com/features/actions)                                              | CI/CD pipeline                  |

---

## Project Structure

```
gatling-performance-tests/
├── src/
│   └── test/
│       ├── java/
│       │   ├── config/
│       │   │   └── Config.java           # Base URLs, thresholds, load profiles
│       │   ├── scenarios/
│       │   │   └── PostScenarios.java    # Reusable scenario builders
│       │   └── simulations/
│       │       ├── BasicSimulation.java  # Smoke test
│       │       ├── LoadSimulation.java   # Normal load test
│       │       ├── StressSimulation.java # Stress test
│       │       ├── SpikeSimulation.java  # Spike test
│       │       └── SoakSimulation.java   # Soak test
│       └── resources/
│           └── gatling.conf              # Gatling configuration
├── pom.xml
└── README.md
```

---

## Simulation Types

### Basic Simulation — Smoke Test
Validates all endpoints function correctly under minimal load (1 user per scenario).

### Load Simulation — Normal Load
Simulates expected production traffic with ramp up, steady state, and ramp down phases.

```
Users
  10 |          ________
   5 |         /        \
   0 |________/          \________
     0s      10s        70s      80s
```

### Stress Simulation — Breaking Point
Progressively increases load in steps to find the system's breaking point.

```
Users
  50 |                   _________
  40 |               ___/
  30 |           ___/
  20 |       ___/
  10 |   ___/
   0 |__/                          \__
     0s                              150s
```

### Spike Simulation — Sudden Bursts
Simulates sudden traffic spikes to test system resilience and recovery.

```
Users
 100 |                        |
  50 |          |             |
   5 |__________| ___________ | __________
     0s         20s          60s         80s
```

### Soak Simulation — Long Duration
Sustains moderate load over an extended period to detect memory leaks and performance degradation.

```
Users
  10 |     ___________________________
   5 |    /                           \
   0 |___/                             \___
     0s  30s                        5m30s  6m
```

---

## Getting Started

### Prerequisites

- Java 25+
- Maven 3.9+

### Installation

```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/gatling-performance-tests.git
cd gatling-performance-tests

# Verify setup
mvn validate
```

---

## Running Simulations

```bash
# Smoke test — verify all endpoints work
mvn gatling:test -Dsimulation=simulations.BasicSimulation

# Load test — normal expected traffic
mvn gatling:test -Dsimulation=simulations.LoadSimulation

# Stress test — find the breaking point
mvn gatling:test -Dsimulation=simulations.StressSimulation

# Spike test — sudden burst of traffic
mvn gatling:test -Dsimulation=simulations.SpikeSimulation

# Soak test — sustained load over time
mvn gatling:test -Dsimulation=simulations.SoakSimulation
```

---

## Reports

Gatling automatically generates an HTML report after every simulation run:

```
target/gatling/<SimulationName>-<timestamp>/index.html
```

Open it in your browser to see:

- Response time distribution
- Requests per second
- Error rate over time
- Percentile breakdowns (50th, 75th, 95th, 99th)
- Active users over time

---

## CI/CD

Simulations run automatically on every push and pull request via **GitHub Actions**:

- Runs `BasicSimulation` on every push
- Uploads Gatling HTML report as a build artifact
- Supports triggering specific simulations via workflow dispatch

---

## Design Patterns

### Scenario Separation
All HTTP actions and flows are defined in dedicated `Scenario` classes, keeping simulations clean and focused on load injection profiles only.

### Centralized Config
All base URLs, thresholds, and load profile values are defined in `Config.java` — change once, applies everywhere.

### Threshold-based Assertions
Every simulation defines performance thresholds:
- Max response time < 60 000ms
- 95th percentile < 1 500ms
- Error rate < 1%

---

## Author

**Enes Akyel**
SDET | QA Automation Engineer
[LinkedIn](https://www.linkedin.com/in/enes-akyel-2a77a7122/) • [GitHub](https://github.com/EnesAkyel)