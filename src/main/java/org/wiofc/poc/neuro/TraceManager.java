package org.wiofc.poc.neuro;

import java.util.BitSet;
import java.util.Deque;
import java.util.ArrayDeque;

/**
 * Gestionnaire de mémoire holographique (Holographic Trace Manager)
 * Implémente une mémoire holographique suivant l'équation :
 * 𝐓𝐫𝐚𝐜𝐞_𝐤 = ⨁ ρ^(𝐤-𝐢)(𝐄_𝐢)
 *
 * Où :
 * - Trace_k est la trace holographique à l'étape k
 * - ⨁ représente l'opération de superposition (XOR dans notre implémentation HDC)
 * - ρ est l'opérateur de permutation (décalage cyclique)
 * - E_i représente une expérience ou épisode mémorisé à l'étape i
 * - k-i est le délai depuis l'expérience
 */
public class TraceManager {

    /** Référence au moteur VDC pour les opérations hyperdimensionnelles */
    private final VDCEngine vdcEngine;

    /** Facteur de permutation ρ (décalage de 1 position par défaut) */
    private static final int RHO = 1;

    /** Capacité maximale de la mémoire holographique (peut être configurée) */
    private int capacity;

    /** Historique des entrées (expériences) */
    private Deque<ExperiencedEntry> history;

    /**
     * Entrée dans l'historique holographique
     */
    private static class ExperiencedEntry {
        final int step;
        final BitSet experience;

        ExperiencedEntry(int step, BitSet experience) {
            this.step = step;
            this.experience = experience;
        }
    }

    /**
     * Constructeur avec capacité illimitée (par défaut)
     */
    public TraceManager() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Constructeur avec capacité spécifiée
     * @param capacity nombre maximum d'expériences à conserver
     */
    public TraceManager(int capacity) {
        this.vdcEngine = new VDCEngine();
        this.capacity = Math.max(1, capacity);
        this.history = new ArrayDeque<>();
    }

    /**
     * Ajoute une expérience à la mémoire holographique
     * @param experience le vecteur hyperdimensionnel représentant l'expérience
     * @param step l'étape temporelle associée à cette expérience
     */
    public void addExperience(BitSet experience, int step) {
        // Limiter la taille de l'historique
        if (history.size() >= capacity) {
            history.removeFirst(); // Supprimer la plus ancienne entrée
        }

        history.addLast(new ExperiencedEntry(step, experience));
    }

    /**
     * Calcule la trace holographique pour l'étape spécifiée
     * 𝐓𝐫𝐚𝐜𝐞_𝐤 = ⨁ ρ^(𝐤-𝐢)(𝐄_𝐢)
     * @param currentStep l'étape k pour laquelle calculer la trace
     * @return la trace holographique à l'étape k
     */
    public BitSet getTrace(int currentStep) {
        if (history.isEmpty()) {
            return new BitSet(VDCEngine.DIMENSION);
        }

        BitSet trace = new BitSet(VDCEngine.DIMENSION);

        // Sommation (XOR) de toutes les expériences permutées selon leur âge
        for (ExperiencedEntry entry : history) {
            int delay = currentStep - entry.step;
            if (delay < 0) {
                // Ne pas inclure les expériences futures
                continue;
            }

            // Calculer ρ^(k-i) : application répétée de la permutation ρ
            BitSet permutedExperience = applyRhoPower(entry.experience, delay);

            // Superposition : XOR (dans l'espace HDC bipolar, cela correspond à l'addition)
            trace.xor(permutedExperience);
        }

        return trace;
    }

    /**
     * Applique l'opérateur de permutation ρ élevé à la puissance n
     * ρ^n signifie appliquer la permutation ρ n fois de suite
     * @param vector le vecteur à permuter
     * @param power la puissance à laquelle élever ρ (nombre d'applications)
     * @return le vecteur après application de ρ^n
     */
    private BitSet applyRhoPower(BitSet vector, int power) {
        if (power == 0) {
            return (BitSet) vector.clone();
        }

        BitSet result = (BitSet) vector.clone();
        // Appliquer la permutation ρ 'power' fois
        // Comme ρ est un décalage de RHO positions, ρ^n est un décalage de n*RHO positions
        int totalShift = (power * RHO) % VDCEngine.DIMENSION;
        if (totalShift < 0) {
            totalShift += VDCEngine.DIMENSION;
        }
        return VDCEngine.permute(result, totalShift);
    }

    /**
     * Réinitialise la mémoire holographique (supprime toutes les expériences)
     */
    public void clear() {
        history.clear();
    }

    /**
     * Obtient le nombre d'expériences actuellement stockées
     * @return le nombre d'expériences dans l'historique
     */
    public int size() {
        return history.size();
    }

    /**
     * Obtient la capacité maximale de la mémoire
     * @return la capacité maximale
     */
    public int getCapacity() {
        return capacity;
    }
}