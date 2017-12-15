package es_miniproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ES_MiniProject {
   
    public static void main(String[] args) {
        String file_path = "C:\\Users\\User\\Documents\\NetBeansProjects\\ES_MiniProject\\src\\files\\Book1.csv"; 
        String line = "";
        String cvsSplitBy = ",";
        double[][] table = new double[250][8];
        double[] summations = new double[8]; 
        
        readFile(file_path, cvsSplitBy, table);
        solveXSqrd (table); 
        solveXY(table); 
        solveSummations(table, summations); 
        
        displayContents(table,summations); 
        

    }
    
    public static void displayContents (double[][] table,  double[] summations){
        for(int x = 0 ; x < table.length ; x++){
            for(int y=0 ; y < 8; y++){
                System.out.print(table[x][y] + " | "); 
            }
            System.out.println("");
        }
        
        
        System.out.println("**********************************************************************");
         for(int y=0 ; y < 8; y++){
             switch(y){
                 case 0 :  System.out.println("Summation of X: " + summations[y] ); break; 
                 case 1 :  System.out.println("Summation of Y: " + summations[y] ); break; 
                 case 2 :  System.out.println("Summation of X Sqrd: " + summations[y] ); break;
                 case 3 :  System.out.println("Summation of XY: " + summations[y] ); break;
             }
        }
    }
    public static void readFile( String file_path, String splitBy, double[][] table){
        String line = "";
        int ndx = 0; 
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] xy = line.split(splitBy);
                table[ndx][0] = Double.parseDouble(xy[0]); 
                table[ndx][1] = Double.parseDouble(xy[1]); 
                ndx++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void solveXSqrd (double[][] table){
       for(int x = 0 ; x < table.length ; x++ ){
          table[x][2] = table[x][0] *  table[x][0]; 
       }
    }
    
    public static void solveXY(double[][] table){
       for(int x = 0 ; x < table.length ; x++ ){
          table[x][3] = table[x][0] *  table[x][1]; 
       }
    }
    
    public static void solveSummations(double[][] table, double[] summations){
         for(int x = 0 ; x < table.length ; x++){
            for(int y=0 ; y < 8; y++){
                summations[y] += table[x][y]; 
            }  
        }
    }
}
