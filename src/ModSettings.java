/**
 * Main interface class for Settings API
 * @author lahwran
 * @version 0.9.5
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import de.matthiasmann.twl.Widget;

import net.minecraft.client.Minecraft;
//import java.util.Vector;


public class ModSettings {
	//public Vector<int[]> settings;
	/**
	 * all registered settings for this mod
	 */
	public ArrayList<Setting> Settings;
	//public HashMap<String,Integer> NameID;
	
	public static ArrayList<ModSettings> all = new ArrayList<ModSettings>();
	
	/**
	 * Mod name as used in .minecraft/mods/${modbackendname}/
	 */
	public String backendname;
	
	public static String currentContext;
	public static HashMap<String,String> contextDatadirs;
	static {
		contextDatadirs=new HashMap<String,String>();
		currentContext = "";
		contextDatadirs.put("", "mods");
	}
	//public Widget mainwidget = null;
	
	public boolean have_loaded = false;
	
	
	/**
	 * @param modbackendname used to initialize class modbackendname field
	 */
	public ModSettings(String modbackendname)
	{
		//modnicename = _modnicename;
		backendname = modbackendname;
		Settings = new ArrayList<Setting>();
		all.add(this);
		//NameID = new HashMap<String,Integer>();
	}
	//boolean
	/**
	 * convenience boolean setting adder
	 */
	public SettingBoolean addSetting(ModSettingScreen screen, String nicename, String backendname, boolean value)
	{
		SettingBoolean s = new SettingBoolean(backendname, value);
		WidgetBoolean w = new WidgetBoolean(s, nicename);
		screen.append(w);
		this.append(s);
		return s;
	}
	/**
	 * convenience boolean setting adder
	 */
	public SettingBoolean addSetting(ModSettingScreen screen, String nicename, String backendname, boolean value, String truestring, String falsestring)
	{
		SettingBoolean s = new SettingBoolean(backendname, value);
		WidgetBoolean w = new WidgetBoolean(s, nicename, truestring, falsestring);
		screen.append(w);
		this.append(s);
		return s;
	}
	//float
	/**
	 * convenience float setting adder
	 */
	public SettingFloat addSetting(ModSettingScreen screen, String nicename, String backendname, float value)
	{
		SettingFloat s = new SettingFloat(backendname, value);
		WidgetFloat w = new WidgetFloat(s, nicename);
		screen.append(w);
		this.append(s);
		return s;
	}
	/**
	 * convenience float setting adder
	 */
	public SettingFloat addSetting(ModSettingScreen screen, String nicename, String backendname, float value, float min, float step, float max)
	{
		SettingFloat s = new SettingFloat(backendname, value, min, step, max);
		WidgetFloat w = new WidgetFloat(s, nicename);
		screen.append(w);
		this.append(s);
		return s;
	}
	//int
	/**
	 * convenience int setting adder
	 */
	public SettingInt addSetting(ModSettingScreen screen, String nicename, String backendname, int value, int min, int max)
	{
		SettingInt s = new SettingInt(backendname, value, min, 1, max);
		WidgetInt w = new WidgetInt(s, nicename);
		screen.append(w);
		this.append(s);
		return s;
	}
	/**
	 * convenience int setting adder
	 */
	public SettingInt addSetting(ModSettingScreen screen, String nicename, String backendname, int value, int min, int step, int max)
	{
		SettingInt s = new SettingInt(backendname, value, min, step, max);
		WidgetInt w = new WidgetInt(s, nicename);
		screen.append(w);
		this.append(s);
		return s;
	}
	
	//key
	/**
	 * convenience key setting adder
	 */
	public SettingKey addSetting(ModSettingScreen screen, String nicename, String backendname, int value)
	{
		SettingKey s = new SettingKey(backendname, value);
		WidgetKeybinding w = new WidgetKeybinding(s, nicename);
		screen.append(w);
		this.append(s);
		return s;
	}
	
	//multi
	/**
	 * convenience multi setting adder
	 */
	public SettingMulti addSetting(ModSettingScreen screen, String nicename, String backendname, int value, String... labels)
	{
		SettingMulti s = new SettingMulti(backendname, value, labels);
		WidgetMulti w = new WidgetMulti(s, nicename);
		screen.append(w);
		this.append(s);
		return s;
	}
	
	//text
	/**
	 * convenience text setting adder
	 */
	public SettingText addSetting(ModSettingScreen screen, String nicename, String backendname, String value)
	{
		SettingText s = new SettingText(backendname, value);
		WidgetText w = new WidgetText(s, nicename);
		screen.append(w);
		this.append(s);
		return s;
	}
	
	///disgusting shitloads of overloads
	
	/**
	 * convenience boolean setting adder
	 */
	public SettingBoolean addSetting(Widget w2, String nicename, String backendname, boolean value)
	{
		SettingBoolean s = new SettingBoolean(backendname, value);
		WidgetBoolean w = new WidgetBoolean(s, nicename);
		w2.add(w);
		this.append(s);
		return s;
	}
	/**
	 * convenience boolean setting adder
	 */
	public SettingBoolean addSetting(Widget w2, String nicename, String backendname, boolean value, String truestring, String falsestring)
	{
		SettingBoolean s = new SettingBoolean(backendname, value);
		WidgetBoolean w = new WidgetBoolean(s, nicename, truestring, falsestring);
		w2.add(w);
		this.append(s);
		return s;
	}
	//float
	/**
	 * convenience float setting adder
	 */
	public SettingFloat addSetting(Widget w2, String nicename, String backendname, float value)
	{
		SettingFloat s = new SettingFloat(backendname, value);
		WidgetFloat w = new WidgetFloat(s, nicename);
		w2.add(w);
		this.append(s);
		return s;
	}
	/**
	 * convenience float setting adder
	 */
	public SettingFloat addSetting(Widget w2, String nicename, String backendname, float value, float min, float step, float max)
	{
		SettingFloat s = new SettingFloat(backendname, value, min, step, max);
		WidgetFloat w = new WidgetFloat(s, nicename);
		w2.add(w);
		this.append(s);
		return s;
	}
	//int
	/**
	 * convenience int setting adder
	 */
	public SettingInt addSetting(Widget w2, String nicename, String backendname, int value, int min, int max)
	{
		SettingInt s = new SettingInt(backendname, value, min, 1, max);
		WidgetInt w = new WidgetInt(s, nicename);
		w2.add(w);
		this.append(s);
		return s;
	}
	/**
	 * convenience int setting adder
	 */
	public SettingInt addSetting(Widget w2, String nicename, String backendname, int value, int min, int step, int max)
	{
		SettingInt s = new SettingInt(backendname, value, min, step, max);
		WidgetInt w = new WidgetInt(s, nicename);
		w2.add(w);
		this.append(s);
		return s;
	}
	
	//key
	/**
	 * convenience key setting adder
	 */
	public SettingKey addSetting(Widget w2, String nicename, String backendname, int value)
	{
		SettingKey s = new SettingKey(backendname, value);
		WidgetKeybinding w = new WidgetKeybinding(s, nicename);
		w2.add(w);
		this.append(s);
		return s;
	}
	
	//multi
	/**
	 * convenience multi setting adder
	 */
	public SettingMulti addSetting(Widget w2, String nicename, String backendname, int value, String... labels)
	{
		SettingMulti s = new SettingMulti(backendname, value, labels);
		WidgetMulti w = new WidgetMulti(s, nicename);
		w2.add(w);
		this.append(s);
		return s;
	}
	
	//text
	/**
	 * convenience text setting adder
	 */
	public SettingText addSetting(Widget w2, String nicename, String backendname, String value)
	{
		SettingText s = new SettingText(backendname, value);
		WidgetText w = new WidgetText(s, nicename);
		w2.add(w);
		this.append(s);
		return s;
	}
	
	/*public Widget getWidget()
	{
		//TODO: should add new settings to screen
		if (mainwidget == null)
		{
			Widget subwidget = new WidgetClassicTwocolumn();
			for (int i=0; i<Settings.size(); i++)
			{
				subwidget.add(Settings.get(i));
			}
			mainwidget = new WidgetClassicWindow(subwidget, modnicename);
		}
		return mainwidget;
	}* /
	
	/**
	 * getSetting - name version
	 * returns setting associated with name
	 * provides no error checking
	 * @param name setting name to retrieve
	 * @return requested setting
	 * /
	public Widget getSetting(String name){
		return getSetting(NameID.get(name));
	}
	/**
	 * getSetting - idx version
	 * @param id idx to retrieve
	 * @return setting at idx, or null if it's not a setting
	 * /
	public Widget getSetting(int id){
		if(Settings.get(id) instanceof Setting)
			return Settings.get(id);
		else
			return null;
		
	}
	
	

	
	public Widget genericAddSetting(String nicename, String backendname, Widget s)
	{
		//if(Settings.size() >= 8)
		//	return s;
		Settings.add(s);
		if(s instanceof Setting)
		{
			((Setting)s).nicename = nicename;
			((Setting)s).backendname = backendname; //backendname needs/has uniqueness constraint
			((Setting)s).parentmod = this;
			((Setting)s).update();
		}
		if (context == null)
			return defvalue;
		if(nicename != backendname)
		{
			NameID.put(backendname, Settings.indexOf(s));
			NameID.put(nicename, Settings.indexOf(s));
		}
		else
		{
			NameID.put(backendname, Settings.indexOf(s));//
		}
		return s;
	}
	
	/**
	 * Add a setting to setting list, single name version
	 * this must be called on a setting for it to function correctly.
	 * @param name Name to use for setting - must be unique, recommended no special characters (use seperate name version for that)
	 * @param s Setting object to register
	 * @return Setting object passed in
	 * @see Setting
	 * /
	public Widget addSetting(String name, Widget s)
	{
		return genericAddSetting(name, name, s);
	}
	public Setting addSetting(String name, Setting s)
	{
		return (Setting)genericAddSetting(name, name, s);
	}
	
	/* *
	 * Add a setting to setting list, seperate name version
	 * this must be called on a setting for it to function correctly.
	 * @param nicename Name used in GUI
	 * @param backendname Name used in config file - recommended no special characters, must be unique
	 * @param s Setting to register
	 * @return Setting passed in
	 * @see Setting
	 * /
	public Widget addSetting(String nicename, String backendname, Widget s)
	{
		return genericAddSetting(nicename, backendname, s);
	}
	public Setting addSetting(String nicename, String backendname, Setting s)
	{
		return (Setting)genericAddSetting(nicename, backendname, s);
	}*/
	
	public static void setContext(String name, String location)
	{
		if(name != null)
		{

			contextDatadirs.put(name, location);
			currentContext = name;
			if(!name.equals(""))
				ModSettings.loadAll(ModSettings.currentContext);
		}
		else
		{
			currentContext = "";
		}
	}
	/**
	 * add a setting to be saved.
	 * @param s setting to add - sets s.parent as well, don't add a setting to more than one modsettings
	 */
	public void append(Setting s)
	{
		Settings.add(s);
		s.parent=this;
	}
	/**
	 * removes a setting using ArrayList.remove
	 * @param s setting to remove
	 */
	public void remove(Setting s)
	{
		Settings.remove(s);
		s.parent = null;
	}
	/**
	 * 
	 * @return count of settings registered
	 */
	public int size()
	{
		return Settings.size();
	}
	/**
	 * reset all settings to default values. use as the handler for a modaction passed into a button handler.
	 */
	public void resetAll(String context)
	{
		for(int i=0;i<Settings.size();i++)
		{
			Settings.get(i).reset(context);
		}
	}
	public void resetAll()
	{
		resetAll(currentContext);
	}
	public void copyContextAll(String src, String dest)
	{
		
		for(int i=0;i<Settings.size();i++)
		{
			Settings.get(i).copyContext(src, dest);
		}
		
	}
	
	public static File getAppDir(String app)
	{
		return Minecraft.a(app);
	}
	
	/**
	 * called every time a setting is changed <br/>
	 * saves settings file to .minecraft/mods/$backendname/guiconfig.properties <br/>
	 * coming soon: set name of config file
	 */
	public void save(String context)
	{
		if (! have_loaded)
		{
			return;
		}
		try{
			  File path = getAppDir("minecraft/"+contextDatadirs.get(context)+"/"+backendname+"/");
			dbgout("saving context "+context+" (" + path.getAbsolutePath() +" ["+contextDatadirs.get(context)+"])");
			  if (! path.exists())
			  {
				  path.mkdirs();
			  }
			  File file = new File(path,"guiconfig.properties");
		      Properties p = new Properties();
		      for (int i = 0; i < Settings.size(); i++)
		      {
		    	  
	    		  Setting z = (Setting)Settings.get(i);
	    		  p.put(z.backendname, z.toString(context));
		      }
		      
		      
		      FileOutputStream out = new FileOutputStream(file);
		      p.store(out, "");
		      }
		    catch (Exception e) {
		      e.printStackTrace();
		      }
	}
	/**
	 * must be called after all settings are added for loading/saving to work. <br/>
	 * loads from .minecraft/mods/$backendname/guiconfig.properties if it exists. <br/>
	 * coming soon: set name of config file
	 */
	public void load(String context)
	{
		for(;;)
		{
			try{
				if(contextDatadirs.get(context) == null)
					break;
				File path = getAppDir("minecraft/"+contextDatadirs.get(context)+"/"+backendname+"/");
				  if (! path.exists()) break;
				  File file = new File(path,"guiconfig.properties");
				  if (!file.exists()) break;
			      Properties p = new Properties();
			      p.load(new FileInputStream(file));
			      for (int i = 0; i < Settings.size(); i++) //TODO: could be more effecient
			      {											//TODO: file version handling
			    	  if(Settings.get(i) instanceof Setting)
			    	  {
			    		  dbgout("setting load");
			    		  Setting z = (Setting)Settings.get(i);
			    		  if(p.containsKey(z.backendname))
			    		  {
			    			  dbgout("setting "+(String)p.get(z.backendname));
			    			  z.fromString((String)p.get(z.backendname), context);
			    		  }
			    	  }
			      }
			      break;
		      }
		    catch (Exception e) {
		      System.out.println(e);
		      break;
		      }
		}
	}
	public void load()
	{
		load("");
	    have_loaded = true;
	}
	public static void loadAll(String context)
	{
		for(int i=0; i<all.size(); i++)
		{
			all.get(i).load(context);
		}
	}
	
	public static final boolean debug = false;
	public static void dbgout(String s)
	{
		if(debug)
		System.out.println(s);
	}
	
}
