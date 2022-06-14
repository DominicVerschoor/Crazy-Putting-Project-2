package com.badlogic.GameLogistics;

import com.badlogic.Bots.AdvancedHillClibing;
import com.badlogic.Bots.BasicBruteForce;
import com.badlogic.Bots.RuleBasedBot;
import com.badlogic.GameScreens.*;
import com.badlogic.PhyiscSolvers.Rk2;
import com.badlogic.PhyiscSolvers.Rk4;

public class ShootAI implements Shoot {
    AdvancedHillClibing advancedHillClibing = new AdvancedHillClibing();
    BasicBruteForce bruteForceRule = new BasicBruteForce();
    RuleBasedBot ruleBasedBot = new RuleBasedBot();
    Rk2 rk2 = new Rk2();
    Rk4 rk4 = new Rk4();
    Win win = new Win();
    static public double[] velPosArray = new double[4];
    @Override
    public void shoot() {
        rk4.accelerationType(true);
        if (AIGameController.shoot){
            System.out.printf("im in shoot");
            if (OptionsGameScreen.basicBot) {
                System.out.println("Basic Bot");
                //velPosArray = bruteForceRule.basicShooting(BotGameScreen.ballCoordinatesX, BotGameScreen.ballCoordinatesY);
                System.out.println("x "+BotGameScreen.ballCoordinatesX + "y "+BotGameScreen.ballCoordinatesY);
                velPosArray = ruleBasedBot.shoot(BotGameScreen.ballCoordinatesX, BotGameScreen.ballCoordinatesY);
                BotGameScreen.ballCoordinatesX = (float) velPosArray[0];
                BotGameScreen.ballCoordinatesY = (float) velPosArray[1];
                AIGameController.update(velPosArray[0],velPosArray[1]);
                AIGameController.shoot = false;
                win.winCondition();
            }
            if (OptionsGameScreen.smartBot) {
                //TODO
                System.out.println("Advanced Bot");
                velPosArray = advancedHillClibing.hillClibing(BotGameScreen.ballCoordinatesX,BotGameScreen.ballCoordinatesY);
                velPosArray = rk4.solve(velPosArray);
                BotGameScreen.ballCoordinatesX =(float) velPosArray[0];
                BotGameScreen.ballCoordinatesY =(float) velPosArray[1];
                AIGameController.update(velPosArray[0],velPosArray[1]);
                AIGameController.shoot = false;
                win.winCondition();
            }
            if (OptionsGameScreen.randomBot) {
                //TODO
                System.out.println("Random Bot");
                velPosArray[2] = (Math.random()*(10))-5;
                velPosArray[3] = (Math.random()*(10))-5;
                velPosArray = rk2.solve(velPosArray);
                BotGameScreen.ballCoordinatesX = (float) velPosArray[0];
                BotGameScreen.ballCoordinatesY = (float) velPosArray[1];
                AIGameController.update(velPosArray[0],velPosArray[1]);
                AIGameController.shoot = false;
                win.winCondition();
            }
        }
    }
}
