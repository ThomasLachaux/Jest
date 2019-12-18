package com.projet.models.utils;

public interface Observer {
    void update(EventType eventType, Object payload);
}
