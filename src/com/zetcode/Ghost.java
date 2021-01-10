package com.zetcode;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class Ghost {
	Graphics2D g2d;
	int x;
	int y;
	private short[] screenData;
	private int N_GHOSTS = 6;
	private final int BLOCK_SIZE = 24;
	private final int N_BLOCKS = 15;
	private boolean dying = false;
	private boolean inGame = false;
    private final int MAX_GHOSTS = 12;

	private int[] dx, dy;
	private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
	private int pacman_x, pacman_y;
	
	public Ghost(Graphics2D g2d) {
        screenData = new short[N_BLOCKS * N_BLOCKS];
        ghost_x = new int[MAX_GHOSTS];
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
		this.g2d = g2d;
		moveGhosts(g2d);
	}
	
	protected void moveGhosts(Graphics2D g2d) {

        short i;
        int pos;
        int count;

        for (i = 0; i < N_GHOSTS; i++) {
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            
            
            if(i % 3 == 0) {
                drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);
            }
            
//            if(i % 3 == 1) {
//            	drawGhost2(g2d, ghost_x[i] + 1, ghost_y[i] + 1);
//            }
//            
//            if(i % 3 == 2) {
//            	drawGhost3(g2d, ghost_x[i] + 1, ghost_y[i] + 1);
//            }

            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame) {

                dying = true;
            }
        }
    }
	
	protected void drawGhost(Graphics2D g2d, int x, int y) {
		Object obj = null;
		System.out.println(obj.getClass());
		try {
			g2d.drawImage(Assets.ghostorange, x, y, (ImageObserver) this);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
	}
	
	
}
