package es_miniproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ES_MiniProject {

  
    public static void main(String[] args) {
        String file_path = "C:\\Users\\User\\Documents\\NetBeansProjects\\ES_MiniProject\\src\\files\\Book1.csv"; 
        String line = "";
        String cvsSplitBy = ",";

        readFile(file_path, cvsSplitBy);

    }
    
    public static void readFile( String file_path, String splitBy){
        String line = ""; 
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] xy = line.split(splitBy);

                System.out.println("x= " + xy[0] + " , y=" + xy[1] );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
