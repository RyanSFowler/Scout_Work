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
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import model.Tree;
import model.TreeVector;

/**
 *
 * @author berghen
 */

public class RemoveTreeView extends View {

        protected Button cancelButton;
        protected Button submitButton;
	private Button doneButton;
        protected MessageView statusLog;
        private TextField barcode;

        private Locale locale = new Locale("en", "CA");
        private ResourceBundle buttons;
        private ResourceBundle titles;
        private ResourceBundle labels;
        private ResourceBundle alerts;
        private String cancelTitle;
        private String submitTitle;
        private String barcodeTitle;
        private String title;
        private String alertTitle;
        private String alertSubTitle;
        private String alertBody;

        private String alertTitleSucceeded;
        private String alertSubTitleSucceeded;
        private String alertBodySucceeded;
        
        private TableColumn barcodeColumn;
        private TableColumn NotesColumn;

        private TableView<TreeVector> tableOfTree;
        
        protected Button cancel;
        protected Button submit;
        
        public RemoveTreeView(IModel book)
    {
        super(book, "RemoveTreeView");

        Preferences prefs = Preferences.userNodeForPackage(AddNewTreeView.class);
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
        //populateFields();
        myModel.subscribe("RemoveTreeViewError", this);
    }

        protected void populateFields()
	{
            barcode.setText("");
            getEntryTableModelValues();
	}

        protected void getEntryTableModelValues()
        {
            ObservableList<TreeVector> tableData = FXCollections.observableArrayList();
            try
                {
                    Tree tree = (Tree)myModel.getState("Tree");
                    Vector entryList = (Vector)tree.getResultFromDB("ModifyTree");
                    Enumeration entries = entryList.elements();
                    Vector<String> view = entryList;
                    TreeVector nextTableRowData = new TreeVector(view);
                    tableData.add(nextTableRowData);
                    tableOfTree.setItems(tableData);
                    tableOfTree.setEditable(true);
                    tableOfTree.setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                            Properties props = new Properties();
                            props.setProperty("Barcode", view.get(0));
                            props.setProperty("Notes", view.get(1));
                            myModel.stateChangeRequest("RemoveTree", props);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(alerts.getString("DeletedTree1"));
                            alert.setHeaderText(alerts.getString("DeletedTree2"));
                            alert.setContentText(alerts.getString("DeletedTree3"));
                            alert.showAndWait();
                            myModel.stateChangeRequest("Done", null);
                        }
                    });
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(alertTitleSucceeded);
                    alert.setHeaderText(alertSubTitleSucceeded);
                    alert.setContentText(alertBodySucceeded);
                    alert.showAndWait();
                }
            catch (Exception e) {
                System.out.print("Enter " + e);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(alertSubTitle);
                alert.setContentText(alertBody);
                alert.showAndWait();
            }
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

        private void createInput2(GridPane grid, Integer pos)
        {
            HBox hb = new HBox(10);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().add(new Label("Barcode:"));
            barcode = new TextField();
            hb.getChildren().add(barcode);
            grid.add(hb, 1, pos);
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
            createInput2(grid, 0);
            tableOfTree = new TableView<TreeVector>();
            barcodeColumn = new TableColumn("Barcode");
            barcodeColumn.setMinWidth(240);
            barcodeColumn.setCellValueFactory(new PropertyValueFactory<Tree, String>("barcode"));
            NotesColumn = new TableColumn("Notes");
            NotesColumn.setMinWidth(240);
            NotesColumn.setCellValueFactory(new PropertyValueFactory<Tree, String>("notes"));
            tableOfTree.getColumns().addAll(barcodeColumn, NotesColumn);
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPrefSize(500, 150);
            scrollPane.setContent(tableOfTree);
            grid.add(scrollPane, 1, 1);
            createButton(grid, submit, submitTitle, 1, 4, 1);
            createButton(grid, cancel, cancelTitle, 0, 4, 2);
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
        
         private TextField createInput(GridPane grid, TextField textfield, String label, Integer pos)
	{
            Label Author = new Label(label);
            GridPane.setHalignment(Author, HPos.RIGHT);
            grid.add(Author, 0, pos);
            textfield = new TextField();
            grid.add(textfield, 1, pos);
            return textfield;
	}

          private void createButton(GridPane grid, Button button, String nameButton, Integer pos)
	{
            button = new Button(nameButton);
            button.setId(Integer.toString(pos));
            button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                public void handle(ActionEvent e) {
                    processAction(e);
                }
            });
            HBox btnContainer = new HBox(10);
            btnContainer.setAlignment(Pos.BOTTOM_RIGHT);
            btnContainer.getChildren().add(button);
            if (pos == 4) {
                grid.add(btnContainer, 1, 2);
            }
            else {
                grid.add(btnContainer, 0, 2);
            }
	}
          public void processAction(Event evt)
	{
            Object source = evt.getSource();
            Button clickedBtn = (Button) source;
            if (clickedBtn.getId().equals("2") == true)
            {
            	myModel.stateChangeRequest("Done", null);
            }
            clearErrorMessage();
            String barcodeField = barcode.getText();
            if (clickedBtn.getId().equals("1") == true)
            {
                if ((barcodeField == null) || (barcodeField.length() == 0))
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
                    props.setProperty("Barcode", barcode.getText());
                    try
                    {
                        myModel.stateChangeRequest("ModifyTree", props);
//                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle(alertTitleSucceeded);
//                        alert.setHeaderText(alertSubTitleSucceeded);
//                        alert.setContentText(alertBodySucceeded);
//                        alert.showAndWait();
                        populateFields();
                    }
                    catch (Exception ex)
                    {
                     System.out.print("Error Remove Tree");
                    }
                }
            }
	}

        private void refreshFormContents()
        {
            submitTitle = buttons.getString("submitDeleteTree");
            cancelTitle = buttons.getString("cancelTree");
            barcodeTitle = labels.getString("barcodeTree");
            title = titles.getString("mainTitleRemoveTree");
            alertTitle = alerts.getString("DeleteTreeTitle");
            alertSubTitle = alerts.getString("DeleteTreeSubtitle");
            alertBody = alerts.getString("DeleteTreeBody");
            alertTitleSucceeded = alerts.getString("DeleteTreeTitleS");
            alertSubTitleSucceeded = alerts.getString("DeleteTreeSubtitleS");
            alertBodySucceeded = alerts.getString("DeleteTreeBodyS");
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
            if (key.equals("RemoveTreeViewError") == true)
            {
		displayErrorMessage((String)value);
            }
	}

        public void clearErrorMessage()
	{
            statusLog.clearErrorMessage();
	}
}
