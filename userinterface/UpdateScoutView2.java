/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface;

import impresario.IModel;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
import model.Scout;
import model.ScoutVector;
import model.ModifyScout;


/**
 *
 * @author florianjousselin
 */
public class UpdateScoutView2 extends View {

  private TextField firstNameField;
  private TextField lastNameField;
  private TextField middleInitialField;
  private TextField dobField;
  private TextField phoneNumField;
  private TextField emailField;
  protected Button cancel;
  protected Button submit;

  private Locale locale = new Locale("en", "CA");
  private ResourceBundle buttons;
  private ResourceBundle titles;
  private ResourceBundle labels;
  private ResourceBundle alerts;
  private String cancelTitle;
  private String submitTitle;
  private String firstNameTitle;
  private String lastNameTitle;
  private String middleTitle;
  private String dobTitle;
  private String phoneTitle;
  private String emailTitle;
  private String statusTitle;//change
  private String mI;

  private String title;
  private String alertTitle;
  private String alertSubTitle;
  private String alertBody;
  private TableColumn barcodeColumn;

  private TableColumn firstNameTable;
  private TableColumn lastNameTable;
  private TableColumn middleTable;
  private TableColumn dobTable;
  private TableColumn phoneTable;
  private TableColumn emailTable;
  private TableColumn statusTable;
  private TableColumn scoutIdTable;
  private ComboBox statusField;
  private String selectStatus;


  private TableView<ScoutVector> tableOfScouts;

    private String alertTitleSucceeded;
    private String alertSubTitleSucceeded;
    private String alertBodySucceeded;
    private Preferences prefs;


    public UpdateScoutView2(IModel model) {
        super(model, "UpdateScoutView");
        prefs = Preferences.userNodeForPackage(AddScoutView.class);
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

    private void createInput2(GridPane grid, Integer pos)
    {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.BASELINE_LEFT);
        hb.getChildren().add(new Label(firstNameTitle));
        firstNameField = new TextField();

        String firstText = prefs.get("firstName", null);
        firstNameField.setText(firstText);

        hb.getChildren().add(firstNameField);
        grid.add(hb, 1, pos);
    }
    private void createInput3(GridPane grid, Integer pos)
    {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.BASELINE_LEFT);
        hb.getChildren().add(new Label(middleTitle));
        middleInitialField = new TextField();

        String middleText = prefs.get("middleInitial", null);
        middleInitialField.setText(middleText);

        hb.getChildren().add(middleInitialField);
        grid.add(hb, 1, pos);
    }
    private void createInput4(GridPane grid, Integer pos)
    {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.BASELINE_LEFT);
        hb.getChildren().add(new Label(lastNameTitle));
        lastNameField = new TextField();

        String lastNameText = prefs.get("lastName", null);
        lastNameField.setText(lastNameText);

        hb.getChildren().add(lastNameField);
        grid.add(hb, 1, pos);
    }
    private void createInput5(GridPane grid, Integer pos)
    {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.BASELINE_LEFT);
        hb.getChildren().add(new Label(dobTitle));
        dobField = new TextField();

        String dobText = prefs.get("dateOfBirth", null);
        dobField.setText(dobText);

        hb.getChildren().add(dobField);
        grid.add(hb, 1, pos);
    }
    private void createInput6(GridPane grid, Integer pos)
    {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.BASELINE_LEFT);
        hb.getChildren().add(new Label(phoneTitle));
        phoneNumField = new TextField();

        String phoneText = prefs.get("phoneNumber", null);
        dobField.setText(phoneText);

        hb.getChildren().add(phoneNumField);
        grid.add(hb, 1, pos);
    }
    private void createInput7(GridPane grid, Integer pos)
    {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.BASELINE_LEFT);
        hb.getChildren().add(new Label(emailTitle));
        emailField = new TextField();

        String emailText = prefs.get("email", null);
        dobField.setText(emailText);

        hb.getChildren().add(emailField);
        grid.add(hb, 1, pos);
    }
    private void createInput8(GridPane grid, Integer pos)
    {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.BASELINE_LEFT);
        hb.getChildren().add(new Label(statusTitle));
        statusField = new ComboBox();

        statusField.getItems().addAll(
          "Active",
          "Inactive"//Change
        );
        statusField.setPromptText(selectStatus);
        hb.getChildren().add(statusField);

        grid.add(hb,1,pos);

    }
    private GridPane createFormContent()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

  createInput2(grid,0);
  createInput3(grid,1);
  createInput4(grid,2);
  createInput5(grid,3);
  createInput6(grid,4);
  createInput7(grid,5);
  createInput8(grid,6);
//  lastNameField = createInput(grid, lastNameField, lastNameTitle, 2);
  //dobField = createInput(grid, dobField, dobTitle, 3);
//  phoneNumField = createInput(grid, phoneNumField, phoneTitle, 4);
  //emailField = createInput(grid, emailField, emailTitle, 5);
//  statusField = createInput(grid, statusField, statusTitle, 6);


	createButton(grid, submit, submitTitle, 1, 7, 1);
	createButton(grid, cancel, cancelTitle, 0, 7, 2);



	return grid;

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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitleSucceeded);
                alert.setHeaderText(alertSubTitleSucceeded);
                alert.setContentText(alertBodySucceeded);
                alert.showAndWait();
                Properties props = new Properties();
                props.setProperty("FirstName", firstNameField.getText());
                props.setProperty("MiddleInitial", middleInitialField.getText());
                props.setProperty("LastName", lastNameField.getText());
                props.setProperty("DateOfBirth", dobField.getText());
                props.setProperty("PhoneNumber", phoneNumField.getText());
                props.setProperty("Email", emailField.getText());
                props.setProperty("Status", statusField.getValue().toString());
                try
                {
                    myModel.stateChangeRequest("ModifyScout3", props);
                }
                catch (Exception ex)
                {
                    System.out.print("An Error occured: " + ex);
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
        submitTitle = buttons.getString("submitModifyTree");
        cancelTitle = buttons.getString("cancelTree");
        firstNameTitle = labels.getString("firstName");
        lastNameTitle = labels.getString("lastName");
        middleTitle = labels.getString("middle");
        dobTitle = labels.getString("dateOfBirth");
        phoneTitle = labels.getString("phone");
        emailTitle = labels.getString("email");
        statusTitle = labels.getString("status");
        title = titles.getString("mainTitleModifyScout");
        alertTitle = alerts.getString("AddTreeTitle");
        alertSubTitle = alerts.getString("AddTreeSubTitle");
        alertBody = alerts.getString("AddTreeBody");
        alertTitleSucceeded = alerts.getString("AddTreeTitleSucceeded");
        alertSubTitleSucceeded = alerts.getString("AddTreeSubTitleSucceeded");
        alertBodySucceeded = alerts.getString("UpdateTreeBodySucceeded");
        selectStatus = buttons.getString("SelectStatus");
    }


    @Override
    public void updateState(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
