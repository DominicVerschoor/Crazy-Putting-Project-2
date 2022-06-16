package com.badlogic.PhyiscSolvers;

public class PhysicsTestFile {
    public static void main(String[] args) {
        Euler eulerTest = new Euler();
        Rk2 rk2Test = new Rk2();
        Rk4 rk4Test = new Rk4();

        double time1;
        double time2;

        double[] testArray = new double[4];

        double x0 = 0;
        double y0 = 0;
        double vx0 = 0;
        double vy0 = 0;
        boolean accelerationType = false;

        System.out.println("Euler");
        testArray[0] = x0;
        testArray[1] = y0;
        testArray[2] = vx0;
        testArray[3] = vy0;
        eulerTest.accelerationType(accelerationType);
        time1=System.currentTimeMillis();
        eulerTest.solve(testArray);
        time2 = System.currentTimeMillis();
        System.out.println("Computation Time: "+ Math.abs(time1 - time2));
        System.out.println();

        System.out.println("RK2");
        testArray[0] = x0;
        testArray[1] = y0;
        testArray[2] = vx0;
        testArray[3] = vy0;
        rk2Test.accelerationType(accelerationType);
        time1=System.currentTimeMillis();
        rk2Test.solve(testArray);
        time2 = System.currentTimeMillis();
        System.out.println("Computation Time: "+ Math.abs(time1 - time2));
        System.out.println();

        System.out.println("RK4");
        testArray[0] = x0;
        testArray[1] = y0;
        testArray[2] = vx0;
        testArray[3] = vy0;
        rk4Test.accelerationType(accelerationType);
        time1 = System.currentTimeMillis();
        rk4Test.solve(testArray);
        time2 = System.currentTimeMillis();
        System.out.println("Computation Time: "+ Math.abs(time1 - time2));
    }
}
