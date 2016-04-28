package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;

public class Controller implements Initializable {
  public String directoryPath;
  public String fileMatrixPath = "neni";
  public String fileSMTPath;
  public String fileSMBPath;
  public String fileTOPCOMPPath;
  public String fileBOTCOMPPath;
    
  @FXML
  private ListView<String> lv;
  @FXML
  private Label label;
  @FXML
  private Button tlacitko;
  @FXML
  private MenuItem menuClose;
  @FXML
  private MenuItem menuOpen;
  
  
  @FXML
  private void handleListViewAction(ActionEvent event) {
	  label.setText(lv.getSelectionModel().getSelectedItem());
	  
	  
  }
  
  @FXML
  private void handleButtonAction(ActionEvent event) {
	 label.setText(lv.getSelectionModel().getSelectedItem());  	  
  }

  @FXML
  private void zavri(ActionEvent event) {
	  Platform.exit();	  
  }
  
  @FXML
  public void menuopen(ActionEvent event) {
	  DirectoryChooser fc = new DirectoryChooser();
	  fc.setTitle("Specify ODB++ directory");
	  File f = fc.showDialog(null);
	  directoryPath = f.getAbsolutePath();
	  fileMatrixPath = directoryPath + "\\matrix\\matrix";
	  label.setText(fileMatrixPath);
  }


  
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
      // after program start
	  ObservableList<String> items = FXCollections.observableArrayList ("Single", "Double", "Suite", "Family App");
	  lv.setItems(items);
  }

  
}