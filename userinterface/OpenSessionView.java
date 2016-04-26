/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface;

import impresario.IModel;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import model.Session;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class OpenSessionView extends View {

    private TextField date;
    private TextField startTime;
    private TextField endTime;
    private TextField startCash;
    private TextArea notes;
    private ComboBox scout1;
    private ComboBox scout2;
    private ComboBox scout3;
    private TextField scout1Companion;
    private TextField scout2Companion;
    private TextField scout3Companion;
    protected Button cancel;
    protected Button submit;
    
    private Locale locale = new Locale("en", "CA");
    private ResourceBundle buttons;
    private ResourceBundle titles;
    private ResourceBundle labels;
    private ResourceBundle alerts;
    private String cancelTitle;
    private String submitTitle;
    private String title;
    private String alertTitle;
    private String alertSubTitle;
    private String alertBody;
    
    private String alertTitleSucceeded;
    private String alertSubTitleSucceeded;
    private String alertBodySucceeded;
    
    private String description;
    
    
    public OpenSessionView(IModel model) {
        super(model, "OpenSessionView");
        Preferences prefs = Preferences.userNodeForPackage(OpenSessionView.class);
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
        displayWindow();
        
    }
    
    
    public void displayWindow()
    {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);	
        container.setPadding(new Insets(15, 5, 5, 5));
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());
        getChildren().add(container);
        populateFields();
        myModel.subscribe("LoginError", this);
    }
    
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
    
    private GridPane createFormContent()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        date = createInput(grid, date, "Date:", 0);
        startTime = createInput(grid, startTime, "Start Time:", 1);
        endTime = createInput(grid, startTime, "End Time:", 2);
        startCash = createInput(grid, startCash, "Starting Cash: ($)", 3);
        notes = new TextArea();
        notes = createInputTextArea(grid, notes, description, 4);
        scout1 = new ComboBox((ObservableList)(((Session) myModel).findAllScouts()));
        scout1.setPromptText("Select a Scout");
        grid.add(scout1, 0, 5);
        scout1Companion = createInput(grid, scout1Companion, "This Scout's Companion", 2, 5);
        scout2 = new ComboBox((ObservableList)(((Session) myModel).findAllScouts()));
        scout2.setPromptText("Select a Scout");
        grid.add(scout2, 0, 6);
        scout2Companion = createInput(grid, scout2Companion, "This Scout's Companion", 2, 6);
        scout3 = new ComboBox((ObservableList)(((Session) myModel).findAllScouts()));
        scout3.setPromptText("Select a Scout");
        grid.add(scout3, 0, 7);
        scout3Companion = createInput(grid, scout3Companion, "This Scout's Companion", 2, 7);
        createButton(grid, submit, submitTitle, 1, 9, 1);
        createButton(grid, cancel, cancelTitle, 0, 9, 2);
        return grid;
    }
    
    private TextField createInput(GridPane grid, TextField textfield, String label, Integer pos)
    {
        Label fieldLabel = new Label(label);
        GridPane.setHalignment(fieldLabel, HPos.RIGHT);
        grid.add(fieldLabel, 0, pos);
        textfield = new TextField();
        grid.add(textfield, 1, pos);
        return textfield;
    }
    
    private TextField createInput(GridPane grid, TextField textfield, String label, Integer cPos, Integer rPos)
    {
        Label fieldLabel = new Label(label);
        GridPane.setHalignment(fieldLabel, HPos.RIGHT);
        grid.add(fieldLabel, cPos-1, rPos);
        textfield = new TextField();
        grid.add(textfield, cPos, rPos);
        return textfield;
    }
    
    private TextArea createInputTextArea(GridPane grid, TextArea textarea, String label, Integer pos)
    {
        Label Author = new Label(label);
        GridPane.setHalignment(Author, HPos.RIGHT);
        grid.add(Author, 0, pos);
        textarea = new TextArea();
        textarea.setDisable(false);
        textarea.setWrapText(true);
        textarea.setPrefSize(300, 100);
        grid.add(textarea, 1, pos);
        return textarea;
    }
    
    private void createButton(GridPane grid, Button button, String nameButton, Integer pos1, Integer pos2, Integer id)
    {
        button = new Button(nameButton);
        button.setId(Integer.toString(id));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                public void handle(ActionEvent e) {
                    processAction(e);
                }
            });
        HBox btnContainer = new HBox(10);
        btnContainer.setAlignment(Pos.BOTTOM_RIGHT);
        btnContainer.getChildren().add(button);
        grid.add(btnContainer, pos1, pos2);
    }
    
    public void processAction(Event evt)
    {
        Object source = evt.getSource();
        Button clickedBtn = (Button) source;
        if (clickedBtn.getId().equals("1"))
        {
            if (date.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertSubTitle);
                alert.setContentText(alertBody);
                alert.showAndWait();
            }
            else if (startTime.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertSubTitle);
                alert.setContentText(alertBody);
                alert.showAndWait();
            }
            else if (endTime.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertSubTitle);
                alert.setContentText(alertBody);
                alert.showAndWait();
            }
            else if (startCash.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertSubTitle);
                alert.setContentText(alertBody);
                alert.showAndWait();
            }
            else if (notes.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertSubTitle);
                alert.setContentText(alertBody);
                alert.showAndWait();
            }
            else
            {
            	String scoutData1 = (String)scout1.getValue();
            	String scoutData2 = (String)scout2.getValue();
            	String scoutData3 = (String)scout3.getValue();
                Properties props = new Properties();
                props.setProperty("Date", date.getText());
                props.setProperty("StartTime", startTime.getText());
                props.setProperty("EndTime", endTime.getText());
                props.setProperty("StartCash", startCash.getText());
                props.setProperty("Notes", notes.getText());
                props.setProperty("Scout1", scoutData1.substring(0, scoutData1.indexOf(" ")));
                props.setProperty("Scout1Companion", scout1Companion.getText());
                props.setProperty("Scout2", scoutData2.substring(0, scoutData2.indexOf(" ")));
                props.setProperty("Scout2Companion", scout2Companion.getText());
                props.setProperty("Scout3", scoutData3.substring(0, scoutData3.indexOf(" ")));
                props.setProperty("Scout3Companion", scout3Companion.getText());
                System.out.println(props);
                try
                {
                    myModel.stateChangeRequest("OpenSession", props);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(alertTitleSucceeded);
                    alert.setHeaderText(alertSubTitleSucceeded);
                    alert.setContentText(alertBodySucceeded);
                    alert.showAndWait();
                    populateFields();
                }
                catch (Exception ex)
                {
                    System.out.print("Error Opening Session");
                }
            }
        }
        else if (clickedBtn.getId().equals("2") == true)
        {
            myModel.stateChangeRequest("Done", null);
        }
    }
    
    private void refreshFormContents()
    {
        submitTitle = buttons.getString("submitTree");
        cancelTitle = buttons.getString("cancelTree");
        description = labels.getString("notes");
        title = titles.getString("mainTitleOpenSession");
        alertTitle = alerts.getString("AddTreeTitle");
        alertSubTitle = alerts.getString("AddTreeSubTitle");
        alertBody = alerts.getString("AddTreeBody");
        alertTitleSucceeded = alerts.getString("AddTreeTitleSucceeded");
        alertSubTitleSucceeded = alerts.getString("AddTreeSubTitleSucceeded");
        alertBodySucceeded = alerts.getString("AddTreeBodySucceeded");
    }
    
    
    protected void populateFields()
    {
        date.setText("");
        startTime.setText("");
        endTime.setText("");
        startCash.setText("");
        notes.setText("");
    }
    
    @Override
    public void updateState(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
