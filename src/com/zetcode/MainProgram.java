package com.zetcode;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainProgram extends JFrame {

    public MainProgram() {
    	Board b = new Board();
    	add(b);
    	setTitle("Pacman");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(380, 425);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var ex = new MainProgram();
            ex.setVisible(true);
        });
    }
}
