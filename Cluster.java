package Assignment2;

import java.util.ArrayList;

public class Cluster {

	double[] clusterCentroid;
	int id;
	ArrayList<Data> datapoints;
	double J;
	String name;
	
	public Cluster(int IDnum, int buses){
		id = IDnum;
		name = Integer.toString(id);
		datapoints = new ArrayList<Data>();
		clusterCentroid = new double[buses*2];
		J=0;
	}
	
	
	public void updateCentroid(){
		
		for(int i=0; i<this.clusterCentroid.length; i++){
			double newvalue = 0;
			for(int j=0; j<this.datapoints.size(); j++){
				newvalue += this.datapoints.get(j).var[i];
			}
			newvalue = newvalue/Double.valueOf(datapoints.size());
			this.clusterCentroid[i] = newvalue;
		}
		
	}

}
