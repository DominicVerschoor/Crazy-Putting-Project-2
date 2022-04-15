
import java.io.*;
import java.util.ArrayList;


public class fileReader {
    /**
     @param x0 x-coordinate of the initial position
     @param y0 y-coordinate of the initial position
     @param xt x-coordinate of the target position
     @param yt y-coordinate of the target position
     @param r radius of the target
     @param muk kinetic friction coefficient of grass
     @param mus static friction coefficient of grass
     @param function heightProfile
     */

    Double x0,y0,xt,yt,r,muk,mus; //
    String function;

    public fileReader(){
        readFile();
    }

    /**
     Reading the values from the given file and assigning them globally
     */
    public  void readFile(){
        try {
            ArrayList<Double> addLines = new ArrayList<Double>();
            BufferedReader reader = new BufferedReader(new FileReader("C://Users//DELL//OneDrive//Desktop//School//Project 2//Golf2D//src//example_inputfile.txt"));
            String lines = "";
            while ((lines = reader.readLine()) != null){
                if (lines.indexOf("=")>=0){
                   if(lines.charAt(0)=='h')
                   {
                       String sub = lines.substring(lines.indexOf("=")+1,lines.length());
                       sub = sub.replaceAll("Math.", "");
                       sub = sub.replaceAll(" ","");
                       function=sub;
                   }
                   else
                   {
                    String sub = lines.substring(lines.indexOf("=")+1,lines.length());
                    sub = sub.replaceAll(" ","");
                    addLines.add(Double.parseDouble(sub));
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

            reader.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
