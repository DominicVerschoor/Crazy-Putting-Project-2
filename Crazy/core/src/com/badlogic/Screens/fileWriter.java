package com.badlogic.Screens;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class fileWriter {
    private static fileReader r = new fileReader();

    public void function () {
        String text = r.function;


        {
            try {
                FileWriter writer = new FileWriter("C://Users//DELL//OneDrive//Desktop//School//Project 2//Crazy//Crazy//core//src//com//badlogic//Screens//Terrain.java");

                writer.write("package com.badlogic.Screens;\n");
                writer.write("public class Terrain{\n");
                writer.write("public double terrain(double x,double y) {\n");
                writer.write("return " + text + ";\n");
                writer.write("}");
                writer.write("\n}\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}