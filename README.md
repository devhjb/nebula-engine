# Nebula Engine

A lightweight business rules engine that diffuses like nebula.

## What It Is

Nebula Engine is a developer-oriented, rule-driven engine designed to extract complex business decision logic
from hard-coded application flows and execute it in a declarative, composable, and maintainable manner.

It is delivered as an embedded SDK / library and integrates seamlessly into existing applications.

## When to Use

Nebula Engine is a good fit when:

- Business rules are complex, cross-cutting, and change frequently
- `if-else` and hard-coded logic have become difficult to maintain
- Introducing a heavyweight rules or workflow engine (e.g. Drools, BPMN) is not justified
- You need fast iteration on business decisions without restructuring core business code

## When Not to Use

Nebula Engine is **not** intended for:

- BPM / BPMN workflow modeling
- Visual or human-driven process orchestration
- Long-running or distributed transaction coordination
- Fully standalone middleware deployment

## Key Characteristics

- **Lightweight abstraction** – focuses on decision routing, not complex inference engines
- **Low intrusion** – works as an embedded library without imposing architectural constraints
- **Composable rules** – business logic is expressed as reusable decision units
- **Dynamic updates** – rules can be updated without restarting the application

## Quick Start

> Coming soon.

## Documentation

- [Architecture Brief](./docs/architecture/01-Architecture%20Definition-V1.md) – Project positioning, core concepts, and runtime model
- [User Guide](./docs/user-guide.md) – Usage patterns and integration guide (coming soon)

## Project Status

Nebula Engine is currently in **early development**.
APIs and core abstractions may evolve as the conceptual model is validated through real-world use cases.
