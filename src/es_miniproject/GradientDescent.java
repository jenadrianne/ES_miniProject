/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es_miniproject;

/**
 *
 * @author Ted125
 */
public class GradientDescent {
    private double[][] points;
    private double learningRate;
    private double m;   // slope
    private double b;   // y-intercept
    private int numIterations;
    
    public  GradientDescent(double[][] points, double learningRate, double m, double b, int numIterations){
        this.points = points;
        this.learningRate = learningRate;
        this.m = m;
        this.b = b;
        this.numIterations = numIterations;
    }
    
    public void Run(){
        System.out.println("GRADIENT DESCENT");
        System.out.printf("Starting gradient descent at m = %.4f, b = %.4f, mean error = %.4f \n", m, b, ComputeError());
        Update();
        System.out.printf("After %d iterations m = %.4f, b = %.4f, mean error = %.4f \n", numIterations, m, b, ComputeError());
    }
    
    private void Update(){
        for(int i = 0; i < numIterations; i++){
            System.out.printf("Iteration %d: \n", i + 1);
            Step();
        }
    }
    
    private void Step(){
        double mGradient = 0;
        double bGradient = 0;
        
        for(int i = 0; i < points.length; i++){
            double x = points[i][0];
            double y = points[i][1];
            mGradient += -((double)2/points.length) * x * (y - (m  * x + b));
            bGradient += -((double)2/points.length) * (y - (m  * x + b));
        }
        
        b = b - learningRate * bGradient;
        m = m - learningRate * mGradient;
        
        System.out.printf("\t m = %f \n", m);
        System.out.printf("\t b = %f \n", b);
        System.out.printf("\t mean error = %f \n", ComputeError());
    }
    
    private double ComputeError(){
        double totalError = 0;
        
        for(int i = 0; i < points.length; i++){
            double x = points[i][0];
            double y = points[i][1];
            totalError += Math.pow(y - (m * x + b), 1);
//            totalError += Math.pow(y - (m * x + b), 1);
        }
        
        return totalError / points.length;
    }
}
