package com.badlogic.PhyiscSolvers;

import com.badlogic.Bots.AdvancedHillClibing;
import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameLogistics.ShootUser;
import com.badlogic.GameLogistics.TerrainInput;
import com.badlogic.GameScreens.OptionsGameScreen;

public class PredicatorCorrector implements Solver{
// this class uses 2-stage Adams-Bashforth method as a predicator
// and 2-stage Adams-Moulton method as a corrector
    private Rk2 rk2 = new Rk2();
    private PartialDerivative derive = new PartialDerivative();
    private FileReader read = new FileReader();
    private static TerrainInput function = new TerrainInput();

    private Acceleration acceleration;
    private static final double g = 9.81;
    private double h = 0.0001;
    private double uk = read.muk;     // kinetic friction coefficient of grass
    private double us = read.mus;

    private double derivativeXwn1,derivativeXwn2,accelerationXwn1,accelerationXwn2,derivativeYwn1,accelerationYwn1,derivativeYwn2,accelerationYwn2,partialX,accelerationvelPosArrayY,
            partialY,accelerationWnCorrectorX,accelerationWnCorrectorY,derivativeWnMinusTwoPX,accelerationStepRK2X,partialXCorrectorWnOneX,partialXCorrectorWnOneY,
            derivativevelPosArrayX,accelerationWnOnePX,accelerationWnOnePY,derivativeStepRK2X,derivativeWnOnePX,derivativeWnOnePY,accelerationvelPosArrayX,accelerationWnMinusTwoPX,
            accelerationWnMinusTwoPY,derivativeWnMinusTwoPY,derivativeCorrectorWnTwoX,derivativeCorrectorWnTwoY, accelerationCorrectorWnTwoX,accelerationCorrectorWnTwoY,
            accelerationCorrectorWnOneX,accelerationCorrectorWnOneY,derivativeStepRK2Y,accelerationStepRK2Y,derivativevelPosArrayY;

    private double [] predictorWn = new double[4];
    private double [] correctorWnMinusTwo = new double[4];
    private double [] predictorWnMinusTwo = new double[4];
    private double [] predictrWnMinusOne = new double[4];
    private double [] stepRk2= new double[4];
    private double [] tempArray= new double[4];
    private double [] k1= new double[6];
    private double [] k2= new double[6];
    private double [] correctorWnMinusOne = new double[4];
    private double [] wnCorrector = new double[4];


        public double[] solve (double[] ballVector){
            double initialX = ballVector[0];
            double initialY = ballVector[1];
            ballVector = speedLimit(ballVector);

            rk2.accelerationType(ShootUser.accelerationButton);
            System.arraycopy(ballVector, 0, tempArray, 0, ballVector.length);
            // FIRST STEP WN1 - BOOTSTRAPPING USING RK2
            k1=rk2.k1Calculations(ballVector);
            k2=rk2.k2Calculations(k1);
            stepRk2 = rk2.finalCalculations(k1,k2);
            stepRk2[2]=tempArray[2]+stepRk2[2];
            stepRk2[3]=tempArray[3]+stepRk2[3];
            System.out.println(stepRk2[0]);
            System.out.println(stepRk2[1]);
            System.out.println(stepRk2[2]);
            System.out.println(stepRk2[3]);
            //SECOND STEP WN2 - PREDICTOR ADAM-BASHFORTH SECOND ORDER
            predictorWnMinusTwo[0] = stepRk2[0]+(3.0/2)*h*stepRk2[2]-(1.0/2)*h*tempArray[2];
            predictorWnMinusTwo[1] = stepRk2[1]+(3.0/2)*h*stepRk2[3]-(1.0/2)*h*tempArray[3];

            derivativeStepRK2X = derive.partialX(stepRk2[0],stepRk2[1]);
            derivativevelPosArrayX = derive.partialX(tempArray[0],tempArray[1]);
            derivativeStepRK2Y = derive.partialY(stepRk2[0],stepRk2[1]);
            derivativevelPosArrayY = derive.partialY(tempArray[0],tempArray[1]);

            accelerationStepRK2X = acceleration.accelerationEquationX(stepRk2[2],stepRk2[3],derivativeStepRK2X,derivativeStepRK2Y);
            accelerationvelPosArrayX = acceleration.accelerationEquationX(tempArray[2],tempArray[3],derivativevelPosArrayX,derivativevelPosArrayY);
            accelerationStepRK2Y = acceleration.accelerationEquationY(stepRk2[2],stepRk2[3],derivativeStepRK2X,derivativeStepRK2Y);
            accelerationvelPosArrayY = acceleration.accelerationEquationY(tempArray[2],tempArray[3],derivativevelPosArrayX,derivativevelPosArrayY);

            predictorWnMinusTwo[2] = stepRk2[2]+(3.0/2)*h*accelerationStepRK2X-(1.0/2)*h*accelerationvelPosArrayX;
            predictorWnMinusTwo[3] = stepRk2[3]+(3.0/2)*h*accelerationStepRK2Y-(1.0/2)*h*accelerationvelPosArrayY;
            //SECOND STEP WN2 - CORRECTOR ADAM MOULTON SECOND ORDER
            correctorWnMinusTwo[0]= stepRk2[0]+(1.0/2)*h*(predictorWnMinusTwo[2]+stepRk2[2]);
            correctorWnMinusTwo[1]= stepRk2[1]+(1.0/2)*h*(predictorWnMinusTwo[3]+stepRk2[3]);

            derivativeWnMinusTwoPX = derive.partialX(predictorWnMinusTwo[0],predictorWnMinusTwo[1]);
            derivativeWnMinusTwoPY= derive.partialY(predictorWnMinusTwo[0],predictorWnMinusTwo[1]);

            accelerationWnMinusTwoPX = acceleration.accelerationEquationX(predictorWnMinusTwo[2],predictorWnMinusTwo[3],derivativeWnMinusTwoPX,derivativeWnMinusTwoPY);
            accelerationWnMinusTwoPY = acceleration.accelerationEquationY(predictorWnMinusTwo[2],predictorWnMinusTwo[3],derivativeWnMinusTwoPX,derivativeWnMinusTwoPY);

            correctorWnMinusTwo[2]= stepRk2[2]+(1.0/2)*h*(accelerationWnMinusTwoPX+accelerationStepRK2X);
            correctorWnMinusTwo[3]= stepRk2[3]+(1.0/2)*h*(accelerationWnMinusTwoPY+accelerationStepRK2Y);
            //THIRD STEP WN3 - PREDICTOR ADAM BASHFORTH SECOND ORDER
            predictrWnMinusOne[0] = correctorWnMinusTwo[0]+(3.0/2)*h* correctorWnMinusTwo[2]-(1.0/2)*h*stepRk2[2];
            predictrWnMinusOne[1] = correctorWnMinusTwo[1]+(3.0/2)*h* correctorWnMinusTwo[3]-(1.0/2)*h*stepRk2[3];

            derivativeCorrectorWnTwoX = derive.partialX(correctorWnMinusTwo[0], correctorWnMinusTwo[1]);
            derivativeCorrectorWnTwoY = derive.partialY(correctorWnMinusTwo[0], correctorWnMinusTwo[1]);

            accelerationCorrectorWnTwoX= acceleration.accelerationEquationX(correctorWnMinusTwo[2], correctorWnMinusTwo[3],derivativeCorrectorWnTwoX,derivativeCorrectorWnTwoY);
            accelerationCorrectorWnTwoY = acceleration.accelerationEquationY(predictorWnMinusTwo[2], predictorWnMinusTwo[3],derivativeCorrectorWnTwoX,derivativeCorrectorWnTwoY);

            predictrWnMinusOne[2] = correctorWnMinusTwo[2]+(3.0/2)*h*accelerationCorrectorWnTwoX-(1.0/2)*h*accelerationStepRK2X;
            predictrWnMinusOne[3] = correctorWnMinusTwo[3]+(3.0/2)*h*accelerationCorrectorWnTwoY-(1.0/2)*h*accelerationStepRK2Y;
            //THIRD STEP WN3 - CORRECTOR ADAM MOULTON SECOND ORDER
            correctorWnMinusOne[0]= correctorWnMinusTwo[0]+(1.0/2)*h*(predictrWnMinusOne[2]+correctorWnMinusTwo[2]);
            correctorWnMinusOne[1]= correctorWnMinusTwo[1]+(1.0/2)*h*(predictrWnMinusOne[3]+correctorWnMinusTwo[3]);

            derivativeCorrectorWnTwoX = derive.partialX(correctorWnMinusTwo[0],correctorWnMinusTwo[1]);
            derivativeCorrectorWnTwoY = derive.partialY(correctorWnMinusTwo[0],correctorWnMinusTwo[1]);

            accelerationCorrectorWnTwoX = acceleration.accelerationEquationX(correctorWnMinusTwo[2],correctorWnMinusTwo[3],derivativeCorrectorWnTwoX,derivativeCorrectorWnTwoY);
            accelerationCorrectorWnTwoY = acceleration.accelerationEquationY(correctorWnMinusTwo[2],correctorWnMinusTwo[3],derivativeCorrectorWnTwoX,derivativeCorrectorWnTwoY);

            derivativeWnOnePX = derive.partialX(predictrWnMinusOne[0],predictrWnMinusOne[1]);
            derivativeWnOnePY = derive.partialY(predictrWnMinusOne[0],predictrWnMinusOne[1]);

            accelerationWnOnePX = acceleration.accelerationEquationX(predictrWnMinusOne[2],predictrWnMinusOne[3],derivativeWnOnePX,derivativeWnOnePY);
            accelerationWnOnePY = acceleration.accelerationEquationY(predictrWnMinusOne[2],predictrWnMinusOne[3],derivativeWnOnePX,derivativeWnOnePY);

            correctorWnMinusOne[2]= correctorWnMinusTwo[2]+(1.0/2)*h*(accelerationWnOnePX+accelerationCorrectorWnTwoX);
            correctorWnMinusOne[3]= correctorWnMinusTwo[3]+(1.0/2)*h*(accelerationWnOnePY+accelerationCorrectorWnTwoY);

            while (Math.abs(tempArray[2]) > 0.0001 || Math.abs(tempArray[3]) > 0.0001) {

                uk = read.muk;
                us = read.mus;
                if ((tempArray[0] >= read.sandPitXMin && tempArray[0] <= read.sandPitXMin)
                        && (tempArray[1] >= read.sandPitXMin && tempArray[1] <= read.sandPitXMin)) {
                    uk = read.muks;
                    us = read.muss;
                    System.out.println("ew sand");
                }

                derivativeXwn1 = derive.partialX(correctorWnMinusOne[0], correctorWnMinusOne[1]);
                derivativeYwn1 = derive.partialY(correctorWnMinusOne[0], correctorWnMinusOne[1]);
                accelerationXwn1 = acceleration.accelerationEquationX(correctorWnMinusOne[2], correctorWnMinusOne[3],derivativeXwn1,derivativeYwn1);
                accelerationYwn1 = acceleration.accelerationEquationY(correctorWnMinusOne[2], correctorWnMinusOne[3],derivativeXwn1,derivativeYwn1);

                derivativeXwn2 = derive.partialX(correctorWnMinusTwo[0], correctorWnMinusTwo[1]);
                derivativeYwn2 = derive.partialY(correctorWnMinusTwo[0], correctorWnMinusTwo[1]);

                accelerationXwn2 = acceleration.accelerationEquationX(correctorWnMinusTwo[2], correctorWnMinusTwo[3],derivativeXwn2,derivativeYwn2);
                accelerationYwn2 = acceleration.accelerationEquationY(correctorWnMinusTwo[2], correctorWnMinusTwo[3],derivativeXwn2,derivativeYwn2);

                predictorWn[0] = correctorWnMinusOne[0]+(3.0/2)*h* correctorWnMinusOne[2]-(1.0/2)*h* correctorWnMinusTwo[2];
                predictorWn[1] = correctorWnMinusOne[1]+(3.0/2)*h* correctorWnMinusOne[3]-(1.0/2)*h* correctorWnMinusTwo[3];
                predictorWn[2] = correctorWnMinusOne[2]+(3.0/2)*h*accelerationXwn1-(1.0/2)*h*accelerationXwn2;
                predictorWn[3] = correctorWnMinusOne[3]+(3.0/2)*h*accelerationYwn1-(1.0/2)*h*accelerationYwn2;

                wnCorrector[0] = correctorWnMinusOne[0]+(1/2.0)*h*(predictorWn[2]+correctorWnMinusOne[2]);
                wnCorrector[1] = correctorWnMinusOne[1]+(1/2.0)*h*(predictorWn[3]+correctorWnMinusOne[3]);

                partialX = derive.partialX(predictorWn[0], predictorWn[1]);
                partialY = derive.partialY(predictorWn[0], predictorWn[1]);

                accelerationWnCorrectorX = acceleration.accelerationEquationX(predictorWn[2],predictorWn[3],partialX,partialY);
                accelerationWnCorrectorY = acceleration.accelerationEquationY(predictorWn[2],predictorWn[3],partialX,partialY);

                partialXCorrectorWnOneX = derive.partialX(correctorWnMinusOne[0], correctorWnMinusOne[1]);
                partialXCorrectorWnOneY = derive.partialY(correctorWnMinusOne[0], correctorWnMinusOne[1]);

                accelerationCorrectorWnOneX = acceleration.accelerationEquationX(correctorWnMinusOne[2],correctorWnMinusOne[3],partialXCorrectorWnOneX,partialXCorrectorWnOneY);
                accelerationCorrectorWnOneY = acceleration.accelerationEquationY(correctorWnMinusOne[2],correctorWnMinusOne[3],partialXCorrectorWnOneX,partialXCorrectorWnOneY);
                wnCorrector[2]= correctorWnMinusOne[2]+(1/2.0)*h*(accelerationCorrectorWnOneX+accelerationWnCorrectorX);
                wnCorrector[3]= correctorWnMinusOne[3]+(1/2.0)*h*(accelerationCorrectorWnOneY+accelerationWnCorrectorY);

                System.arraycopy(wnCorrector, 0, tempArray, 0, wnCorrector.length);

                if (function.terrain(tempArray[0], tempArray[1]) < 0) {
                    tempArray[0] = initialX;
                    tempArray[1] = initialY;
                    System.out.println("HELP ME im unda tha wata ");
                    return tempArray;
                }
                if ((tempArray[0] > 21 || tempArray[0] < -21 || tempArray[1] > 21 || tempArray[1] < -21)&&!AdvancedHillClibing.inAdvancedBot) {
                    tempArray[0] = initialX;
                    tempArray[1] = initialY;
                    System.out.println("BALL OUT OF BOUNDS");
                    return tempArray;
                }

                if ((Math.abs(tempArray[2]) <= 0.0001 && Math.abs(tempArray[3]) <= 0.0001) && (Math.abs(partialXCorrectorWnOneX) > 0.0001 || Math.abs(partialXCorrectorWnOneY) > 0.0001)) {
                    System.out.println(" im in if");
                    double sqrt = Math.sqrt(partialXCorrectorWnOneX * partialXCorrectorWnOneX + partialXCorrectorWnOneY * partialXCorrectorWnOneY);
                    if (us > sqrt) {
                        break;
                    } else {
                        tempArray[2] = h * -g * partialXCorrectorWnOneX + uk * g * partialXCorrectorWnOneY / sqrt;
                        tempArray[3] = h * -g * partialXCorrectorWnOneX + uk * g * partialXCorrectorWnOneY / sqrt;

                    }
                }

                System.arraycopy(correctorWnMinusOne, 0, correctorWnMinusTwo, 0, predictrWnMinusOne.length);
                System.arraycopy(wnCorrector, 0, correctorWnMinusOne, 0, wnCorrector.length);
            }
            System.out.println(tempArray[0]);
            System.out.println(tempArray[1]);
            return tempArray;
        }
    public double[] speedLimit(double[] ballVector) {
        if (ballVector[2] > 5) {
            ballVector[2] = 5;
        }
        if (ballVector[2] < -5) {
            ballVector[2] = -5;
        }
        if (ballVector[3] > 5) {
            ballVector[3] = 5;
        }
        if (ballVector[3] < -5) {
            ballVector[3] = -5;
        }
        return ballVector;
    }

    public void accelerationType(boolean buttonInput){
        if (buttonInput){
            acceleration = new BasicAcceleration();
        }else{
            acceleration = new SteepAcceleration();
        }
    }
}
