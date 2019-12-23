package com.projet.models.utils;

import java.util.concurrent.LinkedBlockingQueue;

public class Bus extends LinkedBlockingQueue<String> {


    public Bus(int capacity) {
        super(capacity);
    }

    @Override
    public String take() {
        try {
            return super.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void put(String s) {
        try {
            System.out.println("Debug Bus: " + s);
            super.put(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void put(int i) {
        put(String.valueOf(i));
    }
}
