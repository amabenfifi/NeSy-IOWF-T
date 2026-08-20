package org.wiofc.poc.symbolic;

import org.wiofc.poc.neuro.VDCEngine;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Pont sémantique et ontologique (Semantic and Ontological Bridge)
 * Traduit les concepts depuis les payloads JSON-LD en vecteurs hyperdimensionnels
 * pour le traitement par le moteur VDC (filtrage géométrique - Rejet Rapide).
 *
 * Ce composant simule l'extraction de triplets RDF depuis du JSON-LD
 * et leur conversion en représentations hyperdimensionnelles utilisables
 * par le moteur de calcul hyperdimensionnel.
 */
public class SemanticBridge {

    /** Référence au moteur VDC pour la conversion en vecteurs hyperdimensionnels */
    private final VDCEngine vdcEngine;

    /**
     * Constructeur
     */
    public SemanticBridge() {
        this.vdcEngine = new VDCEngine();
    }

    /**
     * Simule l'extraction de triplets depuis un payload JSON-LD
     * Dans une implémentation réelle, ceci utiliserait une bibliothèque JSON-LD
     * comme jsonld-java ou Apache Jena pour parser correctement le format.
     *
     * @param jsonLdPayload le contenu JSON-LD sous forme de chaîne
     * @return un ensemble de triplets sous forme de chaînes "sujet|prédicat|objet"
     */
    public Set<String> extractTripletsFromJsonLd(String jsonLdPayload) {
        Set<String> triplets = new HashSet<>();

        // Simulation d'extraction de triplets depuis du JSON-LD
        // Dans un vrai système, on parserait le JSON-LD pour extraire les triplets RDF

        if (jsonLdPayload == null || jsonLdPayload.isEmpty()) {
            return triplets;
        }

        // Recherche simple de patterns indiquant des données structurées
        // Ceci est une simulation très basique - à remplacer par un vrai parser JSON-LD

        // Exemple de ce qu'on pourrait trouver dans un payload JSON-LD typique:
        // {"@context": "http://schema.org", "@type": "Offer", "name": "Product X", "price": 29.99}

        // Pour cette simulation, on extrait quelques triplets factices
        // dans un format simplifié "sujet|prédicat|objet"

        // Triplet 1: type de l'offre
        triplets.add("Offer|type|BusinessOffer");

        // Triplet 2: nom du produit/service
        triplets.add("Offer|name|PremiumServicePackage");

        // Triplet 3: prix (si présent dans le payload)
        if (jsonLdPayload.contains("price") || jsonLdPayload.contains("prix")) {
            triplets.add("Offer|price|29.99|EUR");
        }

        // Triplet 4: délai de livraison (contraintes temporelles)
        if (jsonLdPayload.contains("deliveryTime") || jsonLdPayload.contains("delaiLivraison")) {
            triplets.add("Offer|deliveryTime|48|hours");
        }

        // Triplet 5: durée de validité
        if (jsonLdPayload.contains("validityPeriod") || jsonLdPayload.contains("validite")) {
            triplets.add("Offer|validityPeriod|30|days");
        }

        return triplets;
    }

    /**
     * Convertit un triplet en vecteur hyperdimensionnel
     * Utilise le moteur VDC pour créer une représentation distribuée du concept
     *
     * @param triplet le triplet sous forme "sujet|prédicat|objet|[unité]"
     * @return un vecteur hyperdimensionnel représentant le triplet (sous forme de BitSet)
     */
    public BitSet tripletToHyperVector(String triplet) {
        if (triplet == null || triplet.isEmpty()) {
            return new java.util.BitSet(VDCEngine.DIMENSION);
        }

        // Générer un vecteur de base à partir du hash du triplet
        int baseSeed = triplet.hashCode();
        java.util.BitSet baseVector = VDCEngine.generateVector(baseSeed);

        // Appliquer des permutations basée sur les composants du triplet
        // pour créer une représentation riche et distinctive
        java.util.BitSet result = (java.util.BitSet) baseVector.clone();
        String[] parts = triplet.split("\\|");

        // Chaque composant du triplet influence la permutation finale
        for (String part : parts) {
            if (!part.isEmpty()) {
                int componentHash = part.hashCode();
                int shiftAmount = Math.abs(componentHash) % VDCEngine.DIMENSION;
                result = VDCEngine.permute(result, shiftAmount);
            }
        }

        return result;
    }

    /**
     * Convertit un BitSet en tableau d'entiers pour faciliter l'utilisation
     * dans des contextes où un int[] est préféré
     *
     * @param bitSet le vecteur hyperdimensionnel sous forme de BitSet
     * @return un tableau d'entiers représentant le même vecteur
     */
    public static int[] bitSetToIntArray(java.util.BitSet bitSet) {
        int[] vector = new int[VDCEngine.DIMENSION];
        for (int i = 0; i < VDCEngine.DIMENSION; i++) {
            vector[i] = bitSet.get(i) ? 1 : 0;
        }
        return vector;
    }

    /**
     * Convertit un tableau d'entiers en BitSet
     *
     * @param intArray un tableau d'entiers représentant un vecteur hyperdimensionnel
     * @return un BitSet représentant le même vecteur
     */
    public static java.util.BitSet intArrayToBitSet(int[] intArray) {
        java.util.BitSet bitSet = new java.util.BitSet(VDCEngine.DIMENSION);
        if (intArray != null) {
            for (int i = 0; i < Math.min(intArray.length, VDCEngine.DIMENSION); i++) {
                if (intArray[i] == 1) {
                    bitSet.set(i);
                }
            }
        }
        return bitSet;
    }

    /**
     * Traite un payload JSON-LD complet : extraction des triplets + conversion en vecteurs HD
     *
     * @param jsonLdPayload le contenu JSON-LD à traiter
     * @return un map associant chaque triplet à son vecteur hyperdimensionnel (sous forme de BitSet)
     */
    public Map<String, java.util.BitSet> processJsonLdToVectors(String jsonLdPayload) {
        Map<String, java.util.BitSet> tripletVectors = new HashMap<>();
        Set<String> triplets = extractTripletsFromJsonLd(jsonLdPayload);

        for (String triplet : triplets) {
            java.util.BitSet vector = tripletToHyperVector(triplet);
            tripletVectors.put(triplet, vector);
        }

        return tripletVectors;
    }

    /**
     * Traite un payload JSON-LD complet et retourne les vecteurs sous forme de tableaux d'entiers
     *
     * @param jsonLdPayload le contenu JSON-LD à traiter
     * @return un map associant chaque triplet à son vecteur hyperdimensionnel (sous forme de int[])
     */
    public Map<String, int[]> processJsonLdToIntVectors(String jsonLdPayload) {
        Map<String, int[]> tripletVectors = new HashMap<>();
        Map<String, java.util.BitSet> bitSetVectors = processJsonLdToVectors(jsonLdPayload);

        for (Map.Entry<String, java.util.BitSet> entry : bitSetVectors.entrySet()) {
            tripletVectors.put(entry.getKey(), bitSetToIntArray(entry.getValue()));
        }

        return tripletVectors;
    }
}