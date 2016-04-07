/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface;

import impresario.IModel;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Scout;


public class EnterScoutView extends View {

        protected Button cancelButton;
        protected Button submitButton;
        private Button doneButton;
        protected MessageView statusLog;
        private TextField firstNameField;
      	private TextField lastNameField;
        private Scout myScout;
        
        private Locale locale = new Locale("en", "CA");
        private ResourceBundle buttons;
        private ResourceBundle titles;
        private ResourceBundle labels;
        private ResourceBundle alerts;
        
        private String cancel;
        private String submit;
        private String firstName;
        private String lastName;
        private String title;
        private String alertTitle;
        private String alertSubTitle;
        private String alertBody;

        public EnterScoutView(IModel scout)
    {
        super(scout, "EnterScoutView");
        
        Preferences prefs = Preferences.userNodeForPackage(EnterScoutView.class);
        String langage = prefs.get("langage", null);
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
        
        myScout=(Scout)scout;
        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
	container.getChildren().add(createTitle());
	container.getChildren().add(createFormContent());

	// Error message area
	container.getChildren().add(createStatusLog("                                            "));
	getChildren().add(container);
        populateFields();
        myModel.subscribe("EnterScoutViewError", this);
    }

        protected void populateFields()
	{
            firstNameField.setText("");
            lastNameField.setText("");
	}

        private MessageView createStatusLog(String initialMessage)
	{
            statusLog = new MessageView(initialMessage);
            return statusLog;
	}

        // Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
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
	private GridPane createFormContent()
        {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            firstNameField = createInput(grid, firstNameField, firstName, 0);
            lastNameField = createInput(grid, lastNameField, lastName, 1);
            createButton(grid, submitButton, submit, 4);
            createButton(grid, doneButton, cancel, 3);
            return grid;
	}

         private TextField createInput(GridPane grid, TextField textfield, String label, Integer pos)
	{
            Label Author = new Label(label);
            grid.add(Author, 0, pos);
            textfield = new TextField();
            grid.add(textfield, 1, pos);
            return textfield;
	}

          private void createButton(GridPane grid, Button button, String nameButton, Integer pos)
	{
            button = new Button(nameButton);
            button.setId(Integer.toString(pos));
            if(nameButton==cancel)
            {
              button.setOnAction(new EventHandler<ActionEvent>() {
              @Override
                  public void handle(ActionEvent e) {
                      myScout.done();
                  }
              });
            }
            else
            {
                button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                  public void handle(ActionEvent e) {
                      processAction(e);
                    }
                });
            }
            HBox btnContainer = new HBox(10);
            btnContainer.setAlignment(Pos.BOTTOM_RIGHT);
            btnContainer.getChildren().add(button);
            if (pos == 3) {
                grid.add(btnContainer, 0, 4);
            }
            else {
                grid.add(btnContainer, 1, 4);
            }
	}
          public void processAction(Event evt)
	{
            Object source = evt.getSource();
            Button clickedBtn = (Button) source;
            if (clickedBtn.getId().equals("5") == true)
            {
            	myModel.stateChangeRequest("Done", null);
            }
            //clearErrorMessage();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            if ((firstName.length() == 0) || (lastName.length() == 0))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertSubTitle);
                alert.setContentText(alertBody);
                alert.showAndWait();
            }
            else
            {
                // SEARCH AND MODIFY SCOUT
            }
	}
          
          private void refreshFormContents()
        {
            cancel = buttons.getString("cancelModifyScout");
            submit = buttons.getString("submitModifyScout");
            firstName = labels.getString("firstName");
            lastName = labels.getString("lastName");
            title = titles.getString("mainTitleModifyScout");
            alertTitle = alerts.getString("ModifyScoutTitle");
            alertSubTitle = alerts.getString("ModifyScoutSubTitle");
            alertBody = alerts.getString("ModifyScoutBody");
	}

          public void displayMessage(String message)
	{
            statusLog.displayMessage(message);
	}

        public void displayErrorMessage(String message)
	{
            statusLog.displayErrorMessage(message);
	}

        public void updateState(String key, Object value)
	{
            if (key.equals("EnterScoutViewError") == true)
            {
		displayErrorMessage((String)value);
            }
	}

        public void clearErrorMessage()
	{
            statusLog.clearErrorMessage();
	}
}
