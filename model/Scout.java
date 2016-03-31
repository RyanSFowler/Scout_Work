// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.JFrame;



// project imports
import exception.InvalidPrimaryKeyException;
import database.*;
import impresario.IView;
import userinterface.*;
import userinterface.AddScoutView;


/** The class containing the Account for the ATM application */
//==============================================================
public class Scout extends EntityBase implements IView
{
	private static final String myTableName = "Scout";

	protected Properties dependencies;
	protected Stage myStage;
	protected TreeLotCoordinator myTLC;

	// GUI Components

	private String updateStatusMessage = "";

	// constructor for this class
	//----------------------------------------------------------
	public Scout(int scoutId)
		throws InvalidPrimaryKeyException
	{
		super(myTableName);

		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (scoutId = " + scoutId + ")";

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one account at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			// There should be EXACTLY one bookIds. More than that is an error
			if (size != 1)
			{
				throw new InvalidPrimaryKeyException("Multiple scouts matching id : "
					+ scoutId + " found.");
			}
			else
			{
				// copy all the retrieved data into persistent state
				Properties retrievedScoutData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedScoutData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedScoutData.getProperty(nextKey);

					if (nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}

		else
		{
			throw new InvalidPrimaryKeyException("No scout matching id : "
				+ scoutId  + " found.");
		}
	}

	// Can also be used to create a NEW scout (if the system it is part of
	// allows for a new book to be set up)
	//----------------------------------------------------------
	public Scout(Properties props)
	{
		super(myTableName);

		setDependencies();
		setData(props);
	}

	public Scout(TreeLotCoordinator t)
		{
		super(myTableName);
		myStage = MainStageContainer.getInstance();
		myTLC = t;

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

	private void setDependencies()
	{
		dependencies = new Properties();

		myRegistry.setDependencies(dependencies);
	}

	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("UpdateStatusMessage") == true)
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}




	//-----------------------------------------------------------------------------------
	public static int compare(Scout a, Scout b)
	{
		String aNum = (String)a.getState("scoutId");//this or title
		String bNum = (String)b.getState("scoutId");

		return aNum.compareTo(bNum);
	}

	//-----------------------------------------------------------------------------------
	//****Needs to be modified.
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
				updateStatusMessage = "Scout data for scout number : " + persistentState.getProperty("scoutId") + " updated successfully in database!";
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
					((AddScoutView)currentScene.getRoot()).displayMessage("Scout saved successfully!");
			}

		}
		catch (SQLException ex)
		{
			System.out.println("Error in installing Scout data in database!");
			updateStatusMessage = "Error in installing Scout data in database!";

			Scene currentScene = (Scene)myViews.get("AddScoutView");

			if (currentScene != null)
			{
					((AddScoutView)currentScene.getRoot()).displayErrorMessage("ERROR in Scout save!");
			}
		}
	}

	public Vector<String> getEntryListView()
	{
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("scoutId"));
		v.addElement(persistentState.getProperty("firstName"));
		v.addElement(persistentState.getProperty("middleInitial"));
		v.addElement(persistentState.getProperty("lastName"));
		v.addElement(persistentState.getProperty("dob"));
		v.addElement(persistentState.getProperty("phoneNum"));
		v.addElement(persistentState.getProperty("email"));
		v.addElement(persistentState.getProperty("status"));

		return v;
	}

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}

	//-------------------------------------------------------------------------
	public String toString()
	{
		return persistentState.toString();
	}
	//-------------------------------------------------------------------------
	//NEW METHODS 2-21-16------------------------------------------------------
	public void createAndShowScoutView()
	{
		Scene currentScene = (Scene)myViews.get("ScoutView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = new AddScoutView(this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("AddScoutView", currentScene);
		}

		swapToView(currentScene);

	}

	public void done()
	{
		myTLC.transactionDone();

	}
}
