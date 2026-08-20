package org.wiofc.poc.neuro;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TraceManager
 */
class TraceManagerTest {

    private TraceManager traceManager;

    @BeforeEach
    void setUp() {
        traceManager = new TraceManager(10); // Small capacity for testing
    }

    @AfterEach
    void tearDown() {
        if (traceManager != null) {
            traceManager.clear();
        }
    }

    @Test
    void testAddExperienceAndSize() {
        assertEquals(0, traceManager.size());

        BitSet exp1 = new BitSet(VDCEngine.DIMENSION);
        exp1.set(10);
        traceManager.addExperience(exp1, 0);

        assertEquals(1, traceManager.size());

        BitSet exp2 = new BitSet(VDCEngine.DIMENSION);
        exp2.set(20);
        traceManager.addExperience(exp2, 1);

        assertEquals(2, traceManager.size());
    }

    @Test
    void testCapacityLimit() {
        TraceManager smallManager = new TraceManager(2); // Capacity of 2

        // Add three experiences
        for (int i = 0; i < 3; i++) {
            BitSet exp = new BitSet(VDCEngine.DIMENSION);
            exp.set(i);
            smallManager.addExperience(exp, i);
        }

        // Should only have 2 experiences (capacity limit)
        assertEquals(2, smallManager.size());
        // The oldest (first) should have been removed
    }

    @Test
    void testGetTraceEmpty() {
        BitSet trace = traceManager.getTrace(0);
        assertNotNull(trace);
        // Verify we can access the full range of indices without exception
        assertTrue(trace.get(0) == false || trace.get(0) == true); // Should not throw
        assertTrue(trace.get(VDCEngine.DIMENSION - 1) == false || trace.get(VDCEngine.DIMENSION - 1) == true); // Should not throw
        assertEquals(0, trace.cardinality()); // Should be all zeros
    }

    @Test
    void testGetTraceWithExperiences() {
        // Add an experience at step 0
        BitSet exp = new BitSet(VDCEngine.DIMENSION);
        exp.set(10);
        exp.set(20);
        traceManager.addExperience(exp, 0);

        // Get trace at step 0 (should include the experience with rho^0 = identity)
        BitSet traceAt0 = traceManager.getTrace(0);
        assertTrue(traceAt0.get(10));
        assertTrue(traceAt0.get(20));

        // Get trace at step 1 (should include the experience with rho^1 = one permutation)
        BitSet traceAt1 = traceManager.getTrace(1);
        // With RHO=1, the bits should have moved one position
        assertTrue(traceAt1.get(11)); // 10 + 1
        assertTrue(traceAt1.get(21)); // 20 + 1
        assertFalse(traceAt1.get(10)); // Original position should be 0 after XOR with shifted version
        assertFalse(traceAt1.get(20));
    }

    @Test
    void testClear() {
        BitSet exp = new BitSet(VDCEngine.DIMENSION);
        exp.set(10);
        traceManager.addExperience(exp, 0);

        assertEquals(1, traceManager.size());

        traceManager.clear();

        assertEquals(0, traceManager.size());
        BitSet trace = traceManager.getTrace(0);
        assertEquals(0, trace.cardinality()); // Should be empty
    }
}