package com.badlogic.Screens;
public class Terrain{
public double terrain(double x,double y) {
return Math.exp(-Math.pow(Math.pow(x-4,2)+Math.pow(y-4,2),2)/1000)+Math.exp(-Math.pow(Math.pow(x+4,2)+Math.pow(y+4,2),2)/1000)+Math.exp(-Math.pow(Math.pow(x+4,2)+Math.pow(y+4,2),2))+Math.exp(-Math.pow(Math.pow(x-4,2)+Math.pow(y-4,2),2));
}
}
