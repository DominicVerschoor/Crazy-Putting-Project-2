package com.badlogic.FileHandling;

import java.io.IOException;

public class FileWriter {
    private static FileReader r = new FileReader();

    public static void function () {
        String text = r.function;

        {
            try {
                java.io.FileWriter writer = new java.io.FileWriter("Crazy/core/src/com/badlogic/GameLogistics/TerrainInput.java");

                writer.write("package com.badlogic.GameLogistics;\n");
                writer.write("public class TerrainInput{\n");
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