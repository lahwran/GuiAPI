import de.matthiasmann.twl.*;
import de.matthiasmann.twl.model.SimpleButtonModel;


public class WidgetSimplewindow extends Widget {
	public String Title = "";
	public Widget mainwidget = null;
	
	public Label TitleWidget = null;
	public Button BackButton = null;
	public WidgetSingleRow buttonbar = null;
	//public WidgetHoriGroup buttonbar = null; 
	
	protected WidgetSimplewindow()
	{
		Title = "test";
		setTheme("");
		init();
	}
	
	public WidgetSimplewindow(Widget w)
	{
		ScrollPane mainwidget_ = new ScrollPane(w);
		mainwidget_.setFixed(ScrollPane.Fixed.HORIZONTAL);
		mainwidget = mainwidget_;
		Title = "test";
		setTheme("");
		init();
	}
	
	public WidgetSimplewindow(Widget w, String s)
	{
		ScrollPane mainwidget_ = new ScrollPane(w);
		mainwidget_.setFixed(ScrollPane.Fixed.HORIZONTAL);
		mainwidget = mainwidget_;
		Title = s;
		setTheme("");
		init();
	}
	
	protected void init()
	{
		TitleWidget = new Label(Title);
		add(TitleWidget);
		
		BackButton = new Button(new SimpleButtonModel());
		BackButton.getModel().addActionCallback(new ModCallback(ModCallback.BACK, (Object) null));
		BackButton.setText("Back");
		buttonbar = new WidgetSingleRow(200,20, BackButton);
		//buttonbar = new WidgetHoriGroup();
		add(buttonbar);
		
		add(mainwidget);
	}
	
	@Override
	public void layout()
	{
		int s = 1;
		buttonbar.setSize(buttonbar.getPreferredWidth(), buttonbar.getPreferredHeight());
		buttonbar.setPosition((GuiWidgetScreen.screenwidth/2) - (buttonbar.getPreferredWidth()/2), GuiWidgetScreen.screenheight - (buttonbar.getPreferredHeight()+4));
		//System.out.println("layout call");
		
		TitleWidget.setPosition(GuiWidgetScreen.screenwidth/2 - TitleWidget.computeTextWidth()/2, 10*s);
		TitleWidget.setSize(TitleWidget.computeTextWidth(), TitleWidget.computeTextHeight());
		//TitleWidget.adjustSize();
		
		int hpad=30*s;
		int vpad=40*s;
		mainwidget.setPosition(hpad,vpad);
		mainwidget.setSize(GuiWidgetScreen.screenwidth-(hpad*2), GuiWidgetScreen.screenheight-(vpad*2));
		//BackButton.setPosition(0,0);
	}
}
