# NeSy-IOWF++_T : Neuro-Symbolic Inter-Organizational Workflows

Ce dépôt contient la preuve de concept (PoC) d'une architecture neuro-symbolique distribuée pour l'évaluation et la validation des workflows inter-organisationnels (B2B).

## 🧠 Architecture Scientifique

Le système hybride s'appuie sur deux piliers massivement parallèles :

* **Essaim Multi-Agents (BDI) :** Orchestré via le framework Jadex, déployant un réseau décentralisé (ex: 10 `InitiatorAgent`, 5 `ArchivisteAgent`) pour simuler un trafic B2B à haute densité.
* **Évaluateur Symbolique (ROBDD) :** Un censeur logique multidimensionnel qui filtre le trafic réseau en validant simultanément :
  * **Les contraintes matérielles** (Temps de traitement maximal).
  * **Les règles de sécurité** (Validité booléenne des certificats).
  * **L'Épaisseur Sémantique ($\mathcal{EL}^{++}$)** : Validation ontologique par subsomption pour garantir la cohérence des services échangés.

## ⚙️ Prérequis Techniques

Pour exécuter cette simulation, votre environnement doit disposer de :

* **Docker Desktop** (pour l'isolation de la plateforme)
* **Java 17 & Maven** (pour la compilation du moteur logique)
* **Windows PowerShell** (pour l'automatisation du déploiement)

## 🚀 Déploiement et Simulation

L'intégralité de la compilation, du nettoyage et de la conteneurisation est gérée par un script automatisé.

1. Clonez le dépôt :
   `git clone https://github.com/amabenfifi/NeSy-IOWF-T.git`
2. Lancez l'infrastructure depuis la racine :
   `.\deploy-wiofc.ps1`

## 📊 Télémétrie et Résultats

Lors des tests de charge (ex: 600 requêtes concurrentes), le système génère un fichier `nesy_metrics.csv` dans le répertoire `/logs`. Les métriques démontrent que le censeur ROBDD maintient un temps de décision médian de **0 ms**, même lors de l'évaluation combinatoire incluant les règles de subsomption sémantique, prouvant la viabilité matérielle de l'approche neuro-symbolique.

---
