package org.wiofc.poc.symbolic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;

public class RobddEvaluatorTest {

    @Test
    public void testEvaluateRobddFactor() {
        // Préparation
        RobddEvaluator evaluator = new RobddEvaluator();
        evaluator.addTemporalConstraint("maxTime", 5000);

        HashMap<String, Long> actualTemporal = new HashMap<>();
        actualTemporal.put("maxTime", 3000L); // 3000ms est bien inférieur à 5000ms

        HashMap<String, Boolean> actualBoolean = new HashMap<>();

        // Exécution
        double result = evaluator.evaluateRobddFactor(actualTemporal, actualBoolean);

        // Vérification
        assertEquals(1.0, result, "Le facteur retourné doit être 1.0 (CONSTRAINT_PASSED)");
    }
}