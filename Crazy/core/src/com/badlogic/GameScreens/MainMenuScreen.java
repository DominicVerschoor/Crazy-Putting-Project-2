package com.badlogic.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.Base.Animations;
import com.badlogic.Base.App;

public class MainMenuScreen implements Screen {

    //INITIALIZING VARIABLES
    Animation<TextureRegion> animation;
    float elapsed;
    Texture playButton,optionsButton,exitButton,newBotGameButton,animat,customGameButton;
    public static boolean userPlaying=false;
    public static boolean botPlaying=false;
    App game;

    public MainMenuScreen(App game) {
        this.game=game;
        playButton = new Texture("newGame.png");
        optionsButton = new Texture("options.png");
        exitButton = new Texture("Exit_Button.png");
        newBotGameButton = new Texture("NewGameBot.png");
        animation = Animations.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("menu.gif").read());
        animat = new Texture("menu.gif");
        customGameButton = new Texture("CustomMap.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        game.batch.begin();
        //game.batch.draw(animat,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.draw(animation.getKeyFrame(elapsed), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(playButton,110,330,150,70);
        game.batch.draw(optionsButton,110,270,150,65);
        game.batch.draw(exitButton,810,0,70,70);
        game.batch.draw(newBotGameButton,110,395,160,60);
        game.batch.draw(customGameButton,110,220,160,60);
         keyboardHandler();

        game.batch.end();

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

    /**
     * All keyboard actions are being handled in this method
     */
    public void keyboardHandler(){
        //NEW GAME BUTTON
        if (Gdx.input.getX() <243 && Gdx.input.getX()>126 && Gdx.input.getY()>307 && Gdx.input.getY()<352) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if(Gdx.input.isTouched()){
                this.dispose();
                userPlaying =true;
                game.setScreen(new GameField(game));
            };
        }
        //OPTIONS BUTTON
        else if (Gdx.input.getX() <228 && Gdx.input.getX()>130 && Gdx.input.getY()>372 && Gdx.input.getY()<413) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new OptionsGameScreen(game));
            };
        }
        //CUSTOM MAP BUTTON
        else if (Gdx.input.getX() <258 && Gdx.input.getX()>120 && Gdx.input.getY()>432 && Gdx.input.getY()<473) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new CustomMap(game));
            };
        }
        //BOT GAME BUTTON
        else if (Gdx.input.getX() <266 && Gdx.input.getX()>110 && Gdx.input.getY()>250 && Gdx.input.getY()<294) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if(Gdx.input.isTouched()){
                botPlaying=true;
                game.setScreen(new BotGameScreen(game));
            };
        }
        //EXIT BUTTON
        else if (Gdx.input.getX() <880 && Gdx.input.getX()>812 && Gdx.input.getY()>637 && Gdx.input.getY()<684) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if(Gdx.input.isTouched()){Gdx.app.exit();};
        }
        //DEFAULT
        else {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
    }
}
