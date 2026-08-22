package org.wiofc.poc.agent;

import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wiofc.poc.symbolic.RobddEvaluator;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Agent
public class ArchivisteAgent {

    private static final Logger metricsLogger = LoggerFactory.getLogger("CSV_METRICS");
    private RobddEvaluator robddEvaluator;

    // Générateur d'ID 100% Java (contournement de l'API Jadex)
    private static final AtomicInteger agentCounter = new AtomicInteger(1);
    private final int myId = agentCounter.getAndIncrement();

    @AgentBody
    public void executeBody() {
        String nomAgent = "Archiviste_" + myId;
        System.out.println("[" + nomAgent + "] Moteur ROBDD en ligne. En attente de la charge réseau...");

        // --- 1. CONFIGURATION DU MOTEUR ROBDD ---
        robddEvaluator = new RobddEvaluator();
        robddEvaluator.addTemporalConstraint("maxProcessingTime", 5000);
        robddEvaluator.addBooleanConstraint("requireValidCertificate", true);

        // NOUVEAU : Le service doit être validé par l'ontologie (Subsomption)
        robddEvaluator.addBooleanConstraint("semanticSubsumptionValid", true);

        Random rand = new Random();
        int nombreRequetes = 100;

        // --- 2. TRAITEMENT DE LA CHARGE RESEAU ---
        for (int i = 1; i <= nombreRequetes; i++) {
            long startTime = System.currentTimeMillis();

            // Injection des paramètres temporels
            HashMap<String, Long> actualTemporal = new HashMap<>();
            actualTemporal.put("maxProcessingTime", 1000L + rand.nextInt(5000));

            // Injection des paramètres booléens (Certificat + Sémantique)
            HashMap<String, Boolean> actualBoolean = new HashMap<>();
            actualBoolean.put("requireValidCertificate", rand.nextBoolean());

            // NOUVEAU : Simulation du pont sémantique (Taux de validation de 80%)
            boolean isSubsumed = rand.nextDouble() > 0.2;
            actualBoolean.put("semanticSubsumptionValid", isSubsumed);

            // Évaluation de l'arbre de décision
            double robddFactor = robddEvaluator.evaluateRobddFactor(actualTemporal, actualBoolean);

            // Calcul des métriques
            long processingTime = System.currentTimeMillis() - startTime;
            long latency = 10 + rand.nextInt(40);

            // Écriture dans le fichier CSV
            String agentId = nomAgent + "_Req_" + i;
            metricsLogger.info("{};{};{};1", agentId, processingTime, latency);
        }

        System.out.println("[" + nomAgent + "] ✅ Test de charge terminé.");
    }
}