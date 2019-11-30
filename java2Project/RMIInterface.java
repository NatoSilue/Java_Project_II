package java2Project;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIInterface extends Remote {
	public String addMessage(Message inc) throws RemoteException;
	public String removeMessage(Message inc) throws RemoteException;
	public ArrayList<Message> listMessages() throws RemoteException;
	
} // end RMIInterface
