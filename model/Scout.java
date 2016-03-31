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


public class Scout extends EntityBase implements IView, IModel {

     protected Stage myStage;
     protected TreeLotCoordinator myTreeLotCoordinator;
     protected Properties dependencies;
     private static final String myTableName = "Scout";
     private String updateStatusMessage = "";


     public Scout(TreeLotCoordinator l, String type) throws Exception {
            super(myTableName);
            myTreeLotCoordinator = l;
            persistentState = new Properties();
            setDependencies();
            if (type == "Add") {
                createAddScoutView();
            }
            else if (type == "Update") {
                createEnterScoutView();
            }
            else if (type == "Remove") {
                createEnterScoutView();
            }
        }

     public void createAddScoutView() {
            Scene currentScene = (Scene)myViews.get("AddScoutView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("AddScoutView", this);
                currentScene = new Scene(newView);
                myViews.put("AddScoutView", currentScene);
            }
            swapToView(currentScene);
        }

     public void createEnterScoutView() {
            Scene currentScene = (Scene)myViews.get("EnterScoutView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("EnterScoutView", this);
                currentScene = new Scene(newView);
                myViews.put("EnterScoutView", currentScene);
            }
            swapToView(currentScene);
        }

     public void createUpdateScoutView() {
            Scene currentScene = (Scene)myViews.get("UpdateScoutView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("UpdateScoutView", this);
                currentScene = new Scene(newView);
                myViews.put("UpdateScoutView", currentScene);
            }
            swapToView(currentScene);
        }

      public void createRemoveScoutView() {
             Scene currentScene = (Scene)myViews.get("RemoveScoutView");
             if (currentScene == null)
             {
                   View newView = ViewFactory.createView("RemoveScoutView", this);
                   currentScene = new Scene(newView);
                   myViews.put("RemoveScoutView", currentScene);
             }
             swapToView(currentScene);
        }

       public void createScoutCollectionView() {
              Scene currentScene = (Scene)myViews.get("ScoutCollectionView");

              if (currentScene == null)
              {
                  View newView = ViewFactory.createView("ScoutCollectionView", this);
                  currentScene = new Scene(newView);
                  myViews.put("ScoutCollectionView", currentScene);
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
            if (key.equals("Scout"))
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
