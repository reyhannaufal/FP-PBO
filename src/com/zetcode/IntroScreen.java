package com.zetcode;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//
//public class IntroScreen  {
//	 JFrame f;
//	  
//	  
//	 IntroScreen() {
//	    f = new JFrame();
//	    JButton b = new JButton("click");//creating instance of JButton  
////	    b.setBounds(130,100,100, 40);
//	    JButton button = new JButton("Mulai Game");
//	    button.setBounds(130,100,100,40);
//	    button.addActionListener( new ActionListener()
//	    {
//	        @Override
//	        public void actionPerformed(ActionEvent e)
//	        {
//	            System.out.println("Do Something Clicked");
//	        }
//	    });
//	              
//	    f.add(button);//adding button in JFrame  
//	              
//	    f.setSize(400,500);//400 width and 500 height  
//	    f.setLayout(null);//using no layout managers  
//	    f.setVisible(true);//making the frame visible  
//	  }
//	  public static void main(String args[]) {
//	    new IntroScreen();
//	  }
//	  
//}

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class IntroScreen  extends JFrame implements ActionListener {
    private static final long serialVersionUID = 8679886300517958494L;

    private JButton button;
    private Board frame2 = null;

    public IntroScreen() {

        //frame1 stuff
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,200);
        setLayout(new FlowLayout());

        //create button
        button = new JButton("Open other frame");
        button.addActionListener(this);
        add(button);

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IntroScreen  frame = new IntroScreen();
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            if (frame2 == null)
                frame2 = new Board();
            if (!frame2.isVisible())
                frame2.setVisible(true);
        }

    }

}
