package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import java.util.Locale;
import java.util.prefs.Preferences;
//import userinterface.EnterModifyScoutView;

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import java.util.Properties;
import model.Scout;
import model.ScoutCollection;
import model.TreeLotCoordinator;
//==============================================================================
public class ScoutCollectionView extends View
{
	protected TableView<ScoutTableModel> tableOfScouts;
	protected Button cancelButton;
	protected Button selectScoutButton;
	private ScoutCollection mySc;
	protected MessageView statusLog;
	private String title;
	private String cancelTitle;
	private String submitTitle;
	private Locale locale = new Locale("en", "CA");
	private ResourceBundle buttons;
	private ResourceBundle titles;
	private ResourceBundle labels;
	private ResourceBundle alerts;
	private Scout scout;


	//--------------------------------------------------------------------------
	public ScoutCollectionView(ScoutCollection sc)
	{
		super(sc, "ScoutCollectionView");
		mySc = sc;
		Preferences prefs = Preferences.userNodeForPackage(ScoutCollectionView.class);
		String langage = prefs.get("langage", null);
		// create a container for showing the content

		if (langage.toString().equals("en") == true)
		{
				locale = new Locale("en", "US");
		}
		else if (langage.toString().equals("fr") == true)
		{
				locale = new Locale("fr", "CA");
		}
		buttons = ResourceBundle.getBundle("ButtonsBundle", locale);
		titles = ResourceBundle.getBundle("TitlesBundle", locale);
		labels = ResourceBundle.getBundle("LabelsBundle", locale);
		alerts = ResourceBundle.getBundle("AlertsBundle", locale);
		refreshFormContents();
		displayWindow();
                

	}
	private void refreshFormContents()
	{

			submitTitle = buttons.getString("submitModifyScout");
			cancelTitle = buttons.getString("cancelAddScout");
			title = titles.getString("mainTitleModifyScout");//change
	}
	public void displayWindow()
	{
			VBox container = new VBox(10);
			container.setAlignment(Pos.CENTER);
			container.setPadding(new Insets(15, 5, 5, 5));

			container.getChildren().add(createTitle());
			container.getChildren().add(createFormContent());

			//container.getChildren().add(createStatusLog("                                            "));
			getChildren().add(container);
			populateFields();
			myModel.subscribe("LoginError", this);
	}
	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getEntryTableModelValues();
	}
	private Button createButton(GridPane grid, Button button, String nameButton, Integer pos1, Integer pos2, Integer id)
	{
			button = new Button(nameButton);
			button.setId(Integer.toString(id));
			button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
							public void handle(ActionEvent e) {
									processAction(e);
							}
					});

			//grid.add(btnContainer, pos1, pos2);
			return button;
	}
	//-------------------------------------------------------------------------------
	public void processAction(Event evt)
	{
		Object source = evt.getSource();
		Button clickedBtn = (Button) source;
		if (clickedBtn.getId().equals("2") == true)
		{
				myModel.stateChangeRequest("Done", null);
		}
			/*String mI;
			Object source = evt.getSource();
			Button clickedBtn = (Button) source;
			if (clickedBtn.getId().equals("1") == true)
			{
					if(middleInitialField.getText().isEmpty() == true)
					{
						mI="";
					}
					else
					{
						mI = middleInitialField.getText();
					}
					if ((firstNameField.getText().isEmpty() == true) || (lastNameField.getText().isEmpty() == true)
							 || (dobField.getText().isEmpty() == true) || (phoneNumField.getText().isEmpty() == true) || (emailField.getText().isEmpty() == true))
					{
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle(alertTitle);
							alert.setHeaderText(alertSubTitle);
							alert.setContentText(alertBody);
							alert.showAndWait();
					}
					else
					{
							Properties props = new Properties();
							props.setProperty("FirstName", firstNameField.getText());
							props.setProperty("MiddleInitial", mI);
							props.setProperty("LastName", lastNameField.getText());
							props.setProperty("DateOfBirth", dobField.getText());
							props.setProperty("PhoneNumber", phoneNumField.getText());
							props.setProperty("Email", emailField.getText());
							props.setProperty("Status", "Active");
							try
							{
									myModel.stateChangeRequest("ModifyScout", props);
									Alert alert = new Alert(Alert.AlertType.INFORMATION);
									alert.setTitle(alertTitleSucceeded);
									alert.setHeaderText(alertSubTitleSucceeded);
									alert.setContentText(alertBodySucceeded);
									alert.showAndWait();
									populateFields();
							}
							catch (Exception ex)
							{
									System.out.print("Error New Scout Modify");
							}
					}*/
			}
	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{

		ObservableList<ScoutTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			ScoutCollection scoutCollection = (ScoutCollection)myModel.getState("ScoutList");

	 		Vector entryList = (Vector)scoutCollection.getState("Scouts");
			Enumeration entries = entryList.elements();

			while (entries.hasMoreElements() == true)
			{
				Scout nextScout = (Scout)entries.nextElement();
				//Vector<String> view = nextScout.getEntryListView();

				// add this list entry to the list
				//ScoutTableModel nextTableRowData = new ScoutTableModel(view);
				//tableData.add(nextTableRowData);

			}

			tableOfScouts.setItems(tableData);
		}
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
		}
	}

	// Create the title container
	//-------------------------------------------------------------
	public Node createTitle()
	{
			HBox container = new HBox();
			container.setAlignment(Pos.CENTER);
			Text titleText = new Text(title);
			titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
			titleText.setWrappingWidth(300);
			titleText.setTextAlignment(TextAlignment.CENTER);
			titleText.setFill(Color.DARKGREEN);
			container.getChildren().add(titleText);
			return container;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{

	VBox vbox = new VBox(10);
	GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

	tableOfScouts = new TableView<ScoutTableModel>();
	tableOfScouts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

	TableColumn scoutIDColumn = new TableColumn("scoutId") ;
	scoutIDColumn.setMinWidth(100);
	scoutIDColumn.setCellValueFactory(
            new PropertyValueFactory<ScoutTableModel, String>("ScoutId"));

	TableColumn firstNameColumn = new TableColumn("firstName") ;
	firstNameColumn.setMinWidth(100);
	firstNameColumn.setCellValueFactory(
	    new PropertyValueFactory<ScoutTableModel, String>("FirstName"));

	TableColumn middleInitialColumn = new TableColumn("middleInitial") ;
	middleInitialColumn.setMinWidth(100);
	middleInitialColumn.setCellValueFactory(
	    new PropertyValueFactory<ScoutTableModel, String>("MiddleInitial"));

	TableColumn lastNameColumn = new TableColumn("lastName") ;
	lastNameColumn.setMinWidth(100);
	lastNameColumn.setCellValueFactory(
	    new PropertyValueFactory<ScoutTableModel, String>("LastName"));

	TableColumn dateOfBirthColumn = new TableColumn("dateOfBirth") ;
	dateOfBirthColumn.setMinWidth(100);
	dateOfBirthColumn.setCellValueFactory(
	    new PropertyValueFactory<ScoutTableModel, String>("DateOfBirth"));

	TableColumn phoneNumberColumn = new TableColumn("phoneNumber") ;
	phoneNumberColumn.setMinWidth(100);
	phoneNumberColumn.setCellValueFactory(
            new PropertyValueFactory<ScoutTableModel, String>("PhoneNumber"));

        TableColumn emailColumn = new TableColumn("email") ;
	emailColumn.setMinWidth(100);
	emailColumn.setCellValueFactory(
            new PropertyValueFactory<ScoutTableModel, String>("Email"));

	TableColumn statusColumn = new TableColumn("status") ;
	statusColumn.setMinWidth(100);
	statusColumn.setCellValueFactory(
            new PropertyValueFactory<ScoutTableModel, String>("Status"));

	TableColumn dateStatusUpdatedColumn = new TableColumn("dateStatusUpdated") ;
	dateStatusUpdatedColumn.setMinWidth(100);
	dateStatusUpdatedColumn.setCellValueFactory(
            new PropertyValueFactory<ScoutTableModel, String>("DateStatusUpdated"));

	tableOfScouts.getColumns().addAll(scoutIDColumn,
            firstNameColumn, lastNameColumn, middleInitialColumn, dateOfBirthColumn, phoneNumberColumn, emailColumn, statusColumn, dateStatusUpdatedColumn);


	HBox btnContainer = new HBox(100);
	btnContainer.setAlignment(Pos.CENTER);
	cancelButton = createButton(grid, cancelButton, cancelTitle, 0, 4, 2);
	selectScoutButton = createButton(grid, selectScoutButton, submitTitle, 1, 4, 1);
	cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     		//mySc.modifyDone();

            	 }
        	});
		selectScoutButton.setOnAction(new EventHandler<ActionEvent>() {

			       		     @Override
			       		     public void handle(ActionEvent e) {
                                                 Properties props = new Properties();

                                                props.setProperty("FirstName", "Antoine");
                                                props.setProperty("LastName", "Berghen");
                                                try{
                                                myModel.stateChangeRequest("SearchScout", props);
                                                populateFields();
                                                }
                                                catch (Exception ex)
                                                 {
                                                       //displayErrorMessage("Error to searching Book in database!");
                                                    }
			       		     	//myBc.done();

			      	 }
			    });

		btnContainer.getChildren().add(cancelButton);
		btnContainer.getChildren().add(selectScoutButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(createScrollPane(grid, tableOfScouts,0));
		vbox.getChildren().add(btnContainer);
		/*
		createScrollPane(grid, tableOfScouts,0);
		createButton(grid, selectScoutButton, submitTitle, 1, 4, 1);
		createButton(grid, cancelButton, cancelTitle, 0, 4, 2);
		*/


		return vbox;
	}
	private ScrollPane createScrollPane(GridPane grid,TableView<ScoutTableModel> tableOfScouts, int pos )
	{
	/*	ObservableList<Scout> modifyList = tableOfScouts;
 		tableOfScouts.setItems(modifyList);
*/
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(400, 300); //original values 115 150
		System.out.println(tableOfScouts);
		scrollPane.setContent(tableOfScouts);
		return scrollPane;

	}



	//--------------------------------------------------------------------------
	public void updateState(String key, Object value)
	{
	}

	//--------------------------------------------------------------------------


	//--------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}


	/**
	 * Display info message
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
	/*
	//--------------------------------------------------------------------------
	public void mouseClicked(MouseEvent click)
	{
		if(click.getClickCount() >= 2)
		{
			processAccountSelected();
		}
	}
   */

}
