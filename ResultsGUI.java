package Assignment2;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;

public class ResultsGUI extends JFrame{

	//private JFrame frame;
	//static Plotter myPlotter;
	static int buses;
	static int chosenBus = 1;
	static boolean includeLearn;
	static boolean includeTest;
	int name;
	static MachineLearning program = new MachineLearning();
	static JFreeChart showedChart;
	static JTable showedTable;
	static ChartPanel chartPanel;
	static String[][] substations;
	static JScrollPane scrollPane;

	
	public ResultsGUI(JFreeChart chart, JTable table, int busNum, String[][] substationList) {
		
		showedChart = chart;
		showedTable = table;
		includeLearn = true;
		includeTest = true;
		buses = busNum;
		substations = substationList;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.setBounds(100, 100, 1000, 587);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBounds(50, 64, 955, 888);
		this.getContentPane().add(panel);
		panel.setLayout(null);
		
		//ChartPanel chartPanel = new ChartPanel((JFreeChart) null);
		chartPanel = new ChartPanel((JFreeChart) null);
		chartPanel.setBounds(173, 16, 474, 499);
		chartPanel.setChart(showedChart);
		panel.add(chartPanel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(15, 16, 143, 499);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JTextPane txtpnIncludedSets = new JTextPane();
		txtpnIncludedSets.setText("Included sets:");
		txtpnIncludedSets.setBounds(0, 0, 203, 26);
		panel_1.add(txtpnIncludedSets);
		
		JCheckBox chckbxLearningSet = new JCheckBox("Learning set");
		chckbxLearningSet.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					includeLearn = true;
				}else{
					includeLearn = false;
				}
				
			}
		});
		chckbxLearningSet.setSelected(true);
		chckbxLearningSet.setBounds(10, 31, 139, 29);
		panel_1.add(chckbxLearningSet);
		
		JCheckBox chckbxTestSet = new JCheckBox("Test set");
		chckbxTestSet.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					includeTest = true;
				}else{
					includeTest = false;
				}
			}
			
		});
		chckbxTestSet.setSelected(true);
		chckbxTestSet.setBounds(10, 68, 139, 29);
		panel_1.add(chckbxTestSet);
		
		JTextPane txtpnIncludedBuses = new JTextPane();
		txtpnIncludedBuses.setText("Show bus:");
		txtpnIncludedBuses.setBounds(0, 107, 203, 26);
		panel_1.add(txtpnIncludedBuses);
		
		 ButtonGroup busButtons = new ButtonGroup();
		 name = 0;
		 
		 JRadioButton[] buttonList = new JRadioButton[buses];
		 
		 for(int i=0; i<buses; i++){
			 name++;
			 
			 buttonList[i] = new JRadioButton(substations[i][1]);
			 buttonList[i].addActionListener(new BusButton(name));
			 buttonList[i].setSelected(true);
			 buttonList[i].setBounds(10, 145+35*i, 155, 29);
			panel_1.add(buttonList[i]);
			busButtons.add(buttonList[i]);
		 }
		
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new UpdateButton());
		btnUpdate.setBounds(15, 470, 115, 29);
		panel_1.add(btnUpdate);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(662, 16, 301, 499);
		showedTable.setFillsViewportHeight(true);
		scrollPane.getViewport().add(showedTable);
		panel.add(scrollPane);
		
	}
	
	
	private class BusButton implements ActionListener{

		int bus;
		
		public BusButton(int busPassed){
			this.bus = busPassed;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			chosenBus = bus;
		}
	}
	
	private class UpdateButton implements ActionListener{

		
		
		@Override
		public void actionPerformed(ActionEvent e){ 

			if(includeLearn == false && includeTest == false){
				JOptionPane.showMessageDialog(null, "Choose set(s) to include.", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				showedChart = MachineLearning.updateChart(includeLearn, includeTest, chosenBus);
				showedTable = MachineLearning.updateTable(includeLearn, includeTest);
				updateChart();
				updateTable();
			}
		}
	}
	
	private void updateChart(){
		chartPanel.setChart(showedChart);
		
	}
	
	private void updateTable(){
		scrollPane.getViewport().removeAll();
		scrollPane.getViewport().add(showedTable);
		
	}
}
