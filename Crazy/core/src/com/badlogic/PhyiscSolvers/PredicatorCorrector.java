package com.badlogic.PhyiscSolvers;

import com.badlogic.FileHandling.FileReader;

public class PredicatorCorrector {
    // this class uses 2-stage Adams-Bashforth method as a predicator
// and 2-stage Adams-Moulton method as a corrector
    static Rk2 rk2 = new Rk2();
    static PartialDerivative derive = new PartialDerivative();
    static FileReader read = new FileReader();
    static SteepAcceleration acceleration = new SteepAcceleration();
    private static final double g = 9.81;
    static double h = 0.001;
    private static double uk = read.muk;     // kinetic friction coefficient of grass
    private static double us = read.mus;
    static double[] predictorWn = new double[4];
    static double derivativeXwn1;
    static double accelerationXwn1;
    static double derivativeXwn2;
    static double accelerationXwn2;
    static double derivativeYwn1;
    static double accelerationYwn1;
    static double derivativeYwn2;
    static double accelerationYwn2;
    static double partialX;
    static double partialY;
    static double accelerationWnCorrectorX;
    static double accelerationWnCorrectorY;
    static double derivativeWnMinusTwoPX;
    static double accelerationStepRK2X;
    static double partialXCorrectorWnOneX;
    static double partialXCorrectorWnOneY;
    static double derivativevelPosArrayX;
    static double accelerationWnOnePX;
    static double accelerationWnOnePY;
    static double derivativeStepRK2X;
    static double derivativeWnOnePX;
    static double derivativeWnOnePY;
    static double accelerationvelPosArrayX;
    static double accelerationWnMinusTwoPX;
    static double accelerationWnMinusTwoPY;
    static double derivativeWnMinusTwoPY;
    static double derivativeCorrectorWnTwoX;
    static double derivativeCorrectorWnTwoY;
    static double accelerationCorrectorWnTwoX;
    static double accelerationCorrectorWnTwoY;
    static double derivativeCorrectorWnOneX;
    static double derivativeCorrectorWnOneY;
    static double accelerationCorrectorWnOneX;
    static double accelerationCorrectorWnOneY;
    static double[] correctorWnMinusTwo = new double[4];
    static double[] predictorWnMinusTwo = new double[4];
    static double[] predictrWnMinusOne = new double[4];
    static double[] stepRk2 = new double[4];
    static double[] tempArray = new double[4];
    static double[] k1 = new double[6];
    static double[] k2 = new double[6];
    static double[] correctorWnMinusOne = new double[4];
    static double[] wnCorrector = new double[4];
    static double derivativeStepRK2Y;
    static double accelerationStepRK2Y;
    static double derivativevelPosArrayY;
    static double accelerationvelPosArrayY;

    private float treeX = read.treeX;
    private float treeY = read.treeY;
    private float treeRadius = 2f;


    public static double[] calculate(double[] velPosArray) {
        rk2.accelerationType(false);
        System.arraycopy(velPosArray, 0, tempArray, 0, velPosArray.length);
        // FIRST STEP WN1 - BOOTSTRAPPING USING RK2
        k1 = rk2.k1Calculations(velPosArray);
        k2 = rk2.k2Calculations(k1);
        stepRk2 = rk2.finalCalculations(k1, k2);
        stepRk2[2] = tempArray[2] + stepRk2[2];
        stepRk2[3] = tempArray[3] + stepRk2[3];
        //SECOND STEP WN2 - PREDICTOR ADAM-BASHFORTH SECOND ORDER
        predictorWnMinusTwo[0] = stepRk2[0] + (3.0 / 2) * h * stepRk2[2] - (1.0 / 2) * h * tempArray[2];
        predictorWnMinusTwo[1] = stepRk2[1] + (3.0 / 2) * h * stepRk2[3] - (1.0 / 2) * h * tempArray[3];

        derivativeStepRK2X = derive.partialX(stepRk2[0], stepRk2[1]);
        derivativevelPosArrayX = derive.partialX(tempArray[0], tempArray[1]);
        derivativeStepRK2Y = derive.partialY(stepRk2[0], stepRk2[1]);
        derivativevelPosArrayY = derive.partialY(tempArray[0], tempArray[1]);

        accelerationStepRK2X = acceleration.accelerationEquationX(stepRk2[2], stepRk2[3], derivativeStepRK2X, derivativeStepRK2Y);
        accelerationvelPosArrayX = acceleration.accelerationEquationX(tempArray[2], tempArray[3], derivativevelPosArrayX, derivativevelPosArrayY);
        accelerationStepRK2Y = acceleration.accelerationEquationY(stepRk2[2], stepRk2[3], derivativeStepRK2X, derivativeStepRK2Y);
        accelerationvelPosArrayY = acceleration.accelerationEquationY(tempArray[2], tempArray[3], derivativevelPosArrayX, derivativevelPosArrayY);

        predictorWnMinusTwo[2] = stepRk2[2] + (3.0 / 2) * h * accelerationStepRK2X - (1.0 / 2) * h * accelerationvelPosArrayX;
        predictorWnMinusTwo[3] = stepRk2[3] + (3.0 / 2) * h * accelerationStepRK2Y - (1.0 / 2) * h * accelerationvelPosArrayY;
        //SECOND STEP WN2 - CORRECTOR ADAM MOULTON SECOND ORDER
        correctorWnMinusTwo[0] = stepRk2[0] + (1.0 / 2) * h * (predictorWnMinusTwo[2] + stepRk2[2]);
        correctorWnMinusTwo[1] = stepRk2[1] + (1.0 / 2) * h * (predictorWnMinusTwo[3] + stepRk2[3]);

        derivativeWnMinusTwoPX = derive.partialX(predictorWnMinusTwo[0], predictorWnMinusTwo[1]);
        derivativeWnMinusTwoPY = derive.partialY(predictorWnMinusTwo[0], predictorWnMinusTwo[1]);

        accelerationWnMinusTwoPX = acceleration.accelerationEquationX(predictorWnMinusTwo[2], predictorWnMinusTwo[3], derivativeWnMinusTwoPX, derivativeWnMinusTwoPY);
        accelerationWnMinusTwoPY = acceleration.accelerationEquationY(predictorWnMinusTwo[2], predictorWnMinusTwo[3], derivativeWnMinusTwoPX, derivativeWnMinusTwoPY);

        correctorWnMinusTwo[2] = stepRk2[2] + (1.0 / 2) * h * (accelerationWnMinusTwoPX + accelerationStepRK2X);
        correctorWnMinusTwo[3] = stepRk2[3] + (1.0 / 2) * h * (accelerationWnMinusTwoPY + accelerationStepRK2Y);
        //THIRD STEP WN3 - PREDICTOR ADAM BASHFORTH SECOND ORDER
        predictrWnMinusOne[0] = correctorWnMinusTwo[0] + (3.0 / 2) * h * correctorWnMinusTwo[2] - (1.0 / 2) * h * stepRk2[2];
        predictrWnMinusOne[1] = correctorWnMinusTwo[1] + (3.0 / 2) * h * correctorWnMinusTwo[3] - (1.0 / 2) * h * stepRk2[3];

        derivativeCorrectorWnTwoX = derive.partialX(correctorWnMinusTwo[0], correctorWnMinusTwo[1]);
        derivativeCorrectorWnTwoY = derive.partialY(predictorWnMinusTwo[0], predictorWnMinusTwo[1]);
        accelerationCorrectorWnTwoX = acceleration.accelerationEquationX(correctorWnMinusTwo[2], correctorWnMinusTwo[3], derivativeCorrectorWnOneX, derivativeCorrectorWnOneY);
        accelerationCorrectorWnTwoY = acceleration.accelerationEquationY(predictorWnMinusTwo[2], predictorWnMinusTwo[3], derivativeCorrectorWnOneX, derivativeCorrectorWnOneY);

        predictrWnMinusOne[2] = predictorWnMinusTwo[2] + (3.0 / 2) * h * accelerationCorrectorWnTwoX - (1.0 / 2) * h * accelerationStepRK2X;
        predictrWnMinusOne[3] = predictorWnMinusTwo[3] + (3.0 / 2) * h * accelerationCorrectorWnTwoY - (1.0 / 2) * h * accelerationStepRK2Y;
        //THIRD STEP WN3 - CORRECTOR ADAM MOULTON SECOND ORDER
        correctorWnMinusOne[0] = correctorWnMinusTwo[0] + (1.0 / 2) * h * (predictrWnMinusOne[2] + correctorWnMinusTwo[2]);
        correctorWnMinusOne[1] = correctorWnMinusTwo[1] + (1.0 / 2) * h * (predictrWnMinusOne[3] + correctorWnMinusTwo[3]);

        derivativeCorrectorWnTwoX = derive.partialX(correctorWnMinusTwo[0], correctorWnMinusTwo[1]);
        derivativeCorrectorWnTwoY = derive.partialY(correctorWnMinusTwo[0], correctorWnMinusTwo[1]);

        accelerationCorrectorWnTwoX = acceleration.accelerationEquationX(correctorWnMinusTwo[2], correctorWnMinusTwo[3], derivativeCorrectorWnTwoX, derivativeCorrectorWnTwoY);
        accelerationCorrectorWnTwoY = acceleration.accelerationEquationY(correctorWnMinusTwo[2], correctorWnMinusTwo[3], derivativeCorrectorWnTwoX, derivativeCorrectorWnTwoY);

        derivativeWnOnePX = derive.partialX(predictrWnMinusOne[0], predictrWnMinusOne[1]);
        derivativeWnOnePY = derive.partialY(predictrWnMinusOne[0], predictrWnMinusOne[1]);

        accelerationWnOnePX = acceleration.accelerationEquationX(predictrWnMinusOne[2], predictrWnMinusOne[3], derivativeWnOnePX, derivativeWnOnePY);
        accelerationWnOnePY = acceleration.accelerationEquationY(predictrWnMinusOne[2], predictrWnMinusOne[3], derivativeWnOnePX, derivativeWnOnePY);

        correctorWnMinusOne[2] = correctorWnMinusTwo[2] + (1.0 / 2) * h * (accelerationWnOnePX + accelerationCorrectorWnTwoX);
        correctorWnMinusOne[3] = correctorWnMinusTwo[3] + (1.0 / 2) * h * (accelerationWnOnePY + accelerationCorrectorWnTwoY);
        System.out.println("predictor");
        System.out.println(predictrWnMinusOne[0]);
        System.out.println(predictrWnMinusOne[1]);
        System.out.println(predictrWnMinusOne[2]);
        System.out.println(predictrWnMinusOne[3]);
        System.out.println("corrector");
        System.out.println(correctorWnMinusOne[0]);
        System.out.println(correctorWnMinusOne[1]);
        System.out.println(correctorWnMinusOne[2]);
        System.out.println(correctorWnMinusOne[3]);
        while (Math.abs(tempArray[2]) > 0.001 || Math.abs(tempArray[3]) > 0.001) {

            derivativeXwn1 = derive.partialX(correctorWnMinusOne[0], correctorWnMinusOne[1]);
            derivativeYwn1 = derive.partialY(correctorWnMinusOne[0], correctorWnMinusOne[1]);
            accelerationXwn1 = acceleration.accelerationEquationX(correctorWnMinusOne[2], correctorWnMinusOne[3], derivativeXwn1, derivativeYwn1);
            accelerationYwn1 = acceleration.accelerationEquationY(correctorWnMinusOne[2], correctorWnMinusOne[3], derivativeXwn1, derivativeYwn1);

            derivativeXwn2 = derive.partialX(correctorWnMinusTwo[0], correctorWnMinusTwo[1]);
            derivativeYwn2 = derive.partialY(correctorWnMinusTwo[0], correctorWnMinusTwo[1]);

            accelerationXwn2 = acceleration.accelerationEquationX(correctorWnMinusTwo[2], correctorWnMinusTwo[3], derivativeXwn2, derivativeYwn2);
            accelerationYwn2 = acceleration.accelerationEquationY(correctorWnMinusTwo[2], correctorWnMinusTwo[3], derivativeXwn2, derivativeYwn2);

            predictorWn[0] = correctorWnMinusOne[0] + (3.0 / 2) * h * correctorWnMinusOne[2] - (1.0 / 2) * h * correctorWnMinusTwo[2];
            predictorWn[1] = correctorWnMinusOne[1] + (3.0 / 2) * h * correctorWnMinusOne[3] - (1.0 / 2) * h * correctorWnMinusTwo[3];
            predictorWn[2] = correctorWnMinusOne[2] + (3.0 / 2) * h * accelerationXwn1 - (1.0 / 2) * h * accelerationXwn2;
            predictorWn[3] = correctorWnMinusOne[3] + (3.0 / 2) * h * accelerationYwn1 - (1.0 / 2) * h * accelerationYwn2;

            wnCorrector[0] = correctorWnMinusOne[0] + (1 / 2.0) * h * (predictorWn[2] + correctorWnMinusOne[2]);
            wnCorrector[1] = correctorWnMinusOne[1] + (1 / 2.0) * h * (predictorWn[3] + correctorWnMinusOne[3]);

            partialX = derive.partialX(predictorWn[0], predictorWn[1]);
            partialY = derive.partialY(predictorWn[0], predictorWn[1]);

            accelerationWnCorrectorX = acceleration.accelerationEquationX(predictorWn[2], predictorWn[3], partialX, partialY);
            accelerationWnCorrectorY = acceleration.accelerationEquationY(predictorWn[2], predictorWn[3], partialX, partialY);

            partialXCorrectorWnOneX = derive.partialX(correctorWnMinusOne[0], correctorWnMinusOne[1]);
            partialXCorrectorWnOneY = derive.partialY(correctorWnMinusOne[0], correctorWnMinusOne[1]);

            accelerationCorrectorWnOneX = acceleration.accelerationEquationX(correctorWnMinusOne[2], correctorWnMinusOne[3], partialXCorrectorWnOneX, partialXCorrectorWnOneY);
            accelerationCorrectorWnOneY = acceleration.accelerationEquationY(correctorWnMinusOne[2], correctorWnMinusOne[3], partialXCorrectorWnOneX, partialXCorrectorWnOneY);
            wnCorrector[2] = correctorWnMinusOne[2] + (1 / 2.0) * h * (accelerationCorrectorWnOneX + accelerationWnCorrectorX);
            wnCorrector[3] = correctorWnMinusOne[3] + (1 / 2.0) * h * (accelerationCorrectorWnOneY + accelerationWnCorrectorY);
//                System.out.println("predictor");
//                System.out.println(predictorWn[0]);
//                System.out.println(predictorWn[1]);
//                System.out.println(predictorWn[2]);
//                System.out.println(predictorWn[3]);
//                System.out.println("corrector");
//                System.out.println(wnCorrector[0]);
//                System.out.println(wnCorrector[1]);
//                System.out.println(wnCorrector[2]);
//                System.out.println(wnCorrector[3]);


            System.arraycopy(correctorWnMinusOne, 0, correctorWnMinusTwo, 0, predictrWnMinusOne.length);
            System.arraycopy(wnCorrector, 0, correctorWnMinusOne, 0, wnCorrector.length);
            System.arraycopy(wnCorrector, 0, tempArray, 0, wnCorrector.length);

            if ((Math.abs(predictorWn[2]) <= 0.001 && Math.abs(predictorWn[3]) <= 0.001) && (Math.abs(partialX) > 0.001 || Math.abs(partialY) > 0.001)) {
                double sqrt = Math.sqrt(partialX * partialX + partialY * partialY);
                if (us > sqrt) {
                    break;
                } else {
                    predictorWn[2] = h * -g * partialX + uk * g * partialX / sqrt;
                    predictorWn[3] = h * -g * partialY + uk * g * partialY / sqrt;

                }
            }
        }
        System.out.println(tempArray[0]);
        System.out.println(tempArray[1]);
        System.out.println(tempArray[2]);
        System.out.println(tempArray[3]);
        return tempArray;
    }

    public static void main(String[] args) {
        double[] tempBallVector = new double[4];
        tempBallVector[0] = 0;
        tempBallVector[1] = 0;
        tempBallVector[2] = 2;
        tempBallVector[3] = 0;
        calculate(tempBallVector);
    }

}
