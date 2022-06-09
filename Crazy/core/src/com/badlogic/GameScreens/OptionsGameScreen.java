package com.badlogic.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.Base.Animations;
import com.badlogic.Base.App;

public class OptionsGameScreen implements Screen {
    Animation<TextureRegion> animation;
    Texture musicLogo,exitButton,onButton,offButton,mathSolverButton,RK2Button,RK4Button,EulerButton,
            advancedBotButton,basicBotButton,botsLogo,randomBotButton;
    App game;
    public static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Melody.wav"));
    float elapsed;
    public static boolean RK2 =false;
    public static boolean RK4 =false;
    public static boolean Euler =true;
    public static boolean randomBot = false;
    public static boolean basicBot = true;
    public static boolean smartBot = false;

    public OptionsGameScreen(App game) {
        this.game=game;
        //TEXTURES
        musicLogo = new Texture("Music.png");
        onButton = new Texture("ON.png");
        offButton = new Texture("Off.png");
        exitButton = new Texture("Exit_Button.png");
        mathSolverButton = new Texture("MathSolvers.png");
        RK2Button = new Texture("RK2.png");
        RK4Button = new Texture("RK4.png");
        EulerButton = new Texture("Euler.png");
        advancedBotButton = new Texture("AdvancedBot.png");
        basicBotButton = new Texture("BasicBot.png");
        botsLogo = new Texture("Bots.png");
        randomBotButton = new Texture("RandomBot.png");


        animation = Animations.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("menu.gif").read());

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


        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        // DRAWING BUTTONS
        game.batch.draw(animation.getKeyFrame(elapsed), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(exitButton,810,0,70,70);
        game.batch.draw(musicLogo,305,390);
        game.batch.draw(onButton,300,350);
        game.batch.draw(offButton,350,350);
        game.batch.draw(mathSolverButton,275,300,170,55);
        game.batch.draw(EulerButton,240,255,100,50);
        game.batch.draw(RK2Button,320,255,100,50);
        game.batch.draw(RK4Button,385,255,100,50);
        game.batch.draw(botsLogo,270,205,190,55);
        game.batch.draw(advancedBotButton,228,160,150,50);
        game.batch.draw(basicBotButton,370,160,150,50);
        game.batch.draw(randomBotButton,290,120,150,50);

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

    }

    /**
     * All keyboard actions are being handled in this method
     */
    public void keyboardHandler() {
        //EXIT BUTTON EVENT HANDLING
        if (Gdx.input.getX() < 880 && Gdx.input.getX() > 812 && Gdx.input.getY() > 637 && Gdx.input.getY() < 684) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainMenuScreen(game));
            }
            ;
            //ON BUTTON EVENT HANDLING
        } else if (Gdx.input.getX() < 340 && Gdx.input.getX() > 300 && Gdx.input.getY() > 310 && Gdx.input.getY() < 346) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                System.out.println("im over");
                if (!gameMusic.isPlaying()) {
                    System.out.println("im in");
                    gameMusic.setLooping(true);
                    gameMusic.setVolume(0.05f);
                    gameMusic.play();
                }
            }
        }
        //OFF BUTTON EVENT HANDLING
        else if (Gdx.input.getX() < 405 && Gdx.input.getX() > 350 && Gdx.input.getY() > 310 && Gdx.input.getY() < 346) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                if (gameMusic.isPlaying()) {
                    gameMusic.setLooping(false);
                    gameMusic.stop();
                }
            }

        }
        //EULER BUTTON EVENT HANDLING
        else if (Gdx.input.getX() < 324 && Gdx.input.getX() > 258 && Gdx.input.getY() > 400 && Gdx.input.getY() < 437) {

            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                Euler = true;
                RK4 = false;
                RK2 = false;
            }
        }
        //RK2 BUTTON EVENT HANDLING
        else if (Gdx.input.getX() < 396 && Gdx.input.getX() > 342 && Gdx.input.getY() > 400 && Gdx.input.getY() < 437) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                Euler = false;
                RK4 = false;
                RK2 = true;
            }
        }
        //RK4 BUTTON EVENT HANDLING
        else if (Gdx.input.getX() < 460 && Gdx.input.getX() > 412 && Gdx.input.getY() > 400 && Gdx.input.getY() < 437) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                Euler = false;
                RK4 = true;
                RK2 = false;
            }
        }
            //Basic Bot Button
            else if (Gdx.input.getX() < 500 && Gdx.input.getX() > 390 && Gdx.input.getY() > 500 && Gdx.input.getY() < 537) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                if (Gdx.input.isTouched()) {
                    basicBot = true;
                    smartBot = false;
                    randomBot = false;
                }
            }
            //Random Bot Button
            else if (Gdx.input.getX() < 425 && Gdx.input.getX() > 295 && Gdx.input.getY() > 535 && Gdx.input.getY() < 567) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                if (Gdx.input.isTouched()) {
                    basicBot = false;
                    smartBot = false;
                    randomBot = true;
                }
            }
            //Advanced Bot Button
            else if (Gdx.input.getX() < 376 && Gdx.input.getX() > 227 && Gdx.input.getY() > 500 && Gdx.input.getY() < 537) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                if (Gdx.input.isTouched()) {
                    basicBot = false;
                    smartBot = true;
                    randomBot = false;
                }
            }

            //DEFAULT
            else {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        }

    }
