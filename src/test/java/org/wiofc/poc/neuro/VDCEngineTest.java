package org.wiofc.poc.neuro;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for VDCEngine
 */
class VDCEngineTest {

    @Test
    void testGenerateRandomVector() {
        BitSet vector = VDCEngine.generateRandomVector();
        assertNotNull(vector);
        // Verify we can access the full range of indices
        assertTrue(vector.get(0) == false || vector.get(0) == true); // Should not throw
        assertTrue(vector.get(VDCEngine.DIMENSION - 1) == false || vector.get(VDCEngine.DIMENSION - 1) == true); // Should not throw
        // Should have approximately half the bits set (uniform distribution)
        int cardinality = vector.cardinality();
        assertTrue(cardinality > VDCEngine.DIMENSION / 3);
        assertTrue(cardinality < 2 * VDCEngine.DIMENSION / 3);
    }

    @Test
    void testGenerateVectorWithSeed() {
        long seed = 12345L;
        BitSet vector1 = VDCEngine.generateVector(seed);
        BitSet vector2 = VDCEngine.generateVector(seed);

        // Same seed should produce same vector
        assertEquals(vector1, vector2);
        assertNotNull(vector1);
        // Verify we can access the full range of indices
        assertTrue(vector1.get(0) == false || vector1.get(0) == true); // Should not throw
        assertTrue(vector1.get(VDCEngine.DIMENSION - 1) == false || vector1.get(VDCEngine.DIMENSION - 1) == true); // Should not throw
    }

    @Test
    void testBindOperation() {
        BitSet a = new BitSet(VDCEngine.DIMENSION);
        BitSet b = new BitSet(VDCEngine.DIMENSION);

        // Set some bits
        a.set(10);
        a.set(20);
        b.set(20);
        b.set(30);

        BitSet result = VDCEngine.bind(a, b);

        // XOR: bits that are set in exactly one of the inputs
        // Bit 10: set in a (1), not in b (0) -> 1 XOR 0 = 1 -> should be set
        assertTrue(result.get(10));
        // Bit 20: set in a (1), set in b (1) -> 1 XOR 1 = 0 -> should NOT be set
        assertFalse(result.get(20));
        // Bit 30: not in a (0), set in b (1) -> 0 XOR 1 = 1 -> should be set
        assertTrue(result.get(30));
    }

    @Test
    void testBundleOperation() {
        BitSet v1 = new BitSet(VDCEngine.DIMENSION);
        BitSet v2 = new BitSet(VDCEngine.DIMENSION);
        BitSet v3 = new BitSet(VDCEngine.DIMENSION);

        // Set bit 10 in all vectors
        v1.set(10);
        v2.set(10);
        v3.set(10);

        // Set bit 20 in only two vectors (majority)
        v1.set(20);
        v2.set(20);
        // v3 does not have bit 20 set

        // Set bit 30 in only one vector (minority)
        v1.set(30);
        // v2 and v3 do not have bit 30 set

        BitSet[] vectors = {v1, v2, v3};
        BitSet result = VDCEngine.bundle(vectors);

        // Bit 10: set in all 3 vectors (> 3/2 = 1.5) -> should be set
        assertTrue(result.get(10));

        // Bit 20: set in 2 vectors (> 1.5) -> should be set
        assertTrue(result.get(20));

        // Bit 30: set in 1 vector (< 1.5) -> should NOT be set
        assertFalse(result.get(30));
    }

    @Test
    void testHammingDistance() {
        BitSet a = new BitSet(VDCEngine.DIMENSION);
        BitSet b = new BitSet(VDCEngine.DIMENSION);

        // Identical vectors
        a.set(10);
        a.set(20);
        b.set(10);
        b.set(20);

        assertEquals(0, VDCEngine.hammingDistance(a, b));

        // Different vectors
        b.clear();
        b.set(30); // Different bit

        assertEquals(3, VDCEngine.hammingDistance(a, b)); // 10, 20, 30 are different
    }

    @Test
    void testSimilarity() {
        BitSet a = new BitSet(VDCEngine.DIMENSION);
        BitSet b = new BitSet(VDCEngine.DIMENSION);

        // Identical vectors -> similarity should be 1.0
        a.set(10);
        a.set(20);
        b.set(10);
        b.set(20);

        assertEquals(1.0, VDCEngine.similarity(a, b), 0.0001);

        // Completely different vectors (no overlap) -> similarity should be lower
        b.clear();
        b.set(30);
        b.set(40);

        double similarity = VDCEngine.similarity(a, b);
        assertTrue(similarity < 1.0);
        assertTrue(similarity >= 0.0);

        // Both empty vectors -> similarity should be 1.0
        a.clear();
        b.clear();
        assertEquals(1.0, VDCEngine.similarity(a, b), 0.0001);
    }

    @Test
    void testPermuteOperation() {
        BitSet vector = new BitSet(VDCEngine.DIMENSION);
        vector.set(0);
        vector.set(1);
        vector.set(2);

        // Permute by 2 positions
        BitSet result = VDCEngine.permute(vector, 2);

        // Original bits at 0,1,2 should now be at 2,3,4
        assertFalse(result.get(0));
        assertFalse(result.get(1));
        assertTrue(result.get(2));
        assertTrue(result.get(3));
        assertTrue(result.get(4));

        // Permute by 0 should return identical vector
        BitSet result0 = VDCEngine.permute(vector, 0);
        assertEquals(vector, result0);

        // Test negative permutation (should wrap around)
        BitSet resultNeg = VDCEngine.permute(vector, -1);
        // Bit 0 should move to position DIMENSION-1
        // Bit 1 should move to position 0
        // Bit 2 should move to position 1
        assertTrue(resultNeg.get(VDCEngine.DIMENSION - 1)); // from bit 0
        assertTrue(resultNeg.get(0)); // from bit 1
        assertTrue(resultNeg.get(1)); // from bit 2
    }
}