package org.wiofc.poc.agent;

import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;

@Agent
public class InitiatorAgent {

    @AgentBody
    public void executeBody() {
        System.out.println("[Initiateur] Agent démarré. Prêt à émettre des offres.");
    }
}