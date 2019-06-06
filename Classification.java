package Assignment2;

import java.util.ArrayList;

public class Classification {

	 
	double[][] clustermean = new double[4][3];
	double[][] classifiedCluster = new double[4][4];
	double[][] voltages = new double[9][4]; 
	double[][] angles = new double[9][4];
	double[][] compareang = new double[3][2];
	double[][] comparevolt = new double[4][2];
	String[] labels = new String[4];
	
	double voltMean;
	double angleMean;
	int iter;
	int voltiter;
	public ArrayList<Cluster> assignClass(ArrayList<Cluster> clusters){
		
		for(int k = 0; k < 4; k++) {
			voltMean = 0;
			angleMean = 0; 
			voltiter = 0;
			iter = 1;
			for(int i = 0; i <clusters.get(0).clusterCentroid.length/2; i++) { 
				
				voltMean = voltMean + clusters.get(k).clusterCentroid[voltiter];
				voltages[i][k] = clusters.get(k).clusterCentroid[voltiter];
				
				angleMean = angleMean + clusters.get(k).clusterCentroid[iter];
				angles[i][k] = clusters.get(k).clusterCentroid[iter];
				voltiter = voltiter+2;
				iter = iter + 2; 
			
			}
			
			//System.out.println("Iterations etc :" + iter+ "\n" + voltMean);
			
			clustermean[k][0] = k+1;
			clustermean[k][1] = voltMean/(clusters.get(0).clusterCentroid.length/2);
			clustermean[k][2] = angleMean/(clusters.get(0).clusterCentroid.length/2); 
			
		}
		
		
		int max = 0;
		int high = 1;
		int low = 2;
		int min = 3;
		
		double maxv = 0;
		double minv = 2;
		double lowang = 0;
		double highang = 0;

		
		
		for(int z = 0; z< clustermean.length; z++) {
			for(int x = 0; x < clustermean.length; x++) {
				if(clustermean[z][1]>clustermean[x][1] && clustermean[z][1] > maxv) {
					classifiedCluster[max][3] = z;
					maxv = clustermean[z][1];
					labels[z] = "Low load"; 
				}else if(clustermean[z][1]<clustermean[x][1] && clustermean[z][1] < minv){
					classifiedCluster[min][3] = z;
					minv = clustermean[z][1];
					labels[z] = "High load";
					
				}
			}
		}
		
		
		for(int z = 0; z< clustermean.length; z++) {
			
			if(clustermean[z][1] != maxv && clustermean[z][1] != minv && clustermean[z][2] >  highang) {
				
			classifiedCluster[high][3] = z;
			highang = clustermean[z][2];
			
			}else if(clustermean[z][1] != maxv && clustermean[z][1] != minv && clustermean[z][2] <  lowang) {
				
			classifiedCluster[low][3] = z;
			lowang = clustermean[z][2];
			
			}
		}
		
		
		
		
		//low angle
		comparevolt[0][0] = voltages[0][(int) classifiedCluster[low][3]]-voltages[3][(int) classifiedCluster[low][3]];
		comparevolt[1][0] = voltages[2][(int) classifiedCluster[low][3]]-voltages[5][(int) classifiedCluster[low][3]];
		comparevolt[2][0] = voltages[1][(int) classifiedCluster[low][3]]-voltages[7][(int) classifiedCluster[low][3]];
		comparevolt[3][0] = Math.abs(comparevolt[0][0]) + Math.abs(comparevolt[1][0]) + Math.abs(comparevolt[2][0]); 
		
		// High angle
		comparevolt[0][1] = voltages[0][(int) classifiedCluster[high][3]]-voltages[3][(int) classifiedCluster[high][3]];
		comparevolt[1][1] = voltages[2][(int) classifiedCluster[high][3]]-voltages[5][(int) classifiedCluster[high][3]];
		comparevolt[2][1] = voltages[1][(int) classifiedCluster[high][3]]-voltages[7][(int) classifiedCluster[high][3]];
		comparevolt[3][1] = Math.abs(comparevolt[0][1]) + Math.abs(comparevolt[1][1]) + Math.abs(comparevolt[2][1]); 
		
		
		if(comparevolt[3][1] > comparevolt[3][0]) {
			labels[(int) classifiedCluster[high][3]] = "Line disconnected";
			labels[(int) classifiedCluster[low][3]] = "Generator disconnected";
		}else {
			
			labels[(int) classifiedCluster[low][3]] = "Line disconnected";
			labels[(int) classifiedCluster[high][3]] = "Generator disconnected";
		}
	
		
		
		for(int i=0; i<clusters.size(); i++) {
			clusters.get(i).name = labels[i];
		}
		
		return clusters;
		
		
		
	}
	
	
}
