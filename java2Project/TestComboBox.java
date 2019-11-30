package java2Project;

//Java Program to create a simple JComboBox 
//and add elements to it 
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.*; 
import javax.swing.*;

import uca.OracleWrapper; 
class TestComboBox extends JFrame implements ItemListener { 
	private static ArrayList <Integer> allServiceIDs = new ArrayList();
	private static ArrayList <String> allServicesNames = new ArrayList();
	// frame 
	static JFrame f; 

	// label 
	static JLabel l, l1; 

	// combobox 
	static JComboBox c1; 

	// main class 
	public static void main(String[] args) 
	{ 
		// create a new frame 
		f = new JFrame("frame"); 

		// create a object 
		TestComboBox s = new TestComboBox(); 

		// set layout of frame 
		f.setLayout(new FlowLayout()); 
		// array of string contating cities
		try
		{
		OracleWrapper.openConn();
		OracleWrapper.prepareStatement("SELECT ID,NAME FROM SERVICES ORDER BY NAME");
		ResultSet rs = OracleWrapper.queryDB();
		while(rs.next())
		{
			allServiceIDs.add(rs.getInt(1));
			allServicesNames.add(rs.getString(2));
		}//end while
		
		rs.close();
		//OracleWrapper.closeConn();
		}catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		} // 
		
		String[] strArr = null;
		strArr = allServicesNames.toArray(new String[allServicesNames.size()]);
		//String s1[] = { "Jalpaiguri", "Mumbai", "Noida", "Kolkata", "New Delhi" }; 

		// create checkbox 
		c1 = new JComboBox(strArr); 

		// add ItemListener 
		c1.addItemListener(s); 

		// create labels 
		l = new JLabel("select your Sercice Name "); 
		l1 = new JLabel("Id selected"); 

		// set color of text 
		l.setForeground(Color.red); 
		l1.setForeground(Color.blue); 

		// create a new panel 
		JPanel p = new JPanel(); 

		p.add(l); 

		// add combobox to panel 
		p.add(c1); 

		p.add(l1); 

		// add panel to frame 
		f.add(p); 

		// set the size of frame 
		f.setSize(400, 300); 

		f.show(); 
	} 
	public void itemStateChanged(ItemEvent e) 
	{ 
		// if the state combobox is changed 
		if (e.getSource() == c1) { 
			l1.setText(String.valueOf(allServiceIDs.get(allServicesNames.indexOf(c1.getSelectedItem()))));
			//l1.setText(c1.getSelectedItem() + " selected"); 
		} 
	} 
} 
