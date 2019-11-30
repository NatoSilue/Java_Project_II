package java2Project;

public class View {

	private final Model model;

	public View(Model inc) {
		this.model = inc;
	} // end ctor

	public void showData(String inc) {
		System.out.println(inc);
	} // end showData
} // end View



