// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.MainStageContainer;
import userinterface.TreeLotCoordinatorView;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

//==============================================================
public class TreeLotCoordinator implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
	// For Impresario
	private Properties dependencies;
	private ModelRegistry myRegistry;

	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage myStage;

	// constructor for this class
	//----------------------------------------------------------
	public TreeLotCoordinator()
	{
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();

		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("TreeLotCoordinator");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "TreeLotCoordinator",
				"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		setDependencies();

		// Set up the initial view
		createAndShowTreeLotCoordinatorView();
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("Deposit", "TransactionError");
		dependencies.setProperty("Withdraw", "TransactionError");
		dependencies.setProperty("Transfer", "TransactionError");
		dependencies.setProperty("BalanceInquiry", "TransactionError");
		dependencies.setProperty("ImposeServiceCharge", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		/*if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("Name") == true)
		{
			if (myAccountHolder != null)
			{
				return myAccountHolder.getState("Name");
			}
			else
				return "Undefined";
		}
		else*/
			return "";
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// STEP 4: Write the sCR method component for the key you
		// just set up dependencies for
		// DEBUG System.out.println("Teller.sCR: key = " + key);
/*
		if (key.equals("Login") == true)
		{
			if (value != null)
			{
				loginErrorMessage = "";

				boolean flag = loginAccountHolder((Properties)value);
				if (flag == true)
				{
					createAndShowTransactionChoiceView();
				}
			}
		}
		else
		if (key.equals("CancelTransaction") == true)
		{
			createAndShowTransactionChoiceView();
		}
		else
		if ((key.equals("Deposit") == true) || (key.equals("Withdraw") == true) ||
			(key.equals("Transfer") == true) || (key.equals("BalanceInquiry") == true) ||
			(key.equals("ImposeServiceCharge") == true))
		{
			String transType = key;

			if (myAccountHolder != null)
			{
				doTransaction(transType);
			}
			else
			{
				transactionErrorMessage = "Transaction impossible: Customer not identified";
			}

		}
		else
		if (key.equals("Logout") == true)
		{
			myAccountHolder = null;
			myViews.remove("TransactionChoiceView");

			createAndShowTellerView();
		}*/

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// DEBUG System.out.println("Teller.updateState: key: " + key);

		stateChangeRequest(key, value);
	}


	/**
	 * Create a Transaction depending on the Transaction type (deposit,
	 * withdraw, transfer, etc.). Use the AccountHolder holder data to do the
	 * create.
	 */
	//----------------------------------------------------------
	public void doTransaction(String transactionCategory, String transactionType)
	{
		try
		{
			EntityBase trans = EntityBaseFactory.createEntityBase(this, transactionCategory, transactionType);
			//the below lines need work
			trans.subscribe("EndTransaction", this);
			trans.stateChangeRequest("DoYourJob", "");
		}
		catch (Exception ex)
		{
			//transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
			new Event(Event.getLeafLevelClassName(this), "createTransaction",
					"Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
					Event.ERROR);
		}
	}
	public void transactionDone()
	{
		createAndShowTreeLotCoordinatorView();
	}
	//------------------------------------------------------------
	private void createAndShowTreeLotCoordinatorView()
	{
		Scene currentScene = (Scene)myViews.get("TreeLotCoordinatorView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("TreeLotCoordinatorView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			currentScene.getStylesheets().add("styleSheet.css");
			myViews.put("TreeLotCoordinatorView", currentScene);
		}

		swapToView(currentScene);
	}

	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}


	//-----------------------------------------------------------------------------
	public void swapToView(Scene newScene)
	{

		if (newScene == null)
		{
			System.out.println("TreeLotCoordinator.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();


		//Place in center
		WindowPosition.placeCenter(myStage);

	}

}
