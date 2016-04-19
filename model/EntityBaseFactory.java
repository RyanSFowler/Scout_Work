/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author berghen
 */
public class EntityBaseFactory {
    public static EntityBase createEntityBase(TreeLotCoordinator myTreeLotCoordinator, String transCategory, String transType)
		throws Exception
	{
            EntityBase retValue = null;

            if(transCategory.equals("Tree")){
                retValue = new Tree(myTreeLotCoordinator, transType);
            }
            if(transCategory.equals("Scout")){
                retValue = new Scout(myTreeLotCoordinator, transType);
            }
            if(transCategory.equals("TreeType")){
                retValue = new TreeType(myTreeLotCoordinator, transType);
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
