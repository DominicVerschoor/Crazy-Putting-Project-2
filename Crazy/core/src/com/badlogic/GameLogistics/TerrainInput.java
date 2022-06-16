package com.badlogic.GameLogistics;
public class TerrainInput{
public double terrain(double x,double y) {
<<<<<<< HEAD
return 0.1*x+1;
=======
return 0.4*(0.9-Math.exp(-(Math.pow(x,2)+Math.pow(y,2))/8));
>>>>>>> 6a7bf598f66c65adb39770e3f85eb18a9f29ddf1
}
}
