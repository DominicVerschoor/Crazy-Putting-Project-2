package com.badlogic.GameLogistics;

import com.badlogic.GameScreens.MainMenuScreen;
import com.badlogic.PhyiscSolvers.Euler;
import com.badlogic.PhyiscSolvers.Rk2;
import com.badlogic.PhyiscSolvers.Rk4;
import com.badlogic.GameScreens.GameField;
import com.badlogic.GameScreens.OptionsGameScreen;
import com.badlogic.GameScreens.UserGameController;

public class ShootUser implements Shoot {
    Rk2 rk2 = new Rk2();
    Rk4 rk4 = new Rk4();
    Euler e = new Euler();
    Win win = new Win();

    static public double[] velPosArray = new double[4];
    @Override
    public void shoot() {
        if (UserGameController.shoot){
            boolean accelerationButton = true;
            if (OptionsGameScreen.Euler) {
                System.out.println("Euler");
                //TODO: e.accelerationType(accelerationButton);
                e.accelerationType(accelerationButton);
                velPosArray[2] = UserGameController.xVel;
                velPosArray[3] = UserGameController.yVel;
                velPosArray = e.solve(velPosArray);
                GameField.ballCoordinatesX = (float) velPosArray[0];
                GameField.ballCoordinatesY = (float) velPosArray[1];
                UserGameController.update(velPosArray[0], velPosArray[1]);
                UserGameController.shoot = false;
                win.winCondition();
                System.out.println(velPosArray[0]);
                System.out.println(velPosArray[1]);
            }
            if (OptionsGameScreen.RK2) {
                System.out.println("RK2");
                //TODO: rk2.accelerationType(accelerationButton);
                rk2.accelerationType(accelerationButton);
                velPosArray[2] = UserGameController.xVel;
                velPosArray[3] = UserGameController.yVel;
                velPosArray = rk2.solve(velPosArray);
                GameField.ballCoordinatesX = (float) velPosArray[0];
                GameField.ballCoordinatesY = (float) velPosArray[1];
                UserGameController.update(velPosArray[0], velPosArray[1]);
                UserGameController.shoot = false;
                win.winCondition();
                System.out.println(velPosArray[0]);
                System.out.println(velPosArray[1]);
            }
            if (OptionsGameScreen.RK4) {
                System.out.println("RK4");
                //TODO: rk4.accelerationType(accelerationButton);
                rk4.accelerationType(accelerationButton);
                velPosArray[2] = UserGameController.xVel;
                velPosArray[3] = UserGameController.yVel;
                velPosArray = rk4.solve(velPosArray);
                GameField.ballCoordinatesX = (float) velPosArray[0];
                GameField.ballCoordinatesY = (float) velPosArray[1];
                UserGameController.update(velPosArray[0], velPosArray[1]);
                UserGameController.shoot = false;
                win.winCondition();
                System.out.println(velPosArray[0]);
                System.out.println(velPosArray[1]);
            }
        }
    }
}
