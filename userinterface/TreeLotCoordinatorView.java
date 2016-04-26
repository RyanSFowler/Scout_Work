
// specify the package
package userinterface;

// system imports
import java.io.File;
import java.nio.channels.SelectableChannel;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import model.TreeLotCoordinator;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// project imports
import impresario.IModel;
import java.util.prefs.Preferences;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;

//==============================================================
public class TreeLotCoordinatorView extends View
{

	// GUI stuff
	private TreeLotCoordinator myTLC;
	private TextField userid;
	private PasswordField password;
	private RadioButton scoutButton, treeButton, treeTypeButton, salesButton;
	final ToggleGroup group = new ToggleGroup();
	private Button addScoutButton, modifyScoutButton, removeScoutButton;
	private Button addTreeButton, modifyTreeButton, removeTreeButton;
	private Button addTreeTypeButton, modifyTreeTypeButton;
	private Button openShiftButton, closeShiftButton, recordSaleButton;
	private Button englishButton, frenchButton;
	private String transCategory;
	private String transType;
        private Label infoText;
	private Locale locale = new Locale("en", "US");

	private ResourceBundle buttons;
        private ResourceBundle titles;

	// For showing error message
	private MessageView statusLog;

        private Preferences prefs;

        private String optionOne;
        private String optionTwo;
        private String optionThree;
        private String optionFour;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public TreeLotCoordinatorView( IModel treeLotCoordinator)
	{
		super(treeLotCoordinator, "TreeLotCoordinatorView");


		buttons = ResourceBundle.getBundle("ButtonsBundle", locale);
                titles = ResourceBundle.getBundle("TitlesBundle", locale);

                prefs = Preferences.userNodeForPackage(TreeLotCoordinatorView.class);
                prefs.put("langage", "en");
		myTLC = (TreeLotCoordinator) treeLotCoordinator;
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setStyle("-fx-background-color: #ffffff;");
		container.setPadding(new Insets(5, 5, 5, 5));

		// create a Node (Text) for showing the title
		//container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());

		getChildren().add(container);

		setVisibility("scout", false);
		setVisibility("tree", false);
		setVisibility("treeType", false);
		setVisibility("sales", false);

                autoInternalization();
		//populateFields();

		// STEP 0: Be sure you tell your model what keys you are interested in
		myModel.subscribe("LoginError", this);
	}
        
        private void autoInternalization()
        {
            if (System.getProperty("user.language").equals("fr") == true)
            {
                locale = new Locale("fr", "CA");
                frenchButton.setOpacity(1.0);
                englishButton.setOpacity(0.3);
                buttons = ResourceBundle.getBundle("ButtonsBundle", locale);
                titles = ResourceBundle.getBundle("TitlesBundle", locale);
                prefs = Preferences.userNodeForPackage(TreeLotCoordinatorView.class);
                prefs.put("langage", "fr");
                refreshFormContents();
            }
            else
            {
                locale = new Locale("en", "US");
                englishButton.setOpacity(1.0);
                frenchButton.setOpacity(0.3);
                buttons = ResourceBundle.getBundle("ButtonsBundle", locale);
                titles = ResourceBundle.getBundle("TitlesBundle", locale);
                prefs = Preferences.userNodeForPackage(TreeLotCoordinatorView.class);
                prefs.put("langage", "en");
                refreshFormContents();
            }
        }

	// Create the label (Text) for the title of the screen
	//-------------------------------------------------------------
	private Node createTitle()
	{
		Text titleText = new Text("Boy Scout Tree Sales System");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.BLACK);
		titleText.localToScene(getBoundsInLocal());

		return titleText;
	}

	// Create the main form contents
	//-------------------------------------------------------------
	private GridPane createFormContents()
	{
		GridPane grid = new GridPane();
        	grid.setAlignment(Pos.CENTER);
       		grid.setHgap(10);
        	grid.setVgap(10);
        	grid.setPadding(new Insets(25, 25, 25, 25));


        infoText = new Label("Select a category then an option.");
        infoText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        GridPane.setHalignment(infoText, HPos.CENTER);
        grid.add(infoText, 0, 0, 4, 1);

        Line line = new Line(0,0,400,0);
        GridPane.setHalignment(line, HPos.CENTER);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().add(columnConstraints);
        grid.add(line, 0, 1, 4, 1);

 		scoutButton = new RadioButton("Scout Options");
 		scoutButton.setToggleGroup(group);
 		scoutButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	transCategory = "Scout";
       		     	processOptionAction(e);
            	     }
        	});

 		addScoutButton = new Button("Add Scout");
 		addScoutButton.setMaxWidth(Double.MAX_VALUE);
 		addScoutButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	transType = "Add";
	  		     	processAction(e);
	       	     }
 			});

 		modifyScoutButton = new Button("Modify Scout");
 		modifyScoutButton.setMaxWidth(Double.MAX_VALUE);
 		modifyScoutButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	transType = "Modify";
	  		     	processAction(e);
	       	     }
 			});

 		removeScoutButton = new Button("Remove Scout");
 		removeScoutButton.setMaxWidth(Double.MAX_VALUE);
 		removeScoutButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	transType = "Remove";
	  		     	processAction(e);
	       	     }
 			});

 		VBox scoutContainer = new VBox(10);
 		scoutContainer.setAlignment(Pos.TOP_CENTER);
 		scoutContainer.getChildren().add(scoutButton);
 		scoutContainer.getChildren().add(addScoutButton);
 		scoutContainer.getChildren().add(modifyScoutButton);
 		scoutContainer.getChildren().add(removeScoutButton);
		grid.add(scoutContainer, 0, 3);

		treeButton = new RadioButton("Tree Options");
		treeButton.setToggleGroup(group);
		treeButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	transCategory = "Tree";
       		     	processOptionAction(e);
            	     }
        	});

 		addTreeButton = new Button("Add Tree");
 		addTreeButton.setMaxWidth(Double.MAX_VALUE);
 		addTreeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	transType = "Add";
	  		     	processAction(e);
	       	     }
 			});

 		modifyTreeButton = new Button("Modify Tree");
 		modifyTreeButton.setMaxWidth(Double.MAX_VALUE);
 		modifyTreeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	transType = "Update";
	  		     	processAction(e);
	       	     }
 			});

 		removeTreeButton = new Button("Remove Tree");
 		removeTreeButton.setMaxWidth(Double.MAX_VALUE);
 		removeTreeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	transType = "Remove";
	  		     	processAction(e);
	       	     }
 			});

 		VBox treeContainer = new VBox(10);
 		treeContainer.setAlignment(Pos.TOP_CENTER);
 		treeContainer.getChildren().add(treeButton);
 		treeContainer.getChildren().add(addTreeButton);
 		treeContainer.getChildren().add(modifyTreeButton);
 		treeContainer.getChildren().add(removeTreeButton);
		grid.add(treeContainer, 1, 3);

		treeTypeButton = new RadioButton("Tree Type Options");
		treeTypeButton.setToggleGroup(group);
		treeTypeButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	transCategory = "TreeType";
       		     	processOptionAction(e);
            	 }
        	});

 		addTreeTypeButton = new Button("Add Tree Type");
 		addTreeTypeButton.setMaxWidth(Double.MAX_VALUE);
 		addTreeTypeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	transType = "Add";
	  		     	processAction(e);
	       	     }
 			});

 		modifyTreeTypeButton = new Button("Modify Tree Type");
 		modifyTreeTypeButton.setMaxWidth(Double.MAX_VALUE);
 		modifyTreeTypeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	transType = "Modify";
	  		     	processAction(e);
	       	     }
 			});
 	

 		VBox treeTypeContainer = new VBox(10);
 		treeTypeContainer.setAlignment(Pos.TOP_CENTER);
 		treeTypeContainer.getChildren().add(treeTypeButton);
 		treeTypeContainer.getChildren().add(addTreeTypeButton);
 		treeTypeContainer.getChildren().add(modifyTreeTypeButton);
 		//treeTypeContainer.getChildren().add(removeTreeTypeButton);
		grid.add(treeTypeContainer, 2, 3);

		salesButton = new RadioButton("Sales Options");
		salesButton.setToggleGroup(group);
 		salesButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	transCategory = "Sales";
       		     	processOptionAction(e);
            	 }
        	});

 		openShiftButton = new Button("Open Session");
 		openShiftButton.setMaxWidth(Double.MAX_VALUE);
 		openShiftButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	transType = "Open";
	  		     	processAction(e);
	       	     }
 			});

 		closeShiftButton = new Button("Close Session");
 		closeShiftButton.setMaxWidth(Double.MAX_VALUE);
 		closeShiftButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	transType = "Close";
	  		     	processAction(e);
	       	     }
 			});

 		recordSaleButton = new Button("Record Sale");
 		recordSaleButton.setMaxWidth(Double.MAX_VALUE);
 		recordSaleButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);
	       	     }
 			});

 		VBox salesContainer = new VBox(10);
 		salesContainer.setAlignment(Pos.TOP_CENTER);
 		salesContainer.getChildren().add(salesButton);
 		salesContainer.getChildren().add(openShiftButton);
 		salesContainer.getChildren().add(closeShiftButton);
 		salesContainer.getChildren().add(recordSaleButton);
		grid.add(salesContainer, 3, 3);



		englishButton = new Button();
		Image engIcon = new Image(("USFlag.jpg"));
        englishButton.setGraphic(new ImageView(engIcon));
        englishButton.setPadding(Insets.EMPTY);
        englishButton.setStyle("-fx-background-color: transparent");
        englishButton.setOpacity(1.0);
		englishButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	locale = new Locale("en", "US");
	  		    	englishButton.setOpacity(1.0);
	  		    	frenchButton.setOpacity(0.3);
	  		    	buttons = ResourceBundle.getBundle("ButtonsBundle", locale);
                                titles = ResourceBundle.getBundle("TitlesBundle", locale);
                                prefs = Preferences.userNodeForPackage(TreeLotCoordinatorView.class);
                                prefs.put("langage", "en");
	  		    	refreshFormContents();
	       	     }
 			});

		frenchButton = new Button();
		Image frIcon = new Image(("FrenchFlag.jpg"));
		frenchButton.setGraphic(new ImageView(frIcon));
		frenchButton.setPadding(Insets.EMPTY);
		frenchButton.setStyle("-fx-background-color: transparent");
		frenchButton.setOpacity(0.3);
		frenchButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		    	locale = new Locale("fr", "CA");
	  		    	frenchButton.setOpacity(1.0);
	  		    	englishButton.setOpacity(0.3);
	  		    	buttons = ResourceBundle.getBundle("ButtonsBundle", locale);
                                titles = ResourceBundle.getBundle("TitlesBundle", locale);
                                prefs = Preferences.userNodeForPackage(TreeLotCoordinatorView.class);
                                prefs.put("langage", "fr");
	  		    	refreshFormContents();
	       	     }
 			});

		HBox langContainer = new HBox(-30);
		langContainer.setAlignment(Pos.CENTER_RIGHT);
		langContainer.getChildren().add(englishButton);
		langContainer.getChildren().add(frenchButton);
		//grid.add(langContainer, 0, 4);
		grid.add(langContainer, 3, 0);
		grid.setPadding(new Insets(10, 10, 10, 10));

		return grid;
	}

	private void refreshFormContents(){
		addScoutButton.setText(buttons.getString("mainButton1"));
		modifyScoutButton.setText(buttons.getString("mainButton2"));
		removeScoutButton.setText(buttons.getString("mainButton3"));
		addTreeButton.setText(buttons.getString("mainButton4"));
		modifyTreeButton.setText(buttons.getString("mainButton5"));
		removeTreeButton.setText(buttons.getString("mainButton6"));
		addTreeTypeButton.setText(buttons.getString("mainButton7"));
		modifyTreeTypeButton.setText(buttons.getString("mainButton8"));

                infoText.setText(titles.getString("mainTitleFirstPage"));

                scoutButton.setText(buttons.getString("optionOne"));
                treeButton.setText(buttons.getString("optionTwo"));
                treeTypeButton.setText(buttons.getString("optionThree"));
                salesButton.setText(buttons.getString("optionFour"));

                openShiftButton.setText(buttons.getString("openShift"));
                closeShiftButton.setText(buttons.getString("closeShift"));
                recordSaleButton.setText(buttons.getString("recordSale"));
	}



	// This method processes events generated from our GUI components.
	// Make the ActionListeners delegate to this method
	//-------------------------------------------------------------
	public void processAction(Event evt)
	{
		myTLC.doTransaction(transCategory, transType);
	}

	public void processOptionAction(Event evt)
	{
		if(scoutButton.isSelected()){
			setVisibility("tree", false);
			setVisibility("treeType", false);
			setVisibility("sales", false);
			setVisibility("scout", true);
		}
		else if(treeButton.isSelected()){
			setVisibility("scout", false);
			setVisibility("treeType", false);
			setVisibility("sales", false);
			setVisibility("tree", true);
		}
		else if(treeTypeButton.isSelected()){
			setVisibility("scout", false);
			setVisibility("tree", false);
			setVisibility("sales", false);
			setVisibility("treeType", true);
		}
		else if(salesButton.isSelected()){
			setVisibility("scout", false);
			setVisibility("tree", false);
			setVisibility("treeType", false);
			setVisibility("sales", true);
		}
	}

	public void setVisibility(String buttonSet, boolean vis){
		if(buttonSet.equals("scout")){
			addScoutButton.setVisible(vis);
			modifyScoutButton.setVisible(vis);
			removeScoutButton.setVisible(vis);
		}
		else if(buttonSet.equals("tree")){
			addTreeButton.setVisible(vis);
			modifyTreeButton.setVisible(vis);
			removeTreeButton.setVisible(vis);
		}
		else if(buttonSet.equals("treeType")){
			addTreeTypeButton.setVisible(vis);
			modifyTreeTypeButton.setVisible(vis);
			//removeTreeTypeButton.setVisible(vis);
		}
		else if(buttonSet.equals("sales")){
			openShiftButton.setVisible(vis);
			closeShiftButton.setVisible(vis);
			recordSaleButton.setVisible(vis);
		}
	}



	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// STEP 6: Be sure to finish the end of the 'perturbation'
		// by indicating how the view state gets updated.
		if (key.equals("LoginError") == true)
		{
			// display the passed text
			//displayErrorMessage((String)value);
		}

	}

}
