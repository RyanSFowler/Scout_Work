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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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


public class UpdateTreeTypeView extends View {

    private TextField barcodePrefix;
    private TextField cost;
    private TextField typeDescription;
    private String costTitle;
    private String typeDescriptionTitle;
    protected Button cancel;
    protected Button submit;

    private Locale locale = new Locale("en", "CA");
    private ResourceBundle buttons;
    private ResourceBundle titles;
    private ResourceBundle labels;
    private ResourceBundle alerts;
    private String cancelTitle;
    private String submitTitle;
    private String barcodePrefixTitle;
    private String title;
    private String alertTitle;
    private String alertSubTitle;
    private String alertBody;

    private String alertTitleSucceeded;
    private String alertSubTitleSucceeded;
    private String alertBodySucceeded;

    public UpdateTreeTypeView(IModel model) {
        super(model, "UpdateTreeTypeView");
        Preferences prefs = Preferences.userNodeForPackage(AddNewTreeTypeView.class);
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
        //container.getChildren().add(createStatusLog("                                            "));
	getChildren().add(container);
        populateFields();
        myModel.subscribe("UpdateTreeTypeError", this);
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
        barcodePrefix = createInput(grid, barcodePrefix, barcodePrefixTitle, 0);
        cost = createInput(grid, cost, costTitle, 1);
        typeDescription = createInput(grid, typeDescription, typeDescriptionTitle, 2);
        createButton(grid, submit, submitTitle, 1, 4, 1);
        createButton(grid, cancel, cancelTitle, 0, 4, 2);
        return grid;
    }

    private TextField createInput(GridPane grid, TextField textfield, String label, Integer pos)
    {
        Label Author = new Label(label);
        GridPane.setHalignment(Author, HPos.RIGHT);
        grid.add(Author, 0, pos);
        textfield = new TextField();
        grid.add(textfield, 1, pos);
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
        if (clickedBtn.getId().equals("1") == true)
        {
            if (barcodePrefix.getText().isEmpty() == true)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertSubTitle);
                alert.setContentText(alertBody);
                alert.showAndWait();
            }
            else if (cost.getText().isEmpty() == true)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertSubTitle);
                alert.setContentText(alertBody);
                alert.showAndWait();
            }
            else if (typeDescription.getText().isEmpty() == true)
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
                props.setProperty("BarcodePrefix", barcodePrefix.getText());
                props.setProperty("Cost", cost.getText());
                props.setProperty("TypeDescription", typeDescription.getText());

                try
                {
                    myModel.stateChangeRequest("UpdateTreeType", props);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(alertTitleSucceeded);
                    alert.setHeaderText(alertSubTitleSucceeded);
                    alert.setContentText(alertBodySucceeded);
                    alert.showAndWait();
                    populateFields();
                }
                catch (Exception ex)
                {
                    System.out.print("Error UpdateTreeType");
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
        submitTitle = buttons.getString("submitTreeType");
        cancelTitle = buttons.getString("cancelTreeType");
        barcodePrefixTitle = labels.getString("barcodePrefix");
        costTitle = labels.getString("cost");
        typeDescriptionTitle = labels.getString("typeDescription");
        title = titles.getString("mainTitleModifyTreeType");
        alertTitle = alerts.getString("UpdateTreeTypeTitle");
        alertSubTitle = alerts.getString("UpdateTreeTypeSubTitle");
        alertBody = alerts.getString("UpdateTreeTypeBody");
        alertTitleSucceeded = alerts.getString("UpdateTreeTypeTitleSucceeded");
        alertSubTitleSucceeded = alerts.getString("UpdateTreeTypeSubTitleSucceeded");
        alertBodySucceeded = alerts.getString("UpdateTreeTypeBodySucceeded");
    }

    protected void populateFields()
    {
        barcodePrefix.setText("");
        cost.setText("");
        typeDescription.setText("");
    }

    @Override
    public void updateState(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
