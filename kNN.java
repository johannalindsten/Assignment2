package Assignment2;

import java.util.ArrayList;

public class kNN {
	
	public static void classifySet(int k, ArrayList<Data> learningset, ArrayList<Data> testset, ArrayList<Cluster> clusters){
		
		for(int i=0; i<testset.size(); i++){
			
			Data[] neighbors = new Data[k];
			Double[] distances = new Double[learningset.size()];
			Boolean[] distancesMin = new Boolean[learningset.size()]; 
			
			
			//Calculate distance between testset point and all learningsetpoints
			for(int j=0; j<learningset.size(); j++){
				
				
				distances[j] = Calculator.findDistance(testset.get(i).var, learningset.get(j).var);
				distancesMin[j] = false;
				
			}
			
			
			//Find k number of learningsetpoints that are closest to the testsetpoint
			for(int j=0; j<k; j++){
				
				int min = 0;

				
				for(int n=0; n<distances.length; n++){

					if(distancesMin[n]==false){

						min = n;

						break;
					}
				}

				
				for(int n=0; n<distances.length; n++){
					
					if(distancesMin[n] == false){

						if(distances[n] <= distances[min]){

							min = n;
						}
					}
				}
				
				neighbors[j] = learningset.get(min);
				distancesMin[min] = true;

			}
			
			//Matrix of IDs, column 1 is ID names and column 2 is instances of ID
			ArrayList<ArrayList<Integer>> IDlist = new ArrayList<ArrayList<Integer>>();
			
			
			//Go through neighbors 
			for(int j=0; j<neighbors.length; j++){
				
				int ID = 0;
				ID = neighbors[j].label.id;
				
				
				boolean found = false;
				
				
				
				//Find match with ID to IDlist
				for(int n=0; n<IDlist.size(); n++){
					
					if(ID == IDlist.get(n).get(0)){
						
						
						//Update number of instances of ID
						int numInst = IDlist.get(n).get(1) +1;
						IDlist.get(n).set(1, numInst);
						found = true;
						break;
					}
				}
				
				
				//If ID not used before
				if(found == false){
					
					ArrayList<Integer> newID = new ArrayList<Integer>(); 
					
					newID.add(ID);
					newID.add(1);
					
					//Add ID to list, with one instance
					IDlist.add(newID);
					
				}
			}

			//Find which ID is found most:
			
			int max = 0;
			
			for(int j=0; j<IDlist.size(); j++){
				
				if(IDlist.get(j).get(1) > IDlist.get(max).get(1)){

					max = j;
				}
			}
			
			
			int foundID = IDlist.get(max).get(0);
			
			for(int j=0; j<clusters.size(); j++){
				
				if(clusters.get(j).id == foundID){
					
					testset.get(i).label = clusters.get(j);
				}
			}
		}
	}
}
