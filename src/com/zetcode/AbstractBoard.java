package com.zetcode;

import javax.swing.*;

public abstract class AbstractBoard extends JPanel {
     public void Board(){
         try {
             Assets.init();
         } catch (Exception e) {
             System.err.println(e);
         }
         initVariables();
         initBoard();
     }
     abstract void initBoard();
     abstract void death();
     abstract void initVariables();
}
