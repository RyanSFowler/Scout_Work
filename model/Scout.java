/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.Enumeration;
import impresario.IModel;
import impresario.IView;
import java.util.Properties;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.View;
import userinterface.ViewFactory;
import java.sql.SQLException;

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
            else if (type == "Modify") {
                createEnterScoutView();
            }
            else if (type == "Remove") {
                createEnterScoutView();
            }
        }
     /*public Scout(Properties p)
     {

     }*/
     public void createAddScoutView() {
            Scene currentScene = (Scene)myViews.get("AddScoutView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("AddScoutView", this);
                currentScene = new Scene(newView);
                currentScene.getStylesheets().add("styleSheet.css");
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
                currentScene.getStylesheets().add("styleSheet.css");
                myViews.put("EnterScoutView", currentScene);
            }
            swapToView(currentScene);
        }

     public void createModifyScoutView() {
            Scene currentScene = (Scene)myViews.get("ModifyScoutView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("ModifyScoutView", this);
                currentScene = new Scene(newView);
                currentScene.getStylesheets().add("styleSheet.css");
                myViews.put("ModifyScoutView", currentScene);
            }
            swapToView(currentScene);
        }
        public static int compare(Scout a, Scout b)
  	  {
  		    String aNum = (String)a.getState("scoutId");
  	    	String bNum = (String)b.getState("scoutId");
  		    return aNum.compareTo(bNum);
    	}
      public void createRemoveScoutView() {
             Scene currentScene = (Scene)myViews.get("RemoveScoutView");
             if (currentScene == null)
             {
                   View newView = ViewFactory.createView("RemoveScoutView", this);
                   currentScene = new Scene(newView);
                   currentScene.getStylesheets().add("styleSheet.css");
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
                  currentScene.getStylesheets().add("styleSheet.css");
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
  //-----------------------------------------------------------------------------------
	public void setData(Properties props)
	{
		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);

			if (nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}
	//-----------------------------------------------------------------------------------

  public void update()
	{
		updateStateInDatabase();
	}

	//-----------------------------------------------------------------------------------
	private void updateStateInDatabase()
	{
		try
		{
			if (persistentState.getProperty("scoutId") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("scoutId",
				persistentState.getProperty("scoutId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "scout data for scoutId number : " + persistentState.getProperty("scoutId") + " updated successfully in database!";
				System.out.println("Trying to update Scout");
			}
			else
			{

				Integer scoutId =
					insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("scoutId", "" + scoutId.intValue());
				System.out.println("Scout data for new scout installed successfully in database");
				updateStatusMessage = "Scout data for new scout : " +  persistentState.getProperty("scoutId")
					+ "installed successfully in database!";
			}
			Scene currentScene = (Scene)myViews.get("AddScoutView");

			if (currentScene != null)
			{
					System.out.println("Scout saved successfully!");
			}

		}
    catch (SQLException ex)
		{
			System.out.println("Error in installing scout data in database!");
		}
  }
  public void done()
  {
    myTreeLotCoordinator.transactionDone();
  }



}
