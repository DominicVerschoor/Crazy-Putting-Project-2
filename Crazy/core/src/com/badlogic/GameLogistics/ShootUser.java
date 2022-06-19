package com.badlogic.GameLogistics;

import com.badlogic.GameScreens.MainMenuScreen;
import com.badlogic.PhyiscSolvers.Euler;
import com.badlogic.PhyiscSolvers.PredicatorCorrector;
import com.badlogic.PhyiscSolvers.Rk2;
import com.badlogic.PhyiscSolvers.Rk4;
import com.badlogic.GameScreens.GameField;
import com.badlogic.GameScreens.OptionsGameScreen;
import com.badlogic.GameScreens.UserGameController;

public class ShootUser implements Shoot {
    public static boolean accelerationButton = false;
    Rk2 rk2 = new Rk2();
    Rk4 rk4 = new Rk4();
    Euler e = new Euler();
    Win win = new Win();
    PredicatorCorrector predicatorCorrector = new PredicatorCorrector();

    static public double[] velPosArray = new double[4];
    @Override
    public void shoot() {
        if (UserGameController.shoot){
            if (OptionsGameScreen.Euler) {
                System.out.println("Euler");
                if (OptionsGameScreen.basicAcceleration){
                   accelerationButton=true;
                }
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
                if (OptionsGameScreen.basicAcceleration){
                    accelerationButton=true;
                }
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
                if (OptionsGameScreen.basicAcceleration){
                    accelerationButton=true;
                }
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
            if (OptionsGameScreen.adamSolvers){
                System.out.println("ADAM");
                if (OptionsGameScreen.basicAcceleration){
                    accelerationButton=true;
                }
                System.out.println(accelerationButton);
                predicatorCorrector.accelerationType(accelerationButton);
                velPosArray[2] = UserGameController.xVel;
                velPosArray[3] = UserGameController.yVel;
                velPosArray = predicatorCorrector.solve(velPosArray);
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
