# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

- **Build**: `mvn clean compile`
- **Run Tests**: `mvn test`
- **Run a Single Test**: `mvn test -Dtest=<TestClassName>` (e.g., `mvn test -Dtest=VDCEngineTest`)
- **Run Demonstration**: `mvn exec:java -Dexec.mainClass="org.wiofc.poc.Main"`
- **Clean Install**: `mvn clean install`

## Project Architecture

This project implements a neuro-symbolic AI system for automated business contract evaluation with a four-pole architecture:

1. **Neuro Pole** (`org.wiofc.poc.neuro`)
   - `VDCEngine`: Hyperdimensional Computing engine using 10,000-dimensional binary vectors
   - `TraceManager`: Holographic memory system implementing Trace_k = ⨁ ρ^(k-i)(E_i)

2. **Symbolic Pole** (`org.wiofc.poc.symbolic`)
   - `RobddEvaluator`: Deterministic constraint evaluation using ROBDD-inspired logic
   - `SemanticBridge`: JSON-LD to hyperdimensional vector conversion

3. **Agent Pole** (`org.wiofc.poc.agent` and `org.wiofc.poc.jadex`)
   - `ArchivisteAgent`: Core asynchronous orchestrator implementing Future<Void> with temporal concession planning
   - `ArchivistAgent`: Jadex BDI agent that wraps ArchivisteAgent and provides network communication
   - `InitiatorAgent`: Jadex BDI agent that generates and sends JSON-LD offers to archivist agents

4. **Communication Pole** (Jadex Platform)
   - Directory Facilitator (DF) for service discovery
   - Message passing between agents using FIPA ACL
   - Agent lifecycle management

### System Overview

The system operates as a peer-to-peer (Gossip) network where each node is an autonomous Jadex BDI agent. Agents make decisions using a hybrid neuro-symbolic utility equation: 
**Q = Sim(E_req, E_off) × F_ROBDD**
where:
- Sim(E_req, E_off) is the similarity between required and offered hyperdimensional vectors
- F_ROBDD is the factor from ROBDD constraint evaluation (0.0 or 1.0 in current implementation)

### Key Components

- **Hyperdimensional Computing (VDCEngine)**: Uses 10,000-bit vectors with operations: binding (XOR), bundling (majority thresholding), similarity measurement (normalized Hamming distance), and permutation-based symbolic representation.
- **Holographic Memory (TraceManager)**: Implements Jefferson Burge's holographic memory model with configurable memory capacity via Trace_k = ⨁ ρ^(k-i)(E_i).
- **Constraint Evaluation (RobddEvaluator)**: Handles temporal constraints (maximum allowed durations) and boolean constraints, returning F_ROBDD factor.
- **Semantic Processing (SemanticBridge)**: Simulates RDF triplet extraction from JSON-LD and converts triplets to hyperdimensional vectors.
- **Asynchronous Orchestration (ArchivisteAgent)**: Implements java.util.concurrent.Future<Void> interface with workflow: JSON-LD → triplet extraction → HDC conversion → constraint validation → memory update, including temporal concession planning for graceful timeout handling.

### Project Structure

```
src/main/java/
├── org.wiofc.poc/
│   ├── Main.java                    # Demonstration class
│   ├── agent/
│   │   └── ArchivisteAgent.java     # Asynchronous orchestrator
│   ├── neuro/
│   │   ├── VDCEngine.java           # Hyperdimensional computing engine
│   │   └── TraceManager.java        # Holographic memory manager
│   ├── symbolic/
│   │   ├── RobddEvaluator.java      # Constraint evaluator
│   │   └── SemanticBridge.java      # JSON-LD to HDC bridge
│   └── jadex/
│       ├── JadexPlatformLauncher.java
│       ├── ArchivistAgent.java      # Jadex BDI agent wrapping ArchivisteAgent
│       └── InitiatorAgent.java      # Jadex BDI agent generating offers
└── ... (unit tests in src/test/java)
```

### Technology Stack

- **Backend**: Java 17, Jadex Active Components V3, jsonld-java
- **Infrastructure**: Docker containers orchestrated by PowerShell scripts
- **Logging**: Logback with SLF4J API
- **Testing**: JUnit 5
- **Database**: None (distributed in-memory storage)
- **Frontend**: None (headless P2P infrastructure)

### Dependencies

- Jadex Active Components V3 (jadex-platform-base:4.0.267)
- JSON-LD Java library (jsonld-java:0.13.6)
- Logback (logback-classic:1.4.0)
- SLF4J API (slf4j-api:2.0.9)
- JUnit 5 (junit-jupiter:5.10.0, test scope)

## Common Tasks

When working with this codebase, you'll typically:

1. Modify core components in `src/main/java/org/wiofc/poc/` (neuro, symbolic, agent, jadex packages)
2. Update corresponding tests in `src/test/java/org/wiofc/poc/`
3. Build and test changes using Maven commands above
4. Run the demonstration to verify end-to-end functionality

## Notes

- The system uses Java 17 and Maven 3.6+
- Hyperdimensional vector operations are optimized using java.util.BitSet and cardinality() for POPCNT simulation
- Temporal concession planning allows gradual relaxation of constraints when processing exceeds timeout
- Design minimizes dependencies for future Edge Computing (IoT) compatibility
- Memory footprint is optimized through exclusive use of java.util.BitSet for hyperdimensional calculations
- Fault tolerance includes autonomous BDI fallback for relaxing temporal requirements during network impasses