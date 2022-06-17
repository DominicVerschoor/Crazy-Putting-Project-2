package com.badlogic.GameLogistics;

import com.badlogic.Bots.AdvancedHillClibing;
import com.badlogic.Bots.BasicBruteForce;
import com.badlogic.GameScreens.*;
import com.badlogic.PhyiscSolvers.Rk2;
import com.badlogic.PhyiscSolvers.Rk4;

public class ShootAI implements Shoot {
    AdvancedHillClibing advancedHillClibing = new AdvancedHillClibing();
    BasicBruteForce bruteForceRule = new BasicBruteForce();
    Rk2 rk2 = new Rk2();
    Rk4 rk4 = new Rk4();
    Win win = new Win();
<<<<<<< HEAD
    private final double errorRate = 0.0;
=======
>>>>>>> f31c30cbdda80c7f4da942cbd9d38cc5be5a2a9b
    static public double[] velPosArray = new double[4];
    @Override
    public void shoot() {
        if (AIGameController.shoot){
            System.out.printf("im in shoot");
            if (OptionsGameScreen.ruleBasedBot) {
                System.out.println("Basic Bot");
                velPosArray = bruteForceRule.basicShooting(GameField.ballCoordinatesX, GameField.ballCoordinatesY);
                BotGameScreen.ballCoordinatesX = (float) velPosArray[0];
                BotGameScreen.ballCoordinatesY = (float) velPosArray[1];
                AIGameController.update(velPosArray[0],velPosArray[1]);
                AIGameController.shoot = false;
                win.winCondition();
            }
            if (OptionsGameScreen.hillClimbingBot) {
                //TODO
                System.out.println("Advanced Bot");
                velPosArray = advancedHillClibing.hillClibing(GameField.ballCoordinatesX,GameField.ballCoordinatesY);
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
