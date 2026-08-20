package org.wiofc.poc.symbolic;

import java.util.HashMap;
import java.util.Map;

public class RobddEvaluator {

    public static final double CONSTRAINT_PASSED = 1.0;
    public static final double CONSTRAINT_FAILED = 0.0;

    private Map<String, Long> temporalConstraints = new HashMap<>();
    private Map<String, Boolean> booleanConstraints = new HashMap<>();

    // Méthode attendue par l'ArchivisteAgent
    public void addTemporalConstraint(String key, long value) {
        temporalConstraints.put(key, value);
    }

    // Méthode attendue par l'ArchivisteAgent
    public void addBooleanConstraint(String key, boolean value) {
        booleanConstraints.put(key, value);
    }

    // Méthode principale d'évaluation attendue par l'ArchivisteAgent
    public double evaluateRobddFactor(Map<String, Long> actualTemporal, Map<String, Boolean> actualBoolean) {
        System.out.println("[RobddEvaluator] Évaluation symbolique (ROBDD) des contraintes en cours...");

        boolean isValid = true;

        // 1. Validation temporelle
        for (Map.Entry<String, Long> entry : temporalConstraints.entrySet()) {
            if (actualTemporal.containsKey(entry.getKey())) {
                if (actualTemporal.get(entry.getKey()) > entry.getValue()) {
                    System.out.println("[RobddEvaluator] Échec temporel sur : " + entry.getKey());
                    isValid = false;
                }
            }
        }

        // 2. Validation booléenne
        for (Map.Entry<String, Boolean> entry : booleanConstraints.entrySet()) {
            if (actualBoolean.containsKey(entry.getKey())) {
                if (!actualBoolean.get(entry.getKey()).equals(entry.getValue())) {
                    System.out.println("[RobddEvaluator] Échec booléen sur : " + entry.getKey());
                    isValid = false;
                }
            }
        }

        if (isValid) {
            System.out.println("[RobddEvaluator] Validation réussie : Facteur 1.0");
            return CONSTRAINT_PASSED;
        } else {
            System.out.println("[RobddEvaluator] Échec de validation : Facteur 0.0");
            return CONSTRAINT_FAILED;
        }
    }
}