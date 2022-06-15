package com.badlogic.ModelInstances;

import com.badlogic.GameScreens.GameScreen;
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
            modelInstanceBall = new ModelInstance(modelBall, GameScreen.ballCoordinatesX, (float) function.terrain(GameScreen.ballCoordinatesX, GameScreen.ballCoordinatesY) + 0.75f, GameScreen.ballCoordinatesY);
            modelInstanceBall.transform.setTranslation(GameScreen.ballCoordinatesX, (float) function.terrain(GameScreen.ballCoordinatesX, GameScreen.ballCoordinatesY) + 0.75f, GameScreen.ballCoordinatesY);
        }
        if (MainMenuScreen.botPlaying){
            modelInstanceBall = new ModelInstance(modelBall, GameScreen.ballCoordinatesX, (float) function.terrain(GameScreen.ballCoordinatesX, GameScreen.ballCoordinatesY) + 0.75f, GameScreen.ballCoordinatesY);
            modelInstanceBall.transform.setTranslation(GameScreen.ballCoordinatesX, (float) function.terrain(GameScreen.ballCoordinatesX, GameScreen.ballCoordinatesY) + 0.75f, GameScreen.ballCoordinatesY);
        }
    }
}
