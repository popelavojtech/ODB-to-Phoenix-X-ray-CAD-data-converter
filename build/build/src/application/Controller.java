package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller implements Initializable {
	// Defines of JavaFX components
	@FXML
	private ListView<String> lv1cmp;
	@FXML
	private ListView<String> lv1pads;
	@FXML
	private ListView<String> lv2cmp;
	@FXML
	private ListView<String> lv2pads;
	@FXML
	private Label labl2;
	@FXML
	private Label labl2cmp;
	@FXML
	private Label labl2pads;
	@FXML
	private Label labl1;
	@FXML
	private Label labl1cmp;
	@FXML
	private Label labl1pads;
	@FXML
	private Button buttonSave;
	@FXML
	private Button buttonOpen;
	@FXML
	private Button buttonHelp;
	@FXML
	private TextField txField;
	@FXML
	private CheckBox checkBox;
	@FXML
	private RadioButton rbTOP;
	@FXML
	private RadioButton rbBOT;
	@FXML
	private RadioButton rbTOPBOT;
	@FXML
	final ToggleGroup group = new ToggleGroup();

	////////////////////////////////////////////////////////////////////////

	Vector<padType> padType = new Vector<padType>(2);
	Vector<allCoordinates> allCoordinates = new Vector<allCoordinates>(2);
	Vector<padPosInfo> padPosInfo = new Vector<padPosInfo>(2);
	Vector<padsAllInfoFromPads> padsAllInfoFromPads = new Vector<padsAllInfoFromPads>(2);
	Vector<cmpInfoFromCmp> cmpInfoFromCmp = new Vector<cmpInfoFromCmp>(2);
	Vector<padsInfoFromCmp> padsInfoFromCmp = new Vector<padsInfoFromCmp>(2);
	Vector<padsAllInfoFromPadsAndCmp> padsAllInfoFromPadsAndCmp1 = new Vector<padsAllInfoFromPadsAndCmp>(2);
	Vector<padsAllInfoFromPadsAndCmp> padsAllInfoFromPadsAndCmp1Convers = new Vector<padsAllInfoFromPadsAndCmp>(2);

	Vector<padType> padType1 = new Vector<padType>(2);
	Vector<padPosInfo> padPosInfo1 = new Vector<padPosInfo>(2);
	Vector<padsAllInfoFromPads> padsAllInfoFromPads1 = new Vector<padsAllInfoFromPads>(2);
	Vector<cmpInfoFromCmp> cmpInfoFromCmp1 = new Vector<cmpInfoFromCmp>(2);
	Vector<padsInfoFromCmp> padsInfoFromCmp1 = new Vector<padsInfoFromCmp>(2);
	Vector<padsAllInfoFromPadsAndCmp> padsAllInfoFromPadsAndCmp11 = new Vector<padsAllInfoFromPadsAndCmp>(2);
	Vector<padsAllInfoFromPadsAndCmp> padsAllInfoFromPadsAndCmp1Convers1 = new Vector<padsAllInfoFromPadsAndCmp>(2);

	// Defines of used directory and file paths
	public int openCount = 0;
	public String separator = "";
	public File lastDirectory;
	public String directoryPath = "none";
	public File directoryPath1;
	public String directoryName = "none";
	public String fileMatrixPath = "none";
	public double minPosX;
	public double minPosY;
	public double maxPosX;
	public double maxPosY;
	public double dimX;
	public double dimY;
	public String saveLocation = "none";
	public File saveLocation1;
	public String saveLocation2 = "none";
	public String pathLv1CmpSel = "none";
	public String pathLv1padTypesSel = "none";
	public String pathLv2CmpSel = "none";
	public String pathLv2padTypesSel = "none";
	////////////////////////////////////////////////////////////////////////

	// Headers used in exported files
	public String padsHeader = "PackageID\tlabel\tX-shift\tY-shift\tRotation\tWidth\tHeight\tShape";
	public String cmpHeader1 = ".UNIT:";
	public String cmpHeader2 = "# Board dimensions: X size, Y size, layer height";
	public String cmpHeader3 = ".DIMENSIONS:";
	public String cmpHeader4 = "# RefDes\tPackageID\tX-shift\tY-shift\tROT\tPinCount\tSide\tMountType";
	////////////////////////////////////////////////////////////////////////

	// Class for both Components and pads coordinates, used for finding the
	// smallest coordinates and board dimensions
	public class allCoordinates {
		public double x;
		public double y;
	}
	////////////////////////////////////////////////////////////////////////

	// Class for pad types (information behind character $ in pads files
	public class padType {
		public int id;
		public String shape = "";
		public double width = 0;
		public double height = 0;
	}
	////////////////////////////////////////////////////////////////////////

	// Class for pad information from pads files
	public class padPosInfo {
		public double posX = 0;
		public double posY = 0;
		public double rotation = 0;
		public int id;
	}
	////////////////////////////////////////////////////////////////////////

	// Class for all pads information from pads files, including pads
	// coordinates and pads types
	public class padsAllInfoFromPads {
		public double posX = 0;
		public double posY = 0;
		public double rotation = 0;
		public double width = 0;
		public double height = 0;
		public String shape = "";
	}
	////////////////////////////////////////////////////////////////////////

	// Class for components informations from components files
	public class cmpInfoFromCmp {
		public String name = "";
		public String pckID = "";
		public double cmpPosX;
		public double cmpPosY;
		public double rotation;
		public int pinCount;
		public String side = "";
		public String mountType = "";
	}
	////////////////////////////////////////////////////////////////////////

	// Class for pads informations from components files
	public class padsInfoFromCmp {
		public String pckID = "";
		public String label = "";
		public double rotation;
		public double posX;
		public double posY;
	}
	////////////////////////////////////////////////////////////////////////

	// Class for merge informations from pads files anc from components files
	public class padsAllInfoFromPadsAndCmp {
		public String pckID = "";
		public String label = "";
		public double pinPosX;
		public double pinPosY;
		public double rotation;
		public double width = 0;
		public double height = 0;
		public String shape = "";
	}
	////////////////////////////////////////////////////////////////////////

	// Converting units from mil to mm
	public double milToMM(double inMil) {
		double inMM = inMil * 0.0254;
		return inMM;
	}
	////////////////////////////////////////////////////////////////////////

	// Converting units from inch to mm
	public double inchToMM(double inInch) {
		double inMM = inInch * 25.4;
		return inMM;
	}
	////////////////////////////////////////////////////////////////////////

	// Get layers count (matrix file) for declaration arrays of layers
	public int getLayersCount(String matPath) {
		int count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(matPath))) {
			String s;
			while ((s = br.readLine()) != null) {
				if (s.equals("LAYER {"))
					count++;
			}
		} catch (Exception e) {
			System.err.println("File read error!!");
		}
		return count;
	}
	////////////////////////////////////////////////////////////////////////

	// Get path of steps directory from matrix file
	public String getStepsPath(String matPath) {
		String stepName = "";
		try (BufferedReader br = new BufferedReader(new FileReader(matPath))) {
			String s;
			while ((s = br.readLine()) != null) {
				if (s.equals("STEP {")) {
					s = br.readLine();
					s = br.readLine();
					int equalsPos = s.indexOf("=") + 1;
					while (equalsPos < s.length()) {
						stepName = stepName + Character.toString(s.charAt(equalsPos));
						equalsPos++;
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return (directoryPath + separator + "steps" + separator + stepName + separator + "layers" + separator);
	}
	////////////////////////////////////////////////////////////////////////

	// Get all componnets info from file choosed in ListView1 and save to
	// cmpInfoFromCmp array - layer TOP
	public void getCmpInfoFromCmplv1(String cmp1path) {
		try (BufferedReader br = new BufferedReader(new FileReader(cmp1path))) {
			String s;
			while ((s = br.readLine()) != null) {
				if (s.contains("# CMP ")) {
					s = br.readLine();

					String symbolName = "";
					String packageName = "";
					double cmpPosXtemp;
					double cmpPosYtemp;

					if (s.contains("CMP")) {
						int spacePos = s.indexOf(" ");
						int spacePosNext = s.indexOf(" ", spacePos + 1);
						spacePos = spacePosNext;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						cmpPosXtemp = Double.parseDouble(s.substring(spacePos + 1, spacePosNext));
						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						cmpPosYtemp = Double.parseDouble(s.substring(spacePos, spacePosNext));
						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						symbolName = s.substring(spacePos, spacePosNext);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						packageName = s.substring(spacePos, spacePosNext);
						int pinCount = 0;
						boolean endCmp = false;
						cmpInfoFromCmp tempcmpInfoFromCmp = new cmpInfoFromCmp();
						while (endCmp == false) {

							s = br.readLine();
							if (s.contains("TOP")) {
								pinCount++;

							}
							if (s.contains("#")) {
								endCmp = true;
							}
						}
						tempcmpInfoFromCmp.name = symbolName;
						tempcmpInfoFromCmp.pckID = symbolName;
						tempcmpInfoFromCmp.cmpPosX = inchToMM(cmpPosXtemp);
						tempcmpInfoFromCmp.cmpPosY = inchToMM(cmpPosYtemp);
						tempcmpInfoFromCmp.pinCount = pinCount;
						cmpInfoFromCmp.addElement(tempcmpInfoFromCmp);

					}
				}

			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		/*
		 * for (int x = 0; x < cmpInfoFromCmp.size(); x++) {
		 * System.out.println(cmpInfoFromCmp.get(x).name + " " +
		 * cmpInfoFromCmp.get(x).pckID + " " + cmpInfoFromCmp.get(x).cmpPosX +
		 * " " + cmpInfoFromCmp.get(x).cmpPosY + " " +
		 * cmpInfoFromCmp.get(x).pinCount); }
		 */
	}
	////////////////////////////////////////////////////////////////////////

	// Get all componnets info from file choosed in ListView2 and save to
	// cmpInfoFromCmp1 array - layer BOT
	public void getCmpInfoFromCmplv2(String cmp2path) {
		try (BufferedReader br = new BufferedReader(new FileReader(cmp2path))) {
			String s;
			while ((s = br.readLine()) != null) {
				if (s.contains("# CMP ")) {
					s = br.readLine();

					String symbolName = "";
					String packageName = "";
					double cmpPosXtemp;
					double cmpPosYtemp;

					if (s.contains("CMP")) {
						int spacePos = s.indexOf(" ");
						int spacePosNext = s.indexOf(" ", spacePos + 1);
						spacePos = spacePosNext;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						cmpPosXtemp = Double.parseDouble(s.substring(spacePos + 1, spacePosNext));
						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						cmpPosYtemp = Double.parseDouble(s.substring(spacePos, spacePosNext));
						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						symbolName = s.substring(spacePos, spacePosNext);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						packageName = s.substring(spacePos, spacePosNext);
						int pinCount = 0;
						boolean endCmp = false;
						cmpInfoFromCmp tempcmpInfoFromCmp = new cmpInfoFromCmp();
						while (endCmp == false) {

							s = br.readLine();
							if (s.contains("TOP")) {
								pinCount++;

							}
							if (s.contains("#")) {
								endCmp = true;
							}
						}
						tempcmpInfoFromCmp.name = symbolName;
						// tempcmpInfoFromCmp.pckID = symbolName + "/" +
						// packageName;
						tempcmpInfoFromCmp.pckID = symbolName;
						tempcmpInfoFromCmp.cmpPosX = inchToMM(cmpPosXtemp);
						tempcmpInfoFromCmp.cmpPosY = inchToMM(cmpPosYtemp);
						tempcmpInfoFromCmp.pinCount = pinCount;
						cmpInfoFromCmp1.addElement(tempcmpInfoFromCmp);
					}
				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		/*
		 * for (int x = 0; x < cmpInfoFromCmp.size(); x++) {
		 * System.out.println(cmpInfoFromCmp.get(x).name + " " +
		 * cmpInfoFromCmp.get(x).pckID + " " + cmpInfoFromCmp.get(x).cmpPosX +
		 * " " + cmpInfoFromCmp.get(x).cmpPosY + " " +
		 * cmpInfoFromCmp.get(x).pinCount); }
		 */
	}
	////////////////////////////////////////////////////////////////////////

	// Merge informations of pads from pads and components file - layer TOP
	public void getAllPadsInfoFromCmpAndPads1() {
		for (int x = 0; x < padsAllInfoFromPads.size(); x++) {
			for (int y = 0; y < padsInfoFromCmp.size(); y++) {
				if ((padsAllInfoFromPads.get(x).posX == padsInfoFromCmp.get(y).posX)
						&& (padsAllInfoFromPads.get(x).posY == padsInfoFromCmp.get(y).posY)) {
					padsAllInfoFromPadsAndCmp temppadsAllInfoFromPadsAndCmp = new padsAllInfoFromPadsAndCmp();
					temppadsAllInfoFromPadsAndCmp.pckID = padsInfoFromCmp.get(y).pckID;
					temppadsAllInfoFromPadsAndCmp.label = padsInfoFromCmp.get(y).label;
					temppadsAllInfoFromPadsAndCmp.pinPosX = inchToMM(padsAllInfoFromPads.get(x).posX);
					temppadsAllInfoFromPadsAndCmp.pinPosY = inchToMM(padsAllInfoFromPads.get(x).posY);
					temppadsAllInfoFromPadsAndCmp.rotation = 0;
					temppadsAllInfoFromPadsAndCmp.width = milToMM(padsAllInfoFromPads.get(x).width);
					temppadsAllInfoFromPadsAndCmp.height = milToMM(padsAllInfoFromPads.get(x).height);
					temppadsAllInfoFromPadsAndCmp.shape = padsAllInfoFromPads.get(x).shape;
					padsAllInfoFromPadsAndCmp1.addElement(temppadsAllInfoFromPadsAndCmp);
				}
			}
		}
		/*
		 * for (int x = 0; x < padsAllInfoFromPadsAndCmp1.size(); x++) {
		 * System.out.println(padsAllInfoFromPadsAndCmp1.get(x).pckID + " " +
		 * padsAllInfoFromPadsAndCmp1.get(x).label + " " +
		 * padsAllInfoFromPadsAndCmp1.get(x).pinPosX + " " +
		 * padsAllInfoFromPadsAndCmp1.get(x).pinPosY + " " +
		 * padsAllInfoFromPadsAndCmp1.get(x).rotation + " " +
		 * padsAllInfoFromPadsAndCmp1.get(x).width + " " +
		 * padsAllInfoFromPadsAndCmp1.get(x).height + " " +
		 * padsAllInfoFromPadsAndCmp1.get(x).shape); }
		 */

	}
	////////////////////////////////////////////////////////////////////////

	// Merge informations of pads from pads and components file - layer BOT
	public void getAllPadsInfoFromCmpAndPads2() {
		for (int x = 0; x < padsAllInfoFromPads1.size(); x++) {
			for (int y = 0; y < padsInfoFromCmp1.size(); y++) {
				if ((padsAllInfoFromPads1.get(x).posX == padsInfoFromCmp1.get(y).posX)
						&& (padsAllInfoFromPads1.get(x).posY == padsInfoFromCmp1.get(y).posY)) {
					padsAllInfoFromPadsAndCmp temppadsAllInfoFromPadsAndCmp = new padsAllInfoFromPadsAndCmp();
					temppadsAllInfoFromPadsAndCmp.pckID = padsInfoFromCmp1.get(y).pckID;
					temppadsAllInfoFromPadsAndCmp.label = padsInfoFromCmp1.get(y).label;
					temppadsAllInfoFromPadsAndCmp.pinPosX = inchToMM(padsAllInfoFromPads1.get(x).posX);
					temppadsAllInfoFromPadsAndCmp.pinPosY = inchToMM(padsAllInfoFromPads1.get(x).posY);
					temppadsAllInfoFromPadsAndCmp.rotation = 0;
					temppadsAllInfoFromPadsAndCmp.width = milToMM(padsAllInfoFromPads1.get(x).width);
					temppadsAllInfoFromPadsAndCmp.height = milToMM(padsAllInfoFromPads1.get(x).height);
					temppadsAllInfoFromPadsAndCmp.shape = padsAllInfoFromPads1.get(x).shape;
					padsAllInfoFromPadsAndCmp11.addElement(temppadsAllInfoFromPadsAndCmp);
				}
			}
		}
		/*
		 * for (int x = 0; x < padsAllInfoFromPadsAndCmp11.size(); x++) {
		 * System.out.println(padsAllInfoFromPadsAndCmp11.get(x).pckID + " " +
		 * padsAllInfoFromPadsAndCmp11.get(x).label + " " +
		 * padsAllInfoFromPadsAndCmp11.get(x).pinPosX + " " +
		 * padsAllInfoFromPadsAndCmp11.get(x).pinPosY + " " +
		 * padsAllInfoFromPadsAndCmp11.get(x).rotation + " " +
		 * padsAllInfoFromPadsAndCmp11.get(x).width + " " +
		 * padsAllInfoFromPadsAndCmp11.get(x).height + " " +
		 * padsAllInfoFromPadsAndCmp11.get(x).shape); }
		 */

	}
	////////////////////////////////////////////////////////////////////////

	// Get pads info from components file and save to padsInfoFromCmp array -
	// layer TOP, ListView1
	public void getPadsInfoFromCmplv1(String cmp1path) {
		try (BufferedReader br = new BufferedReader(new FileReader(cmp1path))) {
			String s;
			while ((s = br.readLine()) != null) {
				if (s.contains("# CMP ")) {
					s = br.readLine();

					String symbolName = "";
					String packageName = "";
					double cmpPosXtemp;
					double cmpPosYtemp;

					if (s.contains("CMP")) {
						int spacePos = s.indexOf(" ");
						int spacePosNext = s.indexOf(" ", spacePos + 1);
						spacePos = spacePosNext;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						cmpPosXtemp = Double.parseDouble(s.substring(spacePos + 1, spacePosNext));
						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						cmpPosYtemp = Double.parseDouble(s.substring(spacePos, spacePosNext));
						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						symbolName = s.substring(spacePos, spacePosNext);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						packageName = s.substring(spacePos, spacePosNext);

						int pinCount = 0;
						boolean endCmp = false;
						int id = 1;
						while (endCmp == false) {
							padsInfoFromCmp temppadsInfoFromCmp = new padsInfoFromCmp();

							s = br.readLine();
							while (s.contains("PRP")) {
								s = br.readLine();
							}

							if (s.contains("TOP")) {
								spacePos = s.indexOf(" ");
								spacePosNext = s.indexOf(" ", spacePos + 1);
								spacePos = spacePosNext;
								spacePosNext = s.indexOf(" ", spacePos + 1);

								double x = Double.parseDouble(s.substring(spacePos + 1, spacePosNext));
								double roundX = Math.round(100000 * x);
								temppadsInfoFromCmp.posX = roundX / 100000;

								spacePos = spacePosNext + 1;
								spacePosNext = s.indexOf(" ", spacePos + 1);

								double y = Double.parseDouble(s.substring(spacePos, spacePosNext));
								double roundY = Math.round(100000 * y);
								temppadsInfoFromCmp.posY = roundY / 100000;

								temppadsInfoFromCmp.pckID = symbolName;
								temppadsInfoFromCmp.label = symbolName + "/" + id;
								padsInfoFromCmp.addElement(temppadsInfoFromCmp);

								id++;

							}
							if (s.contains("#")) {
								endCmp = true;
							}
						}
					}
				}
			}
		}

		catch (Exception e) {
			System.err.println(e.getMessage());
		}

		/*
		 * for (int x = 0; x < padsInfoFromCmp.size(); x++) {
		 * System.out.println(padsInfoFromCmp.get(x).pckID + " " +
		 * padsInfoFromCmp.get(x).label + " " + padsInfoFromCmp.get(x).posX +
		 * " " + padsInfoFromCmp.get(x).posY); }
		 */
	}
	////////////////////////////////////////////////////////////////////////

	// Get pads info from components file and save to padsInfoFromCmp array -
	// layer BOT, ListView2
	public void getPadsInfoFromCmplv2(String cmp2path) {
		try (BufferedReader br = new BufferedReader(new FileReader(cmp2path))) {
			String s;
			while ((s = br.readLine()) != null) {
				if (s.contains("# CMP ")) {
					s = br.readLine();

					String symbolName = "";
					String packageName = "";
					double cmpPosXtemp;
					double cmpPosYtemp;

					if (s.contains("CMP")) {
						int spacePos = s.indexOf(" ");
						int spacePosNext = s.indexOf(" ", spacePos + 1);
						spacePos = spacePosNext;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						cmpPosXtemp = Double.parseDouble(s.substring(spacePos + 1, spacePosNext));
						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						cmpPosYtemp = Double.parseDouble(s.substring(spacePos, spacePosNext));
						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						symbolName = s.substring(spacePos, spacePosNext);

						spacePos = spacePosNext + 1;
						spacePosNext = s.indexOf(" ", spacePos + 1);

						packageName = s.substring(spacePos, spacePosNext);

						int pinCount = 0;
						boolean endCmp = false;
						int id = 1;
						while (endCmp == false) {
							padsInfoFromCmp temppadsInfoFromCmp = new padsInfoFromCmp();
							s = br.readLine();
							while (s.contains("PRP")) {
								s = br.readLine();
							}
							if (s.contains("TOP")) {
								spacePos = s.indexOf(" ");
								spacePosNext = s.indexOf(" ", spacePos + 1);

								spacePos = spacePosNext;
								spacePosNext = s.indexOf(" ", spacePos + 1);

								double x = Double.parseDouble(s.substring(spacePos + 1, spacePosNext));
								double roundX = Math.round(100000 * x);
								temppadsInfoFromCmp.posX = roundX / 100000;

								spacePos = spacePosNext + 1;
								spacePosNext = s.indexOf(" ", spacePos + 1);

								double y = Double.parseDouble(s.substring(spacePos, spacePosNext));
								double roundY = Math.round(100000 * y);
								temppadsInfoFromCmp.posY = roundY / 100000;

								temppadsInfoFromCmp.pckID = symbolName;
								temppadsInfoFromCmp.label = symbolName + "/" + id;
								padsInfoFromCmp1.addElement(temppadsInfoFromCmp);

								id++;

							}
							if (s.contains("#")) {
								endCmp = true;
							}
						}
					}
				}
			}
		}

		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		/*
		 * for (int x = 0; x < padsInfoFromCmp.size(); x++) {
		 * System.out.println(padsInfoFromCmp.get(x).pckID + " " +
		 * padsInfoFromCmp.get(x).label + " " + padsInfoFromCmp.get(x).posX +
		 * " " + padsInfoFromCmp.get(x).posY); }
		 */
	}
	////////////////////////////////////////////////////////////////////////

	// Merge pads info and pads types from pads file to padPosInfo array - layer
	// TOP
	public void getAllPadInfolv1(String padTypes1Path) {
		try (BufferedReader br = new BufferedReader(new FileReader(padTypes1Path))) {
			String s;
			while ((s = br.readLine()) != null) {
				char charP = 80;
				if (s.contains("#Layer features")) {
					while ((s = br.readLine()) != null) {
						padPosInfo temppadPosInfo = new padPosInfo();
						if (s.charAt(0) == charP) {
							int spacePos = s.indexOf(" ");
							int spacePosNext = s.indexOf(" ", spacePos + 1);
							int lastNumPos = spacePosNext + 1;

							double x = Double.parseDouble(s.substring(spacePos + 1, lastNumPos));
							double roundX = Math.round(100000 * x);
							temppadPosInfo.posX = roundX / 100000;
							spacePos = spacePosNext + 1;
							spacePosNext = s.indexOf(" ", spacePos + 1);
							double y = Double.parseDouble(s.substring(spacePos, spacePosNext));
							double roundY = Math.round(100000 * y);
							temppadPosInfo.posY = roundY / 100000;

							// System.out.println(temppadPosInfo.posX + " " +
							// temppadPosInfo.posY);

							spacePos = spacePosNext + 1;
							spacePosNext = s.indexOf(" ", spacePos + 1);
							temppadPosInfo.id = Integer.parseInt(s.substring(spacePos, spacePosNext));
							padPosInfo.addElement(temppadPosInfo);
						}

					}
				}
			}
			for (int x = 0; x < padPosInfo.size(); x++) {
				for (int y = 0; y < padType.size(); y++) {
					if (padType.get(y).id == padPosInfo.get(x).id) {
						padsAllInfoFromPads temppadAllInfo = new padsAllInfoFromPads();
						temppadAllInfo.posX = padPosInfo.get(x).posX;
						temppadAllInfo.posY = padPosInfo.get(x).posY;
						temppadAllInfo.width = padType.get(y).width;
						temppadAllInfo.height = padType.get(y).height;
						temppadAllInfo.shape = padType.get(y).shape;
						padsAllInfoFromPads.addElement(temppadAllInfo);

					}
				}

			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		/*
		 * for (int x = 0; x < padsAllInfoFromPads.size(); x++) {
		 * System.out.println(padsAllInfoFromPads.get(x).posX + " " +
		 * padsAllInfoFromPads.get(x).posY + " " +
		 * padsAllInfoFromPads.get(x).width + " "
		 * +padsAllInfoFromPads.get(x).height); }
		 */

	}
	////////////////////////////////////////////////////////////////////////

	// Merge pads info and pads types from pads file to padPosInfo array - layer
	// BOT
	public void getAllPadInfolv2(String padTypes2Path) {
		try (BufferedReader br = new BufferedReader(new FileReader(padTypes2Path))) {
			String s;
			while ((s = br.readLine()) != null) {
				char charP = 80;
				if (s.contains("#Layer features")) {
					while ((s = br.readLine()) != null) {
						padPosInfo temppadPosInfo = new padPosInfo();
						if (s.charAt(0) == charP) {
							int spacePos = s.indexOf(" ");
							int spacePosNext = s.indexOf(" ", spacePos + 1);
							int lastNumPos = spacePosNext + 1;

							double x = Double.parseDouble(s.substring(spacePos + 1, lastNumPos));
							double roundX = Math.round(100000 * x);
							temppadPosInfo.posX = roundX / 100000;

							spacePos = spacePosNext + 1;
							spacePosNext = s.indexOf(" ", spacePos + 1);
							double y = Double.parseDouble(s.substring(spacePos, spacePosNext));
							double roundY = Math.round(100000 * y);
							temppadPosInfo.posY = roundY / 100000;

							// System.out.println(temppadPosInfo.posX + " " +
							// temppadPosInfo.posY);

							spacePos = spacePosNext + 1;
							spacePosNext = s.indexOf(" ", spacePos + 1);
							temppadPosInfo.id = Integer.parseInt(s.substring(spacePos, spacePosNext));
							padPosInfo1.addElement(temppadPosInfo);
						}

					}
				}
			}
			for (int x = 0; x < padPosInfo1.size(); x++) {
				for (int y = 0; y < padType1.size(); y++) {
					if (padType1.get(y).id == padPosInfo1.get(x).id) {
						padsAllInfoFromPads temppadAllInfo = new padsAllInfoFromPads();
						temppadAllInfo.posX = padPosInfo1.get(x).posX;
						temppadAllInfo.posY = padPosInfo1.get(x).posY;
						temppadAllInfo.width = padType1.get(y).width;
						temppadAllInfo.height = padType1.get(y).height;
						temppadAllInfo.shape = padType1.get(y).shape;
						padsAllInfoFromPads1.addElement(temppadAllInfo);

					}
				}

			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		/*
		 * for (int x = 0; x < padsAllInfoFromPads1.size(); x++) {
		 * System.out.println(padsAllInfoFromPads1.get(x).posX + " " +
		 * padsAllInfoFromPads1.get(x).posY + " " +
		 * padsAllInfoFromPads1.get(x).width + " "
		 * +padsAllInfoFromPads1.get(x).height); }
		 */
	}
	////////////////////////////////////////////////////////////////////////

	// Get pad types from pads file, save to padType array, layer TOP
	public void getPadTypeslv1(String padTypes1Path) {

		try (BufferedReader br = new BufferedReader(new FileReader(padTypes1Path))) {
			String s;
			while ((s = br.readLine()) != null) {

				padType temppadType = new padType();
				int firstNum = s.indexOf("$");
				if (firstNum == 0) {
					int lastNum = s.indexOf(" ");
					temppadType.id = Integer.parseInt(s.substring(firstNum + 1, lastNum));
					if (s.contains("r")) {

						if (s.contains("smd") && s.contains("rec") && !s.contains("+") && !s.contains("rect")) {
							temppadType.shape = "macro";
							temppadType.width = Double.parseDouble(s.substring(s.indexOf("d") + 1, s.indexOf("r")));
							temppadType.height = Double.parseDouble(s.substring(s.indexOf("c") + 1, s.length()));
						}

						if ((s.contains("smd") && s.contains("rec")) && (s.contains("+") && !s.contains("rect"))) {
							temppadType.shape = "macro";
							temppadType.width = Double.parseDouble(s.substring(s.indexOf("d") + 1, s.indexOf("r")));
							temppadType.height = Double.parseDouble(s.substring(s.indexOf("c") + 1, s.indexOf("+")));
						}

						if (s.contains("rect")) {
							int letterIsX = 0;
							char isX = 120;
							for (int i = 0; i < s.length(); i++) {
								if (s.charAt(i) == isX)
									letterIsX++;
							}
							temppadType.shape = "rect";
							if (letterIsX == 1) {
								temppadType.width = Double.parseDouble(s.substring(s.indexOf("t") + 1, s.indexOf("x")));
								int length = s.length() - 1;
								int letterI = 73;
								if (s.charAt(length) == letterI) {
									temppadType.height = Double
											.parseDouble(s.substring(s.lastIndexOf("x") + 1, length - 1));
								} else
									temppadType.height = Double
											.parseDouble(s.substring(s.lastIndexOf("x") + 1, s.length()));
							}
							if (letterIsX == 2) {
								temppadType.width = Double.parseDouble(s.substring(s.indexOf("t") + 1, s.indexOf("x")));
								int isx = s.indexOf("x");
								temppadType.height = Double
										.parseDouble(s.substring(s.indexOf("x") + 1, s.indexOf("x", isx + 1)));
							}
						}

					}

					if (s.contains("r") && !s.contains("rec") && !s.contains("rect")) {
						temppadType.shape = "round";
						temppadType.width = Double.parseDouble(s.substring(s.lastIndexOf("r") + 1, s.length()));
						temppadType.height = 0;
					}

					if (s.contains("oval")) {
						temppadType.shape = "oval";
						temppadType.width = Double.parseDouble(s.substring(s.indexOf("l") + 1, s.indexOf("x")));
						temppadType.height = Double.parseDouble(s.substring(s.lastIndexOf("x") + 1, s.length()));
					}

					if (s.contains("s")) {
						if (s.contains("smd")) {
							temppadType.shape = "macro";
							int letterC = 99;

							if (s.contains("_") && s.contains("+")) {
								temppadType.width = Double.parseDouble(s.substring(s.indexOf("d") + 1, s.indexOf("_")));
								temppadType.height = Double
										.parseDouble(s.substring(s.indexOf("_") + 1, s.indexOf("+")));
							}

							if (s.contains("_") && !s.contains("+")) {
								// _
								temppadType.width = Double.parseDouble(s.substring(s.indexOf("d") + 1, s.indexOf("_")));
								temppadType.height = Double.parseDouble(s.substring(s.indexOf("_") + 1, s.length()));
							}

						} else {
							temppadType.shape = "square";
							temppadType.width = Double.parseDouble(s.substring(s.indexOf("s") + 1, s.length()));
							temppadType.height = Double.parseDouble(s.substring(s.indexOf("s") + 1, s.length()));
						}
					}

					padType.addElement(temppadType);

				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		/*
		 * for (int x = 0; x < padType.size(); x++) {
		 * System.out.println(padType.get(x).id + " " + padType.get(x).shape +
		 * " " + padType.get(x).width + " " + padType.get(x).height); }
		 */
	}
	////////////////////////////////////////////////////////////////////////

	// Get pad types from pads file, save to padType1 array, layer BOT
	public void getPadTypeslv2(String padTypes2Path) {

		try (BufferedReader br = new BufferedReader(new FileReader(padTypes2Path))) {
			String s;
			while ((s = br.readLine()) != null) {

				padType temppadType = new padType();
				int firstNum = s.indexOf("$");
				if (firstNum == 0) {
					int lastNum = s.indexOf(" ");
					temppadType.id = Integer.parseInt(s.substring(firstNum + 1, lastNum));
					if (s.contains("r")) {

						if (s.contains("smd") && s.contains("rec") && !s.contains("+") && !s.contains("rect")) {
							temppadType.shape = "macro";
							temppadType.width = Double.parseDouble(s.substring(s.indexOf("d") + 1, s.indexOf("r")));
							temppadType.height = Double.parseDouble(s.substring(s.indexOf("c") + 1, s.length()));
						}

						if ((s.contains("smd") && s.contains("rec")) && (s.contains("+") && !s.contains("rect"))) {
							temppadType.shape = "macro";
							temppadType.width = Double.parseDouble(s.substring(s.indexOf("d") + 1, s.indexOf("r")));
							temppadType.height = Double.parseDouble(s.substring(s.indexOf("c") + 1, s.indexOf("+")));
						}

						if (s.contains("rect")) {
							int letterIsX = 0;
							char isX = 120;
							for (int i = 0; i < s.length(); i++) {
								if (s.charAt(i) == isX)
									letterIsX++;
							}
							temppadType.shape = "rect";
							if (letterIsX == 1) {
								temppadType.width = Double.parseDouble(s.substring(s.indexOf("t") + 1, s.indexOf("x")));
								int length = s.length() - 1;
								int letterI = 73;
								if (s.charAt(length) == letterI) {
									temppadType.height = Double
											.parseDouble(s.substring(s.lastIndexOf("x") + 1, length - 1));
								} else
									temppadType.height = Double
											.parseDouble(s.substring(s.lastIndexOf("x") + 1, s.length()));
							}
							if (letterIsX == 2) {
								temppadType.width = Double.parseDouble(s.substring(s.indexOf("t") + 1, s.indexOf("x")));
								int isx = s.indexOf("x");
								temppadType.height = Double
										.parseDouble(s.substring(s.indexOf("x") + 1, s.indexOf("x", isx + 1)));
							}
						}

					}
					if (s.contains("r") && !s.contains("rec") && !s.contains("rect")) {
						temppadType.shape = "round";
						temppadType.width = Double.parseDouble(s.substring(s.lastIndexOf("r") + 1, s.length()));
						temppadType.height = 0;
					}

					if (s.contains("oval")) {
						temppadType.shape = "oval";
						temppadType.width = Double.parseDouble(s.substring(s.indexOf("l") + 1, s.indexOf("x")));
						temppadType.height = Double.parseDouble(s.substring(s.lastIndexOf("x") + 1, s.length()));
					}

					if (s.contains("s")) {
						if (s.contains("smd")) {
							temppadType.shape = "macro";
							int letterC = 99;

							if (s.contains("_") && s.contains("+")) {
								temppadType.width = Double.parseDouble(s.substring(s.indexOf("d") + 1, s.indexOf("_")));
								temppadType.height = Double
										.parseDouble(s.substring(s.indexOf("_") + 1, s.indexOf("+")));
							}

							if (s.contains("_") && !s.contains("+")) {
								temppadType.width = Double.parseDouble(s.substring(s.indexOf("d") + 1, s.indexOf("_")));
								temppadType.height = Double.parseDouble(s.substring(s.indexOf("_") + 1, s.length()));
							}

						} else {
							temppadType.shape = "square";
							temppadType.width = Double.parseDouble(s.substring(s.indexOf("s") + 1, s.length()));
							temppadType.height = Double.parseDouble(s.substring(s.indexOf("s") + 1, s.length()));
						}
					}

					padType.addElement(temppadType);

				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		/*
		 * for (int x = 0; x < padType1.size(); x++) {
		 * System.out.println(padType1.get(x).id + " " + padType1.get(x).width +
		 * " " + padType1.get(x).height); }
		 */
	}
	////////////////////////////////////////////////////////////////////////

	// Choosing of save directory location, save location to saveLocation
	public void specifySaveLocation() {
		DirectoryChooser fc = new DirectoryChooser();

		fc.setInitialDirectory(directoryPath1);
		fc.setTitle("Select save directory");
		File f = fc.showDialog(null);
		saveLocation = f.getAbsolutePath();
		System.out.println(saveLocation);

	}
	////////////////////////////////////////////////////////////////////////

	// Saving components informations to file - layer TOP
	public void saveCmpToFile() {

		String sideLv1 = ".BOARD_ID: TOP";
		String side1 = "TOP";
		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter(saveLocation + separator + directoryName + "_" + "ComponentsTop.txt"))) {
			bw.write(sideLv1 + "\r\n");

			bw.write(cmpHeader1 + "\r\n");
			bw.write(cmpHeader2 + "\r\n");
			bw.write(cmpHeader3 + " " + String.format("%.5f", dimX) + " " + String.format("%.5f", dimY) + " 1.500"
					+ "\r\n");
			bw.write(cmpHeader4 + "\r\n");
			for (int x = 0; x < cmpInfoFromCmp.size(); x++) {
				if (checkBox.isSelected()) {
					double finX = cmpInfoFromCmp.get(x).cmpPosX - minPosX;
					double finY = cmpInfoFromCmp.get(x).cmpPosY - minPosY;
					bw.write(cmpInfoFromCmp.get(x).name + "\t" + cmpInfoFromCmp.get(x).pckID + "\t"
							+ String.format("%.5f", finX) + "\t" + String.format("%.5f", finY) + "\t"
							+ cmpInfoFromCmp.get(x).rotation + "\t" + cmpInfoFromCmp.get(x).pinCount + "\t" + side1
							+ "\r\n");
				} else {
					bw.write(cmpInfoFromCmp.get(x).name + "\t" + cmpInfoFromCmp.get(x).pckID + "\t"
							+ String.format("%.5f", cmpInfoFromCmp.get(x).cmpPosX) + "\t"
							+ String.format("%.5f", cmpInfoFromCmp.get(x).cmpPosY) + "\t"
							+ cmpInfoFromCmp.get(x).rotation + "\t" + cmpInfoFromCmp.get(x).pinCount + "\t" + side1
							+ "\r\n");
				}

			}

		} catch (Exception e) {
			System.err.println("File write error top!");
		}
	}
	////////////////////////////////////////////////////////////////////////

	// Saving components informations to file - layer BOT
	public void saveCmpToFile1() {
		String sideLv2 = ".BOARD_ID: BOT";
		String side2 = "BOT";

		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter(saveLocation + separator + directoryName + "_" + "ComponentsBot.txt"))) {
			bw.write(sideLv2 + "\r\n");
			// bw.newLine();
			bw.write(cmpHeader1 + "\r\n");
			// bw.newLine();
			bw.write(cmpHeader2 + "\r\n");
			// bw.newLine();
			bw.write(cmpHeader3 + " " + String.format("%.5f", dimX) + " " + String.format("%.5f", dimY) + " 1.500"
					+ "\r\n");
			// bw.newLine();
			bw.write(cmpHeader4 + "\r\n");
			// bw.newLine();

			for (int x = 0; x < cmpInfoFromCmp1.size(); x++) {
				if (checkBox.isSelected()) {
					double finX = cmpInfoFromCmp1.get(x).cmpPosX - minPosX;
					double finY = cmpInfoFromCmp1.get(x).cmpPosY - minPosY;
					bw.write(cmpInfoFromCmp1.get(x).name + "\t" + cmpInfoFromCmp1.get(x).pckID + "\t"
							+ String.format("%.5f", finX) + "\t" + String.format("%.5f", finY) + "\t"
							+ cmpInfoFromCmp1.get(x).rotation + "\t" + cmpInfoFromCmp1.get(x).pinCount + "\t" + side2
							+ "\r\n");
					// + "\t" + cmpInfoFromCmp.get(x).mountType
					// bw.newLine();
				} else {
					bw.write(cmpInfoFromCmp1.get(x).name + "\t" + cmpInfoFromCmp1.get(x).pckID + "\t"
							+ String.format("%.5f", cmpInfoFromCmp1.get(x).cmpPosX) + "\t"
							+ String.format("%.5f", cmpInfoFromCmp1.get(x).cmpPosY) + "\t"
							+ cmpInfoFromCmp1.get(x).rotation + "\t" + cmpInfoFromCmp1.get(x).pinCount + "\t" + side2
							+ "\r\n");
					// + "\t" + cmpInfoFromCmp.get(x).mountType
					// bw.newLine();
				}

			}
		} catch (Exception e) {
			System.err.println("File write error bot! " + e.getMessage());
		}
	}
	////////////////////////////////////////////////////////////////////////

	// save pads information to file, both TOP and BOT layers
	public void savePadsToFile() {
		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter(saveLocation + separator + directoryName + "_" + "Pads.txt"))) {
			bw.write(padsHeader + "\r\n");
			// bw.newLine();
			if (rbTOP.isSelected()) {
				for (int x = 0; x < padsAllInfoFromPadsAndCmp1.size(); x++) {
					if (checkBox.isSelected()) {
						double finX = padsAllInfoFromPadsAndCmp1.get(x).pinPosX - minPosX;
						double finY = padsAllInfoFromPadsAndCmp1.get(x).pinPosY - minPosY;
						bw.write(padsAllInfoFromPadsAndCmp1.get(x).pckID + "\t"
								+ padsAllInfoFromPadsAndCmp1.get(x).label + "\t" + String.format("%.5f", finX) + "\t"
								+ String.format("%.5f", finY) + "\t" + padsAllInfoFromPadsAndCmp1.get(x).rotation + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).width) + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).height) + "\t"
								+ padsAllInfoFromPadsAndCmp1.get(x).shape + "\r\n");
						// bw.newLine();
					} else {
						bw.write(
								padsAllInfoFromPadsAndCmp1.get(x).pckID + "\t" + padsAllInfoFromPadsAndCmp1.get(x).label
										+ "\t" + String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).pinPosX) + "\t"
										+ String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).pinPosY) + "\t"
										+ padsAllInfoFromPadsAndCmp1.get(x).rotation + "\t"
										+ String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).width) + "\t"
										+ String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).height) + "\t"
										+ padsAllInfoFromPadsAndCmp1.get(x).shape + "\r\n");
						// bw.newLine();
					}

				}
			}
			if (rbBOT.isSelected()) {
				for (int x = 0; x < padsAllInfoFromPadsAndCmp11.size(); x++) {
					if (checkBox.isSelected()) {
						double finX = padsAllInfoFromPadsAndCmp11.get(x).pinPosX - minPosX;
						double finY = padsAllInfoFromPadsAndCmp11.get(x).pinPosY - minPosY;
						bw.write(padsAllInfoFromPadsAndCmp11.get(x).pckID + "\t"
								+ padsAllInfoFromPadsAndCmp11.get(x).label + "\t" + String.format("%.5f", finX) + "\t"
								+ String.format("%.5f", finY) + "\t" + padsAllInfoFromPadsAndCmp11.get(x).rotation
								+ "\t" + String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).width) + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).height) + "\t"
								+ padsAllInfoFromPadsAndCmp11.get(x).shape + "\r\n");
						// bw.newLine();
					} else {
						bw.write(padsAllInfoFromPadsAndCmp11.get(x).pckID + "\t"
								+ padsAllInfoFromPadsAndCmp11.get(x).label + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).pinPosX) + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).pinPosY) + "\t"
								+ padsAllInfoFromPadsAndCmp11.get(x).rotation + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).width) + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).height) + "\t"
								+ padsAllInfoFromPadsAndCmp11.get(x).shape + "\r\n");
						// bw.newLine();
					}
				}
			}

			if (rbTOPBOT.isSelected()) {
				for (int x = 0; x < padsAllInfoFromPadsAndCmp1.size(); x++) {
					if (checkBox.isSelected()) {
						double finX = padsAllInfoFromPadsAndCmp1.get(x).pinPosX - minPosX;
						double finY = padsAllInfoFromPadsAndCmp1.get(x).pinPosY - minPosY;
						bw.write(padsAllInfoFromPadsAndCmp1.get(x).pckID + "\t"
								+ padsAllInfoFromPadsAndCmp1.get(x).label + "\t" + String.format("%.5f", finX) + "\t"
								+ String.format("%.5f", finY) + "\t" + padsAllInfoFromPadsAndCmp1.get(x).rotation + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).width) + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).height) + "\t"
								+ padsAllInfoFromPadsAndCmp1.get(x).shape + "\r\n");
						// bw.newLine();
					} else {
						bw.write(
								padsAllInfoFromPadsAndCmp1.get(x).pckID + "\t" + padsAllInfoFromPadsAndCmp1.get(x).label
										+ "\t" + String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).pinPosX) + "\t"
										+ String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).pinPosY) + "\t"
										+ padsAllInfoFromPadsAndCmp1.get(x).rotation + "\t"
										+ String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).width) + "\t"
										+ String.format("%.5f", padsAllInfoFromPadsAndCmp1.get(x).height) + "\t"
										+ padsAllInfoFromPadsAndCmp1.get(x).shape + "\r\n");
						// bw.newLine();
					}
				}

				for (int x = 0; x < padsAllInfoFromPadsAndCmp11.size(); x++) {
					if (checkBox.isSelected()) {
						double finX = padsAllInfoFromPadsAndCmp11.get(x).pinPosX - minPosX;
						double finY = padsAllInfoFromPadsAndCmp11.get(x).pinPosY - minPosY;
						bw.write(padsAllInfoFromPadsAndCmp11.get(x).pckID + "\t"
								+ padsAllInfoFromPadsAndCmp11.get(x).label + "\t" + String.format("%.5f", finX) + "\t"
								+ String.format("%.5f", finY) + "\t" + padsAllInfoFromPadsAndCmp11.get(x).rotation
								+ "\t" + String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).width) + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).height) + "\t"
								+ padsAllInfoFromPadsAndCmp11.get(x).shape + "\r\n");
						// bw.newLine();
					} else {
						bw.write(padsAllInfoFromPadsAndCmp11.get(x).pckID + "\t"
								+ padsAllInfoFromPadsAndCmp11.get(x).label + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).pinPosX) + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).pinPosY) + "\t"
								+ padsAllInfoFromPadsAndCmp11.get(x).rotation + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).width) + "\t"
								+ String.format("%.5f", padsAllInfoFromPadsAndCmp11.get(x).height) + "\t"
								+ padsAllInfoFromPadsAndCmp11.get(x).shape + "\r\n");
						// bw.newLine();
					}
				}

			}

		} catch (Exception e) {
			System.err.println("File write error pads!");
		}

	}
	////////////////////////////////////////////////////////////////////////

	// Get board dimension from the smallest and the largest coordinates
	public void getDimensions() {

		for (int x = 0; x < padsAllInfoFromPadsAndCmp1.size(); x++) {
			allCoordinates tempallCoordinates = new allCoordinates();
			tempallCoordinates.x = padsAllInfoFromPadsAndCmp1.get(x).pinPosX;
			tempallCoordinates.y = padsAllInfoFromPadsAndCmp1.get(x).pinPosY;
			allCoordinates.addElement(tempallCoordinates);
		}

		for (int x = 0; x < padsAllInfoFromPadsAndCmp11.size(); x++) {
			allCoordinates tempallCoordinates = new allCoordinates();
			tempallCoordinates.x = padsAllInfoFromPadsAndCmp11.get(x).pinPosX;
			tempallCoordinates.y = padsAllInfoFromPadsAndCmp11.get(x).pinPosY;
			allCoordinates.addElement(tempallCoordinates);
		}

		for (int x = 0; x < cmpInfoFromCmp.size(); x++) {
			allCoordinates tempallCoordinates = new allCoordinates();
			tempallCoordinates.x = cmpInfoFromCmp.get(x).cmpPosX;
			tempallCoordinates.y = cmpInfoFromCmp.get(x).cmpPosY;
			allCoordinates.addElement(tempallCoordinates);
		}

		for (int x = 0; x < cmpInfoFromCmp1.size(); x++) {
			allCoordinates tempallCoordinates = new allCoordinates();
			tempallCoordinates.x = cmpInfoFromCmp1.get(x).cmpPosX;
			tempallCoordinates.y = cmpInfoFromCmp1.get(x).cmpPosY;
			allCoordinates.addElement(tempallCoordinates);
		}

		/*
		 * for (int x = 0; x < allCoordinates.size(); x++) {
		 * System.out.println(allCoordinates.get(x).x + "  " +
		 * allCoordinates.get(x).y); }
		 */

		int minX = 0;
		for (int x = 0; x < allCoordinates.size(); x++) {
			if (allCoordinates.get(x).x < allCoordinates.get(minX).x) {
				minX = x;
			}
		}
		int minY = 0;
		for (int x = 0; x < allCoordinates.size(); x++) {
			if (allCoordinates.get(x).y < allCoordinates.get(minY).y) {
				minY = x;
			}
		}
		int maxX = 0;
		for (int x = 0; x < allCoordinates.size(); x++) {
			if (allCoordinates.get(x).x > allCoordinates.get(maxX).x) {
				maxX = x;
			}
		}

		int maxY = 0;
		for (int x = 0; x < allCoordinates.size(); x++) {
			if (allCoordinates.get(x).y > allCoordinates.get(maxY).y) {
				maxY = x;
			}
		}

		minPosX = allCoordinates.get(minX).x - 10;
		minPosY = allCoordinates.get(minY).y - 10;
		maxPosX = allCoordinates.get(maxX).x + 10;
		maxPosY = allCoordinates.get(maxY).y + 10;
		if (checkBox.isSelected()) {
			dimX = maxPosX - minPosX;
			dimY = maxPosY - minPosY;
		} else {
			dimX = allCoordinates.get(maxX).x + 10;
			dimY = allCoordinates.get(maxY).y + 10;
		}
		// System.out.println(dimX + " " + dimY);
	}
	////////////////////////////////////////////////////////////////////////

	// Reset all layers arrays to default
	public void reset() {
		padType.removeAllElements();
		allCoordinates.removeAllElements();
		padPosInfo.removeAllElements();
		padsAllInfoFromPads.removeAllElements();
		cmpInfoFromCmp.removeAllElements();
		padsInfoFromCmp.removeAllElements();
		padsAllInfoFromPadsAndCmp1.removeAllElements();
		padsAllInfoFromPadsAndCmp1Convers.removeAllElements();
		padType1.removeAllElements();
		padPosInfo1.removeAllElements();
		padsAllInfoFromPads1.removeAllElements();
		cmpInfoFromCmp1.removeAllElements();
		padsInfoFromCmp1.removeAllElements();
		padsAllInfoFromPadsAndCmp11.removeAllElements();
		padsAllInfoFromPadsAndCmp1Convers1.removeAllElements();
	}
	////////////////////////////////////////////////////////////////////////

	// Browse button action, getting layers information from matrix file, add
	// layers names to ListViews
	@FXML
	private void openButtonAction(ActionEvent event) {
		DirectoryChooser fc = new DirectoryChooser();
		fc.setTitle("Select ODB++ directory");
		if (openCount > 0) {

			fc.setInitialDirectory(lastDirectory);
			File f = fc.showDialog(null);

			directoryPath1 = f.getAbsoluteFile();
			directoryPath = f.getAbsolutePath();
			directoryName = f.getName();

			lastDirectory = f.getAbsoluteFile();
		} else {
			File f = fc.showDialog(null);
			lastDirectory = f.getAbsoluteFile();

			directoryPath1 = f.getAbsoluteFile();
			directoryPath = f.getAbsolutePath();
			directoryName = f.getName();
		}
		openCount++;

		fileMatrixPath = directoryPath + separator + "matrix" + separator + "matrix";

		if (directoryName.indexOf(".") != -1) {
			directoryName = directoryName.substring(0, directoryName.lastIndexOf("."));
		}

		String[] layersArray = new String[getLayersCount(fileMatrixPath)];
		int i = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(fileMatrixPath))) {
			String s;
			while ((s = br.readLine()) != null) {
				String layName = "";
				if (s.equals("LAYER {")) {
					s = br.readLine();
					s = br.readLine();
					s = br.readLine();
					s = br.readLine();
					int equalsPos = s.indexOf("=") + 1;
					while (equalsPos < s.length()) {
						layName = layName + Character.toString(s.charAt(equalsPos));
						equalsPos++;
					}
					layersArray[i] = layName;
					i++;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		ObservableList<String> names = FXCollections.observableArrayList();
		names.addAll(layersArray);
		lv1cmp.setItems(names);
		lv1pads.setItems(names);
		lv2cmp.setItems(names);
		lv2pads.setItems(names);
		txField.setText(directoryPath);
		buttonSave.setDisable(false);
	}
	////////////////////////////////////////////////////////////////////////

	// Generation of help window
	@FXML
	public void helpButtonAction(ActionEvent event) throws IOException, URISyntaxException {

		Desktop.getDesktop().browse(new URI("http://odbconverter.xf.cz/"));

		/*
		 * Stage helpStage = new Stage(); helpStage.setMinWidth(500);
		 * helpStage.setMinHeight(350); helpStage.setMaxWidth(500);
		 * helpStage.setMaxHeight(350); TextArea help = new TextArea(); Button
		 * buthelp = new Button();
		 * 
		 * 
		 * 
		 * help.setWrapText(true); help.setText(
		 * "This program is used to exporting CAD data for Phoenix X-ray Micromex and Nanomex PCB inspection machines.\n"
		 * +
		 * "Click to Browse button to select the path to unzipped ODB++ direcrory structure.\n"
		 * +
		 * "The component and pad coordinates are exported in the same format as in Phoenix X-ray demo board. "
		 * +
		 * "\nTop and Bottom component files are generared separately, pads file is common.\n"
		 * +
		 * "The switch at the top right allows you to select the type of PCB. "
		 * +
		 * "Some PCB contain a large coordinate offset. To convert the coordinates to smaller offset check - Remove large coordiante offsets. \n"
		 * +
		 * "After selecting the desired layers of data followed by pressing button Generate and save. "
		 * ); help.setEditable(false);
		 * 
		 * StackPane root = new StackPane(); root.getChildren().add(help);
		 * root.getChildren().add(buthelp);
		 * 
		 * 
		 * 
		 * helpStage.setScene(new Scene(root, 350, 500)); helpStage.setTitle(
		 * "ODB++ to Phoenix X-ray CAD data converter - Help");
		 * helpStage.setResizable(false);
		 * //helpStage.initStyle(StageStyle.UTILITY); buthelp.setText("www");
		 * //buthelp.setLayoutX(20); buthelp.setLayoutY(2); helpStage.show();
		 */

	}
	////////////////////////////////////////////////////////////////////////

	// Save and generate button Action, defines of choosed arrays from
	// ListViews, exporting data to files
	@FXML
	private void saveButtonAction(ActionEvent event) {
		specifySaveLocation();
		pathLv1CmpSel = getStepsPath(fileMatrixPath) + lv1cmp.getSelectionModel().getSelectedItem() + separator
				+ "components";
		pathLv1padTypesSel = getStepsPath(fileMatrixPath) + lv1pads.getSelectionModel().getSelectedItem() + separator
				+ "features";
		pathLv2CmpSel = getStepsPath(fileMatrixPath) + lv2cmp.getSelectionModel().getSelectedItem() + separator
				+ "components";
		pathLv2padTypesSel = getStepsPath(fileMatrixPath) + lv2pads.getSelectionModel().getSelectedItem() + separator
				+ "features";

		/*
		 * System.out.println(pathLv1CmpSel);
		 * System.out.println(pathLv1padTypesSel);
		 * System.out.println(pathLv2CmpSel);
		 * System.out.println(pathLv2padTypesSel);
		 */

		if (rbTOP.isSelected()) {
			getPadTypeslv1(pathLv1padTypesSel);
			getAllPadInfolv1(pathLv1padTypesSel);
			getCmpInfoFromCmplv1(pathLv1CmpSel);
			getPadsInfoFromCmplv1(pathLv1CmpSel);
			getAllPadsInfoFromCmpAndPads1();

			getDimensions();

			savePadsToFile();
			saveCmpToFile();
		}
		if (rbTOPBOT.isSelected()) {
			getPadTypeslv1(pathLv1padTypesSel);
			getAllPadInfolv1(pathLv1padTypesSel);
			getCmpInfoFromCmplv1(pathLv1CmpSel);
			getPadsInfoFromCmplv1(pathLv1CmpSel);
			getAllPadsInfoFromCmpAndPads1();

			getPadTypeslv2(pathLv2padTypesSel);
			getAllPadInfolv2(pathLv2padTypesSel);
			getCmpInfoFromCmplv2(pathLv2CmpSel);
			getPadsInfoFromCmplv2(pathLv2CmpSel);

			getAllPadsInfoFromCmpAndPads2();

			getDimensions();

			savePadsToFile();
			saveCmpToFile();
			saveCmpToFile1();
		}
		if (rbBOT.isSelected()) {
			getPadTypeslv2(pathLv2padTypesSel);
			getAllPadInfolv2(pathLv2padTypesSel);
			getCmpInfoFromCmplv2(pathLv2CmpSel);
			getPadsInfoFromCmplv2(pathLv2CmpSel);
			getAllPadsInfoFromCmpAndPads2();

			getDimensions();

			saveCmpToFile1();
			savePadsToFile();
		}
		reset();

	}
	////////////////////////////////////////////////////////////////////////

	// initialize function after program start, managing of radioButtons group
	// and listView components
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		String OS = System.getProperty("os.name");
		if (OS.contains("Windows"))
			separator = "\\";
		else
			separator = "/";

		Locale.setDefault(new Locale("en", "US", "WIN"));
		final ToggleGroup group = new ToggleGroup();
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {
					if (rbTOP.isSelected()) {
						lv1cmp.setVisible(true);
						lv1pads.setVisible(true);
						labl1.setVisible(true);
						labl1cmp.setVisible(true);
						labl1pads.setVisible(true);

						lv2cmp.setVisible(false);
						lv2pads.setVisible(false);
						labl2.setVisible(false);
						labl2cmp.setVisible(false);
						labl2pads.setVisible(false);
					}
					if (rbBOT.isSelected()) {
						lv2cmp.setVisible(true);
						lv2pads.setVisible(true);
						labl2.setVisible(true);
						labl2cmp.setVisible(true);
						labl2pads.setVisible(true);

						lv1cmp.setVisible(false);
						lv1pads.setVisible(false);
						labl1.setVisible(false);
						labl1cmp.setVisible(false);
						labl1pads.setVisible(false);
					}

					if (rbTOPBOT.isSelected()) {
						lv2cmp.setVisible(true);
						lv2pads.setVisible(true);
						labl2.setVisible(true);
						labl2cmp.setVisible(true);
						labl2pads.setVisible(true);

						lv1cmp.setVisible(true);
						lv1pads.setVisible(true);
						labl1.setVisible(true);
						labl1cmp.setVisible(true);
						labl1pads.setVisible(true);
					}
				}
			}
		});
		rbTOP.setToggleGroup(group);
		rbTOP.setSelected(true);
		rbBOT.setToggleGroup(group);
		rbTOPBOT.setToggleGroup(group);
	}
	////////////////////////////////////////////////////////////////////////
}
