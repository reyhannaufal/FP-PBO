package com.zetcode;

public class Constants {
	protected static int BLOCK_SIZE;
	protected static int N_BLOCKS;
	protected static int SCREEN_SIZE;
	protected static int PAC_ANIM_DELAY;
	protected static int PACMAN_ANIM_COUNT;
	protected static int MAX_GHOSTS;
	protected static int PACMAN_SPEED;
	
	public static void init() {
		 BLOCK_SIZE = 24;
		 N_BLOCKS = 15;
		 SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
		 PAC_ANIM_DELAY = 2;
		 PACMAN_ANIM_COUNT = 4;
		 MAX_GHOSTS = 12;
		 PACMAN_SPEED = 6;
	}
	
	/* fungsi yang menggunakan nilai konstan
	 * Gimana caranya nilai-nilai konstan ini jadi variabel global
	 * terus bisa dipake dari mana aja?
	 * 
	 * initVariables()
	 * doAnim()
	 * showIntoScreen
	 * drawScore
	 * checkMaze
	 * moveGhost
	 * drawMaze
	 * initLevel
	 * continueLevel
	 * test
	 */
}
