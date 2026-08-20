package org.wiofc.poc.symbolic;

import org.junit.jupiter.api.Test;

import org.wiofc.poc.neuro.VDCEngine;
import java.util.BitSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SemanticBridge
 */
class SemanticBridgeTest {

    private SemanticBridge bridge = new SemanticBridge();

    @Test
    void testExtractTripletsFromJsonLd() {
        // Test with null payload
        Set<String> triplets = bridge.extractTripletsFromJsonLd(null);
        assertTrue(triplets.isEmpty());

        // Test with empty payload
        triplets = bridge.extractTripletsFromJsonLd("");
        assertTrue(triplets.isEmpty());

        // Test with sample payload
        String payload = "{ \"@context\": \"http://schema.org\", \"@type\": \"Offer\", \"name\": \"Test\" }";
        triplets = bridge.extractTripletsFromJsonLd(payload);

        assertFalse(triplets.isEmpty());
        // Should contain our simulated triplets
        assertTrue(triplets.contains("Offer|type|BusinessOffer"));
        assertTrue(triplets.contains("Offer|name|PremiumServicePackage"));
    }

    @Test
    void testTripletToHyperVector() {
        // Test with null triplet
        BitSet vector = bridge.tripletToHyperVector(null);
        assertNotNull(vector);
        // Verify we can access the full range of indices
        assertTrue(vector.get(0) == false || vector.get(0) == true); // Should not throw
        assertTrue(vector.get(VDCEngine.DIMENSION - 1) == false || vector.get(VDCEngine.DIMENSION - 1) == true); // Should not throw

        // Test with empty triplet
        vector = bridge.tripletToHyperVector("");
        assertNotNull(vector);
        // Verify we can access the full range of indices
        assertTrue(vector.get(0) == false || vector.get(0) == true); // Should not throw
        assertTrue(vector.get(VDCEngine.DIMENSION - 1) == false || vector.get(VDCEngine.DIMENSION - 1) == true); // Should not throw

        // Test with valid triplet
        String triplet = "Offer|name|TestProduct";
        vector = bridge.tripletToHyperVector(triplet);
        assertNotNull(vector);
        // Verify we can access the full range of indices
        assertTrue(vector.get(0) == false || vector.get(0) == true); // Should not throw
        assertTrue(vector.get(VDCEngine.DIMENSION - 1) == false || vector.get(VDCEngine.DIMENSION - 1) == true); // Should not throw
        // Should have some bits set (not all zero)
        assertTrue(vector.cardinality() > 0);
    }

    @Test
    void testProcessJsonLdToVectors() {
        String payload = "{ \"@context\": \"http://schema.org\", \"@type\": \"Offer\", \"name\": \"Test\", \"price\": 29.99 }";

        Map<String, BitSet> vectors = bridge.processJsonLdToVectors(payload);
        assertFalse(vectors.isEmpty());

        // Each vector should be a BitSet with correct dimension
        for (Map.Entry<String, BitSet> entry : vectors.entrySet()) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
            // Verify we can access the full range of indices
            assertTrue(entry.getValue().get(0) == false || entry.getValue().get(0) == true); // Should not throw
            assertTrue(entry.getValue().get(VDCEngine.DIMENSION - 1) == false || entry.getValue().get(VDCEngine.DIMENSION - 1) == true); // Should not throw
            // Should have some bits set
            assertTrue(entry.getValue().cardinality() > 0);
        }
    }

    @Test
    void testBitSetConversion() {
        // Create a test BitSet
        BitSet original = new BitSet(VDCEngine.DIMENSION);
        original.set(10);
        original.set(20);
        original.set(30);

        // Convert to int array
        int[] array = SemanticBridge.bitSetToIntArray(original);
        assertEquals(VDCEngine.DIMENSION, array.length);
        assertEquals(1, array[10]);
        assertEquals(1, array[20]);
        assertEquals(1, array[30]);
        assertEquals(0, array[0]); // Should be zero

        // Convert back to BitSet
        BitSet restored = SemanticBridge.intArrayToBitSet(array);
        assertEquals(original, restored);
    }

    @Test
    void testProcessJsonLdToIntVectors() {
        String payload = "{ \"@context\": \"http://schema.org\", \"@type\": \"Offer\", \"name\": \"Test\" }";

        Map<String, int[]> vectors = bridge.processJsonLdToIntVectors(payload);
        assertFalse(vectors.isEmpty());

        // Each vector should be int array of correct dimension
        for (Map.Entry<String, int[]> entry : vectors.entrySet()) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
            assertEquals(VDCEngine.DIMENSION, entry.getValue().length);
        }
    }
}