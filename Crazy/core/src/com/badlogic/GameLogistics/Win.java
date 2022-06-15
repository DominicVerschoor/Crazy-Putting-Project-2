package com.badlogic.GameLogistics;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameScreens.GameScreen;
import com.badlogic.GameScreens.MainMenuScreen;
import com.badlogic.GameScreens.Controller;

import javax.swing.*;

public class Win {

    FileReader fileReader = new FileReader();
    Shoot shooter;
    double holeCoordinateX = fileReader.xt;
    double holeCoordinateY = fileReader.yt;
    double holeRadius = fileReader.r;
    boolean winBoolean = false;
    TerrainInput function = new TerrainInput();

    public void winCondition() {

        double distance = Math.sqrt(Math.pow(shooter.getBall()[0] - holeCoordinateX, 2) + Math.pow(shooter.getBall()[1] - holeCoordinateY, 2));
        if ((holeRadius > distance)) {
            winBoolean = true;
            System.out.println("win");

            JOptionPane.showMessageDialog(new JFrame(), "You finished with score: " + Controller.score);

            GameScreen.game.setScreen(new MainMenuScreen(GameScreen.game));
            GameScreen.ballCoordinatesX = fileReader.x0;
            GameScreen.ballCoordinatesY = fileReader.y0;
            Controller.frame.setVisible(false);
            Controller.score=0;
            GameScreen.visible=false;
            Controller.positionX=0;
            Controller.positionY=0;
            Controller.positionZ=function.terrain(Controller.positionX,Controller.positionY);
        }
    }

    public void setShooter(Shoot shooter) {
        this.shooter = shooter;
    }
}
