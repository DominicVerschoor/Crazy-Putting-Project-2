package com.badlogic.GameScreens;
import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameLogistics.ShootUser;
import com.badlogic.GameLogistics.TerrainInput;
import com.badlogic.ModelInstances.CameraBuilder;
import com.badlogic.ModelInstances.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.Base.App;

public class GameField implements Screen{


    public static App game;
    static FileReader fileReader= new FileReader();
    UserGameController options = new UserGameController();
    TerrainInput function = new TerrainInput();
    ShootUser shootUser = new ShootUser();
    CameraBuilder camera = new CameraBuilder();
    Map mapBuilder = new Map();

    public static boolean visible=false;
    public static float ballCoordinatesX;
    public static float ballCoordinatesY;



    public GameField(App game){
        this.game = game;
        mapBuilder.getMapCoordinates();
        mapBuilder.combineField();
        camera.cameraPerspective();
        ballCoordinatesX = fileReader.x0;
        ballCoordinatesY = fileReader.y0;
        ShootUser.velPosArray[0]=ballCoordinatesX;
        ShootUser.velPosArray[1]=ballCoordinatesY;
        if (!visible){
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
        shootUser.shoot();
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
            if (visible){
                options.frame.setVisible(false);
                UserGameController.score=0;
                MainMenuScreen.userPlaying=false;
                UserGameController.positionX=fileReader.x0;
                UserGameController.positionY=fileReader.y0;
                UserGameController.positionZ= function.terrain(fileReader.x0, fileReader.y0);
            }
            game.setScreen(new MainMenuScreen(game));
        }
    }

}
