package com.projet.controllers;

import com.projet.models.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController {

    public ButtonController(JButton button, int value) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    App.getInstance().getQueue().put(String.valueOf(value));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
