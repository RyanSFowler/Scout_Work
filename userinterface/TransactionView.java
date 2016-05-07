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
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author florianjousselin
 */
public class TransactionView extends View {

    private ComboBox transTypeField;
    private TextField barcodeField;
    private TextField transAmountField;
    private ComboBox paymentField;
    private TextField custNameField;
    private TextField custPhoneField;
    private TextField custEmailField;
    private TextField dateField;
    private TextField timeField;
    private DateFormat df;
    private DateFormat tf;
    protected Button cancel;
    protected Button submit;
    private Date dateobj = new Date();

    private Locale locale = new Locale("en", "CA");
    private ResourceBundle buttons;
    private ResourceBundle titles;
    private ResourceBundle labels;
    private ResourceBundle alerts;
    private String cancelTitle;
    private String submitTitle;

    private String transTypeTitle;
    private String barcodeTitle;
    private String transAmountTitle;
    private String paymentTitle;
    private String custNameTitle;
    private String custPhoneTitle;
    private String custEmailTitle;
    private String dateTitle;
    private String timeTitle;
    private String cashString;
    private String checkString;
    private String dateStamp;
    private String timeStamp;
    private String title;
    private String alertTitle;
    private String alertSubTitle;
    private String alertBody;
    private String transactionTypeString;

    private String alertTitleSucceeded;
    private String alertSubTitleSucceeded;
    private String alertBodySucceeded;

    private String description;
    private String selectPayment;
    private String selectType;


    public TransactionView(IModel model) {
        super(model, "TransactionView");
        Preferences prefs = Preferences.userNodeForPackage(TransactionView.class);
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
        timeStamp = new SimpleDateFormat(labels.getString("timeFormat")).format(new Date());
        dateStamp = new SimpleDateFormat(labels.getString("dateFormat")).format(new Date());
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


        transTypeField =new ComboBox();
        transTypeField.getItems().addAll(
          transactionTypeString
        );
        transTypeField.setPromptText(selectType);
        Label b = new Label(transTypeTitle);
        GridPane.setHalignment(b, HPos.RIGHT);
        grid.add(b,0,0);
        grid.add(transTypeField,1,0);
        barcodeField = createInput(grid, barcodeField, barcodeTitle, 1);
        transAmountField = createInput(grid, transAmountField, transAmountTitle, 2);
        paymentField =new ComboBox();
        paymentField.getItems().addAll(
          cashString,
          checkString
        );
        paymentField.setPromptText(selectPayment);
        Label a = new Label(paymentTitle);
        GridPane.setHalignment(a, HPos.RIGHT);
        grid.add(a,0,3);
        grid.add(paymentField,1,3);
        custNameField = createInput(grid, custNameField, custNameTitle, 4);
        custPhoneField = createInput(grid, custPhoneField, custPhoneTitle, 5);
        custEmailField = createInput(grid, custEmailField, custEmailTitle, 6);
        dateField = createInput(grid, dateField, dateTitle, 7);
        timeField = createInput(grid, timeField, timeTitle, 8);
        createButton(grid, submit, submitTitle, 1, 9, 1);
        createButton(grid, cancel, cancelTitle, 0, 9, 2);
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
        int sessionId = 0;
        Object source = evt.getSource();
        Button clickedBtn = (Button) source;
        if (clickedBtn.getId().equals("1") == true)
        {
          if(sessionId != -1){

            if ((barcodeField.getText().isEmpty() == true) || (transAmountField.getText().isEmpty() == true)
                  || (custNameField.getText().isEmpty() == true) || (custPhoneField.getText().isEmpty() == true)
                  || (custEmailField.getText().isEmpty() == true) || (dateField.getText().isEmpty() == true)
                  || (timeField.getText().isEmpty() == true)  )
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertSubTitle);
                alert.setContentText(alertBody);
                alert.showAndWait();
            }
            else
            {
                String sessionString = ""+ sessionId;
                System.out.println(sessionString+ "\n"+(String)transTypeField.getValue().toString()+"\n"+ barcodeField.getText()+ "\n"+ transAmountField.getText());
                System.out.println((String)paymentField.getValue().toString()+ "\n"+ custNameField.getText()+ "\n"+ custPhoneField.getText()+ "\n"+custEmailField.getText());
                System.out.println(dateField.getText() + "\n"+timeField.getText());
                Properties props = new Properties();
                props.setProperty("SessionId", sessionString);
                props.setProperty("TransactionType",(String)transTypeField.getValue().toString());
                props.setProperty("Barcode", barcodeField.getText());
                props.setProperty("TransactionAmount", transAmountField.getText());
                props.setProperty("PaymentMethod", (String)paymentField.getValue().toString());
                props.setProperty("CustomerName", custNameField.getText());
                props.setProperty("CustomerPhone", custPhoneField.getText());
                props.setProperty("CustomerEmail", custEmailField.getText());
                props.setProperty("TransactionDate", dateField.getText());
                props.setProperty("TransactionTime", timeField.getText());
                try
                {

                    myModel.stateChangeRequest("Add", props);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(alertTitleSucceeded);
                    alert.setHeaderText(alertSubTitleSucceeded);
                    alert.setContentText(alertBodySucceeded);
                    alert.showAndWait();
                    //populateFields();
                }
                catch (Exception ex)
                {
                    System.out.print("Error Add Transaction");
                }
            }
          }
        }
        if (clickedBtn.getId().equals("2") == true)
        {
            myModel.stateChangeRequest("Done", null);
        }
      }

    private void refreshFormContents()
    {
        submitTitle = buttons.getString("submitAddScout");
        cancelTitle = buttons.getString("cancelAddScout");
        cashString =  labels.getString("cashString");
        checkString = labels.getString("checkString");
        transTypeTitle = labels.getString("tranType");
        barcodeTitle = labels.getString("barcodeTree");
        transAmountTitle = labels.getString("transAmount");
        paymentTitle = labels.getString("payment");
        custNameTitle = labels.getString("custName");
        custPhoneTitle = labels.getString("custPhone");
        custEmailTitle = labels.getString("custEmail");
        dateTitle = labels.getString("date");
        timeTitle = labels.getString("time");
        transactionTypeString = labels.getString("transactionTypeString");


        title = titles.getString("mainTitleTransaction");

        alertTitle = alerts.getString("AddTransactionTitle");
        alertSubTitle = alerts.getString("AddTransactionSubTitle");
        alertBody = alerts.getString("AddTransactionBody");
        alertTitleSucceeded = alerts.getString("AddTransactionTitleSuccess");
        alertSubTitleSucceeded = alerts.getString("AddTransactionSubTitleSucceeded");
        alertBodySucceeded = alerts.getString("AddTransactionBodySucceeded");

        selectPayment = buttons.getString("SelectPayment");
        selectType = buttons.getString("SelectType");
    }


    protected void populateFields()
    {

      barcodeField.setText("");
      transAmountField.setText("");
      custNameField.setText("");
      custPhoneField.setText("");
      custEmailField.setText("");
      dateField.setText(dateStamp);
      timeField.setText(timeStamp);
    }

    @Override
    public void updateState(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
