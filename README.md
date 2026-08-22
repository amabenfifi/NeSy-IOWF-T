# NeSy-IOWF++_T : Neuro-Symbolic Inter-Organizational Workflows

Ce dépôt contient la preuve de concept (PoC) d'une architecture neuro-symbolique distribuée pour l'évaluation et la validation des workflows inter-organisationnels (B2B).

## 🧠 Architecture Scientifique

Ce démonstrateur s'écarte des orchestrations centralisées classiques pour proposer une **véritable chorégraphie décentralisée**, reposant sur deux piliers :

1. **Chorégraphie Multi-Agents (BDI & FIPA-ACL) :**
   * Déploiement d'un essaim asynchrone (ex: 10 `InitiatorAgent`, 5 `ArchivisteAgent`) sans orchestrateur central.
   * Communication Pair-à-Pair (P2P) via un bus de messages asynchrone respectant les standards de requêtes FIPA-ACL (Call For Proposal - `CFP`).

2. **Évaluateur Symbolique (ROBDD / HDC) :**
   * Chaque agent de traitement embarque un censeur logique multidimensionnel pré-compilé.
   * Le trafic réseau P2P est filtré en validant simultanément :
       * **Les contraintes matérielles** (Temps de traitement maximal).
       * **Les règles de sécurité** (Validité booléenne des certificats).
       * **L'Épaisseur Sémantique ($\mathcal{EL}^{++}$)** : Validation ontologique par subsomption pour garantir la cohérence des services échangés.

## ⚙️ Prérequis Techniques

Pour exécuter cette simulation de chorégraphie B2B, votre environnement doit disposer de :

* **Docker Desktop** (pour l'isolation de la plateforme)
* **Java 17 & Maven** (pour la compilation du moteur logique et du bus de messages)
* **Windows PowerShell** (pour l'automatisation du déploiement)

## 🚀 Déploiement et Simulation

L'intégralité de la compilation, du nettoyage et de la conteneurisation est gérée par un script automatisé.

1. Clonez le dépôt :
   `git clone https://github.com/amabenfifi/NeSy-IOWF-T.git`
2. Lancez l'infrastructure depuis la racine :
   `.\deploy-wiofc.ps1`

## 📊 Télémétrie et Résultats

Lors des tests de charge (ex: 600 requêtes asynchrones concurrentes), le système génère un fichier de télémétrie `nesy_metrics.csv`. Les données démontrent que le censeur ROBDD maintient un temps de décision médian de **0 ms**.
Le moteur symbolique s'avère totalement imperméable à la latence réseau, traitant l'évaluation combinatoire (incluant les règles de subsomption sémantique) en un temps constant, prouvant la viabilité et la vélocité matérielle de l'approche neuro-symbolique dans un environnement décentralisé réel.
