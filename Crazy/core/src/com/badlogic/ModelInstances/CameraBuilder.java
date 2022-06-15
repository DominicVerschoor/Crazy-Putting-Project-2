package com.badlogic.ModelInstances;

import com.badlogic.GameScreens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class CameraBuilder {

    static float cameraX=10;
    static float cameraY=10;
    static float cameraZ=10;
    static float zoom =60;
    public Camera cameraPerspective;

    public CameraBuilder() {
        cameraPerspective();
    }

    public void cameraPerspective(){
        cameraPerspective=new PerspectiveCamera(zoom, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraPerspective.position.set(cameraX,cameraZ,cameraY);
        cameraPerspective.lookAt(GameScreen.ballCoordinatesX,0, GameScreen.ballCoordinatesY);
        cameraPerspective.near = 0.1f;
        cameraPerspective.far = 300f;
    }

    public void cameraMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            cameraY--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            cameraY++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            cameraZ++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            cameraZ--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)){
            zoom++;
            if (zoom>90){
                zoom=90;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)){
            zoom--;
            if (zoom<0){
                zoom=0;
            }
        }
    }
}
