package com.badlogic.GameLogistics;
public class TerrainInput{
public double terrain(double x,double y) {
return 0.5*(Math.sin((x-y)/2)+1.2);
}
}
