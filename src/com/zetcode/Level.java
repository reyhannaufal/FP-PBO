package com.zetcode;

public class Level {
	
	private static short points;
	private static short empty;
	
	public Level(short points, short empty) {
		this.points = points;
		this.empty = empty;
	}
	
    static short levelData1[] = {
            19, 26, 26, 26, 18, 18, 18, 18, 18, 26, 26, 18, 18, 18, 22,
            21, empty, empty, empty, 25, points, points, points, 20, empty, empty, 17, points, points, 20,
            21, empty, empty, empty, empty, 17, points, points, points, 22, empty, 17, points, points, 20,
            21, empty, empty, empty, empty, 17, points, points, points, points, 18, points, points, points, 20,
            17, 18, 18, 18, 18, points, points, points, points, points, points, points, points, points, 20,
            17, points, points, points, points, points, points, points, points, points, points, points, points, 24, 20,
            17, points, points, 24, 24, 24, points, points, points, points, points, points, 20, empty, 21,
            17, points, 20, empty, empty, empty, 17, points, points, points, points, points, 20, empty, 21,
            17, points, points, 22, empty, 19, points, points, points, points, points, points, 20, empty, 21,
            17, points, points, 20, empty, 17, points, points, 24, points, points, points, points, 26, 20,
            17, points, 24, 28, empty, 17, points, 20, empty, 25, 24, points, 20, empty, 21,
            17, 20, empty, empty, 19, points, 24, 28, empty, empty, empty, 17, 20, empty, 21,
            17, points, 18, 18, points, 20, empty, empty, empty, 19, 18, points, 20, empty, 21,
            17, points, points, points, points, points, 18, 22, empty, 17, points, points, points, 18, 20,
            25, 24, 24, 24, 24, 24, 24, 24, 26, 24, 24, 24, 24, 24, 28
    };
    
     static short levelData2[] = {
            19, 18, 18, 18, 18, 26, 26, 26, 26, 26, 18, 18, 18, 18, 22,
            17, points, points, 24, 20, empty, empty, empty, empty, empty, 17, 24, points, points, 20,
            17, points, 20, empty, 17, 18, 18, 18, 18, 18, 20, empty, 17, points, 20,
            17, 24, 28, empty, 17, points, points, points, points, points, 20, empty, 25, 24, 20,
            21, empty, empty, empty, 17, points, points, points, points, points, 20, empty, empty, empty, 21,
            25, 26, 26, 26, 24, points, 24, points, 24, points, 24, 26, 26, 26, 28,
            empty, empty, empty, empty, empty, 21, empty, 21, empty, 21, empty, empty, empty, empty, empty,
            empty, empty, empty, empty, empty, 21, empty, 21, empty, 21, empty, empty, empty, empty, empty,
            19, 26, 26, 26, 18, points, 18, points, 18, points, 18, 26, 26, 26, 22,
            21, empty, empty, empty, 17, points, points, points, points, points, 20, empty, empty, empty, 21,
            17, 18, 22, empty, 17, points, points, points, points, points, 20, empty, 19, 18, 20,
            17, points, 20, empty, 17, points, points, points, points, points, 20, empty, 17, points, 20,
            17, points, points, 18, points, 24, 24, 24, 24, 24, points, 18, points, points, 20,
            17, points, points, points, 20, empty, empty, empty, empty, empty, 17, points, points, points, 20,
            25, 24, 24, 24, 24, 26, 26, 26, 26, 26, 24, 24, 24, 24, 28
    };
}
