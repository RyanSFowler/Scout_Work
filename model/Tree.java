/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import impresario.IModel;
import impresario.IView;
import java.util.Properties;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.View;
import userinterface.ViewFactory;

/**
 *
 * @author berghen
 */
public class Tree extends EntityBase implements IView, IModel {
    
     protected Stage myStage;
     protected TreeLotCoordinator myTreeLotCoordinator;
     protected Properties dependencies;
     private static final String myTableName = "Tree";
     private String updateStatusMessage = "";
    
     
     public Tree(TreeLotCoordinator l, String type) throws Exception {
            super(myTableName);
            myTreeLotCoordinator = l;
            persistentState = new Properties();
            setDependencies();
            if (type == "Add") {
                createAddTreeView();
            }
            else if (type == "Update") {
                createUpdateTreeView();
            }
            else if (type == "Remove") {
                createRemoveTreeView();
            }
        }
     
     public void createAddTreeView() {
            Scene currentScene = (Scene)myViews.get("AddNewTreeView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("AddNewTreeView", this);
                currentScene = new Scene(newView);
                myViews.put("AddNewTreeView", currentScene);
            }
            swapToView(currentScene);        
        }
     
     public void createRemoveTreeView() {
         //System.out.print("createRemoveTreeView");
            Scene currentScene = (Scene)myViews.get("RemoveTreeView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("RemoveTreeView", this);
                currentScene = new Scene(newView);
                myViews.put("RemoveTreeView", currentScene);
            }
            swapToView(currentScene);        
        }
     
     public void createUpdateTreeView() {
            Scene currentScene = (Scene)myViews.get("UpdateTreeView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("UpdateTreeView", this);
                currentScene = new Scene(newView);
                myViews.put("UpdateTreeView", currentScene);
            }
            swapToView(currentScene);        
        }
     
     public void setDependencies()
	{
            dependencies = new Properties();
            myRegistry.setDependencies(dependencies);
	}
     
     public Object getState(String key)
	{
            if (key.equals("Tree"))
		return this;
            return null;
	}
     
     public void stateChangeRequest(String key, Object value)
	{
            if (key.equals("Done") == true)
            {
               // myLibrarian.createAndShowLibrarianView();
            }
            else if (key.equals("updateBook") == true)
            {
                if (value != null)
                {
                    persistentState = (Properties) value;
                 //   updateStateInDatabase();
                }
            }
            //myRegistry.updateSubscribers(key, this);
	}

   protected void initializeSchema(String tableName)
	{
            if (mySchema == null)
            {
                mySchema = getSchemaInfo(tableName);
            }
	}

   public void updateState(String key, Object value)
	{
            stateChangeRequest(key, value);
	}


     
}
