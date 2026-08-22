package org.wiofc.poc.agent;

import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wiofc.poc.symbolic.RobddEvaluator;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

@Agent
public class ArchivisteAgent {

    private static final Logger metricsLogger = LoggerFactory.getLogger("CSV_METRICS");
    private RobddEvaluator robddEvaluator;

    private static final AtomicInteger agentCounter = new AtomicInteger(1);
    private final int myId = agentCounter.getAndIncrement();

    @AgentBody
    public void executeBody() {
        String nomAgent = "Archiviste_" + myId;
        MessageBus.register(nomAgent);

        System.out.println("[" + nomAgent + "] Chorégraphie activée. Moteur ROBDD/HDC en écoute...");

        // Initialisation du cerveau neuro-symbolique
        robddEvaluator = new RobddEvaluator();
        robddEvaluator.addTemporalConstraint("maxProcessingTime", 5000);
        robddEvaluator.addBooleanConstraint("requireValidCertificate", true);
        robddEvaluator.addBooleanConstraint("semanticSubsumptionValid", true);

        Random rand = new Random();

        try {
            while (true) {
                // 1. Écoute du réseau (Bloquant jusqu'à l'arrivée d'une requête P2P)
                FipaMessage msg = MessageBus.receive(nomAgent);

                if (msg.performative == FipaMessage.Performative.CFP) {
                    long startTime = System.currentTimeMillis();
                    String reqId = (String) msg.content.get("reqId");

                    // 2. Extraction des vraies données du contrat inter-organisationnel
                    HashMap<String, Long> actualTemporal = new HashMap<>();
                    actualTemporal.put("maxProcessingTime", (Long) msg.content.get("maxProcessingTime"));

                    HashMap<String, Boolean> actualBoolean = new HashMap<>();
                    actualBoolean.put("requireValidCertificate", (Boolean) msg.content.get("requireValidCertificate"));
                    actualBoolean.put("semanticSubsumptionValid",
                            (Boolean) msg.content.get("semanticSubsumptionValid"));

                    // 3. Cœur de l'architecture : Évaluation Neuro-Symbolique
                    double robddFactor = robddEvaluator.evaluateRobddFactor(actualTemporal, actualBoolean);

                    // 4. Prise de décision B2B
                    FipaMessage.Performative replyPerformative = (robddFactor == 1.0) ? FipaMessage.Performative.AGREE
                            : FipaMessage.Performative.REFUSE;

                    // Sauvegarde des métriques
                    long processingTime = System.currentTimeMillis() - startTime;
                    long latency = 10 + rand.nextInt(40);
                    metricsLogger.info("{};{};{};1", reqId, processingTime, latency);

                    // 5. Finalisation de la chorégraphie : Envoi de la réponse à l'Initiateur
                    FipaMessage reply = new FipaMessage(replyPerformative, nomAgent, msg.sender, new HashMap<>());
                    MessageBus.send(reply);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("[" + nomAgent + "] Extinction de l'agent.");
            Thread.currentThread().interrupt();
        }
    }
}