package com.example.game_15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game_15 {

    public static class Coord {
        public int x;
        public int y;

        private Coord() {
            this(-1, -1);
        }

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isValid() {
            return x > -1 && y > -1 && x < 4 && y < 4;
        }
    }

    public enum DebugMode {
        ON(true),
        OFF(false);

        private final boolean status;

        DebugMode(boolean status) {
            this.status = status;
        }

        public boolean getStatus() {
            return status;
        }
    }


    private int[][] gameField = new int[4][4];
    private int[][] winFields = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    private int moveCount = 0;
    private long startTime = System.currentTimeMillis();

    public Game_15(DebugMode debugMode) {
        if (debugMode == DebugMode.OFF) {
            initializeGame();
        } else if (debugMode == DebugMode.ON) {
            int[][] orderedField = {
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 0, 15}
            };

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    gameField[i][j] = orderedField[i][j];
                }
            }
        }
    }


    private void initializeGame() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameField[i][j] = numbers.get(i * 4 + j);
            }
        }
    }

    public int getValue(int x, int y) {
        return gameField[y][x];
    }

    public Coord findValue(int val) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (gameField[j][i] == val) {
                    return new Coord(j, i);
                }
            }
        }
        return new Coord();
    }

    public Coord go(int value) {
        Coord zero = findValue(0);
        Coord coord = findValue(value);

        if (Math.abs(zero.x - coord.x) + Math.abs(zero.y - coord.y) != 1) {
            return new Coord();
        }
        gameField[zero.x][zero.y] = value;
        gameField[coord.x][coord.y] = 0;

        moveCount++;
        return zero;
    }

    public boolean checkWin() {
        for (int i = 0; i < 4; i++) {
            if (!Arrays.equals(gameField[i], winFields[i])) {
                return false;
            }
        }
        return true;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
