package org.wiofc.poc;

import jadex.base.PlatformConfiguration;
import jadex.base.Starter;
import jadex.bridge.IExternalAccess;
import jadex.bridge.service.search.SServiceProvider;
import jadex.bridge.service.types.cms.IComponentManagementService;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Démarrage de l'Architecture NeSy-IOWF++_T ===");

        try {
            PlatformConfiguration config = PlatformConfiguration.getDefaultNoGui();

            System.out.println("[Main] Initialisation du framework Jadex...");
            IExternalAccess platform = Starter.createPlatform(config).get(); // Ici on garde le .get() car la plateforme
                                                                             // DOIT être démarrée avant de continuer
            System.out.println("[Main] Plateforme active.");

            IComponentManagementService cms = SServiceProvider.getService(platform, IComponentManagementService.class)
                    .get();

            System.out.println("[Main] Déploiement de l'Agent Archiviste...");
            // SUPPRESSION DU .get() -> Déploiement asynchrone ("Fire and Forget")
            cms.createComponent("Archiviste_1", "org.wiofc.poc.agent.ArchivisteAgent.class", null);

            System.out.println("[Main] Déploiement de l'Agent Initiateur...");
            // SUPPRESSION DU .get() -> Déploiement asynchrone ("Fire and Forget")
            cms.createComponent("Initiateur_1", "org.wiofc.poc.agent.InitiatorAgent.class", null);

            System.out.println("=== Tous les agents sont déployés sur le réseau ===");

        } catch (Exception e) {
            System.err.println("[Main] Erreur critique lors du lancement : " + e.getMessage());
            e.printStackTrace();
        }
    }
}