package com.badlogic.GameLogistics;

import com.badlogic.Base.App;
import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameScreens.GameField;
import com.badlogic.GameScreens.MainMenuScreen;
import com.badlogic.GameScreens.UserGameController;

import javax.swing.*;

public class Win {

    FileReader fileReader = new FileReader();
    double holeCoordinateX = fileReader.xt;
    double holeCoordinateY = fileReader.yt;
    double holeRadius = fileReader.r;
    boolean winBoolean = false;
    UserGameController options = new UserGameController();
    TerrainInput function = new TerrainInput();
    public boolean winCondition(){

        double distance = Math.sqrt(Math.pow(ShootUser.velPosArray[0] - holeCoordinateX, 2) + Math.pow(ShootUser.velPosArray[1] - holeCoordinateY, 2));
        if ((holeRadius >  distance)) {
            winBoolean=true;
            System.out.println("win");

            JOptionPane.showMessageDialog(new JFrame(),"You finished with score: " + UserGameController.score);

            GameField.game.setScreen(new MainMenuScreen(GameField.game));
            GameField.ballCoordinatesX=fileReader.x0;
            GameField.ballCoordinatesY=fileReader.y0;
            options.frame.setVisible(false);
            UserGameController.score=0;
            GameField.visible=false;
            UserGameController.positionX=0;
            UserGameController.positionY=0;
            UserGameController.positionZ=function.terrain(UserGameController.positionX,UserGameController.positionY);
            return winBoolean;
        }
        return winBoolean;
    }
}
