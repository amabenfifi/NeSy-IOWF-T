package org.wiofc.poc.neuro;

import java.util.BitSet;

/**
 * Moteur d'Informatique Hyperdimensionnelle (Hyperdimensional Computing - HDC)
 * Implémente les opérations de base du calcul hyperdimensionnel avec une dimension fixe D=10000.
 * Utilise java.util.BitSet pour une empreinte RAM optimisée et la méthode cardinality()
 * pour tirer parti de l'instruction POPCNT (calcul de la distance de Hamming).
 */
public class VDCEngine {

    /** Dimension fixe de l'espace hyperdimensionnel */
    public static final int DIMENSION = 10000;

    /**
     * Génère un vecteur hyperdimensionnel aléatoire (seed aléatoire)
     * @return un BitSet représentant un vecteur HD aléatoire
     */
    public static BitSet generateRandomVector() {
        BitSet vector = new BitSet(DIMENSION);
        // Définir approximativement la moitié des bits à 1 pour distribution uniforme
        for (int i = 0; i < DIMENSION / 2; i++) {
            int index = (int) (Math.random() * DIMENSION);
            vector.set(index);
        }
        return vector;
    }

    /**
     * Génère un vecteur hyperdimensionnel à partir d'un seed (pour reproductibilité)
     * @param seed la graine pour l'aléatoire
     * @return un BitSet représentant un vecteur HD déterministe
     */
    public static BitSet generateVector(long seed) {
        BitSet vector = new BitSet(DIMENSION);
        java.util.Random random = new java.util.Random(seed);
        // Définir approximativement la moitié des bits à 1
        for (int i = 0; i < DIMENSION / 2; i++) {
            int index = random.nextInt(DIMENSION);
            vector.set(index);
        }
        return vector;
    }

    /**
     * Opération de liaison (binding) : XOR de deux vecteurs
     * @param a premier vecteur
     * @param b second vecteur
     * @return résultat de la liaison (a XOR b)
     */
    public static BitSet bind(BitSet a, BitSet b) {
        BitSet result = (BitSet) a.clone();
        result.xor(b);
        return result;
    }

    /**
     * Opération de groupement (bundling) : opération majoritaire sur plusieurs vecteurs
     * @param vectors tableau de vecteurs à agréger
     * @return vecteur résultant du groupement (1 si majorité des vecteurs ont 1 à cette position)
     */
    public static BitSet bundle(BitSet[] vectors) {
        if (vectors == null || vectors.length == 0) {
            return new BitSet(DIMENSION);
        }

        BitSet result = new BitSet(DIMENSION);
        int[] counts = new int[DIMENSION];

        // Compter le nombre de 1 à chaque position
        for (BitSet vector : vectors) {
            for (int i = vector.nextSetBit(0); i >= 0; i = vector.nextSetBit(i + 1)) {
                if (i >= DIMENSION) break;
                counts[i]++;
            }
        }

        // Définir un bit à 1 si la majorité des vecteurs ont 1 à cette position
        int threshold = vectors.length / 2;
        for (int i = 0; i < DIMENSION; i++) {
            if (counts[i] > threshold) {
                result.set(i);
            }
        }

        return result;
    }

    /**
     * Calcule la distance de Hamming entre deux vecteurs
     * Utilise la méthode cardinality() qui profite de l'instruction POPCNT sur les architectures modernes
     * @param a premier vecteur
     * @param b second vecteur
     * @return la distance de Hamming (nombre de positions différentes)
     */
    public static int hammingDistance(BitSet a, BitSet b) {
        BitSet xor = (BitSet) a.clone();
        xor.xor(b);
        return xor.cardinality(); // Utilise POPCNT si disponible
    }

    /**
     * Calcule la similarité entre deux vecteurs (inverse de la distance de Hamming normalisée)
     * @param a premier vecteur
     * @param b second vecteur
     * @return similarité dans [0,1] où 1 signifie identité parfaite
     */
    public static double similarity(BitSet a, BitSet b) {
        int distance = hammingDistance(a, b);
        return 1.0 - ((double) distance / DIMENSION);
    }

    /**
     * Permute un vecteur hyperdimensionnel (rotation des bits)
     * @param vector le vecteur à permuter
     * @param positions nombre de positions de rotation (peut être négatif)
     * @return le vecteur permuté
     */
    public static BitSet permute(BitSet vector, int positions) {
        BitSet result = new BitSet(DIMENSION);
        int normalizedPos = ((positions % DIMENSION) + DIMENSION) % DIMENSION; // Gère les négatifs

        if (normalizedPos == 0) {
            return (BitSet) vector.clone();
        }

        for (int i = vector.nextSetBit(0); i >= 0; i = vector.nextSetBit(i + 1)) {
            if (i >= DIMENSION) break;
            int newPos = (i + normalizedPos) % DIMENSION;
            result.set(newPos);
        }

        return result;
    }

    /**
     * Inverse un vecteur hyperdimensionnel (pour certaines opérations HDC)
     * Dans le HDC bipolar, l'inverse est souvent le même que l'original (autoinverse)
     * @param vector le vecteur à inverser
     * @return l'inverse du vecteur (dans ce cas, le vecteur lui-même pour un pseudo-inverse simple)
     */
    public static BitSet invert(BitSet vector) {
        // Pour un vecteur bipolar {-1,+1} représenté par {0,1}, l'inverse est complexe
        // Dans de nombreux modèles HDC, on considère que l'inverse approximatif est la permutation
        // Ici, on retourne une version simplifiée : permutation de 1 position comme approximation
        return permute(vector, 1);
    }
}