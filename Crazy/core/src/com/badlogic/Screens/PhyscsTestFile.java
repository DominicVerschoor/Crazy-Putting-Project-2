package com.badlogic.Screens;

public class PhyscsTestFile {
    public static void main(String[] args) {
        Euler eulerTest = new Euler();
        RK2 rk2Test = new RK2();
        RK4 rk4Test = new RK4();

        double time1;
        double time2;

        double[] testArray = new double[4];

        double x0 = 0;
        double y0 = 0;
        double vx0 = 0;
        double vy0 = 0;

        System.out.println("Euler");
        testArray[0] = x0;
        testArray[1] = y0;
        testArray[2] = vx0;
        testArray[3] = vy0;
        time1=System.currentTimeMillis();
        eulerTest.Euler(testArray);
        time2 = System.currentTimeMillis();
        System.out.println("Computation Time: "+ Math.abs(time1 - time2));
        System.out.println();

        System.out.println("RK2");
        testArray[0] = x0;
        testArray[1] = y0;
        testArray[2] = vx0;
        testArray[3] = vy0;
        time1=System.currentTimeMillis();
        rk2Test.BetterEstimationRK2(testArray);
        time2 = System.currentTimeMillis();
        System.out.println("Computation Time: "+ Math.abs(time1 - time2));
        System.out.println();

        System.out.println("RK4");
        testArray[0] = x0;
        testArray[1] = y0;
        testArray[2] = vx0;
        testArray[3] = vy0;
        time1 = System.currentTimeMillis();
        rk4Test.newRK4(testArray);
        time2 = System.currentTimeMillis();
        System.out.println("Computation Time: "+ Math.abs(time1 - time2));
    }
}
