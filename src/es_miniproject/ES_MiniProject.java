package es_miniproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ES_MiniProject {
    private static final int NUM_ITERATIONS = 10000;
    private static final double LEARNING_RATE = 0.0001;
   
    public static void main(String[] args) {
        String file_path = "C:\\Users\\User\\Documents\\NetBeansProjects\\ES_MiniProject\\src\\files\\Old.csv"; 
        String line = "";
        String cvsSplitBy = ",";
        double[][] table = new double[731][8];
        double[] summations = new double[8]; 
        double slope, yintercept; 
        
        // completing the table
        readFile(file_path, cvsSplitBy, table);
        solveXSqrd (table); 
        solveXY(table); 
        solveSummations(table, summations);
        
       
        slope = solveforSlope(table.length, summations);
        yintercept = solveforIntercept(table.length, summations, slope);
        
        
        solveYHat(table, slope, yintercept); 
        solveErrors(table);
        solveSummations(table, summations); 
        
        
        displayContents(table,summations); 
        displayLinerRegression (slope, yintercept);
        
        GradientDescent gd = new GradientDescent(table, LEARNING_RATE, 0, 0, NUM_ITERATIONS);
        gd.Run();
    }
    
    public static void displayContents (double[][] table,  double[] summations){
        for(int x = 0 ; x < table.length ; x++){
            for(int y=0 ; y < 8; y++){
                System.out.printf(" %.4f | " , table[x][y] ); 
            }
            System.out.println("");
        }
        
        
        System.out.println("*****************************************************************************************************************");
         for(int y=0 ; y < 8; y++){
             switch(y){
                 case 0 :  System.out.printf("Summation of X: %.4f \n", summations[y] ); break; 
                 case 1 :  System.out.printf("Summation of Y: %.4f \n",  summations[y] ); break; 
                 case 2 :  System.out.printf("Summation of X Sqrd: %.4f \n", summations[y] ); break;
                 case 3 :  System.out.printf("Summation of XY:  %.4f \n", summations[y] ); break;
                 case 4 :  System.out.printf("Summation of Y':  %.4f \n", summations[y] ); break;
                 case 5 : System.out.printf("Mean absulute error  %.4f \n", summations[y]/table.length ); break;
                 case 6 : System.out.printf("Mean absolute percentage error:  %.4f \n", summations[y]/table.length ); break;
                 case 7 : System.out.printf("Root mean squared error:  %.4f \n",  Math.sqrt(summations[y]/table.length)); break;
             }
        }
    }
    
    public  static void displayLinerRegression (double slope, double intercept){
         System.out.println("*****************************************************************************************************************");
         System.out.println("LINEAR REGRESIION");
         System.out.printf("Slope: %.4f \n",  slope); 
         System.out.printf("Intercept: %.4f \n", intercept);
         System.out.printf("Model: y = %.4fx + %.4f \n", slope, intercept); 
         System.out.println("*****************************************************************************************************************");
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
    
    public static void solveYHat(double[][] table, double slope, double intercept){
        for(int x = 0 ; x < table.length ; x++ ){
          table[x][4] = (table[x][0] *slope) +  intercept; 
       }
    }
    
    public static void solveErrors(double[][] table){
        for(int x = 0 ; x < table.length ; x++ ){
          table[x][5] = Math.abs(table[x][1] - table[x][4]);
          table[x][6] = Math.abs((table[x][1] - table[x][4])/table[x][1]);
          table[x][7] = Math.abs(table[x][5] * table[x][5]);
       }
    }
    
    public static void solveSummations(double[][] table, double[] summations){
        for(int i = 0; i < 8; i++){
            summations[i] = 0;
        }
        
         for(int x = 0 ; x < table.length ; x++){
            for(int y=0 ; y < 8; y++){
                summations[y] += table[x][y]; 
            }  
        }
    }
   
    public static double solveforSlope(int size, double[] summations){
        return ((size * summations[3]) - (summations[0] * summations[1]))/((size* summations[2])-(summations[0]*summations[0]));
    }
    
     public static double solveforIntercept(int size, double[] summations, double slope){
        return (summations[1] - (slope*summations[0]))/ size;
    }
}
