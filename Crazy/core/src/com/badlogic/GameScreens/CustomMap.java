package com.badlogic.GameScreens;

import com.badlogic.Base.App;
import com.badlogic.GameLogistics.TerrainInput;
import com.badlogic.ModelInstances.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CustomMap implements Screen  {
    Map map = new Map();
    App game;
    public static ArrayList<Float> sandCoordinateX = new ArrayList<Float>();
    public static ArrayList<Float> sandCoordinateY = new ArrayList<Float>();
    private static ArrayList<Float> sandDrawX = new ArrayList<Float>();
    private static ArrayList<Float> sandDrawY = new ArrayList<Float>();
    public static ArrayList<Float> treeCoordinateX = new ArrayList<Float>();
    public static ArrayList<Float> treeCoordinateY = new ArrayList<Float>();
    private static ArrayList<Float> treeDrawX = new ArrayList<Float>();
    private static ArrayList<Float> treeDrawY = new ArrayList<Float>();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private ShapeRenderer shapeLine = new ShapeRenderer();
    private TerrainInput function = new TerrainInput();
    private float xCoordinate;
    private float yCoordinate;
    Texture treeTexture,minecraftTree,sandTexture,restartTexture,customMapTexture,obstaclesTexture;
    Boolean treeSelected=false;
    Boolean sandSelected=false;

    public CustomMap(App game) {
        this.game = game;
        treeTexture = new Texture("Tree.png");
        minecraftTree = new Texture("MTree.png");
        sandTexture = new Texture("Sand.png");
        restartTexture = new Texture("Restart.png");
        customMapTexture = new Texture("CustomMap.png");
        obstaclesTexture = new Texture("Select.png");

    }

    @Override
    public void show() {
//        JOptionPane.showMessageDialog(new JFrame(),"To insert obstacles on the map select a desired one and place it pressing \"P\" key\n PRESS OK TO CONTINUE" );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(20 / 255f, 176 / 255f, 161 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawGrid();
        placeObsticles();
        shapeRenderer.end();

        keyBoardHandler();
        game.batch.begin();
        game.batch.draw(minecraftTree,700,450,100,100);
        game.batch.draw(sandTexture,700,250,100,100);
        game.batch.draw(obstaclesTexture,640,610,220,70);
        game.batch.draw(customMapTexture,300,610,220,65);
        game.batch.draw(restartTexture,690,130,120,60);
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

    public void generateGrid() {
        double xColor;
        double yColor;
        for (float i=200; i<=600; i+=5){
            for (float j=200; j<=600; j+=5){
                xCoordinate = i;
                yCoordinate = j;
                xColor = (xCoordinate-400)/10.0;
                yColor = (yCoordinate-400)/10.0;
                shapeRenderer.setColor(Map.getMapColor((float)function.terrain(yColor,xColor)));
                shapeRenderer.rect(xCoordinate,yCoordinate,5,5);
            }
        }
    }

    public void drawGrid() {

        generateGrid();
        shapeRenderer.setColor(new Color(173 / 255f, 236 / 255f, 230 / 255f, 1));
        shapeRenderer.rect(650,200,200,400);
        if (sandDrawX.size()>1){
            shapeRenderer.setColor(Color.YELLOW);
            for (int i=0; i<sandDrawX.size();i++) {
                shapeRenderer.rect(sandDrawX.get(i),sandDrawY.get(i),5,5);
            }
        }
        if (treeDrawX.size()>1){
            shapeRenderer.setColor(Color.LIME);
            for (int i=0; i<treeDrawX.size();i++) {
                shapeRenderer.rect(treeDrawX.get(i),treeDrawY.get(i),5,5);
            }
        }
    }
    public float getX(float x){
        System.out.println(-((x-400)/10.0));
        return ((float)((x-400)/10.0));
    }
    public float getY(float y){
        System.out.println(-((y-400)/10.0));
        return ((float)((y-400)/10.0));
    }
    public void placeObsticles(){
        if (Gdx.input.isKeyPressed(Input.Keys.P) && Gdx.input.getX()<=600 && Gdx.input.getX()>=200
                && (Gdx.graphics.getHeight()-Gdx.input.getY())<=600 && (Gdx.graphics.getHeight()-Gdx.input.getY())>=200) {
            if (sandSelected) {
//                System.out.println(Gdx.input.getX());
//                System.out.println(Gdx.graphics.getHeight()-Gdx.input.getY());
                sandCoordinateY.add(getX(Gdx.input.getX()));
                sandCoordinateX.add(getY(Gdx.graphics.getHeight()-Gdx.input.getY()));
                sandDrawX.add((float)Gdx.input.getX());
                sandDrawY.add((float)(Gdx.graphics.getHeight()-Gdx.input.getY()));
            }
            if (treeSelected) {
                treeCoordinateY.add(getX(Gdx.input.getX()));
                treeCoordinateX.add(getY(Gdx.graphics.getHeight()-Gdx.input.getY()));
                treeDrawX.add((float)Gdx.input.getX());
                treeDrawY.add((float)(Gdx.graphics.getHeight()-Gdx.input.getY()));
            }
        }
    }
    public void keyBoardHandler(){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
        }
        else if (Gdx.input.getX() < 800 && Gdx.input.getX() > 700 && Gdx.input.getY() > 350 && Gdx.input.getY() < 450) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                if (sandSelected) {
                    sandSelected = false;
                    treeSelected = false;
                }
                sandSelected = true;
                treeSelected = false;
            }
        }
        else if (Gdx.input.getX() < 800 && Gdx.input.getX() > 700 && Gdx.input.getY() > 150 && Gdx.input.getY() < 250) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                if (treeSelected) {
                    sandSelected = false;
                    treeSelected = false;
                }
                sandSelected = false;
                treeSelected = true;
            }
        }
        else if (Gdx.input.getX() < 800 && Gdx.input.getX() > 700 && Gdx.input.getY() > 520 && Gdx.input.getY() < 560) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                treeCoordinateY.clear();
                treeCoordinateX.clear();
                treeDrawX.clear();
                treeDrawY.clear();
                sandCoordinateX.clear();
                sandCoordinateY.clear();
                sandDrawX.clear();
                sandDrawY.clear();
            }
        }

        else {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
    }

}

