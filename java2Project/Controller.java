package java2Project;

import javax.swing.SwingUtilities;

public class Controller{

	private static Controller controller = new Controller();
	private Model model;
	private View view;
	private GUIView guiView;

	private Controller() {
		model = new Model();
		view = new View(model);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				guiView = new GUIView(model);
			} // end method run
		});
	} // end ctor

	public static Controller getInstance() {
		return controller;
	} // end getInstance

	public void addMessage(Message inc) {
		model.addMessage(inc);
	} // end addInventory overload

	public void addMessage(int index, Message inc) {
		model.addMessage(index, inc);
	} // end addInventory overload

	public void removeMessage(int inc) {
		model.removeMessage(inc);
	} // end remove overloadMessage

	public void refresh() {
		for (String eachOne: model.getAllMessages()) {
			view.showData(eachOne);
		} // end for

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				guiView.showData();
			} // end method run
		});
	} // end refresh

	public void sortData() {
		model.sortRoster();
		refresh();
	} // end sortData
} // end Controller



