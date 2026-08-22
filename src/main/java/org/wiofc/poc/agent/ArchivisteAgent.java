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

        robddEvaluator = new RobddEvaluator();
        robddEvaluator.addTemporalConstraint("maxProcessingTime", 5000);
        robddEvaluator.addBooleanConstraint("requireValidCertificate", true);

        Random rand = new Random();
        int nombreRequetes = 100;

        for (int i = 1; i <= nombreRequetes; i++) {
            long startTime = System.currentTimeMillis();

            HashMap<String, Long> actualTemporal = new HashMap<>();
            actualTemporal.put("maxProcessingTime", 1000L + rand.nextInt(5000));

            HashMap<String, Boolean> actualBoolean = new HashMap<>();
            actualBoolean.put("requireValidCertificate", rand.nextBoolean());

            double robddFactor = robddEvaluator.evaluateRobddFactor(actualTemporal, actualBoolean);

            long processingTime = System.currentTimeMillis() - startTime;
            long latency = 10 + rand.nextInt(40);

            String agentId = nomAgent + "_Req_" + i;
            metricsLogger.info("{};{};{};1", agentId, processingTime, latency);
        }

        System.out.println("[" + nomAgent + "] ✅ Test de charge terminé.");
    }
}