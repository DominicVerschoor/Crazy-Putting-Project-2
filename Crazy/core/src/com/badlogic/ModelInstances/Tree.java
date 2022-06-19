package com.badlogic.ModelInstances;
import com.badlogic.FileHandling.FileReader;
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

public class Tree {
    ArrayList<Float> heightArrayList = new ArrayList<Float>();
    ArrayList<ModelInstance> arrayModelInstancesTree = new ArrayList<ModelInstance>();
    private ModelBuilder modelBuilderTree;
    private Model modelTree;
    public ModelInstance modelInstanceTree;
    private FileReader fileReader = new FileReader();
    private TerrainInput function = new TerrainInput();
    private float treeRadius =2f;
    private double xCoordinate;
    private double yCoordinate;
    private double zCoordinate;
    public Tree(){
        setzCoordinate();
    }

    public void createTree() {
        if (CustomMap.treeCoordinateX.size() > 0) {
            for (int i = 0; i < CustomMap.treeCoordinateX.size(); i++) {
                xCoordinate = CustomMap.treeCoordinateX.get(i);
                yCoordinate = CustomMap.treeCoordinateY.get(i);
                zCoordinate = heightArrayList.get(i);
                modelBuilderTree = new ModelBuilder();
                modelTree = modelBuilderTree.createCone((treeRadius * 2), 5f, (treeRadius * 2), 10,
                        new Material(ColorAttribute.createDiffuse(Color.FOREST)),
                        (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

                modelInstanceTree = new ModelInstance(modelTree, (float) xCoordinate, (float) (zCoordinate+0.65f), (float) yCoordinate);
                arrayModelInstancesTree.add(modelInstanceTree);
            }
        }
    }
    public void setzCoordinate(){
        for (int i = 0; i< CustomMap.treeCoordinateX.size(); i++) {
            heightArrayList.add((float) function.terrain(CustomMap.treeCoordinateX.get(i), CustomMap.treeCoordinateY.get(i)));
        }
    }
}
