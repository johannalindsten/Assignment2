package Assignment2;

import java.util.ArrayList;

public class Calculator {

	
	public static double findDistance(double[] point1, double[] point2){
		
		double distance = 0;
		
		if(point1.length == point2.length){
			for(int i=0; i<point1.length; i++){
				distance += Math.pow((point1[i] - point2[i]), 2);
			}
			
			distance = Math.sqrt(distance);
		}else{
			System.out.println("Datapoints don't match in length");
		}
		
		return distance;
	}
	
	
	
}
