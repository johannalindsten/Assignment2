package Assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math; 

public class kMeanCluster {
	
	static int buses;
	
	public kMeanCluster(int busNum){
		
		buses = busNum;
		
	}
	
	public ArrayList<Cluster> createBestClusters(int k, ArrayList<Data> dataset){
		
		int number = 5;
		int min = 0;
		
		
		ArrayList<ArrayList<Cluster>> clustersetList = new ArrayList<ArrayList<Cluster>>();
		double[] totJ = new double[number];
		        
		//Create clusters
		for(int i=0; i<number; i++){
			ArrayList<Cluster> clusterset = createClusters(k, dataset);
			clustersetList.add(clusterset);
			totJ[i] = 0.0;
			
			for(int j=0; j<clusterset.size(); j++){
				totJ[i] += clusterset.get(j).J;	
			}
			
			//Find clusters with lowest total J
			if(totJ[i] < totJ[min]){
				min = i;
			}
		}
		
		//Use clusterset with lowest total J
		return clustersetList.get(min);
		
		
	}

	
	public ArrayList<Cluster> createClusters(int k, ArrayList<Data> dataset){
		
		ArrayList<Cluster> clusters = initiateClusters(k, dataset);
		
		int looping = 1;
		int iter = 0;
		
		//Keep updating clusters until the centroids no longer move
		while(looping > 0){
			
			iter += 1;
			
			boolean stop = updateClusters(clusters, dataset);
			
			if(stop){
				break;
			}
			
			
		}
		
		return clusters;
		
	}
	
	private static int findClosest(Data datapoint, ArrayList<Cluster> clusters){
		
		ArrayList<Double> distances = new ArrayList<Double>();
		
		for(int i=0; i<clusters.size(); i++){
			distances.add(Calculator.findDistance(datapoint.var, clusters.get(i).clusterCentroid));
		}
		
		int min = 0;
		
		for(int i=0; i<distances.size(); i++){
			if(distances.get(min) > distances.get(i)){
				min = i;
			}
		}
		
		return min;
		
	}
	
	
	private static boolean updateClusters(ArrayList<Cluster> clusters, ArrayList<Data> dataset){

		ArrayList<double[]> oldCentroids = new ArrayList<double[]>();
		ArrayList<double[]> newCentroids = new ArrayList<double[]>();
		
		boolean stop = false;
		
		for(int i=0; i<clusters.size(); i++){
			
			double[] oldCentroid = new double[clusters.get(i).clusterCentroid.length];
			System.arraycopy(clusters.get(i).clusterCentroid, 0, oldCentroid, 0, clusters.get(i).clusterCentroid.length);
			
			oldCentroids.add(oldCentroid);
			
			clusters.get(i).datapoints.clear();
			clusters.get(i).J = 0;
		}
		
		// Assign all datapoints to a cluster
		for(int i=0; i<dataset.size(); i++){
			int min = findClosest(dataset.get(i), clusters);
			clusters.get(min).datapoints.add(dataset.get(i));
			clusters.get(min).J += Calculator.findDistance(dataset.get(i).var, clusters.get(min).clusterCentroid);

		}
		
		//Update cluster centroid
		for(int i=0; i<clusters.size(); i++){
			
			clusters.get(i).updateCentroid();
			newCentroids.add(clusters.get(i).clusterCentroid);
			
		}

		boolean wrong = false;
		
		//Check old centroids against new centroids
		for(int i=0; i<oldCentroids.size(); i++){
			for(int j=0; j<oldCentroids.get(0).length; j++){
				
				if(oldCentroids.get(i)[j] != newCentroids.get(i)[j]){
					
					wrong = true;
					break;
					
				}
				
			}
			
			if(wrong){
				break;
			}
			
		}
		
		
		//If new and old centroid is the same
		if(wrong == false){
			stop = true;
		}
		
		
		return stop;
		
	}
	
	public ArrayList<Data> assignLabels(ArrayList<Cluster> clusters, ArrayList<Data> dataset){
		
		for(int i=0; i<clusters.size(); i++){
			for(int j=0; j<clusters.get(i).datapoints.size(); j++){
				
				double time1 = clusters.get(i).datapoints.get(j).time;
				
				for(int k=0; k<dataset.size(); k++){
					double time2 = dataset.get(k).time;

					if(time1 == time2){
						
						dataset.get(k).label = clusters.get(i);
						
					}
					
				}
				
				
			}
		}
		
		for(int i=0; i<dataset.size(); i++){
			if(dataset.get(i).label == null){
				System.out.println("Not all datapoints assigned a label");
			}
		}
		
		return dataset;
		
	}

	
	private ArrayList<Cluster> initiateClusters(int k, ArrayList<Data> dataset){
		
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();		
		
		int randomMax = dataset.size() -1; 
		int numClust = 0;
		int[] chosenIndex = new int[k];
		
		for(int i=0; i<k; i++){
			
			numClust += 1;
			
			clusters.add(new Cluster(numClust, buses));
			
			int looping =1;
			int randomIndex = 0;
			
			while(looping > 0){
				
				
				randomIndex = (int) (Math.random()*randomMax);
				boolean duplicate = false;
				
				for(int j=0; j<chosenIndex.length; j++){
					if(randomIndex == chosenIndex[j]){
						duplicate = true;
					}
				}
				
				if(duplicate == false){
					break;
				}
			}

			System.arraycopy(dataset.get(randomIndex).var, 0, clusters.get(i).clusterCentroid, 0, dataset.get(randomIndex).var.length);
			chosenIndex[i] = randomIndex;
	
		}
		
		return clusters;
		
	}
	
	
}
