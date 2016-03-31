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
		/*else if(viewName.equals("UpdateScoutView") == true)
		{
			return new UpdateScoutView(model);
		}
		else if(viewName.equals("RemoveScoutView") == true)
		{
			return new RemoveScoutView(model);
		}
		else if(viewName.equals("AddTreeView") == true)
		{
			return new AddTreeView(model);
		}
		else if(viewName.equals("ModifyTreeView") == true)
		{
			return new ModifyTreeView(model);
		}
		else if(viewName.equals("RemoveTreeView") == true)
		{
			return new RemoveTreeView(model);
		}
		else if(viewName.equals("AddTreeTypeView") == true)
		{
			return new AddTreeTypeView(model);
		}
		else if(viewName.equals("ModifyTreeTypeView") == true)
		{
			return new ModifyTreeTypeView(model);
		}
		else if(viewName.equals("RemoveTreeTypeView") == true)
		{
			return new RemoveTreeTypeView(model);
		}
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
		}*/
		/*else if(viewName.equals("TransferReceipt") == true)
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
