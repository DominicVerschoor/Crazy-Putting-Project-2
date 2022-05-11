public class Terrain{
public static double terrain(double x,double y) {
return Math.exp(-(Math.pow(x,2)+Math.pow(y,2))/40);
}
}
