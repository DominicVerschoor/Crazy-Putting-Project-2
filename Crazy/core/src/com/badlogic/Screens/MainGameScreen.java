package com.badlogic.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.mygame.App;

import javax.swing.*;
import java.util.ArrayList;

public class MainGameScreen implements Screen{

    //INITIALIZING CLASSES
    Euler e = new Euler();
    App game = new App();
    GamePanel options = new GamePanel();
    static FileReader fileReader= new FileReader();
    Terrain function = new Terrain();

    //INITIALIZING VARIABLES
    static ShapeRenderer rectangle;
    private double[] newArrXt = new double[4];
    boolean visible=false;
    ModelBuilder modelBuilder,modelBuilderBall,modelBuilderHole,modelBuilderSand;
    ModelBatch modelBatch,modelBatchBall;
    Model model,modelBall,modelHole,modelSand;
    ModelInstance modelInstance,modelInstanceBall,modelInstanceHole,modelInstanceSand;
    Environment environment;
    Camera ballPerspective;
    ShapeRenderer shapeRenderer;
    float ballRadius=0.2f;
    ArrayList<Float> arrayX = new ArrayList<Float>();
    ArrayList<Float> arrayY = new ArrayList<Float>();
    ArrayList<Float> arrayZ = new ArrayList<Float>();
    ArrayList<Color> heightColor = new ArrayList<Color>();
    ArrayList<ModelInstance> map = new ArrayList<ModelInstance>();

    //FILE INPUT VARIABLES INITIALIZATIONS
    static float BallX;
    static float BallY;
    float TargetX = fileReader.xt;
    float TargetY = fileReader.yt;
    float radius =fileReader.r;
    float sandPitXMax = fileReader.sandPitXMax;
    float sandPitXMin = fileReader.sandPitXMin;
    float sandPitYMax = fileReader.sandPitYMax;
    float sandPitYMin = fileReader.sandPitYMin;


    public MainGameScreen(App game){
        this.game = game;
        getMapCoordinates();
        map3DGenerator();
        cameraPerspective();
        shapeRenderer = new ShapeRenderer();
        BallX = fileReader.x0;
        BallY = fileReader.y0;

        newArrXt[0]=BallX;
        newArrXt[1]=BallY;
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
        //Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
       // map2DGenerator(); <--- render 2D map
        ballPerspective.update();
        modelBatch.begin(ballPerspective);
        cameraPerspective();
        golfBall();
        hole();
        renderMap();
        modelBatch.end();
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
     * Method that calculates what color should be assigned for a specific height in a specific coordinates
     * @param height - heights of the map
     * @return returns a specific assigned Color
     */
    public static Color mapColor(float height){

        if ((height<0)){
            return Color.BLUE;
        }

        if (height >=-10 && height <=10 ) {
            for (double g = 255, i = -10, j = i + 0.1; i < 9.9 && g >0; g -= 1.7, i += 0.1, j += 0.1) {
                if (height > i && height < j) {
                    //System.out.println(g/255);
                    return new Color(Color.argb8888(0,(float)(g)/255,0,1));
                }
            }
        }
       // return new Color(Color.argb8888(0.0f, 0, 0.0f,1.0f));
        return Color.BLACK;
    }
    float zoom =30;
    /**
     * All keyboard actions are being handled in this method
     */
    public void keyboardHandler(){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            this.dispose();
            if (visible==true){
                options.frame.setVisible(false);
                GamePanel.score=0;
            }
            game.setScreen(new MainMenuScreen(game));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
    //            ballPerspective.rotateAround(new Vector3(0f,0f,0f),new Vector3(0f,1f,0f), -1f);
            cameraY--;
            if (zoom<-50){
                zoom=0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
//            ballPerspective.rotateAround(new Vector3(0f,0f,0f),new Vector3(0f,1f,0f), 1f);
            cameraY++;
            if (zoom>50){
                zoom=90;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
//            ballPerspective.rotateAround(new Vector3(0f,0f,0f),new Vector3(1f,0f,0f), 1f);
            cameraZ++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            cameraZ--;
//            ballPerspective.rotateAround(new Vector3(0f,0f,0f),new Vector3(1f,0f,0f), -1f);
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

    /**
     * Generating a 2D map
     */

    float x;
    float y;
    float z;
    Color colorMap;

    /**
     * Generating a 3D map
     */
    public void renderMap(){
        for (int i=0; i<map.size();i++){
            modelBatch.render(map.get(i), environment);
        }
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.8f,0.8f,0.8f,1f));
    }

    public void  map3DGenerator(){

        modelBatch = new  ModelBatch();
        modelBuilder = new ModelBuilder();
        modelBatchBall = new ModelBatch();
        modelBuilderBall = new ModelBuilder();
        modelBuilderHole = new ModelBuilder();
        modelBuilderSand= new ModelBuilder();

        for (int i=0;i<arrayX.size();i++) {
            x=arrayX.get(i);
            y=arrayY.get(i);
            z=arrayZ.get(i);
            colorMap=heightColor.get(i);

            model = modelBuilder.createBox(1f, 1f, 1f,
                    new Material(ColorAttribute.createDiffuse(colorMap)),
                    (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));


            modelInstance = new ModelInstance(model, x, z, y);
            //SAND PITS ACCORDING TO COORDS
            if ((x >= sandPitXMin && x <= sandPitXMax) && (y >= sandPitYMin && y <= sandPitYMax)) {
                modelSand = modelBuilderSand.createBox(1f, 1f, 1f,
                        new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
                        (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

                modelInstanceSand = new ModelInstance(modelSand,x, z + 0.1f,y);
                modelBatch.render(modelInstanceSand, environment);
                map.add(modelInstanceSand);
            }
            map.add(modelInstance);

        }


    }


    public void golfBall() {
        shoot();
        modelBall = modelBuilderBall.createCapsule(ballRadius,0.4f,10,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

                modelInstanceBall = new ModelInstance(modelBall,BallX,(float) function.terrain(BallX,BallY)+0.75f ,BallY);
                //modelInstanceBall.transform.setToTranslation();

                modelInstanceBall.transform.setTranslation(BallX,(float)function.terrain(BallX,BallY)+0.75f, BallY);

        modelBatch.render(modelInstanceBall, environment);

    }
    RK2 rk2 = new RK2();
    RK4 rk4 = new RK4();
    public void shoot(){

        if (options.shoot==true){
            if (OptionsGameScreen.Euler==true) {
                System.out.println("Euler");
                newArrXt[2] = options.xVel;
                newArrXt[3] = options.yVel;
                newArrXt = e.Euler(newArrXt);
                BallX = (float) newArrXt[0];
                BallY = (float) newArrXt[1];
                options.update(newArrXt[0],newArrXt[1]);
                options.shoot = false;
                winCondition();
                System.out.println(newArrXt[0]);
                System.out.println(newArrXt[1]);
            }
            if (OptionsGameScreen.RK2==true) {
                //TODO
                System.out.println("RK2");
                newArrXt[2] = options.xVel;
                newArrXt[3] = options.yVel;
                newArrXt = rk2.BetterEstimationRK2(newArrXt);
                BallX = (float) newArrXt[0];
                BallY = (float) newArrXt[1];
                options.update(newArrXt[0],newArrXt[1]);
                options.shoot = false;
                winCondition();
                System.out.println(newArrXt[0]);
                System.out.println(newArrXt[1]);
            }
            if (OptionsGameScreen.RK4==true) {
                //TODO
                System.out.println("RK4");
                newArrXt[2] = options.xVel;
                newArrXt[3] = options.yVel;
                newArrXt = rk4.newRK4(newArrXt);
                BallX = (float) newArrXt[0];
                BallY = (float) newArrXt[1];
                options.update(newArrXt[0],newArrXt[1]);
                options.shoot = false;
                winCondition();
                System.out.println(newArrXt[0]);
                System.out.println(newArrXt[1]);
            }
        }

    }
    public void hole () {
        modelHole = modelBuilderHole.createCylinder((radius*2),0.02f,(radius*2),10,
                new Material(ColorAttribute.createDiffuse(Color.BROWN)),
                (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

        modelInstanceHole = new ModelInstance(modelHole,TargetX,(float) function.terrain(TargetX,TargetY)+0.65f ,TargetY);

        modelInstanceHole.transform.setTranslation(TargetX,(float)function.terrain(TargetX,TargetY)+0.65f, TargetY);

        modelBatch.render(modelInstanceHole, environment);
    }

    public void winCondition(){

        double distance = Math.sqrt(Math.pow(newArrXt[0] - TargetX, 2) + Math.pow(newArrXt[1] - TargetY, 2));
        if ((radius >  distance)) {
            System.out.println("win");

            JOptionPane.showMessageDialog(new JFrame(),"You finished with score: " + GamePanel.score);

            game.setScreen(new MainMenuScreen(game));
            BallX=fileReader.x0;
            BallY=fileReader.y0;
            options.frame.setVisible(false);
            GamePanel.score=0;
            visible=false;
        }
    }

    /**
     * Handling the perspective of the player
     */
    float cameraX=10;
    float cameraY=10;
    float cameraZ=10;
    public void cameraPerspective(){
        ballPerspective=new PerspectiveCamera(zoom, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        ballPerspective.position.set(cameraX,cameraZ,cameraY);
        ballPerspective.lookAt(BallX,0,BallY);
        ballPerspective.near = 0.1f;
        ballPerspective.far = 300f;
    }
    public void getMapCoordinates(){
        for (float X = -20; X < 20; X+=0.5f) {
            for (float Y = -20; Y < 20; Y += 0.5f) {
                arrayX.add(X);
                arrayY.add(Y);
                arrayZ.add((float) function.terrain(X,Y));
                heightColor.add(mapColor((float) function.terrain(X,Y)));
            }
        }
    }
}
