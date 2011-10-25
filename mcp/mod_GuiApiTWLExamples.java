package net.minecraft.src;

import java.util.Random;

import net.minecraft.src.GuiApiFontHelper.FontStates;
import de.matthiasmann.twl.ColorSelector;
import de.matthiasmann.twl.Label;
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
		int selected = ((WidgetList) setting.displayWidget).listBox
				.getSelected();
		setting.get().remove(selected);
		setting.displayWidget.update();
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

	public mod_GuiApiTWLExamples() {
		modSettings = new ModSettings("mod_GuiApiTWLExamples");
		SetUpColouringWindow();

		SetUpListBox();

		SetUpModWindow();
	}

	private void SetUpColouringWindow() {
		colorFontHelper = new GuiApiFontHelper();
		WidgetSinglecolumn widgetSingleColumn = new WidgetSinglecolumn();
		widgetSingleColumn.childWidth = 300;

		colorLabel = new Label("This is an example of coloring a label's Test.");
		widgetSingleColumn.add(colorLabel);
		colorFontHelper.setFont(colorLabel);

		colorProgressBar = new ProgressBar();
		colorProgressBar.setValue(0.7f);
		// colorProgressBar.setBackground(GuiWidgetScreen.instance.theme.getImage("progress"));
		// colorProgressBar.setProgressImage((GuiWidgetScreen.instance.theme.getImage("progress-white")).createTintedVersion(Color.GREEN));
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

	@Override
	public String Version() {
		return "1.0";
	}

}
