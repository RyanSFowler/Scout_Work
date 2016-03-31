// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;

import javafx.scene.Scene;
import javafx.stage.Stage;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;
import impresario.IView;
import userinterface.BookView;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.BookCollectionView;
//import userinterface.BookCollectionView;


/** The class containing the BookCollection */
//==============================================================
public class ScoutCollection  extends EntityBase implements IView
{
	private static final String myTableName = "Scout";

	private Vector<Book> scouts;
	protected Stage myStage;
	protected TreeLotCoordinator myTLC;

	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public ScoutCollection()   //(BookHolder cust) for param is removed
	{
		super(myTableName);
		scouts = new Vector<Scout>();
	}

	public ScoutCollection(TreeLotCoordinator t)
	{
		super(myTableName);
		myStage = MainStageContainer.getInstance();
		myTLC = t;
	}
	//----------------------------------------------------------------------------------
	public void addScout(Scout a)
	{
		//book.add(a);
		int index = findIndexToAdd(a);
		scouts.insertElementAt(a,index); // To build up a collection sorted on some key
	}
	//----------------------------------------------------------------------------------
	private int findIndexToAdd(Scout b)
	{
		//users.add(u);
		int low=0;
		int high = scouts.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			Scout midSession = books.elementAt(middle);

			int result = Scout.compare(b,midSession);

			if (result ==0)
			{
				return middle;
			}
			else if (result<0)
			{
				high=middle-1;
			}
			else
			{
				low=middle+1;
			}


		}
		return low;
	}


	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("Scouts"))
			return scouts;
		else
		if (key.equals("ScoutList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{

		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public Scout retrieve(String scoutId)
	{
		Scout retValue = null;
		for (int cnt = 0; cnt < books.size(); cnt++)
		{
			Scout nextScout = scouts.elementAt(cnt);
			String nextScoutId = (String)nextBk.getState("ScoutId");
			if (nextScoutId.equals(scoutId) == true)
			{
				retValue = nextScout;
				return retValue; // we should say 'break;' here
			}
		}

		return retValue;
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//------------------------------------------------------
	protected void createAndShowView()
	{

		Scene localScene = myViews.get("ScoutCollectionView");

		if (localScene == null)
		{
				// create our new view
				View newView = ViewFactory.createView("ScoutCollectionView", this);
				localScene = new Scene(newView);
				myViews.put("ScoutCollectionView", localScene);
		}
		// make the view visible by installing it into the frame
		swapToView(localScene);

	}

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
	//----------------------------------------------------------------------------------
	public void findScoutsWithNameLike(String fn, String ln)
	{

		String query = "SELECT * FROM " + myTableName + " WHERE (firstName LIKE '%" + fn + "%' AND lastName LIKE '%" + ln + "%')";

		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null)
		{
			scouts = new Vector<Scout>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextScoutData = (Properties)allDataRetrieved.elementAt(cnt);

				Scout scout = new Scout(nextScoutData);

				if (scout != null)
				{
					addScout(scout);
				}
			}

		}
		else{}

	}

	//---------------------------------------------------------------
	public void display()
	{
		for (int i = 0; i < scouts.size(); i++)
		{
			System.out.println(scouts.get(i));
		}
	}
	//---------------------------------------------------------------
	public void createAndShowScoutCollectionView()
	{
		Scene currentScene = (Scene)myViews.get("ScoutCollectionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = new ScoutCollectionView(this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("ScoutCollectionView", currentScene);
		}

		swapToView(currentScene);


	}

	public void done()
	{
		myTLC.transactionDone();

	}
}
