package com.badlogic.Screens;

public class PhyscsTestFile {
    public static void main(String[] args) {
        Euler eulerTest = new Euler();
        RK2 rk2Test = new RK2();
        RK4 rk4Test = new RK4();
        FileReader read = new FileReader();

        double[] testArray = new double[4];

        double x0 = 0;
        double y0 = -1;
        double vx0 = 1;
        double vy0 = 0;

        System.out.println("Euler");
        testArray[0] = x0;
        testArray[1] = y0;
        testArray[2] = vx0;
        testArray[3] = vy0;

        testArray=eulerTest.Euler(testArray);
        System.out.println(testArray[0]);
        System.out.println(testArray[1]);
        System.out.println("");


        System.out.println("RK2");
        testArray[0] = x0;
        testArray[1] = y0;
        testArray[2] = vx0;
        testArray[3] = vy0;

        testArray=rk2Test.BetterEstimationRK2(testArray);
        System.out.println(testArray[0]);
        System.out.println(testArray[1]);
        System.out.println("");


        System.out.println("RK4");
        testArray[0] = x0;
        testArray[1] = y0;
        testArray[2] = vx0;
        testArray[3] = vy0;

        testArray=rk4Test.newRK4(testArray);
        System.out.println(testArray[0]);
        System.out.println(testArray[1]);
        System.out.println("");
    }
}
