package Assignment2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.JFreeChart;

public class StartGUI {

	static String LearningSetFilePath;
	static String TestSetFilePath;
	static String SubstationFilePath;
	static boolean LearningSetUploaded;
	static boolean TestSetUploaded;
	static boolean SubstationsUploaded;
	static MachineLearning program;
	static int buses = 9;

	private JFrame frame;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartGUI window = new StartGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartGUI() {

		LearningSetUploaded = false;
		TestSetUploaded = false;
		SubstationsUploaded = false;
		
		program = new MachineLearning();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 428, 244);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnImportLearn = new JButton("Import csv-file for learning set");
		btnImportLearn.setBounds(43, 41, 344, 29);
		panel.add(btnImportLearn);
		btnImportLearn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setDialogTitle("Choose csv-file for learning set");
				
				FileFilter xmlFilter = new FileNameExtensionFilter("CSV file", "csv");
				
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setFileFilter(xmlFilter);
				
				if(fc.showOpenDialog(btnImportLearn) == JFileChooser.APPROVE_OPTION){
					LearningSetFilePath = fc.getSelectedFile().getAbsolutePath();
					LearningSetUploaded = true;
				}else if(fc.showOpenDialog(btnImportLearn) == JFileChooser.CANCEL_OPTION){
					JOptionPane.showMessageDialog(null, "No file for learning set chosen.");
				}
			}
		});
		
		JButton btnImportTest = new JButton("Import csv-file for test set");
		btnImportTest.setBounds(43, 86, 344, 29);
		panel.add(btnImportTest);
		btnImportTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setDialogTitle("Choose csv-file for test set.");
				
				FileFilter xmlFilter = new FileNameExtensionFilter("CSV file", "csv");
				
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setFileFilter(xmlFilter);
				
				if(fc.showOpenDialog(btnImportLearn) == JFileChooser.APPROVE_OPTION){
					TestSetFilePath = fc.getSelectedFile().getAbsolutePath();
					TestSetUploaded = true;
				}else if(fc.showOpenDialog(btnImportLearn) == JFileChooser.CANCEL_OPTION){
					JOptionPane.showMessageDialog(null, "No file for test set chosen.");
				}
			}
		});
		
		JButton btnImportSubstations = new JButton("Import csv-file for substation list");
		btnImportSubstations.setBounds(43, 131, 344, 29);
		panel.add(btnImportSubstations);
		btnImportSubstations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setDialogTitle("Choose csv-file for substation list.");
				
				FileFilter xmlFilter = new FileNameExtensionFilter("CSV file", "csv");
				
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setFileFilter(xmlFilter);
				
				if(fc.showOpenDialog(btnImportLearn) == JFileChooser.APPROVE_OPTION){
					SubstationFilePath = fc.getSelectedFile().getAbsolutePath();
					SubstationsUploaded = true;
				}else if(fc.showOpenDialog(btnImportLearn) == JFileChooser.CANCEL_OPTION){
					JOptionPane.showMessageDialog(null, "No file for test set chosen.");
				}
			}
		});
		

		
		JButton btnRunProgram = new JButton("Run program");
		btnRunProgram.setBounds(131, 199, 167, 29);
		panel.add(btnRunProgram);btnRunProgram.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(LearningSetUploaded == false || TestSetUploaded == false || SubstationsUploaded == false){
					
					JOptionPane.showMessageDialog(null, "Choose all needed files.");
					
				}else{
					MachineLearning.run(LearningSetFilePath, TestSetFilePath, SubstationFilePath);
					JFreeChart chart = MachineLearning.initalChart();
					JTable table = MachineLearning.initialTable();
					
					
					ResultsGUI frame = new ResultsGUI(chart, table, buses, MachineLearning.substationList);
					frame.setVisible(true);
				}
				
				
				
			}
			
		});
		
		
	}
}
