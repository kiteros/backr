package com.example.jules.sesl20;

/**
 * Created by jules on 27/03/2017.
 */

public class variables {
    public static int[] Head10 = new int[10];
    public static int[] bacs = new int[10];
    public static int TenScore = 0;

    public static int[] Head20 = new int[20];
    public static int[] bacs20 = new int[20];
    public static int TwentyScore = 0;

    public static int[] Head50 = new int[10];
    public static int[] bacs50 = new int[10];
    public static int FiftyScore = 0;

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

    public static int getHead20(int index) {
        return Head20[index];
    }

    public static void setHead20(int index, int value) {
        Head20[index] = value;
    }

    public static int getBacs20(int index) {
        return bacs20[index];
    }

    public static void setBacs20(int index, int value) {
        bacs20[index] = value;
    }

    public static void setTwentyScore(int score){
        TwentyScore = score;
    }

    public static int getTwentyScore() {
        return TwentyScore;
    }

    public static int getHead50(int index) {
        return Head50[index];
    }

    public static void setHead50(int index, int value) {
        Head50[index] = value;
    }

    public static int getBacs50(int index) {
        return bacs50[index];
    }

    public static void setBacs50(int index, int value) {
        bacs50[index] = value;
    }

    public static void setFiftyScore(int score){
        FiftyScore = score;
    }

    public static int getFiftyScore() {
        return FiftyScore;
    }

    public static void flush(){
        FiftyScore = 0;
        TenScore = 0;
        TwentyScore = 0;
        bacs = new int[10];
        bacs20 = new int[20];
        bacs50 = new int[50];
        Head10 = new int[10];
        Head20 = new int[20];
        Head50 = new int[50];
    }
}
