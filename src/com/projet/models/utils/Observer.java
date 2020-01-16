package com.projet.models.utils;

/**
 * Désigne l'observable dans le design pattern Observer
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public interface Observer {
    /**
     * Appelée lors de la mise à jour d'un observateur
     * @param eventType type de l'évenement
     * @param payload paramètres
     */
    void update(EventType eventType, Object payload);
}
