# NeSy-IOWF++_T Proof of Concept

NeSy-IOWF++_T (Neuro-Symbolic Inter-Organizational Workflow Framework with Temporal concession) is a proof-of-concept implementation of a neuro-symbolic AI system for automated business contract evaluation.

## Architecture Overview

The system implements a four-pole architecture:

1. **Neuro Pole** (`org.wiofc.poc.neuro`)
   - `VDCEngine`: Hyperdimensional Computing engine using 10,000-dimensional binary vectors
   - `TraceManager`: Holographic memory system implementing Trace_k = ⨁ ρ^(k-i)(E_i)

2. **Symbolic Pole** (`org.wiofc.poc.symbolic`)
   - `RobddEvaluator`: Deterministic constraint evaluation using ROBDD-inspired logic
   - `SemanticBridge`: JSON-LD to hyperdimensional vector conversion

3. **Agent Pole** (`org.wiofc.poc.agent` and `org.wiofc.poc.jadex`)
   - `ArchivisteAgent`: Core asynchronous orchestrator implementing Future<Void> with temporal concession planning (used by Jadex agents)
   - `ArchivistAgent`: Jadex BDI agent that wraps ArchivisteAgent and provides network communication
   - `InitiatorAgent`: Jadex BDI agent that generates and sends JSON-LD offers to archivist agents

4. **Communication Pole** (Jadex Platform)
   - Directory Facilitator (DF) for service discovery
   - Message passing between agents using FIPA ACL
   - Agent lifecycle management

## Key Features

- **Hyperdimensional Computing (HDC)**: Uses 10,000-bit vectors with operations:
  - Binding (XOR)
  - Bundling (majority thresholding)
  - Similarity measurement (normalized Hamming distance)
  - Permutation-based symbolic representation

- **Holographic Memory**: Implements Jefferson Burge's holographic memory model

- **Deterministic Constraint Evaluation**: ROBDD-inspired evaluator for business rules, especially temporal constraints (_T)

- **Semantic Processing**: JSON-LD parsing and conversion to HDC-processable format

- **Asynchronous Orchestration**: ArchivisteAgent implements Future<Void> interface with:
  - Temporal concession planning (graceful timeout handling)
  - End-to-end workflow: JSON-LD → triplet extraction → HDC conversion → constraint validation → memory update
  - Inspection methods for monitoring and metrics

## Dependencies

- Jadex Active Components V3 (jadex-platform-base:4.0.267)
- JSON-LD Java library (jsonld-java:0.13.6)
- Logback (logback-classic:1.4.0)
- SLF4J API (slf4j-api:2.0.9)
- JUnit 5 (junit-jupiter:5.10.0, test scope)

## Building and Running

### Prerequisites
- Java 17 JDK
- Maven 3.6+

### Build
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Run Demonstration
```bash
mvn exec:java -Dexec.mainClass="org.wiofc.poc.Main"
```

## Implementation Details

### Hyperdimensional Computing (VDCEngine)
- Fixed dimension: D=10,000
- Uses java.util.BitSet for RAM efficiency
- Leverages cardinality() method to simulate POPCNT instruction
- Core operations: bind (XOR), bundle (majority), permute (rotation), similarity

### Holographic Memory (TraceManager)
- Implements Trace_k = ⨁ ρ^(k-i)(E_i)
- Uses XOR for superposition (⨁)
- Uses circular bit permutation for ρ operator
- Configurable memory capacity

### Constraint Evaluation (RobddEvaluator)
- Temporal constraints: maximum allowed durations
- Boolean constraints: required true/false values
- Returns F_ROBDD factor (0.0 or 1.0 in current implementation)
- Designed for extension to progressive satisfaction factors

### Semantic Processing (SemanticBridge)
- Simulates RDF triplet extraction from JSON-LD
- Converts triplets to hyperdimensional vectors using HDC operations
- Provides conversion utilities between BitSet and int[]

### Asynchronous Orchestration (ArchivisteAgent)
- Implements java.util.concurrent.Future<Void> interface
- Workflow:
  1. JSON-LD parsing and triplet extraction (SemanticBridge)
  2. Triplet-to-HDC vector conversion (SemanticBridge + VDCEngine)
  3. Geometric filtering (VDCEngine bundle operation)
  4. Deterministic constraint validation (RobddEvaluator)
  5. Holographic memory update (TraceManager)
- Temporal concession: When processing exceeds timeout, constraints can be gradually relaxed
- State tracking: PENDING → PROCESSING → COMPLETED/CANCELLED/TIMEOUTED

## Usage Example

```java
// Create agent with 20-second timeout and 0.5 concession factor
ArchivisteAgent agent = new ArchivisteAgent(20, TimeUnit.SECONDS, 0.5);

// Submit JSON-LD payload for asynchronous processing
String jsonLd = "{ \"@context\": \"http://schema.org\", \"@type\": \"Offer\", \"name\": \"Test Product\", \"price\": 29.99 }";
ArchivisteAgent submitted = agent.submit(jsonLd);

// Wait for completion (or use get() with timeout)
while (!submitted.isDone()) {
    Thread.sleep(100);
}

// Check results
if (submitted.getState() == ArchivisteAgent.State.COMPLETED) {
    System.out.println("Processing successful!");
    Map<String, Object> context = submitted.getLastEvaluationContext();
    // Inspect evaluation context...
}

// Clean shutdown
agent.shutdown();
```

## Project Structure

```
src/main/java/
├── org.wiofc.poc/
│   ├── Main.java                    # Demonstration class
│   ├── agent/
│   │   └── ArchivisteAgent.java     # Asynchronous orchestrator
│   ├── neuro/
│   │   ├── VDCEngine.java           # Hyperdimensional computing engine
│   │   └── TraceManager.java        # Holographic memory manager
│   └── symbolic/
│       ├── RobddEvaluator.java      # Constraint evaluator
│       └── SemanticBridge.java      # JSON-LD to HDC bridge
└── ... (unit tests in src/test/java)
```

## Next Steps

This implementation validates the core architecture. Future enhancements could include:
- Real JSON-LD parsing using jsonld-java library
- More sophisticated ROBDD implementation with progressive satisfaction factors
- Integration with actual Jadex agent platform
- Performance optimization and benchmarking
- Extension to other constraint types beyond temporal

## License

This is a research proof-of-concept implementation.