# NeSy-IOWF++_T : Neuro-Symbolic Inter-Organizational Workflows

Ce dépôt contient la preuve de concept (PoC) d'une architecture neuro-symbolique distribuée pour l'évaluation et la validation des workflows inter-organisationnels (B2B).

## 🧠 Architecture Scientifique

Le système hybride s'appuie sur deux piliers :

* **Moteur BDI (Belief-Desire-Intention) :** Orchestré via le framework Jadex, gérant les agents autonomes (`InitiatorAgent`, `ArchivisteAgent`).
* **Évaluateur Symbolique (ROBDD) :** Un censeur logique qui filtre le trafic réseau en validant les contraintes temporelles et booléennes strictes des transactions.

## ⚙️ Prérequis Techniques

Pour exécuter cette simulation, votre environnement doit disposer de :

* **Docker Desktop** (pour l'isolation de la plateforme multi-agents)
* **Java 17 & Maven** (pour la compilation du moteur logique)
* **Windows PowerShell** (pour l'automatisation du déploiement)

## 🚀 Déploiement et Simulation

L'intégralité de la compilation, du nettoyage et de la conteneurisation est gérée par un script automatisé. Ce script monte également un volume partagé pour extraire les métriques de performance en temps réel.

1. Clonez le dépôt :
   `git clone https://github.com/amabenfifi/NeSy-IOWF-T.git`
2. Lancez l'infrastructure depuis la racine :
   `.\deploy-wiofc.ps1`

## 📊 Télémétrie et Résultats

Lors d'un test de charge standard (100 requêtes), le système génère automatiquement un fichier `nesy_metrics.csv` dans le répertoire `/logs`. Ces données incluent :

* Le temps de traitement du moteur symbolique (en millisecondes).
* La latence réseau simulée.
* Les taux de réussite ou de rejet logique des offres (Facteur 1.0 ou 0.0).
