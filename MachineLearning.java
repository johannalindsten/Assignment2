package Assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

public class MachineLearning {

	static Plotter MyPlotter = new Plotter();
	static Classification Classifyer = new Classification();
	
	static String[][] substationList;
	static int buses;
	
	static ArrayList<Data> learningset;
	static ArrayList<Data> testset;
	static ArrayList<Cluster> clusters;
	
	public static void run(String learningFile, String testFile, String substationFile) {
		
		substationList = Reader.readSubstations(substationFile);
		buses = substationList.length;
		
		learningset = Reader.createDataset(learningFile, substationList);
		testset = Reader.createDataset(testFile, substationList);
		
		
		kMeanCluster Clustering = new kMeanCluster(buses);
		clusters = Clustering.createBestClusters(4, learningset);
		clusters = Classifyer.assignClass(clusters);
		learningset = Clustering.assignLabels(clusters, learningset);
		
		kNN.classifySet(10, learningset, testset, clusters);
		
	}
	
	public static JFreeChart initalChart(){
		int var1 = 0;
		int var2 = 1;
		
		ArrayList<Data> setLearnTest = new ArrayList<Data>(learningset);
		setLearnTest.addAll(testset);
		
		XYSeriesCollection datasetLearnTest = Plotter.createDataset(setLearnTest, clusters, var1, var2);
		
		JFreeChart chart = MyPlotter.execute(datasetLearnTest);
		
		
		return chart;
	}
	
	
	
	public static JFreeChart updateChart(boolean learn, boolean test, int bus){
		
		int var1 = bus*2-2;
		int var2 = bus*2-1;
		
		
		XYSeriesCollection datasetLearn = Plotter.createDataset(learningset, clusters, var1, var2);
		XYSeriesCollection datasetTest = Plotter.createDataset(testset, clusters, var1, var2);
		
		ArrayList<Data> setLearnTest = new ArrayList<Data>(learningset);
		setLearnTest.addAll(testset);
		
		XYSeriesCollection datasetLearnTest = Plotter.createDataset(setLearnTest, clusters, var1, var2);
		
		
		JFreeChart chart = MyPlotter.execute(datasetLearnTest);
		if(learn == false){
			chart = MyPlotter.execute(datasetTest);
		}else if(test == false){
			chart = MyPlotter.execute(datasetLearn);
		}
		
		return chart;
		
	}
	
	
	public static JTable initialTable(){
		
		String[] columnNames = {"State time", "Label", "Set"};
		
		ArrayList<Data> setLearnTest = new ArrayList<Data>(learningset);
		setLearnTest.addAll(testset);
		
		int totalLength = learningset.size() + testset.size();
		
		String[][] data = new String[totalLength][3];
		
		for(int i=0; i<learningset.size(); i++){
			data[i][0] = Double.toString(learningset.get(i).time);
			data[i][1] = learningset.get(i).label.name;
			data[i][2] = "Learning set";
		}
		
		for(int i=0; i<testset.size(); i++){
			data[i+learningset.size()][0] = Double.toString(testset.get(i).time);
			data[i+learningset.size()][1] = testset.get(i).label.name;
			data[i+learningset.size()][2] = "Test set";
		}
		
		
		String[][] sortedData = new String[data.length][3];
		
		int max = 0;
		
		for(int i=sortedData.length-1; i>-1; i--){
			for(int j=0; j<data.length; j++){
				if(Double.parseDouble(data[j][0]) > Double.parseDouble(data[max][0])){
					max = j;
				}
			}
			
			sortedData[i][0] = data[max][0];
			sortedData[i][1] = data[max][1];
			sortedData[i][2] = data[max][2];
			
			data[max][0] = "0";
			
		}
		
		JTable table = new JTable(sortedData, columnNames);
		
		return table;
		
	}
	
	
	public static JTable updateTable(boolean learn, boolean test){
		
		JTable table = new JTable();
		
		if(learn && test){
			table = initialTable();
		}else if(learn){
			String[] columnNames = {"State time", "Label", "Set"};

			String[][] data = new String[learningset.size()][3];
			
			for(int i=0; i<learningset.size(); i++){
				data[i][0] = Double.toString(learningset.get(i).time);
				data[i][1] = learningset.get(i).label.name;
				data[i][2] = "Learning set";
			}
			
			
			String[][] sortedData = new String[data.length][3];
			
			int max = 0;
			
			for(int i=sortedData.length-1; i>-1; i--){
				for(int j=0; j<data.length; j++){
					if(Double.parseDouble(data[j][0]) > Double.parseDouble(data[max][0])){
						max = j;
					}
				}
				
				sortedData[i][0] = data[max][0];
				sortedData[i][1] = data[max][1];
				sortedData[i][2] = data[max][2];
				
				data[max][0] = "0";
				
			}
			
			
			table = new JTable(sortedData, columnNames);
			
		}else if(test){
			
			String[] columnNames = {"State time", "Label", "Set"};

			String[][] data = new String[testset.size()][3];
			
			for(int i=0; i<testset.size(); i++){
				data[i][0] = Double.toString(testset.get(i).time);
				data[i][1] = testset.get(i).label.name;
				data[i][2] = "Test set";
			}
			
			
			String[][] sortedData = new String[data.length][3];
			
			int max = 0;
			
			for(int i=sortedData.length-1; i>-1; i--){
				for(int j=0; j<data.length; j++){
					if(Double.parseDouble(data[j][0]) > Double.parseDouble(data[max][0])){
						max = j;
					}
				}
				
				sortedData[i][0] = data[max][0];
				sortedData[i][1] = data[max][1];
				sortedData[i][2] = data[max][2];
				
				data[max][0] = "0";
				
			}
			
			table = new JTable(sortedData, columnNames);
			
		}
	

		return table;
		
	}
	
}
