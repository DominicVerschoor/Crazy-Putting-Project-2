package com.badlogic.ModelInstances;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameLogistics.TerrainInput;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.ArrayList;
public class Map {
    private TerrainInput function = new TerrainInput();
    private Hole hole = new Hole();
    private CameraBuilder camera = new CameraBuilder();
    private FileReader fileReader = new FileReader();
    private ArrayList<Float> xCoordinatesArray = new ArrayList<Float>();
    private ArrayList<Float> yCoordinatesArray = new ArrayList<Float>();
    private ArrayList<Float> zCoordinatesArray = new ArrayList<Float>();
    private ArrayList<Color> heightColorArray = new ArrayList<Color>();
    private ArrayList<ModelInstance> mapModelInstancesArray = new ArrayList<ModelInstance>();
    private GolfBall ball = new GolfBall();
    private float xCoordinate;
    private float yCoordinate;
    private float zCoordinate;
    private Color colorMap;
    private final float sandPitXMax = fileReader.sandPitXMax;
    private final float sandPitXMin = fileReader.sandPitXMin;
    private final float sandPitYMax = fileReader.sandPitYMax;
    private final float sandPitYMin = fileReader.sandPitYMin;
    private Environment environment;
    private ModelBuilder modelBuilderCourse,modelBuilderSand;
    private ModelBatch modelBatchCourse;
    private Model modelCourse,modelSand;
    private ModelInstance modelInstanceCourse,modelInstanceSand;

    public void getMapCoordinates(){
        for (float X = -20; X <= 20; X+=0.5f) {
            for (float Y = -20; Y <= 20; Y += 0.5f) {
                xCoordinatesArray.add(X);
                yCoordinatesArray.add(Y);
                zCoordinatesArray.add((float) function.terrain(X,Y));
                heightColorArray.add(getMapColor((float) function.terrain(X,Y)));
            }
        }
    }

    public static Color getMapColor(float height){

        if ((height<0)){
            return Color.BLUE;
        }

        if (height >=-10 && height <=10 ) {
            for (double g = 255, i = -10, j = i + 0.1; i < 9.9 && g >0; g -= 1.7, i += 0.1, j += 0.1) {
                if (height > i && height < j) {
                    return new Color(Color.argb8888(0,(float)(g)/255,0,1));
                }
            }
        }
        return Color.BLACK;
    }

    public void  combineField(){
        modelBatchCourse = new ModelBatch();
        modelBuilderCourse = new ModelBuilder();
        modelBuilderSand= new ModelBuilder();

        for (int i = 0; i< xCoordinatesArray.size(); i++) {
            xCoordinate = xCoordinatesArray.get(i);
            yCoordinate= yCoordinatesArray.get(i);
            zCoordinate = zCoordinatesArray.get(i);
            colorMap= heightColorArray.get(i);

            modelCourse = modelBuilderCourse.createBox(1f, 1f, 1f,
                    new Material(ColorAttribute.createDiffuse(colorMap)),
                    (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));


            modelInstanceCourse = new ModelInstance(modelCourse, xCoordinate, zCoordinate, yCoordinate);
            //SAND PITS ACCORDING TO COORDS
            if ((xCoordinate >= sandPitXMin && xCoordinate <= sandPitXMax) && (yCoordinate >= sandPitYMin && yCoordinate <= sandPitYMax)) {
                modelSand = modelBuilderSand.createBox(1f, 1f, 1f,
                        new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
                        (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

                modelInstanceSand = new ModelInstance(modelSand, xCoordinate, zCoordinate + 0.1f,yCoordinate);
                modelBatchCourse.render(modelInstanceSand, environment);
                mapModelInstancesArray.add(modelInstanceSand);
            }
            mapModelInstancesArray.add(modelInstanceCourse);

        }
    }
    public void createField(){
        for (int i = 0; i< mapModelInstancesArray.size(); i++){
            modelBatchCourse.render(mapModelInstancesArray.get(i), environment);
        }
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.8f,0.8f,0.8f,1f));
    }
    public void renderCourse() {
        camera.cameraPerspective.update();
        modelBatchCourse.begin(camera.cameraPerspective);
        camera.cameraPerspective();
        ball.createGolfBall();
        hole.createHole();
        modelBatchCourse.render(ball.modelInstanceBall, environment);
        modelBatchCourse.render(hole.modelInstanceHole, environment);
        createField();
        modelBatchCourse.end();
    }

}
