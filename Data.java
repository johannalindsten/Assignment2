package Assignment2;

public class Data {

	double time;
	final double[] var;
	Cluster label;
	
	
	public Data(double timevalue, double measurement, String type, String substationID, String[][] substations){
		time = timevalue;
		var = new double[(substations.length)*2];

		for(int j=0; j<substations.length; j++){

			if(substations[j][0].equals(substationID)){
				
				if(type.equals("VOLT")){
					
					
					int index = j*2;
					var[index] = measurement;
					
					break;
					
				}else if(type.equals("ANG")){
					
					
					int index = (j*2)+1;
					var[index] = measurement;
					break;
					
				}
			}
		}
	}
	
	
	
}
