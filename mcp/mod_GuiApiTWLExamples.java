package net.minecraft.src;

import java.util.Random;

import net.minecraft.src.GuiApiFontHelper.FontStates;
import de.matthiasmann.twl.ColorSelector;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ListBox;
import de.matthiasmann.twl.ProgressBar;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.ColorSpaceHSL;

public class mod_GuiApiTWLExamples extends BaseMod {

	@SuppressWarnings("unused")
	private static void addRandomListboxOption(SettingList setting) {
		Random rand = new Random();
		setting.get().add("Option " + rand.nextInt(10000));
		setting.displayWidget.update();
	}

	@SuppressWarnings("unused")
	private static void removeSelectedListboxOption(SettingList setting) {

		ListBox<String> listbox = ((WidgetList) setting.displayWidget).listBox;
		int selected = listbox.getSelected();
		if (selected == -1) {
			return;
		}
		setting.get().remove(selected);
		setting.displayWidget.update();

		if (selected == listbox.getNumEntries()) // I'm only removing one at a
													// time, so this is OK.
		{
			selected--;
		}
		if (selected == -1) {
			return; // This is if there aren't any entries to select left. I
					// could also check getNumEntries to see if it's 0.
		}

		listbox.setSelected(selected);
	}

	@SuppressWarnings("unused")
	private static void showSelectedListboxOption(SettingList setting) {
		GuiModScreen.show(GuiApiHelper.makeTextDisplayAndGoBack(
				"ListBox Status", setting.displayWidget.userString(),
				"Go Back", false));
	}

	private WidgetText colorEditField;
	private GuiApiFontHelper colorFontHelper;
	private Label colorLabel;
	private ProgressBar colorProgressBar;
	private ColorSelector colorSelector;
	private SettingList listBoxSettingTest;
	private ModSettingScreen modScreen;

	private ModSettings modSettings;

	private WidgetSimplewindow screenColoringWindow;

	private WidgetSimplewindow screenListBoxTest;

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void load() {
		modSettings = new ModSettings("mod_GuiApiTWLExamples");
		SetUpColouringWindow();

		SetUpListBox();

		SetUpModWindow();
	}

	private void SetUpColouringWindow() {
		colorFontHelper = new GuiApiFontHelper();
		WidgetSinglecolumn widgetSingleColumn = new WidgetSinglecolumn();
		widgetSingleColumn.childDefaultWidth = 300;

		colorLabel = new Label("This is an example of coloring a label's Text.");
		widgetSingleColumn.add(colorLabel);
		colorFontHelper.setFont(colorLabel);
		colorProgressBar = new ProgressBar();
		colorProgressBar.setValue(0.7f);
		colorProgressBar.setTheme("/progressbar");
		// This sets the theme manually. I'm not currently sure why but unless
		// you set this it won't actually render the progress bar part, only the
		// text. You can use this to change it somewhat though. The available
		// themes for this are:
		// /progressbar - The standard.
		// /progressbar-white - Changes the progress image to a plain white
		// area.
		// /progressbar-noback - Removes the background image.
		// /progressbar-white-noback - Changes the progress image to a plain
		// white area and removes the background image.
		colorProgressBar.setText("Coloring Progressbar!");
		widgetSingleColumn.add(colorProgressBar);
		colorFontHelper.setFont(colorProgressBar);

		widgetSingleColumn.heightOverrideExceptions.put(colorProgressBar, 30);
		colorEditField = new WidgetText(
				new SettingText("dummyText", "Edit Me!"), null);
		widgetSingleColumn.add(colorEditField);
		colorFontHelper.setFont(colorEditField);
		widgetSingleColumn.heightOverrideExceptions.put(colorEditField, 30);

		colorSelector = new ColorSelector(new ColorSpaceHSL());
		colorSelector.setShowAlphaAdjuster(false);
		colorSelector.setShowNativeAdjuster(true);
		colorSelector.setShowRGBAdjuster(true);
		colorSelector.setShowPreview(true);
		colorSelector.addCallback(new ModAction(this, "updateColors",
				"Updates the colors for the 'color' window."));
		colorSelector.setColor(colorFontHelper.getColor(FontStates.normal));
		widgetSingleColumn.add(colorSelector);
		widgetSingleColumn.heightOverrideExceptions.put(colorSelector, 0);

		screenColoringWindow = new WidgetSimplewindow(widgetSingleColumn,
				"GuiAPI / TWL Coloring examples!");
	}

	
	private void SetUpListBox() {
		WidgetSinglecolumn widgetSingleColumn = new WidgetSinglecolumn();
		listBoxSettingTest = modSettings.addSetting(widgetSingleColumn,
				"ListBox Test One", "listboxTest1", "Option 1", "Option 2",
				"Option 3", "Option 4", "Option 5", "Option 6");
		((WidgetList) listBoxSettingTest.displayWidget).listBox
				.setTheme("/listbox");
		widgetSingleColumn.heightOverrideExceptions.put(
				listBoxSettingTest.displayWidget, 140);

		WidgetSingleRow listBoxRow = new WidgetSingleRow(110, 20);

		listBoxRow
				.add(GuiApiHelper.makeButton("Add Random", new ModAction(this,
						"addRandomListboxOption", SettingList.class)
						.setDefaultArguments(listBoxSettingTest), true));

		listBoxRow.add(GuiApiHelper.makeButton("Display Selected",
				new ModAction(this, "showSelectedListboxOption",
						SettingList.class)
						.setDefaultArguments(listBoxSettingTest), true));

		listBoxRow.add(GuiApiHelper.makeButton("Remove Selected",
				new ModAction(this, "removeSelectedListboxOption",
						SettingList.class)
						.setDefaultArguments(listBoxSettingTest), true));

		widgetSingleColumn.add(listBoxRow);
		widgetSingleColumn.widthOverrideExceptions.put(listBoxRow, 0);
		screenListBoxTest = new WidgetSimplewindow(widgetSingleColumn,
				"GuiAPI / TWL ListBox example!");
	}

	private void SetUpModWindow() {
		modSettings.load();
		modScreen = new ModSettingScreen("GuiAPI TWL Examples");
		if (screenColoringWindow != null) {
			modScreen.append(GuiApiHelper.makeButton("Open Coloring Examples",
					"show", GuiModScreen.class, true,
					new Class[] { Widget.class }, screenColoringWindow));
		}

		if (screenListBoxTest != null) {
			modScreen.append(GuiApiHelper.makeButton("Open Listbox Example",
					"show", GuiModScreen.class, true,
					new Class[] { Widget.class }, screenListBoxTest));
		}
	}

	@SuppressWarnings("unused")
	private void updateColors() {
		colorFontHelper.setColor(FontStates.normal, colorSelector.getColor());
	}

}
