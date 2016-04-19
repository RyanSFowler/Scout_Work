
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
import userinterface.EnterModifyScoutView;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.ScoutCollectionView;
import userinterface.EnterRemoveScoutView;


/** The class containing the ScoutCollection */
//==============================================================
public class ScoutCollection  extends EntityBase implements IView
{
	private static final String myTableName = "SCOUT";

	private Vector<Scout> scouts = new Vector<Scout>();
	protected Stage myStage;
	protected TreeLotCoordinator myTreeLotCoordinator;
	protected Scout scout;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public ScoutCollection()   //(BookHolder cust) for param is removed
	{
		super(myTableName);
		//scouts = new Vector<Scout>();
	}

	public ScoutCollection(TreeLotCoordinator tlc)
	{
		super(myTableName);
		myStage = MainStageContainer.getInstance();
		myTreeLotCoordinator = tlc;
	}
	//----------------------------------------------------------------------------------
	public void addScout(Scout s)
	{

		int index = findIndexToAdd(s);
		scouts.insertElementAt(s,index); // To build up a collection sorted on some key
		System.out.println("addScout:"+s.getEntryListView());
		System.out.println("Index:" + index);
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

			Scout midSession = scouts.elementAt(middle);

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
		if (key.equals("Scout"))
			return scouts;
		else
		if (key.equals("ScoutList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	/*public void stateChangeRequest(String key, Object value)
	{

		myRegistry.updateSubscribers(key, this);
	}*/

	//----------------------------------------------------------
	public Scout retrieve(String scoutId)
	{
		Scout retValue = null;
		for (int cnt = 0; cnt < scouts.size(); cnt++)
		{
			Scout nextScout = scouts.elementAt(cnt);
			String nextScoutNum = (String)nextScout.getState("ScoutId");
			if (nextScoutNum.equals(scoutId) == true)
			{
				retValue = nextScout;
				return retValue; // we should say 'break;' here
			}
		}

		return retValue;
	}
	public void stateChangeRequest(String key, Object value)
{
				 if (key.equals("Done") == true)
				 {
						 myTreeLotCoordinator.createAndShowTreeLotCoordinatorView();
				 }
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


	public void findScoutsWithNameLike(String first, String last)
	{

		String query = "SELECT * FROM " + myTableName + " WHERE ((FirstName LIKE '" + first +	"') AND (LastName LIKE '" + last + "'));";
		System.out.println(query);
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		System.out.println(allDataRetrieved);
		if (allDataRetrieved != null)
		{
			//scouts = new Vector<Scout>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextScoutData = (Properties)allDataRetrieved.elementAt(cnt);

				Scout scout = new Scout(nextScoutData);



				if (scout != null)
				{
					System.out.println("Scout:"+scout.getEntryListView());
					addScout(scout);
					System.out.println("Scouts:"+scouts);
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
	public void createAndShowScoutCollectionView(ScoutCollection sc)
	{
		Scene currentScene = (Scene)myViews.get("ScoutCollectionView");
		if (currentScene == null)
		{
			// create our initial view
			View newView = new ScoutCollectionView(sc); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			currentScene.getStylesheets().add("styleSheet.css");
			myViews.put("ScoutCollectionView", currentScene);
		}

		swapToView(currentScene);


	}
	public void modifyDone()
	{
		scout.modifyScoutDone();

	}
	public void done()
	{
		myTreeLotCoordinator.transactionDone();

	}
}
