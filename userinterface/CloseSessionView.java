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


public class CloseSessionView extends View {

    private TextArea notes;
    private TextField cashCount;
    private TextField checkCount;
    private TextField startCash;
    protected Button cancel;
    protected Button submit;

    private Locale locale = new Locale("en", "CA");
    private ResourceBundle buttons;
    private ResourceBundle titles;
    private ResourceBundle labels;
    private ResourceBundle alerts;
    private String notesTitle;
    private String cashCountTitle;
    private String checkCountTitle;
    private String cashTitle;
    private String checkTitle;
    private String submitTitle;
    private String cancelTitle;
    private int checkAmount = 0;
    private int cashAmount= 0;
    private String title;
    private String alertTitle;
    private String alertSubTitle;
    private String alertBody;

    private String alertTitleSucceeded;
    private String alertSubTitleSucceeded;
    private String alertBodySucceeded;

    private String description;
    private String calculated;
    private String counted;
    private String checkcalculated;
    private String checkcounted;

    public CloseSessionView(IModel model) {
        super(model, "CloseSessionView");
        Preferences prefs = Preferences.userNodeForPackage(CloseSessionView.class);
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


        Label s=  createLabel(grid,cashTitle,0,0);
        Label s2=  createLabel(grid,(""+cashAmount),0,1);
        cashCount = createInput(grid, cashCount, cashCountTitle, 1);
        Label c=  createLabel(grid,checkTitle,2,0);
        Label c2=  createLabel(grid,(""+checkAmount),2,1);
        checkCount = createInput(grid, checkCount, checkCountTitle, 3);

//        Label s=  createLabel(grid,calculated,0,0);
//        Label s2=  createLabel(grid,cashAmount,0,1);
//        cashCount = createInput(grid, cashCount, counted, 1);
//        Label c=  createLabel(grid,checkcalculated,2,0);
//        Label c2=  createLabel(grid,checkAmount,2,1);
//        checkCount = createInput(grid, checkCount, checkcounted, 3);

        notes = createInputTextArea(grid, notes, notesTitle, 4);
        createButton(grid, submit, submitTitle, 1, 5, 1);
        createButton(grid, cancel, cancelTitle, 0, 5, 2);

        return grid;
    }
    private Label createLabel(GridPane grid, String label, Integer pos, Integer pos2)
    {
        Label fieldLabel = new Label(label);
        GridPane.setHalignment(fieldLabel, HPos.RIGHT);
        grid.add(fieldLabel, pos2, pos);
        return fieldLabel;
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
            /*if (date.getText().isEmpty())
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
            }*/
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
        notesTitle = labels.getString("notes");
        title = titles.getString("mainTitleCloseSession");
        cashCountTitle = labels.getString("cashCount");
        checkCountTitle= labels.getString("checkCount");
        cashTitle= labels.getString("cash");
        checkTitle= labels.getString("check");
        //NEED TO FIX ALERTS
        alertTitle = alerts.getString("AddTreeTitle");
        alertSubTitle = alerts.getString("AddTreeSubTitle");
        alertBody = alerts.getString("AddTreeBody");
        alertTitleSucceeded = alerts.getString("AddTreeTitleSucceeded");
        alertSubTitleSucceeded = alerts.getString("AddTreeSubTitleSucceeded");
        alertBodySucceeded = alerts.getString("AddTreeBodySucceeded");
        calculated = labels.getString("Calculated");
        counted = labels.getString("Counted");
        checkcalculated = labels.getString("CheckCalculated");
        checkcounted = labels.getString("CheckCounted");
    }


    protected void populateFields()
    {
        cashCount.setText("");
        checkCount.setText("");
    }

    @Override
    public void updateState(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
