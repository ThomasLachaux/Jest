package com.projet.models.utils;

/**
 * Représente un element clé valeur que l'on peut retrouver dans un HashMap
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 * @param <K> clé
 * @param <V> valeur
 */
public class Entry<K, V> {
    private K key;
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Renvoie la clé
     * @return clé
     */
    public K getKey() {
        return key;
    }

    /**
     * Renvoie la valeur
     * @return valeur
     */
    public V getValue() {
        return value;
    }
}
