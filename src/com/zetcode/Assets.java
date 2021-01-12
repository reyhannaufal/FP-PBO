package com.zetcode;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.io.FileNotFoundException;

public class Assets {
    protected static Image ghostorange, ghostblue, ghostpink;
    protected static Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    protected static Image pacman3up, pacman3down, pacman3left, pacman3right;
    protected static Image pacman4up, pacman4down, pacman4left, pacman4right;
    
    public static void init() throws FileNotFoundException {
    		ghostorange = new ImageIcon("src/resources/images/ghostorange.png").getImage();
			ghostblue = new ImageIcon("src/resources/images/ghostinky.png").getImage();
			ghostpink = new ImageIcon("src/resources/images/ghospink.png").getImage();
			pacman1 = new ImageIcon("src/resources/images/pacman.png").getImage();
			pacman2up = new ImageIcon("src/resources/images/up1.png").getImage();
			pacman3up = new ImageIcon("src/resources/images/up2.png").getImage();
			pacman4up = new ImageIcon("src/resources/images/up3.png").getImage();
			pacman2down = new ImageIcon("src/resources/images/down1.png").getImage();
			pacman3down = new ImageIcon("src/resources/images/down2.png").getImage();
			pacman4down = new ImageIcon("src/resources/images/down3.png").getImage();
			pacman2left = new ImageIcon("src/resources/images/left1.png").getImage();
			pacman3left = new ImageIcon("src/resources/images/left2.png").getImage();
			pacman4left = new ImageIcon("src/resources/images/left3.png").getImage();
			pacman2right = new ImageIcon("src/resources/images/right1.png").getImage();
			pacman3right = new ImageIcon("src/resources/images/right2.png").getImage();
			pacman4right = new ImageIcon("src/resources/images/right3.png").getImage();
    }
    
}
