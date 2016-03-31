
// specify the package
package userinterface;

// system imports
import java.io.File;
import java.nio.channels.SelectableChannel;
import java.text.NumberFormat;
import java.util.Properties;

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

//==============================================================
public class TreeLotCoordinatorView extends View
{

	// GUI stuff
	private TextField userid;
	private PasswordField password;
	private RadioButton scoutButton, treeButton, treeTypeButton, salesButton;
	final ToggleGroup group = new ToggleGroup();
	private Button addScoutButton, modifyScoutButton, removeScoutButton;
	private Button addTreeButton, modifyTreeButton, removeTreeButton;
	private Button addTreeTypeButton, modifyTreeTypeButton, removeTreeTypeButton;
	private Button openShiftButton, closeShiftButton, recordSaleButton;
	private Button englishButton, frenchButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public TreeLotCoordinatorView( IModel treeLotCoordinator)
	{
		super(treeLotCoordinator, "TreeLotCoordinatorView");

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

		//populateFields();

		// STEP 0: Be sure you tell your model what keys you are interested in
		myModel.subscribe("LoginError", this);
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
        	
     	
        Label infoText = new Label("Select a category then select an option.");
        infoText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        GridPane.setHalignment(infoText, HPos.CENTER);
        grid.add(infoText, 0, 0, 4, 1);
        
        Line line = new Line(0,0,400,0);
        GridPane.setHalignment(line, HPos.CENTER);
        grid.add(line, 0, 1, 4, 1);
        	
 		scoutButton = new RadioButton("Scout Options");
 		scoutButton.setToggleGroup(group);
 		scoutButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	processOptionAction(e);    
            	     }
        	});
 		
 		addScoutButton = new Button("Add Scout");
 		addScoutButton.setMaxWidth(Double.MAX_VALUE);
 		addScoutButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);    
	       	     }
 			});
 		
 		modifyScoutButton = new Button("Modify Scout");
 		modifyScoutButton.setMaxWidth(Double.MAX_VALUE);
 		modifyScoutButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);    
	       	     }
 			});
 		
 		removeScoutButton = new Button("Remove Scout");
 		removeScoutButton.setMaxWidth(Double.MAX_VALUE);
 		removeScoutButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);    
	       	     }
 			});
 		
 		VBox scoutContainer = new VBox(10);
 		scoutContainer.setAlignment(Pos.CENTER);
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
       		     	processOptionAction(e);    
            	     }
        	});
 		
 		addTreeButton = new Button("Add Tree");
 		addTreeButton.setMaxWidth(Double.MAX_VALUE);
 		addTreeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);    
	       	     }
 			});
 		
 		modifyTreeButton = new Button("Modify Tree");
 		modifyTreeButton.setMaxWidth(Double.MAX_VALUE);
 		modifyTreeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);    
	       	     }
 			});
 		
 		removeTreeButton = new Button("Remove Tree");
 		removeTreeButton.setMaxWidth(Double.MAX_VALUE);
 		removeTreeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);    
	       	     }
 			});
 		
 		VBox treeContainer = new VBox(10);
 		treeContainer.setAlignment(Pos.CENTER);
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
       		     	processOptionAction(e);    
            	     }
        	});
 		
 		addTreeTypeButton = new Button("Add Tree Type");
 		addTreeTypeButton.setMaxWidth(Double.MAX_VALUE);
 		addTreeTypeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);    
	       	     }
 			});
 		
 		modifyTreeTypeButton = new Button("Modify Tree Type");
 		modifyTreeTypeButton.setMaxWidth(Double.MAX_VALUE);
 		modifyTreeTypeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);    
	       	     }
 			});
 		
 		removeTreeTypeButton = new Button("Remove Tree Type");
 		removeTreeTypeButton.setMaxWidth(Double.MAX_VALUE);
 		removeTreeTypeButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);    
	       	     }
 			});
 		
 		VBox treeTypeContainer = new VBox(10);
 		treeTypeContainer.setAlignment(Pos.CENTER);
 		treeTypeContainer.getChildren().add(treeTypeButton);
 		treeTypeContainer.getChildren().add(addTreeTypeButton);
 		treeTypeContainer.getChildren().add(modifyTreeTypeButton);
 		treeTypeContainer.getChildren().add(removeTreeTypeButton);
		grid.add(treeTypeContainer, 2, 3);
		
		salesButton = new RadioButton("Sales Options");
		salesButton.setToggleGroup(group);
 		salesButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	processOptionAction(e);    
            	     }
        	});
 		
 		openShiftButton = new Button("Open Shift");
 		openShiftButton.setMaxWidth(Double.MAX_VALUE);
 		openShiftButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
	  		     	processAction(e);    
	       	     }
 			});
 		
 		closeShiftButton = new Button("Close Shift");
 		closeShiftButton.setMaxWidth(Double.MAX_VALUE);
 		closeShiftButton.setOnAction(new EventHandler<ActionEvent>() {

	  		     @Override
	  		     public void handle(ActionEvent e) {
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
 		salesContainer.setAlignment(Pos.CENTER);
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
	  		    	englishButton.setOpacity(1.0);
	  		    	frenchButton.setOpacity(0.3);
	  		    	//make language change
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
	  		    	frenchButton.setOpacity(1.0);
	  		    	englishButton.setOpacity(0.3);
	  		    	//make language change
	       	     }
 			});
		
		HBox langContainer = new HBox(0);
		langContainer.setAlignment(Pos.CENTER_LEFT);
		langContainer.getChildren().add(englishButton);
		langContainer.getChildren().add(frenchButton);
		grid.add(langContainer, 0, 4);
		
		
		return grid;
	}

	

	// This method processes events generated from our GUI components.
	// Make the ActionListeners delegate to this method
	//-------------------------------------------------------------
	public void processAction(Event evt)
	{
		
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
			removeTreeTypeButton.setVisible(vis);
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

