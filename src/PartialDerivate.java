import org.mariuszgromada.math.mxparser.*;
import org.mariuszgromada.math.mxparser.mathcollection.Calculus;


public class PartialDerivate {
    /**
     *converting the
     * @param equation
     * @return string equation with
     */
    public String converter(String equation){
        return equation= "h(x,y) = " + r.function;
    }
    private fileReader r = new fileReader();
    public Function f = new Function(converter(r.function));

    /**
     * Calculates the x-slope by calculating the derivative at the position of the ball
     * @param xcoordinate The x-coordinate of the Ball
     * @param ycoordinate The y-coordinate of the Ball
     * @return The x-slope
     */
    public double derivateX (double xcoordinate, double ycoordinate)
    {
        Argument x = new Argument("x", xcoordinate);
        Argument y = new Argument("y", ycoordinate);
        Expression e = new Expression("h(x,y)", f, x, y);

        return Calculus.derivative(e, x, xcoordinate, 3, 0.000000000000001, 10);
    }

    /**
     * Calculates the y-slope by calculating the derivative at the position of the ball
     * @param xcoordinate The x-coordinate of the Ball
     * @param ycoordinate The y-coordinate of the Ball
     * @return The y-slope
     */
    public double derivateY ( double xcoordinate, double ycoordinate)
    {

        Argument x = new Argument("x", xcoordinate);
        Argument y = new Argument("y", ycoordinate);
        Expression e = new Expression("h(x,y)", f, x, y);

        return Calculus.derivative(e, y, ycoordinate, 3, 0.000000000000001, 10);
    }

}
