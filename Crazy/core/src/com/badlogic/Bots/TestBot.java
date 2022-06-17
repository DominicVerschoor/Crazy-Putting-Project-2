package com.badlogic.Bots;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameLogistics.Win;
import com.badlogic.GameScreens.Controller;
import com.badlogic.GameScreens.GameScreen;
import com.badlogic.GameScreens.OptionsGameScreen;
import com.badlogic.PhyiscSolvers.Rk2;
import com.badlogic.PhyiscSolvers.Rk4;

public class TestBot {
    static private double errorRate;
    static AdvancedHillClimbing advancedHillClimbing = new AdvancedHillClimbing();
    static BasicBruteForce bruteForceRule = new BasicBruteForce();
    static RuleBasedBot ruleBasedBot = new RuleBasedBot();
    static Rk2 rk2 = new Rk2();
    static Rk4 rk4 = new Rk4();
    static Win win = new Win();
    static final FileReader read = new FileReader();
    static OptionsGameScreen optionsGameScreen;

    public static void addError(double[] ball) {
        for (int i = 0; i < ball.length; i++) {
            ball[i] = ball[i] + errorRate;
        }
    }

    public static void main(String[] args) {
        double[] test = new double[4];
        double[] storingAnswers = new double[11];
        rk4.accelerationType(true);

        int shots = 0;
        errorRate = -0.05;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[0] = shots/50.0;


        shots = 0;
        errorRate = -0.04;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[1] = shots/50.0;

        shots = 0;
        errorRate = -0.03;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[2] = shots/50.0;

        shots = 0;
        errorRate = -0.02;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[3] = shots/50.0;

        shots = 0;
        errorRate = -0.01;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[4] = shots/50.0;


        shots = 0;
        errorRate = 0;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[5] = shots/50.0;


        shots = 0;
        errorRate = 0.01;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[6] = shots/50.0;

        shots = 0;
        errorRate = 0.02;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[7] = shots/50.0;

        shots = 0;
        errorRate = 0.03;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[8] = shots/50.0;

        shots = 0;
        errorRate = 0.04;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[9] = shots/50.0;

        shots = 0;
        errorRate = 0.05;
        for (int i = 0; i < 50; i++) {
            test[0] = read.x0;
            test[1] = read.y0;
            double distance = 100000;
            int localShots = 0;
            while (read.r < distance && localShots <= 30) {
                test = advancedHillClimbing.hillClimbing(test[0], test[1]);
                addError(test);
                test = rk4.solve(test);
                shots++;
                localShots++;
                distance = Math.sqrt(Math.pow(test[0] - read.xt, 2) + Math.pow(test[1] - read.yt, 2));
            }
        }
        storingAnswers[10] = shots/50.0;

        System.out.println();
        for (int i = 0; i < storingAnswers.length; i++) {
            System.out.println(i + "-->" +storingAnswers[i]);
        }
    }
}
