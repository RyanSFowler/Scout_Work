
//specify the package
package userinterface;

//system imports
import java.text.NumberFormat;
import java.util.Properties;

import model.Scout;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import userinterface.View;
//project imports
import impresario.IModel;
import model.TreeLotCoordinator;


public class AddScoutView extends View
{

	DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
  Date dateobj = new Date();
	// GUI stuff
	private TextField firstNameField;
	private TextField lastNameField;
	private TextField middleInitialField;
	private TextField dobField;
	private TextField phoneNumField;
	private TextField emailField;
	private Button englishButton, frenchButton;

	private Button submitButton;
	private Button doneButton;


	private Scout myScout;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public  AddScoutView(IModel addScoutView)
	{

		super (addScoutView, "AddScoutView");

		//myScout = scout;

		// create a container for showing the contents
		VBox container = new VBox(10);

		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());

		// Error message area
		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);

	}

	// Create the label (Text) for the title of the screen
	//-------------------------------------------------------------
	private Node createTitle()
	{

		Text titleText = new Text("       ADD SCOUT          ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);


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

		// data entry fields
   	//---------------------------------------------
   	Label firstNameLabel = new Label("First Name");//firstNameWord
   	grid.add(firstNameLabel, 0, 0);
   	firstNameField = new TextField();
  	firstNameField.setOnAction(new EventHandler<ActionEvent>() {

      		     @Override
      		     public void handle(ActionEvent e) {
								 processAction(e);
           	     }
       	});
  	grid.add(firstNameField, 1, 0);

   	//---------------------------------------------
		Label lastNameLabel = new Label("Last Name");//lastNameWord
   	grid.add(lastNameLabel, 0, 1);

   	lastNameField = new TextField();

  	lastNameField.setOnAction(new EventHandler<ActionEvent>() {

      		     @Override
      		     public void handle(ActionEvent e) {
								 processAction(e);
           	     }
       	});
  	grid.add(lastNameField, 1, 1);

   	//---------------------------------------------

		Label middleInitialLabel = new Label("Middle Initial");//lastNameWord
		grid.add(middleInitialLabel, 0, 2);

		middleInitialField = new TextField();

		middleInitialField.setOnAction(new EventHandler<ActionEvent>() {

							 @Override
							 public void handle(ActionEvent e) {
								 processAction(e);
								 }
				});
		grid.add(middleInitialField, 1, 2);

   	//---------------------------------------------
		Label dobLabel = new Label("Date of Birth");//lastNameWord
		grid.add(dobLabel, 0, 3);

		dobField = new TextField();

		dobField.setOnAction(new EventHandler<ActionEvent>() {

							 @Override
							 public void handle(ActionEvent e) {
								 	processAction(e);
								 }
				});
		grid.add(dobField, 1, 3);

		//---------------------------------------------
		Label phoneNumLabel = new Label("Phone Number");//lastNameWord
		grid.add(phoneNumLabel, 0, 4);

		phoneNumField = new TextField();

		phoneNumField.setOnAction(new EventHandler<ActionEvent>() {

							 @Override
							 public void handle(ActionEvent e) {
								 processAction(e);
								 }
				});
		grid.add(phoneNumField, 1, 4);

		//---------------------------------------------
		Label emailLabel = new Label("Email");//lastNameWord
		grid.add(emailLabel, 0, 5);

		emailField = new TextField();

		emailField.setOnAction(new EventHandler<ActionEvent>() {

							 @Override
							 public void handle(ActionEvent e) {
								 processAction(e);
								 }
				});
		grid.add(emailField, 1, 5);

  	//---------------------------------------------------

  	submitButton = new Button("SUBMIT");
  	submitButton.setOnAction(new EventHandler<ActionEvent>() {

      		     @Override
      		     public void handle(ActionEvent e) {
      		     	processAction(e);
           	     }
       	});
  	grid.add(submitButton, 0, 6);

  	doneButton = new Button("DONE");
  	doneButton.setOnAction(new EventHandler<ActionEvent>() {

      		     @Override
      		     public void handle(ActionEvent e) {
      		    	 myScout.done();
      		     	//processAction(e);

           	     }
       	});
  	grid.add(doneButton, 1, 6);

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

	// Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage)
	{

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------

	//-------------------------------------------------------------
	public void processAction(Event evt)
	{
		clearErrorMessage();
		String firstNameEntered = firstNameField.getText();
		String lastNameEntered = lastNameField.getText();
		String middleInitialEntered = middleInitialField.getText();
		String dobEntered = dobField.getText();
		String phoneNumEntered = phoneNumField.getText();
		String emailEntered = emailField.getText();

		if((firstNameEntered == null) || (lastNameEntered == null)
				|| (middleInitialEntered == null) || (dobEntered == null)
				|| (phoneNumEntered == null) || (emailEntered == null))
		{
			displayErrorMessage("Error in input fields");
		}
		else
		{
			Properties scoutInsert = new Properties();
			scoutInsert.setProperty("FirstName", firstNameEntered);
			scoutInsert.setProperty("MiddleInitial", middleInitialEntered);
			scoutInsert.setProperty("LastName", lastNameEntered);
			scoutInsert.setProperty("DateOfBirth", dobEntered);
			scoutInsert.setProperty("PhoneNumber", phoneNumEntered);
			scoutInsert.setProperty("Email", emailEntered);
			scoutInsert.setProperty("Status", "Active");
			scoutInsert.setProperty("DateStatusUpdated", (String) df.format(dateobj));


			myScout.setData(scoutInsert);
			myScout.update();
		}
	}



	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Display message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
}
