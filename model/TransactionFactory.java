// specify the package
package model;

// system imports
import java.util.Vector;
import javax.swing.JFrame;

// project imports

//==============================================================
public class TransactionFactory
{

	/**
	 *
	 */
	//----------------------------------------------------------
	public static Transaction createTransaction(TreeLotCoordinator myTreeLotCoordinator, String transCategory, String transType)
		throws Exception
	{
		Transaction retValue = null;

                if(transCategory.equals("Tree")){
                        System.out.print("transType:" + transType);
			//retValue = new Tree(myTreeLotCoordinator, transType);
		}
		
		/*if(transCategory.equals("Scout")){
			retValue = new Scout(transType);
		}
		else if(transCategory.equals("Tree")){
			retValue = new Tree(transType);
		}
		else if(transCategory.equals("TreeType")){
			retValue = new TreeType(transType);
		}*/
		
		
		return retValue;
	}
}
