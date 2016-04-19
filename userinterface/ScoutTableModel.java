
package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class ScoutTableModel
{
	private final SimpleStringProperty scoutId;
	private final SimpleStringProperty firstName;
	private final SimpleStringProperty middleInitial;
	private final SimpleStringProperty lastName;
	private final SimpleStringProperty dob;
  private final SimpleStringProperty phoneNumber;
  private final SimpleStringProperty email;
	private final SimpleStringProperty status;
	private final SimpleStringProperty date;


	//----------------------------------------------------------------------------
	public ScoutTableModel(Vector<String> scoutData)
	{
		scoutId =  new SimpleStringProperty(scoutData.elementAt(0));
		firstName =  new SimpleStringProperty(scoutData.elementAt(1));
		middleInitial =  new SimpleStringProperty(scoutData.elementAt(2));
		lastName =  new SimpleStringProperty(scoutData.elementAt(3));
		dob =  new SimpleStringProperty(scoutData.elementAt(4));
    phoneNumber =  new SimpleStringProperty(scoutData.elementAt(5));
    email =  new SimpleStringProperty(scoutData.elementAt(6));
		status =  new SimpleStringProperty(scoutData.elementAt(7));
		date =  new SimpleStringProperty(scoutData.elementAt(8));
	}

	//----------------------------------------------------------------------------
	public String getScoutId() {
        return scoutId.get();
    }

	//----------------------------------------------------------------------------
  public void setScoutID(String number)
	{
      scoutId.set(number);
  }
	//----------------------------------------------------------------------------
	public String getStatus()
	{
			return status.get();
	}
	//----------------------------------------------------------------------------
	public void setStatus(String number)
	{
			status.set(number);
	}
	//----------------------------------------------------------------------------
		public String getDate()
		{
			 return date.get();
		}

	//----------------------------------------------------------------------------
		public void setDate(String number)
		{
				date.set(number);
		}

    //----------------------------------------------------------------------------
    public String getFirstName() {
        return firstName.get();
    }

    //----------------------------------------------------------------------------
    public void setFirstName(String aType) {
        firstName.set(aType);
    }

    //----------------------------------------------------------------------------
    public String getMiddleInitial() {
        return middleInitial.get();
    }

    //----------------------------------------------------------------------------
    public void setMiddleInitial(String aTitle) {
        middleInitial.set(aTitle);
    }

    //----------------------------------------------------------------------------
    public String getLastName() {
        return lastName.get();
    }

    //----------------------------------------------------------------------------
    public void setLastName(String year)
    {
    	lastName.set(year);
    }
    //----------------------------------------------------------------------------
    public String getDob(){
    	return dob.get();

    }
    //----------------------------------------------------------------------------
    public void setDob(String aStatus){
    	dob.set(aStatus);
    }
    //----------------------------------------------------------------------------
    public String getPhoneNumber(){
    	return phoneNumber.get();

    }
    //----------------------------------------------------------------------------
    public void setPhoneNumber(String aStatus){
    	phoneNumber.set(aStatus);
    }
    //----------------------------------------------------------------------------
    public String getEmail(){
    	return email.get();

    }
    //----------------------------------------------------------------------------
    public void setEmail(String aStatus){
    	email.set(aStatus);
    }

}
