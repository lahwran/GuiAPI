/**
 * @deprecated Use WSimpleWindow
 * @author blendmaster
 *
 */
public class WidgetClassicWindow extends WidgetSimplewindow {

	@Deprecated
	public void init()
	{
		super.init();
		System.err.println("WidgetClassicWindow is deprecated, please update mods using it to use WSimplewindow");
	}
}
