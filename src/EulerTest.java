public class EulerTest {
    static fileReader read = new fileReader();
    static PartialDerivate derivate = new PartialDerivate();

    private static double[] newArrXt = new double[4];
    private static double g = 9.81;          // gravity;
    private static double x = read.x0;       // x-coordinate of the initial position
    private static double y = read.y0;       // y-coordinate of the initial position
    private static double uk = 0.05;     // kinetic friction coefficient of grass
    private static double us = 0.2;     // static friction coefficient of grass
    private static double h = 0.1;   //step size

    public static void Euler(double[] arrXt) {
        //when the velocity of x, y are not 0, cuz its quite impossible to get them to 0,
        // so we take the little range 0.1 instead
        while (Math.abs(arrXt[2]) > 0.1 || Math.abs(arrXt[3]) > 0.1) {

            newArrXt[0] = arrXt[0] + h * arrXt[2];     //position + step * velocity --> get the new position X
            newArrXt[1] = arrXt[1] + h * arrXt[3];     //position + step * velocity --> get the new position Y

            double partialX = PartialDerivate.derivateX(newArrXt[0],newArrXt[1]);    //the partial derivative of X
            double partialY = PartialDerivate.derivateY(newArrXt[0],newArrXt[1]);    //the partial derivative of Y

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



    public static double accelerationEquationX(double[] ArrXt,double derivative){
        return -g * derivative - uk * g * ArrXt[2] / Math.sqrt(Math.pow(ArrXt[2],2) + Math.pow(ArrXt[3],2));
    }
    public static double accelerationEquationY(double[] ArrXt,double derivative){
        return -g * derivative - uk * g * ArrXt[3] / Math.sqrt(Math.pow(ArrXt[2],2) + Math.pow(ArrXt[3],2));
    }
    public static double accelerationEquationXx(double VelX, double VelY, double derivative){
        return -g * derivative - uk * g * VelX / Math.sqrt(Math.pow(VelX,2) + Math.pow(VelY,2));
    }
    public static double accelerationEquationYy(double VelX, double VelY, double derivative){
        return -g * derivative - uk * g * VelY / Math.sqrt(Math.pow(VelX,2) + Math.pow(VelY,2));
    }

    public static double velEquation(double v0, double a) {
        return v0+=a*h;
    }

    public static void newRK4(double [] arrXt){

        while (Math.abs(arrXt[2]) > 0.1 || Math.abs(arrXt[3]) > 0.1) {

            double partialX = 0.1;    //the partial derivative of X
            double partialY = 0;    //the partial derivative of Y

            double newerArrXt [] = new double[4];
            System.arraycopy(arrXt, 0, newerArrXt, 0, arrXt.length);

            double k1posX= newerArrXt[0];
            double k1posY= newerArrXt[1];
            double k1VelX= newerArrXt[2];
            double k1VelY= newerArrXt[3];
            double k1AccelerationX = accelerationEquationXx(k1VelX,k1VelY,partialX);
            double k1AccelerationY = accelerationEquationYy(k1VelX,k1VelY,partialY);

            double k2posX= k1posX+(h*k1VelX)*(1.0/2);
            double k2posY= k1posY+(h*k1VelY)*(1.0/2);
            double k2VelX= k1VelX+(h*k1AccelerationX)*(1.0/2);
            double k2VelY= k1VelY+(h*k1AccelerationY)*(1.0/2);

            double k2AccelerationX = accelerationEquationXx(k2VelX,k2VelY,partialX);
            double k2AccelerationY = accelerationEquationYy(k2VelX,k2VelY,partialY);

            double k3posX= k1posX+(h*k2VelX)*(1.0/2);
            double k3posY= k1posY+(h*k2VelY)*(1.0/2);
            double k3VelX= k1VelX+(h*k2AccelerationX)*(1.0/2);
            double k3VelY= k1VelY+(h*k2AccelerationY)*(1.0/2);

            double k3AccelerationX = accelerationEquationXx(k3VelX,k3VelY,partialX);
            double k3AccelerationY = accelerationEquationYy(k3VelX,k3VelY,partialY);

            double k4posX= k1posX+(h*k3VelX);
            double k4posY= k1posY+(h*k3VelY);
            double k4VelX= k1VelX+(h*k3AccelerationX);
            double k4VelY= k1VelY+(h*k3AccelerationY);

            double k4AccelerationX = accelerationEquationXx(k4VelX,k4VelY,partialX);
            double k4AccelerationY = accelerationEquationYy(k4VelX,k4VelY,partialY);

            double calculatedXPos = (1.0/6)*(k1VelX+2*k2VelX+2*k3VelX+k4VelX)*h;
            double calculatedYPos = (1.0/6)*(k1VelY+2*k2VelY+2*k3VelY+k4VelY)*h;
            double calculatedXVel = (1.0/6)*(k1AccelerationX+2*k2AccelerationX+2*k3AccelerationX+k4AccelerationX)*h;
            double calculatedYVel = (1.0/6)*(k1AccelerationY+2*k2AccelerationY+2*k3AccelerationY+k4AccelerationY)*h;

            arrXt[0]+= calculatedXPos;
            arrXt[1]+= calculatedYPos;
            arrXt[2]+= calculatedXVel;
            arrXt[3]+= calculatedYVel;


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

    public static void BetterEstimationRK2(double [] arrXt) {
        //when the velocity of x, y are not 0, cuz its quite impossible to get them to 0,
        // so we take the little range 0.1 instead
        while (Math.abs(arrXt[2]) > 0.1 || Math.abs(arrXt[3]) > 0.1) {

            double partialX = 0.1;   //the partial derivative of X
            double partialY = 0;    //the partial derivative of Y
            double newerArrXt [] = new double[4];
            double newernewArrXt [] = new double[4];

            System.arraycopy(arrXt, 0, newerArrXt, 0, arrXt.length);
            System.arraycopy(arrXt, 0, newernewArrXt, 0, arrXt.length);

            //Step of Eulers
            newerArrXt[0] = arrXt[0] + h * arrXt[2];     //position + step * velocity --> get the new position X
            newerArrXt[1] = arrXt[1] + h * arrXt[3];     //position + step * velocity --> get the new position Y

            //the second-order derivative equation, which is the acceleration of X, Y
            double accX = -g * partialX - uk * g * arrXt[2] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);
            double accY = -g * partialY - uk * g * arrXt[3] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);

            newerArrXt[2] = arrXt[2] + h * accX;        //X velocity + step * acceleration --> new velocity
            newerArrXt[3] = arrXt[3] + h * accY;        //Y velocity + step * acceleration --> new velocity

            arrXt[0] = newernewArrXt[0] + (newernewArrXt[2]+newerArrXt[2])/2*h;
            arrXt[1] = newernewArrXt[1] + (newernewArrXt[3]+newerArrXt[3])/2*h;
            //currentVel += h*(accelarationcurrentpos +acclerationforfutpos)/2
            arrXt[2] += h*(accelerationEquationX(newernewArrXt, partialX)+accelerationEquationX(newerArrXt,partialX))/2;
            arrXt[3] += h*(accelerationEquationY(newernewArrXt, partialY)+accelerationEquationY(newerArrXt,partialY))/2;

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

//        System.out.println("Euler");
//        Euler(newArrXt);
//        newArrXt[0] = 0;
//        newArrXt[1] = 0;
//        newArrXt[2] = Double.parseDouble("0");
//        newArrXt[3] = Double.parseDouble("-1");
//
//        newArrXt[0] = 0;
//        newArrXt[1] = 0;
//        newArrXt[2] = Double.parseDouble("2");
//        newArrXt[3] = Double.parseDouble("0");

//        System.out.println("RK2");
//        BetterEstimationRK2(newArrXt);
//        newArrXt[0] = 0;
//        newArrXt[1] = 0;
//        newArrXt[2] = Double.parseDouble("2");
//        newArrXt[3] = Double.parseDouble("0");
//
//        newArrXt[0] = 0;
//        newArrXt[1] = 0;
//        newArrXt[2] = Double.parseDouble("2");
//        newArrXt[3] = Double.parseDouble("0");
//
//        System.out.println("RK4");
//        newRK4(newArrXt);
//        newArrXt[0] = 0;
//        newArrXt[1] = 0;
//        newArrXt[2] = Double.parseDouble("2");
//        newArrXt[3] = Double.parseDouble("0");

        newArrXt[0] = 0;
        newArrXt[1] = 0;
        newArrXt[2] = Double.parseDouble("2");
        newArrXt[3] = Double.parseDouble("0");

        System.out.println("Euler");
        Euler(newArrXt);
//        newArrXt[0] = 0;
//        newArrXt[1] = 0;
//        newArrXt[2] = Double.parseDouble("2");
//        newArrXt[3] = Double.parseDouble("0");

//        System.out.println("RK2");
//        RungeKuttaSecondOrder(newArrXt);
//        newArrXt[0] = 0;
//        newArrXt[1] = 0;
//        newArrXt[2] = Double.parseDouble("2");
//        newArrXt[3] = Double.parseDouble("0");

//        System.out.println("RK4");
//        RungeKuttaFourthOrder(newArrXt);
    }
}