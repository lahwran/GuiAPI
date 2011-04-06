/**
 * class that should be completely replaced by modaction, but is not. use if you dare.
 * @author lahwran
 * @version 0.9.5
 */
public class ModCallback implements Runnable {

	public static final int BACK = 0;
	public static final int SELECTMOD = 1;
	
	public Object data;
	public int type;
	
	public ModCallback(int t, Object _data)
	{
		type = t;
		data = _data;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (type == BACK)
		{
			GuiModScreen.back();
			GuiModScreen.clicksound();
		}
		else if (type == SELECTMOD)
		{
			//there has got to be a better way to do this
			Integer i = (Integer) data;
			int modnum = i;
			GuiModScreen.show(ModSettingScreen.modscreens.get(modnum).thewidget);
			GuiModScreen.clicksound();
		}
	}

}
