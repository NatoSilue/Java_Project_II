package java2Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import uca.OracleWrapper;

public class Model {
	private ArrayList<Message> allMessages = new ArrayList();

	public Model() {
		makeDemoData();
	} // end ctor

	public void addMessage(Message inc) {
		allMessages.add(inc);
	} // end addInventory

	public void addMessage(int index, Message inc) {
		allMessages.add(index, inc);
	} // end addInventory overload

	public void removeMessage(int inc) {
		allMessages.remove(inc);
	} // end removeInventory

	public void sortRoster() {
		Collections.sort(allMessages);
	} // end sortRoster

	public ArrayList<Message> getRoster() {
		return allMessages;
	} // end getRoster

	public ArrayList<String> getAllMessages() {
		ArrayList<String> temp = new ArrayList();
		for (Message eachOne: allMessages) {
			temp.add(eachOne.toString());
		} // end for
		return temp;
	} // end getInventory

	private void makeDemoData() {
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
	} // end makeDemoData
} // end Model


