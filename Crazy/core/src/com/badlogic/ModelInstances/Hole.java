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

public class Hole {
        private ModelBuilder modelBuilderHole;
        private Model modelHole;
        public ModelInstance modelInstanceHole;
        private FileReader fileReader = new FileReader();
        private TerrainInput function = new TerrainInput();
        private float TargetX = fileReader.xt;
        private float TargetY = fileReader.yt;
        private float holeRadius =fileReader.r;
    public void createHole () {
        modelBuilderHole = new ModelBuilder();
        modelHole = modelBuilderHole.createCylinder((holeRadius *2),0.02f,(holeRadius *2),10,
                new Material(ColorAttribute.createDiffuse(Color.BROWN)),
                (VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

        modelInstanceHole = new ModelInstance(modelHole,TargetX,(float) function.terrain(TargetX,TargetY)+0.65f ,TargetY);

        modelInstanceHole.transform.setTranslation(TargetX,(float)function.terrain(TargetX,TargetY)+0.65f, TargetY);

    }
}
