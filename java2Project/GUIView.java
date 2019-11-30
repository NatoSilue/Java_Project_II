package java2Project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class GUIView extends JFrame{

	private final Model model;
	private JFrame window;
	private JPanel panel;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton addMessage;
	private JButton sortRoster;
	private ArrayList<Message> allData;

	private class Editor extends JDialog {

		private JLabel messageIDLabel;
		private JLabel serviceNameLabel;
		private JLabel showAfterLabel;
		private JLabel showBeforeLabel;
		private JLabel CreationDateLabel;
		private JLabel messageDetailsLabel;

		private JTextField messageID;
		private JTextField serviceName;
		private JTextField showAfter;
		private JTextField showBefore;
		private JTextField creationDate;
		private JTextField messageDetails;

		private JButton submit;
		private JButton cancel;
		private JPanel panel;
		private boolean newRecord = false;


		private class CancelHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Editor.this.dispose();
			} // end actionPerformed
		} // end CancelHandler

		private class SubmitHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {

				Message temp = new Message(messageID.getText(), serviceName.getText(), showAfter.getText(), showBefore.getText(), creationDate.getText(), messageDetails.getText());

				if (!newRecord) {
					Controller.getInstance().removeMessage(table.getSelectedRow());
					Controller.getInstance().addMessage(table.getSelectedRow(), temp);
				} else {
					Controller.getInstance().addMessage(temp);
				} // end else
				Controller.getInstance().refresh();
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
			messageID = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getMessageID());
			messageID.setSize(175, 20);
			messageID.setLocation(110, 10);
			if (inc) {
				messageID.setEditable(true); // new customer so allow editing this field
			} else {
				messageID.setEditable(false); // unique identifier should never be edited!
			}
			panel.add(messageID);

			serviceNameLabel = new JLabel("Service Name");
			serviceNameLabel.setSize(100, 20);
			serviceNameLabel.setLocation(10, 35);
			panel.add(serviceNameLabel);
			serviceName = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getServiceName());
			serviceName.setSize(175, 20);
			serviceName.setLocation(110, 35);
			panel.add(serviceName);    		

			showAfterLabel = new JLabel("Show After");
			showAfterLabel.setSize(100, 20);
			showAfterLabel.setLocation(10, 60);
			panel.add(showAfterLabel);
			showAfter = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getShowAfter());
			showAfter.setSize(175, 20);
			showAfter.setLocation(110, 60);
			panel.add(showAfter);

			showBeforeLabel = new JLabel("Show Before");
			showBeforeLabel.setSize(100, 20);
			showBeforeLabel.setLocation(10, 85);
			panel.add(showBeforeLabel);
			showBefore = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getShowBefore());
			showBefore.setSize(175, 20);
			showBefore.setLocation(110, 85);
			panel.add(showBefore);

			CreationDateLabel = new JLabel("Creation Date");
			CreationDateLabel.setSize(100, 20);
			CreationDateLabel.setLocation(10, 110);
			panel.add(CreationDateLabel);
			creationDate = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getCreationDate());
			creationDate.setSize(175, 20);
			creationDate.setLocation(110, 110);
			panel.add(creationDate);

			messageDetailsLabel = new JLabel("Message");
			messageDetailsLabel.setSize(100, 20);
			messageDetailsLabel.setLocation(10, 135);
			panel.add(messageDetailsLabel);
			messageDetails = inc ? new JTextField() : new JTextField(allData.get(table.getSelectedRow()).getMessageDetails());
			messageDetails.setSize(175, 20);
			messageDetails.setLocation(110, 135);
			panel.add(messageDetails);

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

	private class RowSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (table.getSelectedRow() != -1 && e.getValueIsAdjusting()) {
				new Editor(true);
			} // end if
		} // end valueChanged
	} // end RowSelectionListener

	private class AddMessage implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			new Editor(true);
		} // end actionPerformed
	} // end AddInventory

	private class SortRoster implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			Controller.getInstance().sortData();
		} // end actionPerformed
	} // end SortRoster

	public GUIView(Model inc) {
		model = inc;
		initComponents();
	} // end ctor

	public void initComponents() {
		panel = new JPanel();
		panel.setLayout(null);

		table = new JTable();
		showData();
		ListSelectionModel rowSelectionModel = table.getSelectionModel();
		rowSelectionModel.addListSelectionListener(new RowSelectionListener()); 

		scrollPane = new JScrollPane(table);
		scrollPane.setSize(800, 125);
		panel.add(scrollPane);

		addMessage = new JButton("Add Message");
		addMessage.setSize(130, 25);
		addMessage.setLocation(40, 125);
		addMessage.addActionListener(new AddMessage());
		panel.add(addMessage);

		sortRoster = new JButton("Sort Roster");
		sortRoster.setSize(130, 25);
		sortRoster.setLocation(175, 125);
		sortRoster.addActionListener(new SortRoster());
		panel.add(sortRoster);

		window = new JFrame();
		window.setSize(900, 200);
		window.setTitle("IT Status Message GUI");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setContentPane(panel);
		window.setVisible(true);
	} // end initComponents

	public void showData() {
		String[] columnHeaders = {"Message ID","Service Name","Show After", "Show Before","Creation Date", "Message Details"};
		String[][] rows = new String[model.getAllMessages().size()][6];
		int i = 0;
		for (Message eachOne: model.getRoster()) {
			// no need to parse anymore, just directly use getter methods for instance values
			String[] oneRow = {eachOne.getMessageID(), String.valueOf(eachOne.getServiceName()), eachOne.getShowAfter(), eachOne.getShowBefore(), eachOne.getCreationDate(), eachOne.getMessageDetails()};
			rows[i++] = oneRow;
		} // end for
		// this part attaches the 2 arrays to the JTable with new data replacing the old
		DefaultTableModel tableModel = new DefaultTableModel(rows, columnHeaders);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(1).setWidth(150); 
		table.getColumnModel().getColumn(1).setMaxWidth(150); 
		table.getColumnModel().getColumn(1).setMinWidth(150); 
	} // end initTableData


}//end GUIView

