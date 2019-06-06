package Assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Reader {

	public static ArrayList<Data> createDataset(String datafile, String[][] substations){

		ArrayList<Data> dataset = new ArrayList<Data>();
		
		try {
			FileReader fr = new FileReader(datafile);
			BufferedReader br = new BufferedReader(fr);
			
			String nextLine = new String();
		
			while ((nextLine = br.readLine()) != null) {
				
				String[] param = nextLine.split(",");
				double time = Double.parseDouble(param[2]);
				double measurement = Double.parseDouble(param[3]);
				String substationID = param[4];
				boolean saved = false;
				String[] type = param[1].split("_");

			
				for(int i=0; i<dataset.size(); i++){
					if(dataset.get(i).time == time){
						for(int j=0; j<substations.length; j++){
							
								
							if(substations[j][0].equals(substationID)){
								
								
								if(type[1].equals("VOLT")){
									
									int index = j*2;
									dataset.get(i).var[index] = measurement;

									saved = true;
									break;
									
								}else if(type[1].equals("ANG")){
									
									
									int index = (j*2)+1;
									dataset.get(i).var[index] = measurement;
									
									saved = true;
									break;
									
								}
							}
						}
					}
				}
				
				if(saved == false){
					dataset.add(new Data(time, measurement, type[1], substationID, substations));
				}
				
			}
			
			
			
		}catch(FileNotFoundException e) {
			System.out.println("File not found");
		}catch (IOException e){
			System.out.println("IOException");
		}
		
		return dataset;
		
	}
	
	public static String[][] readSubstations(String datafile){
		
		
		int count =0;
		
		try {
			FileReader fr = new FileReader(datafile);
			BufferedReader br = new BufferedReader(fr);
			
			String nextLine = new String();
		
			
			while ((nextLine = br.readLine()) != null) {
				
				count++;
				
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("File not found");
		}catch (IOException e){
			System.out.println("IOException");
		}
		
		String[][] substationList = new String[count][2];
		
		try {
			
			FileReader fr = new FileReader(datafile);
			BufferedReader br = new BufferedReader(fr);
			
			String nextLine = new String();
			
			int index =0;
			
			while ((nextLine = br.readLine()) != null) {
				
				String[] param = nextLine.split(",");
				String rdfID = param[0];
				String name = param[1]; 
				
				substationList[index][0] = rdfID;
				substationList[index][1] = name;

				index++;
				//System.out.println(time);
				
			}
		}catch(FileNotFoundException e) {
			System.out.println("File not found");
		}catch (IOException e){
			System.out.println("IOException");
		}
		
		return substationList;
	}
}
