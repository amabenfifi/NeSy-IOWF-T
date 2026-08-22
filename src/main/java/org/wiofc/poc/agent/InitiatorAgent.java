package org.wiofc.poc.agent;

import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Agent
public class InitiatorAgent {

    private static final AtomicInteger agentCounter = new AtomicInteger(1);
    private final int myId = agentCounter.getAndIncrement();

    @AgentBody
    public void executeBody() {
        String nomAgent = "Initiateur_" + myId;
        MessageBus.register(nomAgent);

        System.out.println("[" + nomAgent + "] En ligne. Début de la chorégraphie B2B.");
        Random rand = new Random();

        // L'Initiateur choisit un Archiviste au hasard (routage B2B)
        int targetId = 1 + rand.nextInt(5);
        String targetArchiviste = "Archiviste_" + targetId;

        // Chaque Initiateur envoie 40 requêtes (pour conserver environ 600 requêtes
        // totales avec 15 agents)
        for (int i = 1; i <= 40; i++) {
            Map<String, Object> content = new HashMap<>();
            content.put("reqId", nomAgent + "_Req_" + i);
            content.put("maxProcessingTime", 1000L + rand.nextInt(5000));
            content.put("requireValidCertificate", rand.nextBoolean());
            content.put("semanticSubsumptionValid", rand.nextDouble() > 0.2); // Sémantique EL++

            // Envoi de la requête sur le réseau
            FipaMessage cfp = new FipaMessage(FipaMessage.Performative.CFP, nomAgent, targetArchiviste, content);
            MessageBus.send(cfp);

            try {
                // Attente de la décision du moteur ROBDD de l'Archiviste
                FipaMessage reply = MessageBus.receive(nomAgent);
                // Optionnel : Vous pouvez décommenter la ligne suivante pour voir les réponses
                // en direct
                // System.out.println("[" + nomAgent + "] Réponse de " + reply.sender + " : " +
                // reply.performative);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("[" + nomAgent + "] Terminée.");
    }
}