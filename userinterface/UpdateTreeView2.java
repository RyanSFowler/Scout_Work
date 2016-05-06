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
import model.Tree;
import model.TreeVector;
import model.ModifyTree;

/**
 *
 * @author florianjousselin
 */
public class UpdateTreeView2 extends View {

    private TextField barcode;
    private TextArea notes;
    protected Button cancel;
    protected Button submit;
    private Button searchButton;
    private Button doneButton;
    
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
    private String description;
    
    private TableView<TreeVector> tableOfTree;
    
    private String alertTitleSucceeded;
    private String alertSubTitleSucceeded;
    private String alertBodySucceeded;
    private TableColumn barcodeColumn;
    private TableColumn NotesColumn;
    
    public UpdateTreeView2(IModel model) {
        super(model, "UpdateTreeView");
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

	createInput2(grid, 0);

        
	createButton(grid, submit, submitTitle, 1, 4, 1);
	createButton(grid, cancel, cancelTitle, 0, 4, 2);


        
	return grid;
        
    }
    
    
    private void createInput2(GridPane grid, Integer pos)
    {
	HBox hb = new HBox(10);
	hb.setAlignment(Pos.CENTER);
	hb.getChildren().add(new Label("Notes:"));
	notes = new TextArea();
	hb.getChildren().add(notes);
	grid.add(hb, 1, pos);
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
            if (notes.getText().isEmpty() == true)
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
                props.setProperty("Notes", notes.getText());
                try
                {
                    myModel.stateChangeRequest("ModifyTree3", props);
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
        barcodeTitle = labels.getString("barcodeTree");
        description = labels.getString("notes");
        title = titles.getString("mainTitleModifyTree");
        alertTitle = alerts.getString("AddTreeTitle");
        alertSubTitle = alerts.getString("AddTreeSubTitle");
        alertBody = alerts.getString("AddTreeBody");
        alertTitleSucceeded = alerts.getString("AddTreeTitleSucceeded");
        alertSubTitleSucceeded = alerts.getString("AddTreeSubTitleSucceeded");
        alertBodySucceeded = alerts.getString("UpdateTreeBodySucceeded");
    }
    
    
    @Override
    public void updateState(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
