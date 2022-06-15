package com.badlogic.GameLogistics;

import com.badlogic.GameScreens.*;
import com.badlogic.PhyiscSolvers.Euler;
import com.badlogic.PhyiscSolvers.Rk2;
import com.badlogic.PhyiscSolvers.Rk4;

public class ShootUser implements Shoot {
    Rk2 rk2 = new Rk2();
    Rk4 rk4 = new Rk4();
    Euler e = new Euler();
    Win win = new Win();

    static public double[] velPosArray = new double[4];
    @Override
    public void shoot() {
        win.setShooter(this);
        if (Controller.shoot){
            boolean accelerationButton = true;
            if (OptionsGameScreen.Euler) {
                System.out.println("Euler");
                //TODO: e.accelerationType(accelerationButton);
                e.accelerationType(accelerationButton);
                velPosArray[2] = Controller.xVel;
                velPosArray[3] = Controller.yVel;
                velPosArray = e.solve(velPosArray);
                GameScreen.ballCoordinatesX = (float) velPosArray[0];
                GameScreen.ballCoordinatesY = (float) velPosArray[1];
                Controller.update(velPosArray[0], velPosArray[1]);
                Controller.shoot = false;
                win.winCondition();
                System.out.println(velPosArray[0]);
                System.out.println(velPosArray[1]);
            }
            if (OptionsGameScreen.RK2) {
                System.out.println("RK2");
                //TODO: rk2.accelerationType(accelerationButton);
                rk2.accelerationType(accelerationButton);
                velPosArray[2] = Controller.xVel;
                velPosArray[3] = Controller.yVel;
                velPosArray = rk2.solve(velPosArray);
                GameScreen.ballCoordinatesX = (float) velPosArray[0];
                GameScreen.ballCoordinatesY = (float) velPosArray[1];
                Controller.update(velPosArray[0], velPosArray[1]);
                Controller.shoot = false;
                win.winCondition();
                System.out.println(velPosArray[0]);
                System.out.println(velPosArray[1]);
            }
            if (OptionsGameScreen.RK4) {
                System.out.println("RK4");
                //TODO: rk4.accelerationType(accelerationButton);
                rk4.accelerationType(accelerationButton);
                velPosArray[2] = Controller.xVel;
                velPosArray[3] = Controller.yVel;
                velPosArray = rk4.solve(velPosArray);
                GameScreen.ballCoordinatesX = (float) velPosArray[0];
                GameScreen.ballCoordinatesY = (float) velPosArray[1];
                Controller.update(velPosArray[0], velPosArray[1]);
                Controller.shoot = false;
                win.winCondition();
                System.out.println(velPosArray[0]);
                System.out.println(velPosArray[1]);
            }
        }
    }

    @Override
    public double[] getBall() {
        return velPosArray;
    }

    @Override
    public boolean isBot() {
        return false;
    }
}
