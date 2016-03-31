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
import userinterface.BookView;


/** The class containing the Account for the ATM application */
//==============================================================
public class AddScout extends EntityBase implements IView
{
	public AddScout(TreeLotCoordinator TLC)
	{

	}
	public endTransaction()
	{
		myTLC.transactionDone();
	}
