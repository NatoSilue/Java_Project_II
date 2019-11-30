package java2Project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class GUIClient extends JFrame {
    private class RowSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
        	// need to check .getVAlueIsAdjusting() as the event is fired twice
        	// doing this makes sure only one popup is created!
            if (table.getSelectedRow() != -1 && e.getValueIsAdjusting()) {
                // create a new modal popup window
                new Editor(false);
            } // end if
        } // end valueChanged
    } // end RowSelectionListener
    
    private class AddMessageHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		new Editor(true);
    	} // end actionPerformed
    } // end RefreshHandler
    
    private class RefreshHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		loadTableData();
    		showData();
    	} // end actionPerformed
    } // end RefreshHandler
    
    private class Editor extends JDialog {
    	private JPanel panel;
    	private JLabel messageIDLabel;
    	private JLabel serviceNameLabel;
    	private JLabel showAfterLabel;
    	private JLabel showBeforeLabel;
    	private JLabel CreationDateLabel;
    	private JLabel messageDetailsLabel;
    	private JTextField messageIDTextField;
    	private JTextField serviceNameTextField;
    	private JTextField showAfterTextField;
    	private JTextField showBeforeTextField;
    	private JTextField creationDateTextField;
    	private JTextField messageDetailsTextField;
    	private JButton submit;
    	private JButton cancel;
    	
    	private class CancelHandler implements ActionListener {
        	@Override
        	public void actionPerformed(ActionEvent ae) {
        		Editor.this.dispose();
        	} // end actionPerformed
        } // end CancelHandler
    	
    	private class SubmitHandler implements ActionListener {
    		@Override
    		public void actionPerformed(ActionEvent ae) {
				Message temp = new Message(messageIDTextField.getText(), serviceNameTextField.getText(),
    					showAfterTextField.getText(), showBeforeTextField.getText(), creationDateTextField.getText(), messageDetailsTextField.getText());
    			RMIInterface remoteService;
    			try {
    				remoteService = (RMIInterface) Naming.lookup("rmi://" + address + "/MyRMIService");
    				System.out.println(remoteService.removeMessage(temp));
    				System.out.println(remoteService.addMessage(temp));
    			} catch (Exception e) {
    				System.out.println(e.getMessage());
    				e.printStackTrace();
    			} // end catch
    			loadTableData();
    			showData();
    			Editor.this.dispose();
    		} // end actionPerformed
    	} // end SubmitHandler
    	
    	public Editor(boolean inc) {
    		initComponents(inc);
    	} // end ctor
    	
    	public void initComponents(boolean inc) {
    		panel = new JPanel();
    		panel.setLayout(null);
    		
    		messageIDLabel = new JLabel("Message ID");
    		messageIDLabel.setSize(100, 20);
    		messageIDLabel.setLocation(10, 10);
    		panel.add(messageIDLabel);
    		messageIDTextField = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getMessageID());
    		messageIDTextField.setSize(175, 20);
    		messageIDTextField.setLocation(110, 10);
    		if (inc) {
    			messageIDTextField.setEditable(true); // new customer so allow editing this field
    		} else {
    			messageIDTextField.setEditable(false); // unique identifier should never be edited!
    		}
    		panel.add(messageIDTextField);
		    		
    		serviceNameLabel = new JLabel("Service Name");
    		serviceNameLabel.setSize(100, 20);
    		serviceNameLabel.setLocation(10, 35);
			panel.add(serviceNameLabel);
			serviceNameTextField = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getServiceName());
			serviceNameTextField.setSize(175, 20);
			serviceNameTextField.setLocation(110, 35);
			panel.add(serviceNameTextField);    		
			
			showAfterLabel = new JLabel("Show After");
			showAfterLabel.setSize(100, 20);
			showAfterLabel.setLocation(10, 60);
			panel.add(showAfterLabel);
			showAfterTextField = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getShowAfter());
			showAfterTextField.setSize(175, 20);
			showAfterTextField.setLocation(110, 60);
			panel.add(showAfterTextField);
			
			showBeforeLabel = new JLabel("Show Before");
			showBeforeLabel.setSize(100, 20);
			showBeforeLabel.setLocation(10, 85);
			panel.add(showBeforeLabel);
			showBeforeTextField = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getShowBefore());
			showBeforeTextField.setSize(175, 20);
			showBeforeTextField.setLocation(110, 85);
			panel.add(showBeforeTextField);
			
			CreationDateLabel = new JLabel("Creation Date");
			CreationDateLabel.setSize(100, 20);
			CreationDateLabel.setLocation(10, 110);
			panel.add(CreationDateLabel);
			creationDateTextField = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getCreationDate());
			creationDateTextField.setSize(175, 20);
			creationDateTextField.setLocation(110, 110);
			panel.add(creationDateTextField);
			
			messageDetailsLabel = new JLabel("Message");
			messageDetailsLabel.setSize(100, 20);
			messageDetailsLabel.setLocation(10, 135);
			panel.add(messageDetailsLabel);
			messageDetailsTextField = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getMessageDetails());
			messageDetailsTextField.setSize(175, 20);
			messageDetailsTextField.setLocation(110, 135);
			panel.add(messageDetailsTextField);
			
			submit = new JButton("Submit");
			submit.setSize(75, 20);
			submit.setLocation(90, 160);
			submit.addActionListener(new SubmitHandler());
			panel.add(submit);
			
			cancel = new JButton("Cancel");
			cancel.setSize(75, 20);
			cancel.setLocation(175, 160);
			cancel.addActionListener(new CancelHandler());
			panel.add(cancel);
			
			this.setContentPane(panel);
			this.setTitle("Data Editor");
			this.setSize(350, 250);
			this.setModal(true); // this makes the popup modal
			this.setVisible(true);
		} // end initComponents
	} // end Editor
    
	private JFrame window;
	private JPanel panel;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton refreshData;
	private JButton newMessage;
	private String address;
	private ArrayList<Message> allData;
	
	public GUIClient(String inc) {
		this.address = inc;
		initComponents();
	} // end ctor

	public static void main(String[] args) {
		String address = "10.252.120.188";
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUIClient(address);
			} // end method run
		});
	} // end main

	private void initComponents() {
		panel = new JPanel();
		panel.setLayout(null);
		
		table = new JTable();
		loadTableData();
		showData();
        ListSelectionModel rowSelectionModel = table.getSelectionModel();
        rowSelectionModel.addListSelectionListener(new RowSelectionListener()); 
        
		scrollPane = new JScrollPane(table);
		scrollPane.setSize(900, 125);
		panel.add(scrollPane);
		
		refreshData = new JButton("Refresh");
		refreshData.setSize(120, 20);
		refreshData.setLocation(910, 50);
		refreshData.addActionListener(new RefreshHandler());
		panel.add(refreshData);

		newMessage = new JButton("New Customer");
		newMessage.setSize(120, 20);
		newMessage.setLocation(910, 75);
		newMessage.addActionListener(new AddMessageHandler());
		panel.add(newMessage);
		
		window = new JFrame();
		window.setSize(1050, 200);
		window.setTitle("My demonstration GUI");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setContentPane(panel);
		window.setVisible(true);
	} // end initComponents
	
	private void loadTableData() {
		RMIInterface remoteService;
		try {
			remoteService = (RMIInterface) Naming.lookup("rmi://" + address + "/MyRMIService");
			allData = remoteService.listMessages();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} // end catch
	} // end loadTableData
	
	private void showData() {
		String[] columnHeaders = {"Message ID","Service Name","Show After","Show Before","Creation Date","Message"}; 
        // this part converts from the ArrayList to a String[][]
        // the hard-coded 3 below will need to reflect the actual number
        // of columns being used
        String[][] rows = new String[allData.size()][6];
        int i = 0;
        for (Message eachOne: allData) {
            // we're creating a new String[] for each record and setting that entire array to be
            // the value of the rows[i] array element
            String[] oneRow = {eachOne.getMessageID(), eachOne.getServiceName(), eachOne.getShowAfter(),
            		eachOne.getShowBefore(), eachOne.getCreationDate(), eachOne.getMessageDetails()};
            rows[i++] = oneRow;
        } // end for
        // this part attaches the 2 arrays to the JTable with new data replacing the old
        DefaultTableModel tableModel = new DefaultTableModel(rows, columnHeaders);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setWidth(150); 
        table.getColumnModel().getColumn(0).setMaxWidth(150); 
        table.getColumnModel().getColumn(0).setMinWidth(150); 
        table.getColumnModel().getColumn(1).setWidth(150); 
        table.getColumnModel().getColumn(1).setMaxWidth(150); 
        table.getColumnModel().getColumn(1).setMinWidth(150); 
        table.getColumnModel().getColumn(2).setWidth(150); 
        table.getColumnModel().getColumn(2).setMaxWidth(150); 
        table.getColumnModel().getColumn(2).setMinWidth(150); 
        table.getColumnModel().getColumn(3).setWidth(150); 
        table.getColumnModel().getColumn(3).setMaxWidth(150); 
        table.getColumnModel().getColumn(3).setMinWidth(150); 
        table.getColumnModel().getColumn(4).setWidth(150); 
        table.getColumnModel().getColumn(4).setMaxWidth(150); 
        table.getColumnModel().getColumn(4).setMinWidth(150); 
        table.getColumnModel().getColumn(5).setWidth(150); 
        table.getColumnModel().getColumn(5).setMaxWidth(150); 
        table.getColumnModel().getColumn(5).setMinWidth(150); 
    } // end showData

} // end GUIClient
