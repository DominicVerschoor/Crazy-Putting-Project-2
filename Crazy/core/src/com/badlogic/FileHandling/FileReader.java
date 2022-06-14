package com.badlogic.FileHandling;


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
     @param treeX x-coordinate of the tree position    //tree
     @param treeY y-coordinate of the tree position    //tree
     */

    public float x0;
    public float y0;
    public float xt;
    public float yt;
    public float r;
    public float muk;
    public float mus;
    public float sandPitXMin;
    public float sandPitXMax;
    public float sandPitYMin;
    public float sandPitYMax;
    public float muks;
    public float muss;
    public String function;
    public float treeX;
    public float treeY;

    public FileReader(){
        readFile();
    }

    public void readFile(){
        try {
            ArrayList<Float> addLines = new ArrayList<Float>();
            BufferedReader reader = new BufferedReader(new java.io.FileReader("Crazy/desktop/build/resources/main/input.txt"));
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
                    }
                }
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
            this.treeX=addLines.get(13);
            this.treeY=addLines.get(14);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}