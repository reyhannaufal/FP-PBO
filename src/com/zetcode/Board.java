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
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener, Constants {

    /*
     * TODO: 1. Bikin menu -> Masih bug di helpScreen
     *       2. Refactor Code yang implementasi oop
     *       3. Bikin ghost yang beda-beda [Variasi] [Selesai]
     *       4. Bikin map berbeda setiap level increment [Variasi] [Selesai]
     *
     */

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;

    // Mengubah titk-titik menjadi hijau
    private final Color dotColor = new Color(255, 255, 255);
    private Color mazeColor;

    private boolean inGame = false;
    private boolean dying = false;
    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    private int N_GHOSTS = 6;
    private int pacsLeft, score;
    private int[] dx, dy;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
    private int counterLevel;
    private int showHelp;
    private int escape = 0;

    /*
     * Bisa jadi class sendiri ini controller Terus fungsi-fungsi yang ada mengikuti
     */
    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;



    private int currentSpeed = 3;
    private short[] screenData;
    private Timer timer;

    public Board() {
        Assets.init();
        initVariables();
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());

        setFocusable(true);

        setBackground(Color.DARK_GRAY);
    }

    private void initVariables() {

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
//            new Pacman();
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

        String d = "Tekan s untuk bermain";
        String f = "Tekan h untuk bantuan";

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);

        g.drawString(d, (SCREEN_SIZE - metr.stringWidth(d)) / 2, SCREEN_SIZE / 2 - metr.getHeight() / 2);
        g.drawString(f, (SCREEN_SIZE - metr.stringWidth(f)) / 2, SCREEN_SIZE / 2 + metr.getHeight() / 2);

    }

    private void showHelpScreen(Graphics g) {
        g.setColor(new Color(0, 32, 48));
        g.fillRect(10, 10, SCREEN_SIZE - 15, SCREEN_SIZE - 15);
        g.setColor(Color.white);
        g.drawRect(10, 10, SCREEN_SIZE - 15, SCREEN_SIZE - 15);

        int bx = 25, by = 60;

        String a = "Bantuan";

        Font big = new Font("Helvetica", Font.BOLD, 18);
        Font medium = new Font("Helvetica", Font.BOLD, 14);
        Font small = new Font("Helvetica", Font.PLAIN, 12);
        FontMetrics metr1 = this.getFontMetrics(big);

        g.setColor(Color.white);
        g.setFont(big);
        g.drawString(a, (SCREEN_SIZE - metr1.stringWidth(a)) / 2, 40);
        g.setFont(medium);
        g.drawString("Title Screen", bx, by);
        g.drawString("In Game", bx + 200, by);
        g.drawString("Controls", bx, by + 90);
        g.setFont(small);
        g.drawString("S - Start Single Player", bx + 10, by + 20);
        g.drawString("D - Start Co-op", bx + 10, by + 40);
        g.drawString("F - Start Versus", bx + 10, by + 60);
        g.drawString("Esc - Quit Game", bx + 210, by + 20);
        g.drawString("P - Pause Game", bx + 210, by + 40);
        g.drawString("M - Mute Game", bx + 210, by + 60);

        // if (enableLevelSelect)
        // g.drawString("L - Level Select (Press 1,2,3)", bx + 210, by + 80);
        // else
        // g.drawString("L - Level Select (Blocked)", bx + 210, by + 80);

        g.drawString("Pacman:", bx + 10, by + 110);
        g.drawString("Up Arrow - Move Up", bx + 30, by + 130);
        g.drawString("Left Arrow - Move Left", bx + 30, by + 150);
        g.drawString("Down Arrow - Move Down", bx + 30, by + 170);
        g.drawString("Right Arrow - Move Right", bx + 30, by + 190);

        g.drawString("Mrs. Pacman:", bx + 220, by + 110);
        g.drawString("W - Move Up", bx + 240, by + 130);
        g.drawString("A - Move Left", bx + 240, by + 150);
        g.drawString("S - Move Down", bx + 240, by + 170);
        g.drawString("D - Move Right", bx + 240, by + 190);

        g.drawString("Ghost 1", bx + 10, by + 220);
        g.drawString("W - Move Up", bx + 30, by + 240);
        g.drawString("A - Move Left", bx + 30, by + 260);
        g.drawString("S - Move Down", bx + 30, by + 280);
        g.drawString("D - Move Right", bx + 30, by + 300);

        g.drawString("Ghost 2", bx + 220, by + 220);
        g.drawString("I - Move Up", bx + 240, by + 240);
        g.drawString("J - Move Left", bx + 240, by + 260);
        g.drawString("K - Move Down", bx + 240, by + 280);
        g.drawString("L - Move Right", bx + 240, by + 300);

        g.drawString("Press 'h' to return...", bx + 245, by + 330);
    }

    private void drawScore(Graphics2D g) {
        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Nilai kamu: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 86, SCREEN_SIZE + 16);

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

    private void death() {
        // Case ketika mati
        pacsLeft--;

        // Jika nyawa telah habis
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
        for (i = 0; i < N_GHOSTS; i++) {
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (ghost_y[i] / BLOCK_SIZE);

                Ghost.ghostBrain(i, pos, screenData, ghost_dx, dx, dy, ghost_dy);

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

            // Check for standstill
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

        // Edit game
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

    private void doDrawing(Graphics g)  {


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
            if(escape == 1){
                g2d.dispose();
                showIntroScreen(g2d);
            }
        }
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
                if(key == KeyEvent.VK_ESCAPE || key == 'E' || key == 'e'){
                    escape = 1;
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
}