public class EulerTest {
    static fileReader read = new fileReader();
    static PartialDerivate derivate = new PartialDerivate();

    private static double[] newArrXt = new double[4];
    private static double g = 9.81;          // gravity;
    private static double x = read.x0;       // x-coordinate of the initial position
    private static double y = read.y0;       // y-coordinate of the initial position
    private static double uk = read.muk;     // kinetic friction coefficient of grass
    private static double us = read.mus;     // static friction coefficient of grass
    private static double h = 0.01;   //step of Euler

    public static void Euler(double[] arrXt) {
        //when the velocity of x, y are not 0, cuz its quite impossible to get them to 0,
        // so we take the little range 0.1 instead
        while (Math.abs(arrXt[2]) > 0.1 || Math.abs(arrXt[3]) > 0.1) {

            newArrXt[0] = arrXt[0] + h * arrXt[2];     //position + step * velocity --> get the new position X
            newArrXt[1] = arrXt[1] + h * arrXt[3];     //position + step * velocity --> get the new position Y

            double partialX = derivate.derivateX(arrXt[0], arrXt[1]);    //the partial derivative of X
            double partialY = derivate.derivateY(arrXt[0], arrXt[1]);    //the partial derivative of Y

            //the second-order derivative equation, which is the acceleration of X, Y
            double accX = -g * partialX - uk * g * arrXt[2] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);
            double accY = -g * partialY - uk * g * arrXt[3] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);

            newArrXt[2] = arrXt[2] + h * accX;        //X velocity + step * acceleration --> new velocity
            newArrXt[3] = arrXt[3] + h * accY;        //Y velocity + step * acceleration --> new velocity


            for (int i = 0; i < arrXt.length; i++) {
                arrXt[i] = newArrXt[i];              //clone newArrXt back to arrXt
            }

            //when the ball velocity equals to 0 (smaller than range 0.1 in this case)
            //and the partial derivative of X and Y is not equal to 0
            //it means the ball stop on a slope,

            // then if the static friction bigger than downhill force,
            // means the ball really stop, then break the loop;
            // if not, means the the ball will keep moving then change the friction part of the original equation
            // and go back the loop
            if ((Math.abs(arrXt[2]) <= 0.1 && Math.abs(arrXt[3]) <= 0.1) && (Math.abs(partialX) > 0.1 || Math.abs(partialY) > 0.1)) {
                if (us > Math.sqrt(partialX * partialX + partialY * partialY)) {
                    break;
                } else {
                    arrXt[2] = h * -g * partialX + uk * g * partialX / Math.sqrt(partialX * partialX + partialY * partialY);
                    arrXt[3] = h * -g * partialY + uk * g * partialY / Math.sqrt(partialX * partialX + partialY * partialY);

                }
            }

        }

        System.out.println("X: " + arrXt[0]);
        System.out.println("Y: " + arrXt[1]);
    }

    /*
%Second order Runge-Kutta: Ralston's Method
f = @(t,y)(sin(t) + y - y^3); %ODE equation
hs = 0.1; %step size
wi = 2; %initial y

step = 0;
for t = 0.1:hs:0.2 %only do 2 steps
    counter = counter + 1;
    hki1 = hs * f(t,wi);
    hki2 = hs * f(t + (3/4 * hs), wi + (3/4 * hki1 * hs));
    wi = wi + (1/4) * hs * ((1/3) hki1 + (2/3 * hki2)); %change wi for next iteration
    solutions(counter) = wi; %store solutions
end
 */

    public static double velEquation(double v0, double a, double h){
        return Math.sqrt(Math.pow(v0,2) + 2*(a)*(h));
    }

    public static void RungeKuttaSecondOrder(double[] arrXt) {

        //when the velocity of x, y are not 0, cuz its quite impossible to get them to 0,
        // so we take the little range 0.1 instead
        while (Math.abs(arrXt[2]) > 0.1 || Math.abs(arrXt[3]) > 0.1) {
            newArrXt[0] = arrXt[0] + h * arrXt[2];     //position + step * velocity --> get the new position X
            newArrXt[1] = arrXt[1] + h * arrXt[3];     //position + step * velocity --> get the new position Y

            double partialX = derivate.derivateX(arrXt[0], arrXt[1]);    //the partial derivative of X
            double partialY = derivate.derivateY(arrXt[0], arrXt[1]);    //the partial derivative of Y

            //the second-order derivative equation, which is the acceleration of X, Y
            double accX = -g * partialX - uk * g * arrXt[2] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);
            double accY = -g * partialY - uk * g * arrXt[3] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);

            double k1X = h * velEquation(arrXt[2], accX, h);
            double k1Y = h * velEquation(arrXt[3], accY, h);

            double k2X = h * velEquation(arrXt[2] + 3/4 * h, arrXt[2] + 3/4*k1X*h, h);
            double k2Y = h * velEquation(arrXt[3] + 3/4 * h, arrXt[3] + 3/4*k1Y*h, h);

            newArrXt[2] = arrXt[2] + h * ((1/3 * k1X) + (2/3 * k2X));        //X velocity + step * acceleration --> new velocity
            newArrXt[3] = arrXt[3] + h * ((1/3 * k1Y) + (2/3 * k2Y));         //Y velocity + step * acceleration --> new velocity

            for (int i = 0; i < arrXt.length; i++) {
                arrXt[i] = newArrXt[i];              //clone newArrXt back to arrXt
            }
        }

        System.out.println("X: " + arrXt[0]);
        System.out.println("Y: " + arrXt[1]);
    }


    public static void main(String[] args) {
        newArrXt[0] = 0;
        newArrXt[1] = 0;
        newArrXt[2] = Double.parseDouble("1");
        newArrXt[3] = Double.parseDouble("0");

        Euler(newArrXt);
        //RungeKuttaSecondOrder(newArrXt);
    }
}
