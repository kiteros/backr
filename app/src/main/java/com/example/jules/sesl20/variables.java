package com.example.jules.sesl20;

/**
 * Created by jules on 27/03/2017.
 */

public class variables {
    public static int[] Head10 = new int[10];
    public static int[] bacs = new int[10];
    public static int TenScore = 0;

    public static int getHead10(int index) {
        return Head10[index];
    }

    public static void setHead10(int index, int value) {
        Head10[index] = value;
    }

    public static int getBacs(int index) {
        return bacs[index];
    }

    public static void setBacs(int index, int value) {
        bacs[index] = value;
    }

    public static void setTenScore(int score){
        TenScore = score;
    }

    public static int getTenScore() {
        return TenScore;
    }
}
