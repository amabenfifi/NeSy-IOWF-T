package org.wiofc.poc.agent;

import java.util.Map;

public class FipaMessage {
    public enum Performative {
        CFP, AGREE, REFUSE
    }

    public final Performative performative;
    public final String sender;
    public final String receiver;
    public final Map<String, Object> content;

    public FipaMessage(Performative performative, String sender, String receiver, Map<String, Object> content) {
        this.performative = performative;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
}