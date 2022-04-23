public class EulerTest {
    static fileReader read = new fileReader();
    static PartialDerivate derivate = new PartialDerivate();

    private static double[] newArrXt = new double[4];
    private static double g = 9.81;          // gravity;
    private static double x = read.x0;       // x-coordinate of the initial position
    private static double y = read.y0;       // y-coordinate of the initial position
    private static double uk = read.muk;     // kinetic friction coefficient of grass
    private static double us = read.mus;     // static friction coefficient of grass
    private static double h = 0.01;   //step size

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


            //clone newArrXt back to arrXt
            System.arraycopy(newArrXt, 0, arrXt, 0, arrXt.length);

            //when the ball velocity equals to 0 (smaller than range 0.1 in this case)
            //and the partial derivative of X and Y is not equal to 0
            //it means the ball stop on a slope,
            // then if the static friction bigger than downhill force,
            // means the ball really stop, then break the loop;
            // if not, means the the ball will keep moving then change the friction part of the original equation
            // and go back the loop
            if ((Math.abs(arrXt[2]) <= 0.1 && Math.abs(arrXt[3]) <= 0.1) && (Math.abs(partialX) > 0.1 || Math.abs(partialY) > 0.1)) {
                double sqrt = Math.sqrt(partialX * partialX + partialY * partialY);
                if (us > sqrt) {
                    break;
                } else {
                    arrXt[2] = h * -g * partialX + uk * g * partialX / sqrt;
                    arrXt[3] = h * -g * partialY + uk * g * partialY / sqrt;

                }
            }
        }

        System.out.println("X: " + arrXt[0]);
        System.out.println("Y: " + arrXt[1]);
    }



    public static double accelerationEquation(double t, double w, double derivative){
        return -g * derivative - uk * g * t / Math.sqrt(Math.pow(t,2) + Math.pow(w,2));
    }

    public static double velEquation(double v0, double a) {
        double inSqrt = Math.pow(v0, 2) + 2 * a * h;
        if (inSqrt < 0){
           return -1*Math.sqrt(Math.abs(inSqrt));
        }
        return Math.sqrt(inSqrt);
    }

    public static void RungeKuttaSecondOrder(double [] arrXt) {
        //when the velocity of x, y are not 0, cuz its quite impossible to get them to 0,
        // so we take the little range 0.1 instead
        while (Math.abs(arrXt[2]) > 0.1 || Math.abs(arrXt[3]) > 0.1) {
            //TODO
            double partialX = derivate.derivateX(arrXt[0], arrXt[1]);    //the partial derivative of X
            double partialY = derivate.derivateY(arrXt[0], arrXt[1]);    //the partial derivative of Y

            double yX = accelerationEquation(arrXt[2], arrXt[3], partialX);
            double yY = accelerationEquation(arrXt[3], arrXt[2], partialY);

            double posk1X = h * velEquation(arrXt[2], yX);
            double posk1Y = h * velEquation(arrXt[3], yY);

            double posk2X = h * velEquation(arrXt[2] + ((2*h)/3.0), yX + ((2*posk1X)/3.0));
            double posk2Y = h * velEquation(arrXt[3] + ((2*h)/3.0), yY + ((2*posk1Y)/3.0));

            newArrXt[0] = arrXt[0] + (1.0/4) * (posk1X + (3 * posk2X));
            newArrXt[1] = arrXt[1] + (1.0/4) * (posk1Y + (3 * posk2Y));

            partialX = derivate.derivateX(arrXt[0], arrXt[1]);    //the partial derivative of X
            partialY = derivate.derivateY(arrXt[0], arrXt[1]);    //the partial derivative of Y

            //TODO
            double k1X = h * accelerationEquation(arrXt[2], arrXt[3], partialX);
            double k1Y = h * accelerationEquation(arrXt[3], arrXt[2], partialY);

            double k2X = h * accelerationEquation(arrXt[2] + ((2*h)/3.0), arrXt[3] + ((2*k1X)/3.0), partialX);
            double k2Y = h * accelerationEquation(arrXt[3] + ((2*h)/3.0), arrXt[2] + ((2*k1Y)/3.0), partialY);

            newArrXt[2] = arrXt[2] + (1.0/4) * (k1X + (3 * k2X));
            newArrXt[3] = arrXt[3] + (1.0/4) * (k1Y + (3 * k2Y));

            //clone newArrXt back to arrXt
            System.arraycopy(newArrXt, 0, arrXt, 0, arrXt.length);

            if ((Math.abs(arrXt[2]) <= 0.1 && Math.abs(arrXt[3]) <= 0.1) && (Math.abs(partialX) > 0.1 || Math.abs(partialY) > 0.1)) {
                double sqrt = Math.sqrt(partialX * partialX + partialY * partialY);
                if (us > sqrt) {
                    break;
                } else {
                    arrXt[2] = h * -g * partialX + uk * g * partialX / sqrt;
                    arrXt[3] = h * -g * partialY + uk * g * partialY / sqrt;

                }
            }
        }

        System.out.println("X: " + arrXt[0]);
        System.out.println("Y: " + arrXt[1]);
    }


    public static void RungeKuttaFourthOrder(double[] arrXt) {

        //when the velocity of x, y are not 0, cuz its quite impossible to get them to 0,
        // so we take the little range 0.1 instead
        while (Math.abs(arrXt[2]) > 0.1 || Math.abs(arrXt[3]) > 0.1) {
            //TODO
            double partialX = derivate.derivateX(arrXt[0], arrXt[1]);    //the partial derivative of X
            double partialY = derivate.derivateY(arrXt[0], arrXt[1]);    //the partial derivative of Y

            double yX = accelerationEquation(arrXt[2], arrXt[3], partialX);
            double yY = accelerationEquation(arrXt[3], arrXt[2], partialY);

            double posk1X = h * velEquation(arrXt[2], yX);
            double posk1Y = h * velEquation(arrXt[3], yY);

            double posk2X = h * velEquation(arrXt[2] + (h/3.0), yX + posk1X/3.0);
            double posk2Y = h * velEquation(arrXt[3] + (h/3.0), yY + posk1Y/3.0);

            double posk3X = h * velEquation(arrXt[2] + ((2*h)/3.0), yX - (posk1X/3.0) + posk2X);
            double posk3Y = h * velEquation(arrXt[3] + ((2*h)/3.0), yY - (posk1Y/3.0) + posk2Y);

            double posk4X = h * velEquation(arrXt[2] + h, yX + posk1X - posk2X + posk3X);
            double posk4Y = h * velEquation(arrXt[3] + h, yY + posk1Y - posk2Y + posk3Y);

            newArrXt[0] = arrXt[0] + (1.0/8) * (posk1X + (3 * posk2X) + (3 * posk3X) + posk4X);
            newArrXt[1] = arrXt[1] + (1.0/8) * (posk1Y + (3 * posk2Y) + (3 * posk3Y) + posk4Y);

            partialX = derivate.derivateX(arrXt[0], arrXt[1]);    //the partial derivative of X
            partialY = derivate.derivateY(arrXt[0], arrXt[1]);    //the partial derivative of Y

            //TODO
            double k1X = h * accelerationEquation(arrXt[2], arrXt[3], partialX);
            double k1Y = h * accelerationEquation(arrXt[3], arrXt[2], partialY);

            double k2X = h * accelerationEquation(arrXt[2] + (h/3.0), arrXt[3] + k1X/3.0, partialX);
            double k2Y = h * accelerationEquation(arrXt[3] + (h/3.0), arrXt[2] + k1Y/3.0, partialY);

            double k3X = h * accelerationEquation(arrXt[2] + ((2*h)/3.0), arrXt[3] - (k1X/3.0) + k2X, partialX);
            double k3Y = h * accelerationEquation(arrXt[3] + ((2*h)/3.0), arrXt[2] - (k1Y/3.0) + k2Y, partialY);

            double k4X = h * accelerationEquation(arrXt[2] + h, arrXt[3] + k1X - k2X + k3X, partialX);
            double k4Y = h * accelerationEquation(arrXt[3] + h, arrXt[2] + k1Y - k2Y + k3Y, partialY);

            newArrXt[2] = arrXt[2] + (1.0/8) * (k1X + (3 * k2X) + (3 * k3Y) + k4X);
            newArrXt[3] = arrXt[3] + (1.0/8) * (k1Y + (3 * k2Y) + (3 * k3Y) + k4Y);

            //clone newArrXt back to arrXt
            System.arraycopy(newArrXt, 0, arrXt, 0, arrXt.length);

            if ((Math.abs(arrXt[2]) <= 0.1 && Math.abs(arrXt[3]) <= 0.1) && (Math.abs(partialX) > 0.1 || Math.abs(partialY) > 0.1)) {
                double sqrt = Math.sqrt(partialX * partialX + partialY * partialY);
                if (us > sqrt) {
                    break;
                } else {
                    arrXt[2] = h * -g * partialX + uk * g * partialX / sqrt;
                    arrXt[3] = h * -g * partialY + uk * g * partialY / sqrt;

                }
            }
        }
        System.out.println("X: " + arrXt[0]);
        System.out.println("Y: " + arrXt[1]);
    }



    public static void main(String[] args) {
        newArrXt[0] = 0;
        newArrXt[1] = 0;
        newArrXt[2] = Double.parseDouble("2");
        newArrXt[3] = Double.parseDouble("0");

        System.out.println("Euler");
        Euler(newArrXt);
        newArrXt[0] = 0;
        newArrXt[1] = 0;
        newArrXt[2] = Double.parseDouble("2");
        newArrXt[3] = Double.parseDouble("0");

        System.out.println("RK2");
        RungeKuttaSecondOrder(newArrXt);
        newArrXt[0] = 0;
        newArrXt[1] = 0;
        newArrXt[2] = Double.parseDouble("2");
        newArrXt[3] = Double.parseDouble("0");

        System.out.println("RK4");
        RungeKuttaFourthOrder(newArrXt);
    }
}