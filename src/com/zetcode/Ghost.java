package com.zetcode;

public class Ghost {

    static void GhostBrain(short i, int pos, short[] screenData, int[] ghost_dx, int[] dx, int[] dy, int[] ghost_dy) {
        int count;
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

}
