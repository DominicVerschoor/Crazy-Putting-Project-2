package com.badlogic.Screens;
import java.io.IOException;

public class FileWriter {
    private static FileReader r = new FileReader();

    public static void function () {
        String text = r.function;

        {
            try {
                java.io.FileWriter writer = new java.io.FileWriter("C://Users//DELL//OneDrive//Desktop//School//Crazy-Putting-Project-2//Crazy//core//src//com//badlogic//Screens//Terrain.java");

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