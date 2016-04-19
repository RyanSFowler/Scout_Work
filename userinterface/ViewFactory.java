package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("TreeLotCoordinatorView") == true)
		{
			return new TreeLotCoordinatorView(model);
		}
		else if(viewName.equals("AddScoutView") == true)
		{
			return new AddScoutView(model);
		}
		else if(viewName.equals("EnterRemoveScoutView") == true)
		{
			return new EnterRemoveScoutView(model);
		}
		else if(viewName.equals("EnterModifyScoutView") == true)
		{
			return new EnterModifyScoutView(model);
		}
		/*else if(viewName.equals("RemoveScoutView") == true)
		{
			return new RemoveScoutView(model);
		}*/
		else if(viewName.equals("ModifyScoutView") == true)
		{
			return new ModifyScoutView(model);
		}
		else if(viewName.equals("AddNewTreeView") == true)
		{
			return new AddNewTreeView(model);
		}
		else if(viewName.equals("UpdateTreeView") == true)
		{
			return new UpdateTreeView(model);
		}
		else if(viewName.equals("RemoveTreeView") == true)
		{
			return new RemoveTreeView(model);
		}
		else if(viewName.equals("AddNewTreeTypeView") == true)
		{
			return new AddNewTreeTypeView(model);
		}
		else if(viewName.equals("UpdateTreeTypeView") == true)
		{
			return new UpdateTreeTypeView(model);
		}
/*
		else if(viewName.equals("OpenShiftView") == true)
		{
			return new OpenShiftView(model);
		}
		else if(viewName.equals("CloseShiftView") == true)
		{
			return new CloseShiftView(model);
		}
		else if(viewName.equals("RecordSaleView") == true)
		{
			return new RecordSaleView(model);
		}
		else if(viewName.equals("TransferReceipt") == true)
		{
			return new TransferReceipt(model);
		}*/
		else
			return null;
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
