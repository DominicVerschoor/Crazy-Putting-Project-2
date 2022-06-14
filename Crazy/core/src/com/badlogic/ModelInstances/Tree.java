package com.badlogic.ModelInstances;
import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameLogistics.TerrainInput;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Tree {
    private ModelBuilder modelBuilderTree;
    private Model modelTree;
    public ModelInstance modelInstanceTree;
    private FileReader fileReader = new FileReader();
    private TerrainInput function = new TerrainInput();
    private float TargetX = fileReader.treeX;
    private float TargetY = fileReader.treeY;
    private float treeRadius =1f;

    public void createTree() {
        modelBuilderTree = new ModelBuilder();
        modelTree = modelBuilderTree.createCone((treeRadius*2),5f,1f,4,
                new Material(ColorAttribute.createDiffuse(Color.FOREST)),
                (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

        modelInstanceTree = new ModelInstance(modelTree,TargetX,(float) function.terrain(TargetX,TargetY)+0.65f ,TargetY);

        modelInstanceTree.transform.setTranslation(TargetX,(float)function.terrain(TargetX,TargetY)+0.65f, TargetY);
    }
}
