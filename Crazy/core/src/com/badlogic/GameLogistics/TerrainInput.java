package com.badlogic.GameLogistics;
public class TerrainInput{
public double terrain(double x,double y) {
return 4*Math.exp(-Math.pow(Math.pow(x-19,2)+Math.pow(y-19,2),2)/1000)+0.3*Math.exp(-Math.pow(Math.pow(x-19,2)+Math.pow(y-19,2),2));
}
}
