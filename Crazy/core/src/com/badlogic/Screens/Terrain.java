package com.badlogic.Screens;
public class Terrain{
public double terrain(double x,double y) {
return 0.5*(Math.sin((x-y)/7)+0.9);
}
}
