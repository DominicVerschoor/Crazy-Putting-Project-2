package com.badlogic.GameScreens;


import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameLogistics.ShootAI;
import com.badlogic.GameLogistics.TerrainInput;
import com.badlogic.ModelInstances.CameraBuilder;
import com.badlogic.ModelInstances.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.Base.App;

public class BotGameScreen implements Screen{
    public static App game;
    static FileReader fileReader= new FileReader();
    AIGameController options = new AIGameController();
    TerrainInput function = new TerrainInput();
    ShootAI shootAI = new ShootAI();
    CameraBuilder camera = new CameraBuilder();
    Map mapBuilder = new Map();

    public static boolean visible=false;
    public static float ballCoordinatesX;
    public static float ballCoordinatesY;


    public BotGameScreen(App game){
        this.game = game;
        mapBuilder.getMapCoordinates();
        mapBuilder.combineField();
        camera.cameraPerspective();
        ballCoordinatesX = fileReader.x0;
        ballCoordinatesY = fileReader.y0;
        ShootAI.velPosArray[0]=ballCoordinatesX;
        ShootAI.velPosArray[1]=ballCoordinatesY;
        if (visible==false){
            options.startFrame();
            options.frame.setVisible(true);
            visible=true;
        }
    }

    @Override
    public void show() {


    }

    /**
     * rendering objects onto screen
     * @param delta The time gap between previous and current frame
     */
    @Override
    public void render(float delta) {
        keyboardHandler();
        camera.cameraMovement();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        mapBuilder.renderCourse();
        shootAI.shoot();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
    }

    public void keyboardHandler(){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            this.dispose();
            if (visible==true){
                options.frame.setVisible(false);
                MainMenuScreen.botPlaying=false;
                AIGameController.score=0;
                AIGameController.positionX=fileReader.x0;
                AIGameController.positionY=fileReader.y0;
                AIGameController.positionZ= function.terrain(fileReader.x0, fileReader.y0);
                visible=false;
            }
            game.setScreen(new MainMenuScreen(game));
        }
    }
}
