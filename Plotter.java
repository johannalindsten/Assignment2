package Assignment2;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Plotter extends JFrame {
  
  public Plotter(){
	  
  }

  public JFreeChart execute(XYDataset dataset) {
	    //SwingUtilities.invokeLater(() -> {
	    	
	    	JFreeChart chart = ChartFactory.createScatterPlot(
	    	        "", 
	    	        "Voltage [pu]", "Phase angle [degrees]", dataset);

	    	    
	    	    //Changes background color
	    	    XYPlot plot = (XYPlot)chart.getPlot();
	    	    plot.setBackgroundPaint(new Color(255,228,196));
	    	    
	    	   
	    	    // Create Panel
	    	    ChartPanel panel = new ChartPanel(chart);
	    	    setContentPane(panel);
	    	
	      this.setSize(800, 400);
	      this.setLocationRelativeTo(null);
	      this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	      //this.setVisible(true);
	      
	      //return panel;
	      return chart;
	    //});
	  }
  
  
  
  public static XYSeriesCollection addDataSeries(XYSeriesCollection dataset, ArrayList<Double> seriesData, String name){
	  
	  	XYSeries series = new XYSeries(name);
	    series.add(seriesData.get(0), seriesData.get(1));
	    
	    
	    if(seriesData.size() > 2){
	    	int indexNum = (seriesData.size()/2)-1;
		    
		    for(int i=1; i<=indexNum; i++){
		    	
		    	
		    	int index1 = i*2;
		    	int index2 = index1 + 1;
		    	
		    	series.add(seriesData.get(index1), seriesData.get(index2));
		    }
	    }
	    
	    
	    

	    dataset.addSeries(series);
	    
	    return dataset;
	  
  }
  
  
  public static XYSeriesCollection createDataset(ArrayList<Data> dataset, ArrayList<Cluster> clusters, int var1, int var2){
	  
	  XYSeriesCollection dataseries = new XYSeriesCollection();
		
		for(int i=0; i<clusters.size(); i++){
			
			ArrayList<Data> dataList = sort(dataset, clusters.get(i));
			
			if(dataList.size()> 0){
				ArrayList<Double> doubleList = bus(dataList, var1, var2);
				dataseries = addDataSeries(dataseries, doubleList, dataList.get(0).label.name);
				
			}
			
		}	
		
		return dataseries;
		
  }
  
  private static ArrayList<Data> sort(ArrayList<Data> dataset, Cluster cluster){
		
		ArrayList<Data> sortedList = new ArrayList<Data>();
		
		for(int i=0; i<dataset.size(); i++){
			
			if(dataset.get(i).label.id==cluster.id){
				
				sortedList.add(dataset.get(i));
				
			}
			
		}
		
		return sortedList;
		
	}
	
	private static ArrayList<Double> bus(ArrayList<Data> dataset, int var1, int var2){
		
		ArrayList<Double> result = new ArrayList<Double>();
		
		for(int i=0; i<dataset.size(); i++){

			result.add(dataset.get(i).var[var1]);
			result.add(dataset.get(i).var[var2]);
			
		}
		
		return result;
		
	}
}