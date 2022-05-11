public class DerivativeTest {

    private static fileReader r = new fileReader();
    private static final double h = 0.00000000001;

    public static double f(double x,double y){
        return Terrain.terrain(x,y);
    }

    /**
     * Calculates the x-slope by calculating the derivative at the position of the ball
     *
     * @param xcoordinate The x-coordinate of the Ball
     * @param ycoordinate The y-coordinate of the Ball
     * @return The x-slope
     */
    public double derivateX(double xcoordinate, double ycoordinate) {
        fileWriter.function();

        return (f(xcoordinate-2*h,ycoordinate) - 8*f(xcoordinate-h,ycoordinate) + 8*f(xcoordinate+h,ycoordinate)
                - f(xcoordinate+2*h,ycoordinate)) / (12*h);
    }

    /**
     * Calculates the y-slope by calculating the derivative at the position of the ball
     *
     * @param xcoordinate The x-coordinate of the Ball
     * @param ycoordinate The y-coordinate of the Ball
     * @return The y-slope
     */
    public double derivateY(double xcoordinate, double ycoordinate) {
        fileWriter.function();

        return (f(xcoordinate,ycoordinate-2*h) - 8*f(xcoordinate,ycoordinate-h) + 8*f(xcoordinate,ycoordinate+h)
                - f(xcoordinate,ycoordinate+2*h)) / (12*h);
    }

    public static void main(String[] args) {
        double xcoordinate = 0.2;
        double ycoordinate = 0.1;

        fileWriter.function();

        System.out.println((f(xcoordinate-2*h,ycoordinate) - 8*f(xcoordinate-h,ycoordinate) + 8*f(xcoordinate+h,ycoordinate)
                - f(xcoordinate+2*h,ycoordinate)) / (12*h));

        System.out.println((f(xcoordinate,ycoordinate-2*h) - 8*f(xcoordinate,ycoordinate-h) + 8*f(xcoordinate,ycoordinate+h)
                - f(xcoordinate,ycoordinate+2*h)) / (12*h));
    }
}
