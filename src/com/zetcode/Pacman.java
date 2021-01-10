package com.zetcode;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Pacman extends JFrame {

    public Pacman() {
//        initUI();
    	
    	Board b = new Board();
    	add(b);
    	setTitle("Pacman");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(380, 425);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

    }

    private void initUI() {
    	
	    JButton button = new JButton("Mulai Game");
	    button.setBounds(130,100,100,40);
//	    add(new Board());
	    setSize(380, 425);
	    add(button);
	    button.addActionListener( new ActionListener()
	    {
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {
//	        	setLayout(new BoxLayout(, BoxLayout.X_AXIS));
	        }
	    });
	    

        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(380, 425);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
    	
        EventQueue.invokeLater(() -> {

            var ex = new Pacman();
            ex.setVisible(true);
        });
    }
}
