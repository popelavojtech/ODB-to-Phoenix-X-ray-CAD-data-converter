package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;

public class Controller implements Initializable {
	Vector<padType> padType = new Vector<padType>(2);
//	ArrayList<padType> padType = new ArrayList<padType>();
	
  public String directoryPath = "none";
  public String fileMatrixPath = "none";

  //selected item in ListView
  public String pathLv1CmpSel = "none";
  public String pathLv1padTypesSel = "none";
  public String pathLv2CmpSel = "none";
  public String pathLv2padTypesSel = "none";
  ////////////////////////////  
  
  @FXML
  private ListView<String> lv1cmp;
  @FXML
  private ListView<String> lv1pads;
  @FXML
  private ListView<String> lv2cmp;
  @FXML
  private ListView<String> lv2pads;
  @FXML
  private Label label;
  @FXML
  private Label labl2;
  @FXML
  private Label labl2cmp;
  @FXML
  private Label labl2pads;
  @FXML
  private Button buttonGenerate;
  @FXML
  private MenuItem menuClose;
  @FXML
  private MenuItem menuOpen;
  @FXML
  private RadioButton rb1l;
  @FXML
  private RadioButton rb2l;
  @FXML
  final ToggleGroup group = new ToggleGroup();
  
  public class padType {
	  public int id;
	  public String type = "";
	  public String width = "";
	  public String height = "";
  }
  
  public class padPos {
	  public String posX = "";
	  public String posY = "";
	  
	  
  }
  
  public float getpadTypesDimInMM(float padTypesDimInMil) {
	  float inMM = 0;
	  return inMM;
  }
  
  
  public float getpadTypesPosInMM(float padTypesPosInInch) {
	  float inMM = 0;
	  
	  return inMM;
  }
  
  
  public int getLayersCount(String matPath) {
	int count = 0;
	try (BufferedReader br = new BufferedReader(new FileReader(matPath))) {
		String s;
		while ((s = br.readLine()) != null) {
			if (s.equals("LAYER {")) count++;
			
		}
	}
	catch (Exception e) {
		System.err.println("File read error!!");
	}
	return count;
  }
  
  public String getStepsPath(String matPath) {
	  String stepName = "";
	  try (BufferedReader br = new BufferedReader(new FileReader(matPath))) {
		  String s;
		  while ((s = br.readLine()) != null) {
			  if (s.equals("STEP {")) {
	              s = br.readLine();
	              s = br.readLine();
	              int equalsPos = s.indexOf("=")+1;
	              while (equalsPos<s.length()) {
	            	  stepName = stepName + Character.toString(s.charAt(equalsPos));
	            	  equalsPos++;
	              }
			  }
		  }
	  }
	  catch (Exception e) {
		  System.err.println(e.getMessage());
	  }
	  return (directoryPath+"\\steps\\"+stepName+"\\"+"layers\\");
  }
  
  
  
  @FXML
  private void generateButtonAction(ActionEvent event) {
	label.setText(lv1cmp.getSelectionModel().getSelectedItem());  	
	
	if (rb1l.isSelected()) {
		pathLv1CmpSel = getStepsPath(fileMatrixPath) + lv1cmp.getSelectionModel().getSelectedItem() + "\\components";
		pathLv1padTypesSel = getStepsPath(fileMatrixPath) + lv1pads.getSelectionModel().getSelectedItem() + "\\features";
	}
	  
	if (rb2l.isSelected()) {
		pathLv1CmpSel = getStepsPath(fileMatrixPath) + lv1cmp.getSelectionModel().getSelectedItem() + "\\components";
		pathLv1padTypesSel = getStepsPath(fileMatrixPath) + lv1pads.getSelectionModel().getSelectedItem() + "\\features";
		pathLv2CmpSel = getStepsPath(fileMatrixPath) + lv2cmp.getSelectionModel().getSelectedItem() + "\\components";
		pathLv2padTypesSel = getStepsPath(fileMatrixPath) + lv2pads.getSelectionModel().getSelectedItem() + "\\features";
		
	  }
	
	processpadTypeslv1(pathLv1padTypesSel);
	//System.out.println(pathLv1CmpSel);
	//System.out.println(pathLv1padTypesSel);

  }
  
  
  public void processpadTypeslv1(String padTypes1Path) {
	  try (BufferedReader br = new BufferedReader(new FileReader(padTypes1Path))) {
		  String s;
		  while ((s = br.readLine()) != null) { 
			  padType temppadType = new padType();
			  int firstNum = s.indexOf("$");
			  if (firstNum == 0) {
				  int lastNum = s.indexOf(" ");
				  temppadType.id = Integer.parseInt(s.substring(firstNum+1, lastNum));
				  if (s.contains("r")) {
					  if (s.contains("rect")) {
						  int letterIsX = 0;
						  char isX = 120;
						  for (int i = 0; i < s.length(); i++) {
							  if(s.charAt(i) == isX) letterIsX++;
						  }
						  temppadType.type = "rect";
						  if (letterIsX == 1) {
							  temppadType.width = s.substring(s.indexOf("t")+1, s.indexOf("x"));
							  temppadType.height = s.substring(s.lastIndexOf("x")+1, s.length());
						  }
						  if (letterIsX == 2) {
							  temppadType.width = s.substring(s.indexOf("t")+1, s.indexOf("x"));
							  int isx = s.indexOf("x");
							  temppadType.height = s.substring(s.indexOf("x")+1, s.indexOf("x", isx+1));
						  }
					  }
					  else { 
						  temppadType.type = "round";
						  temppadType.width = s.substring(s.lastIndexOf("r")+1, s.length());
						  temppadType.height = s.substring(s.lastIndexOf("r")+1, s.length());
					  }
				  }
				  
				  if (s.contains("oval")) {
					  temppadType.type = "oval";
					  temppadType.width = s.substring(s.indexOf("l")+1, s.indexOf("x"));
					  temppadType.height = s.substring(s.lastIndexOf("x")+1, s.length());
				  }
				  
				  if (s.contains("s")) {
					  temppadType.type = "square";
					  temppadType.width = s.substring(s.indexOf("s")+1, s.length());
					  temppadType.height = s.substring(s.indexOf("s")+1, s.length());
				  }
				  
				  padType.addElement(temppadType);
			  }
		  }
	  }
	  catch (Exception e) {
			System.err.println(e.getMessage());
	  }
	  
	  for (int x = 0; x < padType.size(); x++) {
		  System.out.println(padType.get(x).id + " " + padType.get(x).type + " " + padType.get(x).width + " " + padType.get(x).height);
	  }
  }

  @FXML
  private void close(ActionEvent event) {
	Platform.exit();	  
  }
  
  @FXML
  public void menuopen(ActionEvent event) {
	DirectoryChooser fc = new DirectoryChooser();
	fc.setTitle("Specify ODB++ directory");
	File f = fc.showDialog(null);
	directoryPath = f.getAbsolutePath();
	fileMatrixPath = directoryPath + "\\matrix\\matrix";
	/*label.setText(directoryPath);
	label.setText(getStepsPath(fileMatrixPath));
	System.out.println(getStepsPath(fileMatrixPath));
	System.out.println("pocet lay: "+getLayersCount(fileMatrixPath));*/
	String[] layersArray = new String[getLayersCount(fileMatrixPath)];
	int i = 0;
	try (BufferedReader br = new BufferedReader(new FileReader(fileMatrixPath))) {
		String s;
		while ((s = br.readLine()) != null) {
			String layName = "";
			if (s.equals("LAYER {"))  {
				s = br.readLine();
				s = br.readLine();
				s = br.readLine();
				s = br.readLine();
				int equalsPos = s.indexOf("=")+1;
				while (equalsPos<s.length()) {
					layName = layName + Character.toString(s.charAt(equalsPos));
					equalsPos++;
				}
				layersArray[i] = layName;
				//System.out.println(layersArray[i]);
				i++;
			}
		}
	}
	catch (Exception e) {
		System.err.println(e.getMessage());
	}
	ObservableList names =  FXCollections.observableArrayList();
	names.addAll(layersArray);
	lv1cmp.setItems(names);
	lv1pads.setItems(names);
	lv2cmp.setItems(names);
	lv2pads.setItems(names);
  }
  

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
	// after program start
	final ToggleGroup group = new ToggleGroup();
	group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){public void changed(ObservableValue<? extends Toggle> ov,Toggle old_toggle, Toggle new_toggle) {
		if (group.getSelectedToggle() != null) {
			if (rb1l.isSelected()){
				lv2cmp.setVisible(false);
				lv2pads.setVisible(false);
				labl2.setVisible(false);
				labl2cmp.setVisible(false);
				labl2pads.setVisible(false);
			}
			if (rb2l.isSelected()){
				lv2cmp.setVisible(true);
				lv2pads.setVisible(true);
				labl2.setVisible(true);
				labl2cmp.setVisible(true);
				labl2pads.setVisible(true);
			}
		}                
	}
	});
	rb1l.setToggleGroup(group);
	rb1l.setSelected(true);
	rb2l.setToggleGroup(group);
  }
}