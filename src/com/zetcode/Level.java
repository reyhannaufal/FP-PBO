package com.zetcode;

public abstract class Level {
	
	private static short poins; 
	private static short empty;
	
	public Level(short poins, short empty) {
		this.poins = poins;
		this.empty = empty;
	}
	
    static short levelData1[] = {
            19, 26, 26, 26, 18, 18, 18, 18, 18, 26, 26, 18, 18, 18, 22,
            21, empty, empty, empty, 25, poins, poins, poins, 20, empty, empty, 17, poins, poins, 20,
            21, empty, empty, empty, empty, 17, poins, poins, poins, 22, empty, 17, poins, poins, 20,
            21, empty, empty, empty, empty, 17, poins, poins, poins, poins, 18, poins, poins, poins, 20,
            17, 18, 18, 18, 18, poins, poins, poins, poins, poins, poins, poins, poins, poins, 20,
            17, poins, poins, poins, poins, poins, poins, poins, poins, poins, poins, poins, poins, 24, 20,
            17, poins, poins, 24, 24, 24, poins, poins, poins, poins, poins, poins, 20, empty, 21,
            17, poins, 20, empty, empty, empty, 17, poins, poins, poins, poins, poins, 20, empty, 21,
            17, poins, poins, 22, empty, 19, poins, poins, poins, poins, poins, poins, 20, empty, 21,
            17, poins, poins, 20, empty, 17, poins, poins, 24, poins, poins, poins, poins, 26, 20,
            17, poins, 24, 28, empty, 17, poins, 20, empty, 25, 24, poins, 20, empty, 21,
            17, 20, empty, empty, 19, poins, 24, 28, empty, empty, empty, 17, 20, empty, 21,
            17, poins, 18, 18, poins, 20, empty, empty, empty, 19, 18, poins, 20, empty, 21,
            17, poins, poins, poins, poins, poins, 18, 22, empty, 17, poins, poins, poins, 18, 20,
            25, 24, 24, 24, 24, 24, 24, 24, 26, 24, 24, 24, 24, 24, 28
    };
    
     static short levelData2[] = {
            19, 18, 18, 18, 18, 26, 26, 26, 26, 26, 18, 18, 18, 18, 22,
            17, poins, poins, 24, 20, empty, empty, empty, empty, empty, 17, 24, poins, poins, 20,
            17, poins, 20, empty, 17, 18, 18, 18, 18, 18, 20, empty, 17, poins, 20,
            17, 24, 28, empty, 17, poins, poins, poins, poins, poins, 20, empty, 25, 24, 20,
            21, empty, empty, empty, 17, poins, poins, poins, poins, poins, 20, empty, empty, empty, 21,
            25, 26, 26, 26, 24, poins, 24, poins, 24, poins, 24, 26, 26, 26, 28,
            empty, empty, empty, empty, empty, 21, empty, 21, empty, 21, empty, empty, empty, empty, empty,
            empty, empty, empty, empty, empty, 21, empty, 21, empty, 21, empty, empty, empty, empty, empty,
            19, 26, 26, 26, 18, poins, 18, poins, 18, poins, 18, 26, 26, 26, 22,
            21, empty, empty, empty, 17, poins, poins, poins, poins, poins, 20, empty, empty, empty, 21,
            17, 18, 22, empty, 17, poins, poins, poins, poins, poins, 20, empty, 19, 18, 20,
            17, poins, 20, empty, 17, poins, poins, poins, poins, poins, 20, empty, 17, poins, 20,
            17, poins, poins, 18, poins, 24, 24, 24, 24, 24, poins, 18, poins, poins, 20,
            17, poins, poins, poins, 20, empty, empty, empty, empty, empty, 17, poins, poins, poins, 20,
            25, 24, 24, 24, 24, 26, 26, 26, 26, 26, 24, 24, 24, 24, 28
    };
}
