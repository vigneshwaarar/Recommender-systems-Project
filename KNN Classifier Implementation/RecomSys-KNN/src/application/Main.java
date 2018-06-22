package application;

import java.io.*;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.ObjectOutputStream;
//import java.io.OutputStreamWriter;
import java.util.*;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.TreeMap;

//import java.awt.Insets;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
//import javafx.scene.*;
//
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	ArrayList<ArrayList<String>> listOListsTrain = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> listOListsTest = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> listOListsFinal = new ArrayList<ArrayList<String>>();
	ArrayList<String> singleList = null;
	int classCount = 0;
	String strDistance = "";
	String cvsSplitBy = ",";
	int kValue = 0;
	int featureCount = 0;
	double Accuracy = 0;
	@Override
	public void start(Stage primaryStage) {
		try {
			Stage stgFileLoading = new Stage();
			primaryStage.setTitle("Upload File");
			primaryStage.setTitle("KNN Classifier");
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));

			Scene scene = new Scene(grid, 300, 275);
			primaryStage.setScene(scene);
			Text scenetitle = new Text("Welcome");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			grid.add(scenetitle, 0, 0, 2, 1);
			Label lblKValue = new Label("Enter K value:");
			grid.add(lblKValue, 0, 1);
			
			TextField txtKValue = new TextField();
			grid.add(txtKValue, 1, 1);
			
			final ToggleGroup group = new ToggleGroup();
			RadioButton rb1 = new RadioButton("Manhattan");
			rb1.setToggleGroup(group);
			rb1.setSelected(true);
			
			RadioButton rb2 = new RadioButton("Euclidean");
			rb2.setToggleGroup(group);
			HBox rbContainer = new HBox(rb1, rb2);
			Label lblDistance = new Label("Distance Measure:");
			grid.add(lblDistance, 0, 2);
			grid.add(rbContainer, 1, 2);
			Label lblTrainingDS = new Label("Select Training dataset:");
			Label lblTestingDS = new Label("Select Testing dataset:");
			grid.add(lblTrainingDS, 0, 3);
			grid.add(lblTestingDS, 0, 4);
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			Button btnTrain = new Button("Train data");
			btnTrain.setMaxWidth(140);
			Button btnTest = new Button("Test data");
			btnTest.setMaxWidth(140);
			Button btnClassify = new Button("Classify");
			btnClassify.setMaxWidth(140);
			grid.add(btnTrain, 1, 3);
			grid.add(btnTest, 1, 4);
			grid.add(btnClassify, 0, 5);
			Text errorMess = new Text("");
			grid.add(errorMess, 0, 6, 2, 1);
			btnTrain.setOnAction(new EventHandler<ActionEvent>() {
			     public void handle(ActionEvent e) {
			    	File fileTrain = fileChooser.showOpenDialog(stgFileLoading);
			    	btnTrain.setText("Train data Loaded");
			    	//System.out.println(fileTrain);
			    	try {
			    		String line = "";
						BufferedReader br = new BufferedReader(new FileReader(fileTrain));
						 while ((line = br.readLine()) != null) 
					        {
							 	singleList = new ArrayList<String>();
					            // use comma as separator
					            String[] data = line.split(cvsSplitBy);
					            for(int i=0; i < data.length; i++)
					            {
					            	singleList.add(data[i]);
					            }
//					            singleList.add(data[1]);
//					            singleList.add(data[2]);
//					            singleList.add(data[3]);
//					            singleList.add(data[4]);
					            listOListsTrain.add(singleList);
					        }
						 //System.out.println(listOListsTrain);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	
			    }
			});
			
			btnTest.setOnAction(new EventHandler<ActionEvent>() {
			     public void handle(ActionEvent e) {
			    	 File fileTest = fileChooser.showOpenDialog(stgFileLoading);
				    	btnTest.setText("Train data Loaded");
				    	//System.out.println(fileTest);
				    	try {
				    		String line = "";
							BufferedReader br = new BufferedReader(new FileReader(fileTest));
							 while ((line = br.readLine()) != null) 
						        {
								 	singleList = new ArrayList<String>();
						            // use comma as separator
						            String[] data = line.split(cvsSplitBy);
						            for(int i=0; i < data.length; i++)
						            {
						            	singleList.add(data[i]);
						            }
//						            singleList.add(data[1]);
//						            singleList.add(data[2]);
//						            singleList.add(data[3]);
//						            singleList.add(data[4]);
						            listOListsTest.add(singleList);
						        }
							 //System.out.println(listOListsTest);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    }
			});
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
			btnClassify.setOnAction(new EventHandler<ActionEvent>() {
			     public void handle(ActionEvent e) {
			    	 if(txtKValue.getText()!=null && txtKValue.getText()!="" && !txtKValue.getText().equals(""))
			    	 {
			    		 
			    		 //System.out.println(txtKValue.getText()+"/");
//			    		 System.out.println(listOListsTrain.size());
//			    		 System.out.println(listOListsTest.size());
			    		 try {
			    			 kValue = Integer.parseInt(txtKValue.getText());
			    			 errorMess.setText("");
			    			 if(rb1.isSelected())
					    		 strDistance = rb1.getText();
					    	 else
					    		 strDistance = rb2.getText();
					    	 //System.out.println(kValue + " : "+ strDistance);
				    		 try {
						    		 if(listOListsTrain.size() != 0 && listOListsTest.size() != 0 && kValue !=0)
						    		 {
						    			 errorMess.setText("");
						    			 classifyWithManhattan();
						    		 }
						    		 else
						    		 {
						    			 if(listOListsTrain.size() == 0 && listOListsTest.size() == 0)
						    				 errorMess.setText("Please select Training and testing dataset");
						    			 else if(listOListsTrain.size() == 0)
						    				 errorMess.setText("Please select Training dataset");
						    			 else
						    				 errorMess.setText("Please enter Testing dataset");
						    		 }
						    			  
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			    		 }catch (NumberFormatException ex) {
			    			 	errorMess.setText("K is not a number.");
			    			 	kValue = 0;
			    			    System.out.println("K is not a number.");
			    			}
			    		 
			    	 }
			    	 else
			    	 {
			    		 errorMess.setText("Please enter k-value");
			    		 System.out.println("Please enter k-value");
			    	 }
			     }
			});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void classifyWithManhattan() throws IOException {
		// TODO Auto-generated method stub
		 
		Map<String, Integer> classHMap = new HashMap<String, Integer>();
		if(listOListsTrain.size() != 0 && listOListsTest.size() != 0 )
		{
			featureCount = listOListsTrain.get(0).size();
			//System.out.println(listOListsTrain.get(0).size());

			Map<Integer, ArrayList<String>> distHMap = new HashMap<Integer, ArrayList<String>>();
			ArrayList<String> distValue = null;
			for(int i=0; i<listOListsTest.size(); i++)
			{
				distValue = new ArrayList<String>();
				for(int j=0; j<listOListsTrain.size(); j++)
				{
					double dis = 0;
					for(int k=0; k < featureCount-1; k++)
					{
						if(strDistance.equals("Manhattan"))
						{
							dis += Math.abs(Double.parseDouble(listOListsTrain.get(j).get(k)) - Double.parseDouble(listOListsTest.get(i).get(k)));
						}
						else
						{
							double tempdis = Math.pow(Double.parseDouble(listOListsTrain.get(j).get(k)) - Double.parseDouble(listOListsTest.get(i).get(k)),2);
							dis += tempdis;
						}
					}
					
					int w = j+1;
					if(!strDistance.equals("Manhattan"))
						dis = Math.sqrt(dis);
					distValue.add(j,dis+"_"+w);
					
				}
				distHMap.put(i+1, distValue);
				
			}

			System.out.println(distHMap.get(1));
			Map<Integer, String> predictHMap = new HashMap<Integer, String>();
			
			Map<Integer, Double> interHMap = null;
			for(int j : distHMap.keySet())
			{
				interHMap = new HashMap<Integer, Double>();
			    for(int i=0; i<distHMap.get(j).size(); i++){
			    	String[] strArr = distHMap.get(j).get(i).split("_");
			    	interHMap.put(Integer.parseInt(strArr[1]), Double.parseDouble(strArr[0]));
			    }
			    Map sortedMap = new TreeMap(new ValueComparator(interHMap));
				sortedMap.putAll(interHMap);
				//System.out.println(sortedMap);
				Map<String, Integer> valueHMap = new HashMap<String, Integer>();
				
				int counter = 0;
				
				for ( Object key : sortedMap.keySet() ) {
					if(counter < kValue)
					{
						if(!valueHMap.containsKey(listOListsTrain.get(Integer.parseInt(key.toString())-1).get(4)))
							valueHMap.put(listOListsTrain.get(Integer.parseInt(key.toString())-1).get(4), 1);
						else
							valueHMap.put(listOListsTrain.get(Integer.parseInt(key.toString())-1).get(4), valueHMap.get(listOListsTrain.get(Integer.parseInt(key.toString())-1).get(4))+1);
						counter ++;
					}
					else
						break;
				}
				//System.out.println(valueHMap);
				int max = Integer.MIN_VALUE;
				String kValue = "";
				
				for(String key: valueHMap.keySet())
				{
					if(valueHMap.get(key) > Integer.MIN_VALUE)
						kValue = key;
				}
				predictHMap.put(j, kValue);
			}
			//System.out.println(predictHMap);
			int counting =0;
			for(int key: predictHMap.keySet())
			{
				if((listOListsTest.get(key-1).get(4).toString().trim()).equals(predictHMap.get(key).toString().trim()))
				{
					counting ++;
				}
			}
			double total = listOListsTest.size();
			Accuracy = (counting/total)*100;
			System.out.println(counting);
			System.out.println(Accuracy);
			classCount = classHMap.size();
			listOListsFinal = (ArrayList<ArrayList<String>>) listOListsTest.clone();
			for(int key: predictHMap.keySet())
			{
				listOListsFinal.get(key-1).add(predictHMap.get(key));
			}
			//System.out.println(listOListsFinal);
			
			BufferedWriter writer = new BufferedWriter (new FileWriter("out.txt")); 
			for(ArrayList<String> str: listOListsFinal) {
			  for(String inStr: str)
			  {
				  writer.write(inStr + " ");
			  }
			  writer.newLine();
			}
			writer.close();

			
		}
		else
			System.out.println("select Train and Test data set");
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

class ValueComparator implements Comparator {
	Map map;
 
	public ValueComparator(Map map) {
		this.map = map;
	}
 
	public int compare(Object keyA, Object keyB) {
		Comparable valueA = (Comparable) map.get(keyA);
		Comparable valueB = (Comparable) map.get(keyB);
		return valueA.compareTo(valueB);
	}
}