package com.zetcode;


import java.awt.*;
import java.awt.image.ImageObserver;

public class Pacman  {
    private static  int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private static int req_dx, req_dy, view_dx, view_dy;
    private static short[] screenData;
    private static int score;
    private static final int N_BLOCKS = 15;
    private static  int BLOCK_SIZE = 24;
    private Graphics2D g2d;
    private static int pacmanAnimPos = 0;

    public Pacman() {
        this.g2d = g2d;
        screenData = new short[N_BLOCKS * N_BLOCKS];
        movePacman();
    }

    public static void movePacman() {
        int pos = 0;
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

    public void drawPacman(Graphics2D g2d) {

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

    public void drawPacmanUp(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1 -> g2d.drawImage(Assets.pacman2up, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            case 2 -> g2d.drawImage(Assets.pacman3up, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            case 3 -> g2d.drawImage(Assets.pacman4up, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            default -> g2d.drawImage(Assets.pacman1, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
        }
    }

    public void drawPacmanDown(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1 -> g2d.drawImage(Assets.pacman2down, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            case 2 -> g2d.drawImage(Assets.pacman3down, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            case 3 -> g2d.drawImage(Assets.pacman4down, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            default -> g2d.drawImage(Assets.pacman1, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
        }
    }

    public void drawPacnanLeft(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1 -> g2d.drawImage(Assets.pacman2left, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            case 2 -> g2d.drawImage(Assets.pacman3left, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            case 3 -> g2d.drawImage(Assets.pacman4left, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            default -> g2d.drawImage(Assets.pacman1, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
        }
    }

    public void drawPacmanRight(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1 -> g2d.drawImage(Assets.pacman2right, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            case 2 -> g2d.drawImage(Assets.pacman3right, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            case 3 -> g2d.drawImage(Assets.pacman4right, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
            default -> g2d.drawImage(Assets.pacman1, pacman_x + 1, pacman_y + 1, (ImageObserver) this);
        }
    }
}
