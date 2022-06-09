package com.badlogic.ModelInstances;

import com.badlogic.GameScreens.BotGameScreen;
import com.badlogic.GameScreens.GameField;
import com.badlogic.GameLogistics.TerrainInput;
import com.badlogic.GameScreens.MainMenuScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class GolfBall {
    private final float ballRadius=0.2f;
    private ModelBuilder modelBuilderBall;
    private Model modelBall;
    public ModelInstance modelInstanceBall;
    private TerrainInput function = new TerrainInput();

    public void createGolfBall() {
        modelBuilderBall = new ModelBuilder();
        modelBall = modelBuilderBall.createCapsule(ballRadius,0.4f,10,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        if (MainMenuScreen.userPlaying) {
            modelInstanceBall = new ModelInstance(modelBall, GameField.ballCoordinatesX, (float) function.terrain(GameField.ballCoordinatesX, GameField.ballCoordinatesY) + 0.75f, GameField.ballCoordinatesY);
            modelInstanceBall.transform.setTranslation(GameField.ballCoordinatesX, (float) function.terrain(GameField.ballCoordinatesX, GameField.ballCoordinatesY) + 0.75f, GameField.ballCoordinatesY);
        }
        if (MainMenuScreen.botPlaying){
            modelInstanceBall = new ModelInstance(modelBall, BotGameScreen.ballCoordinatesX, (float) function.terrain(BotGameScreen.ballCoordinatesX, BotGameScreen.ballCoordinatesY) + 0.75f, BotGameScreen.ballCoordinatesY);
            modelInstanceBall.transform.setTranslation(BotGameScreen.ballCoordinatesX, (float) function.terrain(BotGameScreen.ballCoordinatesX, BotGameScreen.ballCoordinatesY) + 0.75f, BotGameScreen.ballCoordinatesY);
        }
    }
}
