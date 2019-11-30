package java2Project;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import class19.Customer;
import uca.OracleWrapper;

public class RMIServer extends UnicastRemoteObject implements RMIInterface {
	private ArrayList<Message> allMessages = new ArrayList();
	
	public static void main(String[] args) {
		Registry thisOne = null;
        try {
        	String address = "10.252.120.188";
        	System.getProperties().setProperty("java.rmi.server.hostname", address);
            LocateRegistry.createRegistry(1099);
            thisOne = LocateRegistry.getRegistry(address, 1099);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } // end catch
        try {
            RMIServer MyServer = new RMIServer();
            thisOne.bind("MyRMIService", MyServer);
            System.out.println("Service bound and waiting.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // end catch
	} // end main
	
	public RMIServer() throws RemoteException {
		try
		{
		OracleWrapper.openConn();
		OracleWrapper.prepareStatement("SELECT ID, SERVICE_ID, SHOW_AFTER, SHOW_BEFORE, CREATION, MESSAGE FROM MESSAGES");
		ResultSet rs = OracleWrapper.queryDB();
		while(rs.next())
		{
			allMessages.add(new Message(String.valueOf(rs.getInt(1)),String.valueOf(rs.getInt(2)),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
		}//end while
		
		rs.close();
		//OracleWrapper.closeConn();
		}catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		} // 
    } // end ctor

	@Override
	public String addMessage(Message inc) throws RemoteException {
		allMessages.add(inc);
	
		Collections.sort(allMessages);
		return "Added: " + inc.toString();
	} // end addCustomer

	@Override
	public String removeMessage(Message inc) throws RemoteException {
		// can't simply allCustomers.remove(inc) because inc is a completely new object -- thus doesn't exist!
		// must test equality and hold the reference to it, if it is found
		Message thisOne = null;
		for (Message eachOne: allMessages) {
			if (eachOne.compareTo(inc) == 0) {
				thisOne = eachOne;
			} // end if
		} // end for
		if (thisOne == null) {
			return "Not found: " + inc.toString();
		} else {
			allMessages.remove(thisOne);
			return "Removed: " + inc.toString();
		} // end else
	} // end removeCustomer

	@Override
	public ArrayList<Message> listMessages() throws RemoteException {
		return allMessages;
	} // end listCustomers

} // end RMIServer