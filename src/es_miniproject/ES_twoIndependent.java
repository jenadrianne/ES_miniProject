/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es_miniproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Jennifer
 */
public class ES_twoIndependent {
    private static final int NUM_ITERATIONS = 10000;
    private static final double LEARNING_RATE = 0.0001;
   
    public static void main(String[] args) {
        String file_path = "C:\\Users\\Kyle Yap\\Documents\\4thYear\\Expert Systems\\ES_miniProject\\src\\files\\New.csv"; 
        String line = "";
        String cvsSplitBy = ",";
        double[][] table = new double[731][11];
        double[] summations = new double[11]; 
        double slope, slope2, yintercept;
        double[] errors;
        
        // completing the table
        readFile(file_path, cvsSplitBy, table);
        
        solveXSqrd (table);
        solveX2Sqrd (table);
        solveXY(table); 
        solveX2Y(table);
        solveX1X2(table);
        solveSummations(table, summations);

        slope = solveforSlope(table.length, summations);
        slope2 = solveforSlope2(table.length, summations);
        yintercept = solveforIntercept(table.length, summations, slope, slope2);
//        
//        
        summations[8] = solveYHat(table, slope, slope2, yintercept); 
        errors = solveErrors(table, summations);
        summations[9] = errors[0];
        summations[10] = errors[1];
//        solveSummations(table, summations); 
//        
//        
        displayContents(table,summations); 
        displayLinerRegression (slope, slope2, yintercept);
        GradientDescent_multiple descent_multiple = new GradientDescent_multiple(table, LEARNING_RATE, slope, slope2, yintercept, NUM_ITERATIONS);
        descent_multiple.Run();
 
    }
    
    public static void displayContents (double[][] table,  double[] summations){
        for(int x = 0 ; x < table.length ; x++){
            for(int y=0 ; y < summations.length; y++){
                System.out.printf(" %.4f | " , table[x][y] ); 
            }
            System.out.println("");
        }
        
        
        System.out.println("*****************************************************************************************************************");
         for(int y=0 ; y < 11; y++){
             switch(y){
                 case 0 :  System.out.printf("Summation of X1: %.4f \n", summations[y] ); break;
                 case 1 :  System.out.printf("Summation of X2: %.4f \n", summations[y] ); break; 
                 case 2 :  System.out.printf("Summation of Y: %.4f \n",  summations[y] ); break; 
                 case 3 :  System.out.printf("Summation of X1 Sqrd: %.4f \n", summations[y] ); break;
                 case 4 :  System.out.printf("Summation of X2 Sqrd: %.4f \n", summations[y] ); break;
                 case 5 :  System.out.printf("Summation of X1Y:  %.4f \n", summations[y] ); break;
                 case 6 :  System.out.printf("Summation of X2Y:  %.4f \n", summations[y] ); break;
                 case 7 :  System.out.printf("Summation of X1X2:  %.4f \n", summations[y] ); break;
                 case 8 :  System.out.printf("Summation of Y':  %.4f \n", summations[y] ); break;
                 case 9 :  System.out.printf("Mean absulute error:  %.4f \n", summations[y] ); break;
                 case 10 : System.out.printf("Root mean squared error:  %.4f \n",  summations[y]); break;
             }
        }
         System.out.printf("Mean absulute percentage error: %.2f", (summations[9]/table.length)*100);
         System.out.print("% \n");
    }
    
    public  static void displayLinerRegression (double slope, double slope2, double intercept){
         System.out.println("*****************************************************************************************************************");
         System.out.println("LINEAR REGRESIION");
         System.out.printf("Slope1 : %.4f \n",  slope); 
         System.out.printf("Slope2 : %.4f \n",  slope2); 
         System.out.printf("Intercept: %.4f \n", intercept);
         System.out.printf("Model: y = %.4fx + %.4fx + %.4f \n", slope, slope2, intercept); 
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
                table[ndx][2] = Double.parseDouble(xy[2]);
                ndx++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void solveXSqrd (double[][] table){
       for(int x = 0 ; x < table.length ; x++ ){
          table[x][3] = table[x][0] *  table[x][0]; 
       }
    }
    
    public static void solveX2Sqrd (double[][] table){
       for(int x = 0 ; x < table.length ; x++ ){
          table[x][4] = table[x][1] *  table[x][1]; 
       }
    }
    
    public static void solveXY(double[][] table){
       for(int x = 0 ; x < table.length ; x++ ){
          table[x][5] = table[x][0] *  table[x][2]; 
       }
    }
    public static void solveX2Y(double[][] table){
       for(int x = 0 ; x < table.length ; x++ ){
          table[x][6] = table[x][1] *  table[x][2]; 
       }
    }
    
    public static void solveX1X2(double[][] table){
       for(int x = 0 ; x < table.length ; x++ ){
          table[x][7] = table[x][0] *  table[x][1]; 
       }
    }
    
    public static double solveYHat(double[][] table, double slope, double slope2, double intercept){
       double summation = 0; 
       for(int x = 0 ; x < table.length ; x++ ){
            table[x][8] = intercept + (slope*table[x][0]) + (slope2*table[x][1]); 
            summation+=table[x][8];
       }
       
       return summation;
    }
    
    public static double[] solveErrors(double[][] table, double[] summations){
        double[] ret = new double[2];
        for(int x = 0 ; x < table.length ; x++ ){
          ret[0] += table[x][9] = Math.abs((table[x][2] - table[x][8])/table[x][2]);
          ret[1] = table[x][2] - table[x][8];
          ret[1] += ret[1]*ret[1];
          ret[1] = Math.sqrt(ret[1]/table.length);
       }
        return ret;
    }
    
    public static void solveSummations(double[][] table, double[] summations){
        for(int i = 0; i < 8; i++){
            summations[i] = 0;
        }
        
        for(int x = 0 ; x < table.length ; x++){
            for(int y=0 ; y < summations.length; y++){
                summations[y] += table[x][y]; 
            }  
        }
    }
   
    public static double solveforSlope(int size, double[] summations){
        return ((summations[4] * summations[5]) - (summations[7] * summations[6]))/((summations[3]* summations[4])-(summations[7]*summations[7]));
    }
    
    public static double solveforSlope2(int size, double[] summations){
        return ((summations[3] * summations[6]) - (summations[7] * summations[5]))/((summations[3]* summations[4])-(summations[7]*summations[7]));
    }
    
     public static double solveforIntercept(int size, double[] summations, double slope, double slope2){
        return (summations[2] - (slope*summations[0]) -(slope2*summations[1]))/ size;
    }
}
