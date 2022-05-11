import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class fileWriter {

    private static fileReader r = new fileReader();

    public static void function() {
        Scanner s = new Scanner(System.in);
        //String text = s.next();
        String text = r.function;

        {
            try {
                FileWriter writer = new FileWriter("C:/Users/DELL/OneDrive/Desktop/School/Crazy-Putting-Project-2/src/Terrain.java");

                //writer.write("package com.badlogic.Screens;\n");
                writer.write("public class Terrain{\n");
                writer.write("public static double terrain(double x,double y) {\n");
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
