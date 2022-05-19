package com.badlogic.Screens;


import java.io.*;
import java.util.ArrayList;


public class FileReader {
    /**
     @param x0 x-coordinate of the initial position
     @param y0 y-coordinate of the initial position
     @param xt x-coordinate of the target position
     @param yt y-coordinate of the target position
     @param r radius of the target
     @param muk kinetic friction coefficient of grass
     @param mus static friction coefficient of grass
     @param function heightProfile
     @param sandPitXMin x-range of a sand pit
     @param sandPitXMax x-range of a sand pit
     @param sandPitYMin y-range of a sand pit
     @param sandPitYMax y-range of a sand pit
     */

    float x0,y0,xt,yt,r,muk,mus,sandPitXMin,sandPitXMax,sandPitYMin,sandPitYMax,muks,muss; //
    String function;

    public FileReader(){
        readFile();
    }

    /**
     Reading the values from the given file and assigning them globally
     */
    public void readFile(){
        try {
            ArrayList<Float> addLines = new ArrayList<Float>();
            BufferedReader reader = new BufferedReader(new java.io.FileReader("C://Users//DELL//OneDrive//Desktop//School//Crazy-Putting-Project-2//Crazy//desktop//build//resources//main//input.txt"));
            String lines = "";
            while ((lines = reader.readLine()) != null){
                if (lines.contains("=")){
                   if(lines.charAt(0)=='h')
                   {
                       String sub = lines.substring(lines.indexOf("=")+1,lines.length());
                       sub = sub.replaceAll(" ","");
                       function=sub;
                   }

                   else
                   {
                    String sub = lines.substring(lines.indexOf("=")+1,lines.length());
                    sub = sub.replaceAll(" ","");
                    addLines.add((float)Double.parseDouble(sub));
                    // System.out.println(sub);

                   }
                }else System.out.println("DONE");

            }

              this.x0=addLines.get(0);
              this.y0=addLines.get(1);
              this.xt=addLines.get(2);
              this.yt=addLines.get(3);
              this.r=addLines.get(4);
              this.muk=addLines.get(5);
              this.mus=addLines.get(6);
              this.sandPitXMin=addLines.get(7);
              this.sandPitXMax=addLines.get(8);
              this.sandPitYMin=addLines.get(9);
              this.sandPitYMax=addLines.get(10);
              this.muks=addLines.get(11);
              this.muss=addLines.get(12);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
