package com.badlogic.GameLogistics;

import com.badlogic.Bots.AdvancedHillClimbing;
import com.badlogic.Bots.BasicBruteForce;
import com.badlogic.Bots.RuleBasedBot;
import com.badlogic.GameScreens.*;
import com.badlogic.PhyiscSolvers.Rk2;
import com.badlogic.PhyiscSolvers.Rk4;

public class ShootAI implements Shoot {
    AdvancedHillClimbing advancedHillClimbing = new AdvancedHillClimbing();
    BasicBruteForce bruteForceRule = new BasicBruteForce();
    RuleBasedBot ruleBasedBot = new RuleBasedBot();
    Rk2 rk2 = new Rk2();
    Rk4 rk4 = new Rk4();
    Win win = new Win();
    private final double errorRate = 0;
    static public double[] velPosArray = new double[4];

    @Override
    public void shoot() {
        rk4.accelerationType(true);
        win.setShooter(this);
        if (Controller.shoot){
            System.out.print("im in shoot");
            if (OptionsGameScreen.ruleBasedBot) {
                System.out.println("Rule Based Bot");
                System.out.println("x "+ GameScreen.ballCoordinatesX + " y "+ GameScreen.ballCoordinatesY);
                velPosArray = ruleBasedBot.shoot(GameScreen.ballCoordinatesX, GameScreen.ballCoordinatesY);
                addError(velPosArray);
                velPosArray = rk4.solve(velPosArray);
                GameScreen.ballCoordinatesX = (float) velPosArray[0];
                GameScreen.ballCoordinatesY = (float) velPosArray[1];
                Controller.update(velPosArray[0],velPosArray[1]);
                Controller.shoot = false;
                win.winCondition();
            }
            if (OptionsGameScreen.bruteForceBot) {
                System.out.println("Brute Force Bot");
                System.out.println("x "+ GameScreen.ballCoordinatesX + " y "+ GameScreen.ballCoordinatesY);
                velPosArray = bruteForceRule.basicShooting(GameScreen.ballCoordinatesX, GameScreen.ballCoordinatesY);
                GameScreen.ballCoordinatesX = (float) velPosArray[0];
                GameScreen.ballCoordinatesY = (float) velPosArray[1];
                Controller.update(velPosArray[0],velPosArray[1]);
                Controller.shoot = false;
                win.winCondition();
            }
            if (OptionsGameScreen.hillClimbing) {
                //TODO
                System.out.println("Hill Climbing Bot");
                velPosArray = advancedHillClimbing.hillClimbing(GameScreen.ballCoordinatesX, GameScreen.ballCoordinatesY);
                addError(velPosArray);
                velPosArray = rk4.solve(velPosArray);
                GameScreen.ballCoordinatesX =(float) velPosArray[0];
                GameScreen.ballCoordinatesY =(float) velPosArray[1];
                Controller.update(velPosArray[0],velPosArray[1]);
                Controller.shoot = false;
                win.winCondition();
            }
            if (OptionsGameScreen.randomBot) {
                //TODO
                System.out.println("Random Bot");
                velPosArray[2] = (Math.random()*(10))-5;
                velPosArray[3] = (Math.random()*(10))-5;
                velPosArray = rk2.solve(velPosArray);
                GameScreen.ballCoordinatesX = (float) velPosArray[0];
                GameScreen.ballCoordinatesY = (float) velPosArray[1];
                Controller.update(velPosArray[0],velPosArray[1]);
                Controller.shoot = false;
                win.winCondition();
            }
        }
    }

    @Override
    public double[] getBall() {
        return velPosArray;
    }

    @Override
    public boolean isBot() {
        return true;
    }

    public void addError(double[] ball){
        for (int i = 0; i < ball.length; i++) {
            ball[i] = ball[i] + errorRate;
        }
    }
}
