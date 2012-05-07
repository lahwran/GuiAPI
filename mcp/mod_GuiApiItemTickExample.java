package net.minecraft.src;

/**
 * This is an example specifically focused to some usage of {@link WidgetTick}
 * and {@link WidgetItem2DRender}. We'll be making a {@link ModSettingScreen} that lets
 * you select an Item ID and a damage value to display, as well as making it so
 * that if the selected Item ID is a SubItem, if a toggle is on it will
 * automatically cycle it once a second.
 * 
 * @author ShaRose
 */
public class mod_GuiApiItemTickExample extends BaseMod {

	/** The mod screen. */
	public ModSettingScreen myModScreen;

	/** The settings. */
	public ModSettings mySettings;

	/** The setting boolean Auto Cycle SubItems. */
	public SettingBoolean settingBooleanAutoCycleSubItems;

	/** The setting int for Item Damage. */
	public SettingInt settingIntItemDamage;

	/** The setting int Item ID. */
	public SettingInt settingIntItemID;

	/** The {@link WidgetItem2DRender} this uses to render Item Icons. */
	public WidgetItem2DRender widgetRenderer;

	/**
	 * This {@link WidgetTick} is used to call {@link #onTick()} every 1000ms (1
	 * second).
	 */
	public WidgetTick widgetTicker;

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void load() {
		// Create the ModSettings, ModSettingScreen, and set the
		// ModSettingScreen to use a single column, like in the intermediate
		// examples.
		mySettings = new ModSettings("mod_GuiApiItemTickExample");
		myModScreen = new ModSettingScreen("GuiAPI Item / Tick Example");
		myModScreen.setSingleColumn(true);

		// Create a ModAction for the onUpdate() method.
		ModAction onUpdateAction = new ModAction(this, "onUpdate");

		// Create a SettingInt with a range between 1 and the max Item id, and
		// then add it to the ModSettingScreen using a ModSettings helper.
		settingIntItemID = mySettings.addSetting(myModScreen,
				"Selected Item ID", "settingIntItemID", 1, 1,
				Item.itemsList.length - 1);

		// Add a callback so whenever it's changed, it calls that onUpdate
		// ModAction from earlier.
		settingIntItemID.displayWidget.addCallback(onUpdateAction);

		// Create a SettingInt with the full range of a Short (Damage is saved
		// as a Short for ItemStacks), and then add it to the ModSettingScreen
		// using a ModSettings helper.
		settingIntItemDamage = mySettings.addSetting(myModScreen,
				"SubItem / Damage", "settingIntItemDamage", 0, Short.MIN_VALUE,
				Short.MAX_VALUE);

		// Add a callback so whenever it's changed, it calls that onUpdate
		// ModAction from earlier.
		settingIntItemDamage.displayWidget.addCallback(onUpdateAction);

		// Create a SettingBoolean to enable / disable automated SubItem
		// cycling, and then add it to the ModSettingScreen using a ModSettings
		// helper.
		settingBooleanAutoCycleSubItems = mySettings.addSetting(myModScreen,
				"Automatically cycle SubItems",
				"settingBooleanAutoCycleSubItems", true, "Yes", "No");

		// Make a new WidgetItem2DRender.
		widgetRenderer = new WidgetItem2DRender(new ItemStack(1, 1, 0));

		// Add it to the ModSettingScreen.
		myModScreen.append(widgetRenderer);

		// Now create a new WidgetTick.
		widgetTicker = new WidgetTick();

		// Add it to myModScreen.theWidget. It will be a WidgetSimplewindow by
		// default, so you can do the same for any SubScreens you use.
		// Preferably do NOT add this to any layout columns like
		// WidgetClassicTwocolumn or WidgetSinglecolumn, as it will draw a space
		// for the WidgetTick. We don't want it drawn, so we'll add it to a
		// widget that won't add it to the layout.
		myModScreen.theWidget.add(widgetTicker);

		// Create a ModAction for onTick, and then add it to the WidgetTick we
		// created so it will call it every 1000 milliseconds, or 1 second. For
		// example, to make it 'tick' twice a second, the second argument would
		// be 500. If you wanted it to call every frame, make it 0.
		widgetTicker.addCallback(new ModAction(this, "onTick"), 1000);

		// Load any saved settings, if any. This will call the onUpdate method
		// automatically.
		mySettings.load();
	}

	/**
	 * This method is called every 1000ms (1 second) by {@link #widgetTicker}.
	 */
	@SuppressWarnings("unused")
	private void onTick() {
		// Are we supposed to cycle SubItems in the first place? If not, return.
		if (!settingBooleanAutoCycleSubItems.get()) {
			return;
		}

		// Get the current stack.
		ItemStack stack = new ItemStack(settingIntItemID.get(), 1,
				settingIntItemDamage.get());

		// Is the Item null, or is it NOT a subtype? If so, return.
		if ((stack.getItem() == null) || !stack.getHasSubtypes()) {
			return;
		}

		// Get the damage.
		int value = stack.getItemDamage();

		if ((value >= 31) || (value < 0)) {
			value = 0; // If greater than or equal to 31 or lower than 0, make
						// it 0.
		} else {
			value++; // Else, increase.
		}

		// And set settingIntItemDamage's value. This will also call onUpdate()
		// for us, since it will call settingIntItemDamage's callbacks.
		settingIntItemDamage.set(value);
	}

	/**
	 * This is called whenever {@link #settingIntItemID} or
	 * {@link #settingIntItemDamage} change. It sets max / min bounds depending
	 * on if it's a subitem or not, and updates {@link #widgetRenderer} so it
	 * renders with the new Item ID and Damage value.
	 */
	@SuppressWarnings("unused")
	private void onUpdate() {
		// Get the current stack.
		ItemStack stack = new ItemStack(settingIntItemID.get(), 1,
				settingIntItemDamage.get());
		// Is the current item not null, AND have the SubTypes flag set?
		if ((stack.getItem() != null) && stack.getHasSubtypes()) {
			// If yes, set the range as 0 - 31. Normally SubItems only use 0 -
			// 15, to keep compatibility with blocks, and as well there are a
			// few ways of trying to find the max SubItem index for any given
			// Item, but they don't always work, so we are hardcoding it for
			// this demo's purposes.
			settingIntItemDamage.maximumValue = 31;
			settingIntItemDamage.minimumValue = 0;
			// Make sure to update the Display Widget so it reflects the correct
			// range. Note, this WILL cap the value if needed.
			settingIntItemDamage.displayWidget.update();
		} else {
			// If not, set the range as the same as a Short. This is what
			// ItemStack saves damage as.
			settingIntItemDamage.maximumValue = Short.MAX_VALUE;
			settingIntItemDamage.minimumValue = Short.MIN_VALUE;
			// Make sure to update the Display Widget so it reflects the correct
			// range. Note, this WILL cap the value if needed.
			settingIntItemDamage.displayWidget.update();
		}
		// Finally, set the Render Stack. Note, that if the Item is null, the
		// WidgetItem2DRender will just not render anything, which is what we
		// want.
		widgetRenderer.setRenderStack(stack);
	}

}
