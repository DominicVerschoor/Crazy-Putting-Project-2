public class Test {

    /**
     * Testing Results
     * Step 1 = 44
     * Step 0.5 = 43
     * Step 0.1 = 42.2
     * Step 0.01 = 41.97 (most efficient)
     * Step 0.001 = 41.95
     */

    public static void main(String[] args) {
        //determine the step
        double step = 0.001;

        double[] start = new double[2];
        start = startVel(60, 10);
        //initial hit
        double[] startAcc = Acc(-0.15, 0.1, start);
        double[] startVel = Vel(1, startAcc, start);

        //second calculation
        double[] acc = Acc(0, 0.1, startVel);
        double[] vel = Vel(step, acc, startVel);

        double i = step*3;
        while (acc[0] != 0 || acc[1] != 0) {
            //calculate the next acceleration and velocity
            acc = Acc(0, 0.1, vel);
            vel = Vel(step, acc, vel);

            //printing
            System.out.println("step: " + i + " x: " + "acc: " + acc[0] + " vel: " + vel[0]);
            System.out.println("step: " + i + " y: " + "acc: " + acc[1] + " vel: " + vel[1]);
            System.out.println();

            //increment step
            i+=step;
        }

    }

    public static double[] startVel(int angle, double speed) {
        double[] xyVel = new double[2];
        xyVel[0] = speed * Math.cos(Math.toRadians(angle));
        xyVel[1] = speed * Math.sin(Math.toRadians(angle));
        return xyVel;
    }

    public static double[] Acc(double slope, double friction, double[] inVel) {
        double[] xyAcc = new double[2];

        // calculating the bottom part of the fraction part of the equation 
        // to prevent illegal math operatoins and simplicity
        double botFraction = Math.sqrt(Math.pow(inVel[0], 2) + Math.pow(inVel[1], 2));

        if (botFraction == 0) {
            //if its x/0 (illegal function) the acceleration is 0
            xyAcc[0] = 0;
            xyAcc[1] = 0;
        }

        else {
            //calculate the new x,y acceleration using known equatoin
            xyAcc[0] = -(9.81) * (slope)
                    - (friction) * (9.81) * (inVel[0]) / (botFraction);

            xyAcc[1] = -(9.81) * (slope)
                    - (friction) * (9.81) * (inVel[1]) / (botFraction);
        }

        return xyAcc;
    }

    public static double[] Vel(double step, double[] acc, double[] inVel) {
        double[] xyVel = new double[2];

        // calculating the part in the root to prevent illegal math operatoins and simplicity
        double xVel = Math.pow(inVel[0], 2) + 2 * acc[0] * step;
        double yVel = Math.pow(inVel[1], 2) + 2 * acc[1] * step;

        // if the velocity is negative make it 0 instead (negatives cant be rooted)
        if (xVel < 0) {
            xVel = 0;
        }

        if (yVel < 0) {
            yVel = 0;
        }

        //calculate the new velocity
        xyVel[0] = Math.sqrt(xVel);
        xyVel[1] = Math.sqrt(yVel);
        return xyVel;
    }

}
