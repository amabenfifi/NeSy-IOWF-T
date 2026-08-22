package org.wiofc.poc.agent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageBus {
    private static final ConcurrentHashMap<String, BlockingQueue<FipaMessage>> mailboxes = new ConcurrentHashMap<>();

    public static void register(String agentName) {
        mailboxes.putIfAbsent(agentName, new LinkedBlockingQueue<>());
    }

    public static void send(FipaMessage msg) {
        if (mailboxes.containsKey(msg.receiver)) {
            mailboxes.get(msg.receiver).add(msg);
        }
    }

    public static FipaMessage receive(String agentName) throws InterruptedException {
        return mailboxes.get(agentName).take(); // Bloque le thread de l'agent jusqu'à réception d'un message
    }
}