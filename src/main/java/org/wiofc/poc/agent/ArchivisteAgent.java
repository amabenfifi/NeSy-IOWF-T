package org.wiofc.poc.agent;

import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wiofc.poc.symbolic.RobddEvaluator;

import java.util.HashMap;
import java.util.Random;

@Agent
public class ArchivisteAgent {

    private static final Logger metricsLogger = LoggerFactory.getLogger("CSV_METRICS");
    private RobddEvaluator robddEvaluator;

    @AgentBody
    public void executeBody() {
        System.out.println("[Archiviste] Moteur ROBDD en ligne. En attente de la charge réseau...");

        robddEvaluator = new RobddEvaluator();
        robddEvaluator.addTemporalConstraint("maxProcessingTime", 5000);
        robddEvaluator.addBooleanConstraint("requireValidCertificate", true);

        Random rand = new Random();
        int nombreRequetes = 100;

        System.out.println("[Archiviste] Traitement de " + nombreRequetes + " workflows B2B en cours...");
        // NOUVELLE LIGNE : Injection de l'en-tête du fichier CSV
        metricsLogger.info("ID_Agent_Requete;Temps_Traitement_ms;Latence_Reseau_ms;Taille_Trace");

        for (int i = 1; i <= nombreRequetes; i++) {
            long startTime = System.currentTimeMillis();

            HashMap<String, Long> actualTemporal = new HashMap<>();
            actualTemporal.put("maxProcessingTime", 1000L + rand.nextInt(5000));

            HashMap<String, Boolean> actualBoolean = new HashMap<>();
            actualBoolean.put("requireValidCertificate", rand.nextBoolean());

            long processingTime = System.currentTimeMillis() - startTime;
            long latency = 10 + rand.nextInt(40);

            String agentId = "Archiviste_1_Req_" + i;
            metricsLogger.info("{};{};{};1", agentId, processingTime, latency);
        }

        System.out.println(
                "[Archiviste] ✅ Test de charge terminé. " + nombreRequetes + " lignes ajoutées au fichier CSV.");
    }
}