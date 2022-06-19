package com.badlogic.ModelInstances;

import com.badlogic.GameLogistics.TerrainInput;
import com.badlogic.GameScreens.CustomMap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.ArrayList;

public class Sand {
    private ArrayList<Float> heightArrayList = new ArrayList<Float>();
    public ArrayList<ModelInstance> arrayModelInstancesSand = new ArrayList<ModelInstance>();
    private ModelBuilder modelBuilderSand;
    private Model modelSand;
    public ModelInstance modelInstanceSand;
    private TerrainInput function = new TerrainInput();
    private double xCoordinate;
    private double yCoordinate;
    private double zCoordinate;

    public Sand(){
        setzCoordinate();
    }

    public void createSand() {
        modelBuilderSand = new ModelBuilder();
        if (CustomMap.sandCoordinateX.size() > 0) {
            for (int i = 0; i < CustomMap.sandCoordinateX.size(); i++) {
                xCoordinate = CustomMap.sandCoordinateX.get(i);
                yCoordinate = CustomMap.sandCoordinateY.get(i);
                zCoordinate = heightArrayList.get(i);
                modelSand = modelBuilderSand.createBox(1f, 1f, 1f,
                        new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
                        (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

                modelInstanceSand = new ModelInstance(modelSand, (float) xCoordinate, (float) (zCoordinate + 0.1f), (float) yCoordinate);
                arrayModelInstancesSand.add(modelInstanceSand);
            }
        }
    }
    public void setzCoordinate(){
        for (int i=0; i<CustomMap.sandCoordinateX.size(); i++) {
            heightArrayList.add((float) function.terrain(CustomMap.sandCoordinateX.get(i), CustomMap.sandCoordinateY.get(i)));
        }
    }
}
