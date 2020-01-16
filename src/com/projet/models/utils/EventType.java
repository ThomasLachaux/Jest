package com.projet.models.utils;

/**
 * Contient tous les evenement liées au cycle de vie de l'application
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public enum EventType {
    /**
     * Au moment de la création du jeu
     */
    GAME_START,

    /**
     * Après le paramètrage du jeu
     */
    GAME_SET_UP,

    /**
     * Avant le début d'un tour
     */
    TURN_START,

    /**
     * Avant qu'un joueur choisisse une carte
     */
    CHOOSE_CARD,

    /**
     * Après qu'un joueur choisisse une carte
     */
    CHOOSED_CARD,

    /**
     * Avant qu'un joueur vole un autre joueur
     */
    STEAL_PLAYER,

    /**
     * Après qu'un joueur vole un autre joueur
     */
    STOLE_PLAYER,

    /**
     * Avant qu'un joueur vole une carte d'un autre joueur
     */
    STEAL_CARD,

    /**
     * Après qu'un joueur vole une carte d'un autre joueur
     */
    STOLE_CARD,

    /**
     * Après que les trophées aient étés distribués
     */
    TROPHEY_GIVEN,

    /**
     * Après que les scores aient été attribués
     */
    SCORE_GIVEN
}
