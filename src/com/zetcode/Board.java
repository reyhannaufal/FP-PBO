package com.zetcode;

/*The goal of the game is to 
collect all the points in the maze and avoid the 
ghosts. 
*/

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.io.File;
import java.io.IOException;


public class Board extends AbstractBoard implements ActionListener, Constants, LineListener{
    private static final long serialVersionUID = 1L;
    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;


    private final Color dotColor = new Color(255, 255, 255);
    private Color mazeColor;

    private boolean inGame = false;
    private boolean dying = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PAC_ANIM_DELAY = 2;
    private final int MAX_GHOSTS = 12;

    private int pacAnimCount = PAC_ANIM_DELAY; // bisa diganti jadi 2
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    private int N_GHOSTS = 6;
    private int pacsLeft, score;
    private int[] dx, dy;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
    private int counterLevel;
    private int showHelp;


    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;

    private final int[] validSpeeds = { 1, 2, 3, 4, 6, 8 };

    private int currentSpeed = 3;
    private short[] screenData;
    private Timer timer;
    
    boolean playCompleted;
    String audioFilePath = "./src/resources/audio/beginning.wav";
    

    public Board() {
        super();
        try {
    	Assets.init();
        } catch (Exception e) {
        	System.err.println(e);
        }
        initVariables();
        initBoard();
    }

    protected void initBoard() {

        addKeyListener(new TAdapter());

        setFocusable(true);

        setBackground(Color.DARK_GRAY);
    }

    protected void initVariables() {

        screenData = new short[N_BLOCKS * N_BLOCKS];
        mazeColor = new Color(5, 5, 100);
        d = new Dimension(400, 400);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void doAnim() {

        pacAnimCount--;

        if (pacAnimCount <= 0) {
            pacAnimCount = PAC_ANIM_DELAY;
            pacmanAnimPos = pacmanAnimPos + pacAnimDir;

            int PACMAN_ANIM_COUNT = 4;
            if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
                pacAnimDir = -pacAnimDir;
            }
        }
    }

    private void playGame(Graphics2D g2d) {
        if (dying) {
            death();
        } else {
            movePacman();

            drawPacman(g2d);
            moveGhosts(g2d);

            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g) {
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, SCREEN_SIZE / 2 - 50, SCREEN_SIZE - 100, 90);
        g.setColor(Color.white);
        g.drawRect(50, SCREEN_SIZE / 2 - 50, SCREEN_SIZE - 100, 90);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        
        StringBuilder str = new StringBuilder();
        str.append("Tekan s untuk bermain");
        
        g.drawString(str.toString(), (SCREEN_SIZE - metr.stringWidth(str.toString())) / 2, SCREEN_SIZE / 2 - metr.getHeight() / 2);
        
        str.delete(0, str.length());
        
        str.append("Tekan h untuk bantuan");
        g.drawString(str.toString(), (SCREEN_SIZE - metr.stringWidth(str.toString())) / 2, SCREEN_SIZE / 2 + metr.getHeight() / 2);
        
        str.delete(0, str.length());
         
    }

    public void showHelpScreen(Graphics g) {
    	g.setColor(new Color(0, 32, 48));
        g.fillRect(10, 10, SCREEN_SIZE - 15, SCREEN_SIZE - 15);
        g.setColor(Color.white);
        g.drawRect(10, 10, SCREEN_SIZE - 15, SCREEN_SIZE - 15);

        int bx = 25, by = 60;

        String a = "Menu Bantuan";

        Font big = new Font("Helvetica", Font.BOLD, 18);
        Font medium = new Font("Helvetica", Font.BOLD, 14);
        Font small = new Font("Helvetica", Font.PLAIN, 12);
        FontMetrics metr1 = this.getFontMetrics(big);

        g.setColor(Color.white);
        g.setFont(big);
        g.drawString(a, (SCREEN_SIZE - metr1.stringWidth(a)) / 2, 40);
        g.setFont(medium);
        g.drawString("Menu Utama", bx, by);
        g.drawString("Pada Game ", bx + 200, by);
        g.drawString("Kontrol", bx, by + 90);
        g.setFont(small);
        g.drawString("S - Untuk Memulai Permainan", bx + 10, by + 20);
        g.drawString("H - Pindah Ke Menu Bantuan", bx + 10, by + 40);

        g.drawString("Esc - Untuk Keluar", bx + 210, by + 20);

        g.drawString("Pacman:", bx + 10, by + 110);
        g.drawString("Panah Atas - Jalan Keatas", bx + 30, by + 130);
        g.drawString("Panah Kiri - Jalan Kekiri", bx + 30, by + 150);
        g.drawString("Panah Bawah - Jalan Kebawah", bx + 30, by + 170);
        g.drawString("Panah Kana - Jalan Kekanan", bx + 30, by + 190);



        g.drawString("Tekan E untuk kembali.", bx + 100, by + 250);
    }

    private void drawScore(Graphics2D g) {
        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Nilai kamu: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 60, SCREEN_SIZE + 16);

        for (i = 0; i < pacsLeft; i++) {
        	g.drawImage(Assets.pacman3left, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    private void checkMaze() {
        short i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }

            int maxSpeed = 6;
            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

    protected void death() {

        pacsLeft--;

        if (pacsLeft == 0) {
            inGame = false;
            counterLevel = 0;
            score = 0;
        }

        continueLevel();
    }

    private void moveGhosts(Graphics2D g2d) {

        short i;
        int pos;
        int count;

        for (i = 0; i < N_GHOSTS; i++) {
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (ghost_y[i] / BLOCK_SIZE);

                Ghost.GhostBrain(i, pos, screenData, ghost_dx, dx, dy, ghost_dy);
            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            if (i % 3 == 0) {
                drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);
            }

            if (i % 3 == 1) {
                drawGhost2(g2d, ghost_x[i] + 1, ghost_y[i] + 1);
            }

            if (i % 3 == 2) {
                drawGhost3(g2d, ghost_x[i] + 1, ghost_y[i] + 1);
            }

            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12) && pacman_y > (ghost_y[i] - 12)
                    && pacman_y < (ghost_y[i] + 12) && inGame) {

                dying = true;
            }
        }
    }



    private void drawGhost(Graphics2D g2d, int x, int y) {

        g2d.drawImage(Assets.ghostorange, x, y, this);
    }

    private void drawGhost2(Graphics2D g2d, int x, int y) {

        g2d.drawImage(Assets.ghostblue, x, y, this);
    }

    private void drawGhost3(Graphics2D g2d, int x, int y) {

        g2d.drawImage(Assets.ghostpink, x, y, this);
    }

    private void movePacman() {

        int pos;
        short ch;

        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
            pacmand_x = req_dx;
            pacmand_y = req_dy;
            view_dx = pacmand_x;
            view_dy = pacmand_y;
        }

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (pacman_y / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score++;
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0) || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                    view_dx = pacmand_x;
                    view_dy = pacmand_y;
                }
            }


            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }
        int PACMAN_SPEED = 6;
        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
    }

    private void drawPacman(Graphics2D g2d) {

        if (view_dx == -1) {
            drawPacnanLeft(g2d);
        } else if (view_dx == 1) {
            drawPacmanRight(g2d);
        } else if (view_dy == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }

    private void drawPacmanUp(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1 -> g2d.drawImage(Assets.pacman2up, pacman_x + 1, pacman_y + 1, this);
            case 2 -> g2d.drawImage(Assets.pacman3up, pacman_x + 1, pacman_y + 1, this);
            case 3 -> g2d.drawImage(Assets.pacman4up, pacman_x + 1, pacman_y + 1, this);
            default -> g2d.drawImage(Assets.pacman1, pacman_x + 1, pacman_y + 1, this);
        }
    }

    private void drawPacmanDown(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1 -> g2d.drawImage(Assets.pacman2down, pacman_x + 1, pacman_y + 1, this);
            case 2 -> g2d.drawImage(Assets.pacman3down, pacman_x + 1, pacman_y + 1, this);
            case 3 -> g2d.drawImage(Assets.pacman4down, pacman_x + 1, pacman_y + 1, this);
            default -> g2d.drawImage(Assets.pacman1, pacman_x + 1, pacman_y + 1, this);
        }
    }

    private void drawPacnanLeft(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1 -> g2d.drawImage(Assets.pacman2left, pacman_x + 1, pacman_y + 1, this);
            case 2 -> g2d.drawImage(Assets.pacman3left, pacman_x + 1, pacman_y + 1, this);
            case 3 -> g2d.drawImage(Assets.pacman4left, pacman_x + 1, pacman_y + 1, this);
            default -> g2d.drawImage(Assets.pacman1, pacman_x + 1, pacman_y + 1, this);
        }
    }

    private void drawPacmanRight(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1 -> g2d.drawImage(Assets.pacman2right, pacman_x + 1, pacman_y + 1, this);
            case 2 -> g2d.drawImage(Assets.pacman3right, pacman_x + 1, pacman_y + 1, this);
            case 3 -> g2d.drawImage(Assets.pacman4right, pacman_x + 1, pacman_y + 1, this);
            default -> g2d.drawImage(Assets.pacman1, pacman_x + 1, pacman_y + 1, this);
        }
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
    }

    private void initGame() {
        counterLevel = 0;
        pacsLeft = 3;
        initLevel();
        N_GHOSTS = 2;
        currentSpeed = 3;
    }

    private void initLevel() {
    	int i;
        if (counterLevel % 2 == 1) {
            for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
                screenData[i] = Level.levelData1[i];
            }
        } else if (counterLevel % 2 == 0) {
            for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
                screenData[i] = Level.levelData2[i];
            }
            if(counterLevel == 0 && inGame == true) {
          	File audioFile = new File(audioFilePath);
                
                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
         
                    AudioFormat format = audioStream.getFormat();
         
                    DataLine.Info info = new DataLine.Info(Clip.class, format);
         
                    Clip audioClip = (Clip) AudioSystem.getLine(info);
         
                    audioClip.addLineListener((LineListener) this);
         
                    audioClip.open(audioStream);
                     
                    audioClip.start();
                    
                    while (!playCompleted) {
                        // wait for the playback completes
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                     
                } catch (UnsupportedAudioFileException ex) {
                    System.out.println("The specified audio file is not supported.");
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    System.out.println("Audio line for playing back is unavailable.");
                    ex.printStackTrace();
                } catch (IOException ex) {
                    System.out.println("Error playing the audio file.");
                    ex.printStackTrace();
                }     
            }
        }

        counterLevel += 1;
        continueLevel();
    }

    private void continueLevel() {

        short i;
        int dx = 1;
        int random;

        for (i = 0; i < N_GHOSTS; i++) {

            ghost_y[i] = 4 * BLOCK_SIZE;
            ghost_x[i] = 4 * BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }

        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 11 * BLOCK_SIZE;
        pacmand_x = 0;
        pacmand_y = 0;
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
        dying = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);
        doAnim();

        if (inGame) {
            playGame(g2d);
                
        } else {
            showIntroScreen(g2d);
        }
        
        if(showHelp == 1) {
        	showHelpScreen(g2d);
        	
        }
        
        g2d.dispose();
        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
        	
            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                    score = 0;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }              
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
                
                if (key == 'h' || key == 'H') {
                	showHelp = 1;
                }
                
                if (key == 'e' || key == 'E') {
                	showHelp = 0;
                }
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT || key == Event.UP || key == Event.DOWN) {
                req_dx = 0;
                req_dy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

	@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();
        
		if (type == LineEvent.Type.STOP) {
            playCompleted = true;
		}
	}


}