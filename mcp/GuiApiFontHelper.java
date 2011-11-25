package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.matthiasmann.twl.Color;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.TextWidget;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.AnimationState.StateKey;
import de.matthiasmann.twl.renderer.FontParameter;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLFont;
import de.matthiasmann.twl.utils.StateExpression;

/**
 * This class is designed to enable you to make clones of the GuiAPI font,
 * colour it and add options as you want, and then set that font to specific
 * kinds of widgets.
 * 
 * @author ShaRose
 * 
 */
public class GuiApiFontHelper {
	private class FontStateHelper {
		private Color color;
		private StateExpression condition;
		private Object fontState;
		private int offsetX;
		private int offsetY;
		private int style;
		private int underlineOffset;

		public FontStateHelper(Object font) throws Throwable {
			fontState = font;
			condition = (StateExpression) GuiApiFontHelper.fontStateCondition
					.get(fontState);
			color = ((Color) GuiApiFontHelper.fontStateColor.get(fontState));
			offsetX = GuiApiFontHelper.fontStateOffsetX.getInt(fontState);
			underlineOffset = GuiApiFontHelper.fontStateUnderlineOffset
					.getInt(fontState);
			offsetY = GuiApiFontHelper.fontStateOffsetY.getInt(fontState);
			style = GuiApiFontHelper.fontStateStyle.getInt(fontState);
		}

		public Color getColor() {
			return color;
		}

		public StateExpression getCondition() {
			return condition;
		}

		public boolean getLineThrough() {
			return (style & 2) == 2;
		}

		public int getOffsetX() {
			return offsetX;
		}

		public int getOffsetY() {
			return offsetY;
		}

		public boolean getUnderline() {
			return (style & 1) == 1;
		}

		public int getUnderlineOffset() {
			return underlineOffset;
		}

		public void setColor(Color col) {
			color = col;
			SyncWithState();
		}

		public void setInternalReference(Object ref) {
			fontState = ref;
		}

		public void setLineThrough(boolean val) {
			if (getLineThrough() != val) {
				style ^= 2;
				SyncWithState();
			}
		}

		public void setOffsetX(int i) {
			offsetX = i;
			SyncWithState();
		}

		public void setOffsetY(int i) {
			offsetY = i;
			SyncWithState();
		}

		public void setUnderline(boolean val) {
			if (getUnderline() != val) {
				style ^= 1;
				SyncWithState();
			}
		}

		public void setUnderlineOffset(int i) {
			underlineOffset = i;
			SyncWithState();
		}

		public void SyncWithState() {
			try {
				GuiApiFontHelper.fontStateColor.set(fontState, color);
				GuiApiFontHelper.fontStateCondition.set(fontState, condition);
				GuiApiFontHelper.fontStateOffsetX.set(fontState, offsetX);
				GuiApiFontHelper.fontStateOffsetY.set(fontState, offsetY);
				GuiApiFontHelper.fontStateUnderlineOffset.set(fontState,
						underlineOffset);
				GuiApiFontHelper.fontStateStyle.set(fontState, style);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * These are the font states you can use for your settings. Most of the
	 * time, the only ones you will use would be normal and hover.
	 * 
	 * @author ShaRose
	 * 
	 */
	enum FontStates {
		disabled, error, hover, normal, textSelection, warning
	}

	private static Map<Widget, GuiApiFontHelper> customFontWidgets;
	private static Field editFieldTextRenderer;

	static Field fontStateColor;
	static Field fontStateCondition;
	static Field fontStateOffsetX;
	static Field fontStateOffsetY;
	static Field fontStateStyle;
	static Field fontStateUnderlineOffset;
	private static Field getState;

	private static Field lwjglFontStates;
	static {
		GuiApiFontHelper.customFontWidgets = new HashMap<Widget, GuiApiFontHelper>();
		try {
			GuiApiFontHelper.editFieldTextRenderer = EditField.class
					.getDeclaredField("textRenderer");
			GuiApiFontHelper.editFieldTextRenderer.setAccessible(true);

			GuiApiFontHelper.lwjglFontStates = LWJGLFont.class
					.getDeclaredField("fontStates");
			GuiApiFontHelper.lwjglFontStates.setAccessible(true);

			Class fontState = LWJGLFont.class.getDeclaredClasses()[0];

			GuiApiFontHelper.fontStateCondition = fontState
					.getDeclaredField("condition");
			GuiApiFontHelper.fontStateCondition.setAccessible(true);

			GuiApiFontHelper.fontStateColor = fontState
					.getDeclaredField("color");
			GuiApiFontHelper.fontStateColor.setAccessible(true);

			GuiApiFontHelper.fontStateOffsetX = fontState
					.getDeclaredField("offsetX");
			GuiApiFontHelper.fontStateOffsetX.setAccessible(true);

			GuiApiFontHelper.fontStateOffsetY = fontState
					.getDeclaredField("offsetY");
			GuiApiFontHelper.fontStateOffsetY.setAccessible(true);

			GuiApiFontHelper.fontStateStyle = fontState
					.getDeclaredField("style");
			GuiApiFontHelper.fontStateStyle.setAccessible(true);

			GuiApiFontHelper.fontStateUnderlineOffset = fontState
					.getDeclaredField("underlineOffset");
			GuiApiFontHelper.fontStateUnderlineOffset.setAccessible(true);

			Class[] stateClasses = StateExpression.class.getDeclaredClasses();

			GuiApiFontHelper.getState = stateClasses[1]
					.getDeclaredField("state");
			GuiApiFontHelper.getState.setAccessible(true);

		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method is used internally to resync the font references. This has to
	 * be used each time the theme is applied (After you set the screen,
	 * specifically), otherwise TWL will automatically replace it with the
	 * default font.
	 */
	public static void resyncCustomFonts() {
		for (Map.Entry<Widget, GuiApiFontHelper> entry : GuiApiFontHelper.customFontWidgets
				.entrySet()) {
			// probably going to want to optimize this I think
			GuiApiFontHelper font = entry.getValue();
			Widget widget = entry.getKey();
			if (widget instanceof TextWidget) {
				font.setFont((TextWidget) widget);
			}
			if (widget instanceof EditField) {
				font.setFont((EditField) widget);
			}
			if (widget instanceof WidgetText) {
				font.setFont((WidgetText) widget);
			}
		}
	}

	LWJGLFont myFont;

	private Map<FontStates, FontStateHelper> states;

	/**
	 * This creates a new GuiApiFontHelper with it's own internal font
	 * reference.
	 */
	public GuiApiFontHelper() {
		states = new HashMap<GuiApiFontHelper.FontStates, GuiApiFontHelper.FontStateHelper>();
		try {
			GuiWidgetScreen widgetScreen = GuiWidgetScreen.getInstance();
			LWJGLFont baseFont = (LWJGLFont) widgetScreen.theme
					.getDefaultFont();

			Object[] fontStateObjects = (Object[]) GuiApiFontHelper.lwjglFontStates
					.get(baseFont);

			FontStateHelper[] fontStates = new FontStateHelper[fontStateObjects.length];

			for (int i = 0; i < fontStates.length; i++) {
				fontStates[i] = new FontStateHelper(fontStateObjects[i]);
				StateExpression exp = fontStates[i].getCondition();
				if (exp == null) {
					states.put(FontStates.normal, fontStates[i]);
				} else {
					StateKey key = (StateKey) GuiApiFontHelper.getState
							.get(exp);
					String name = key.getName();
					states.put(FontStates.valueOf(name), fontStates[i]);
				}
			}

			HashMap<String, String> defaultParams = new HashMap<String, String>();

			int defStateNum = -1;
			FontStateHelper defHelper = states.get(FontStates.normal);
			for (int i = 0; i < fontStates.length; i++) {
				if (fontStates[i] == defHelper) {
					defStateNum = i;

					defaultParams.put("color", defHelper.getColor().toString());
					defaultParams.put("offsetX",
							Integer.toString(defHelper.getOffsetX()));
					defaultParams.put("offsetY",
							Integer.toString(defHelper.getOffsetY()));
					defaultParams.put("underlineOffset",
							Integer.toString(defHelper.getUnderlineOffset()));
					defaultParams.put("underline",
							Boolean.toString(defHelper.getUnderline()));
					defaultParams.put("linethrough",
							Boolean.toString(defHelper.getLineThrough()));
					break;
				}
			}

			defaultParams.put("filename", "font.fnt");

			Collection<FontParameter> conditionalParameters = new ArrayList<FontParameter>();

			for (int i = 0; i < fontStates.length; i++) {
				if (i == defStateNum) {
					continue;
				}
				FontStateHelper fontHelper = fontStates[i];

				Map<String, String> differences = new HashMap<String, String>();

				if (fontHelper.getColor() != defHelper.getColor()) {
					differences.put("color", fontHelper.getColor().toString());
				}

				if (fontHelper.getOffsetX() != defHelper.getOffsetX()) {
					differences.put("offsetX",
							Integer.toString(fontHelper.getOffsetX()));
				}

				if (fontHelper.getOffsetY() != defHelper.getOffsetY()) {
					differences.put("offsetY",
							Integer.toString(fontHelper.getOffsetY()));
				}

				if (fontHelper.getUnderlineOffset() != defHelper
						.getUnderlineOffset()) {
					differences.put("underlineOffset",
							Integer.toString(fontHelper.getUnderlineOffset()));
				}

				if (fontHelper.getUnderline() != defHelper.getUnderline()) {
					differences.put("underline",
							Boolean.toString(fontHelper.getUnderline()));
				}

				if (fontHelper.getLineThrough() != defHelper.getLineThrough()) {
					differences.put("linethrough",
							Boolean.toString(fontHelper.getLineThrough()));
				}

				conditionalParameters.add(new FontParameter(fontHelper
						.getCondition(), differences));
			}

			myFont = (LWJGLFont) widgetScreen.renderer.loadFont(
					GuiWidgetScreen.themeURL, defaultParams,
					conditionalParameters);

			fontStateObjects = (Object[]) GuiApiFontHelper.lwjglFontStates
					.get(myFont);

			for (int i = 0; i < fontStateObjects.length; i++) {
				FontStateHelper updatedReference = new FontStateHelper(
						fontStateObjects[i]);
				StateExpression exp = updatedReference.getCondition();
				if (exp == null) {
					states.get(FontStates.normal).setInternalReference(
							fontStateObjects[i]);
				} else {
					FontStates state = FontStates
							.valueOf(((StateKey) GuiApiFontHelper.getState
									.get(exp)).getName());
					states.get(state).setInternalReference(fontStateObjects[i]);
				}
			}

		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param state
	 *            The font state you want to check.
	 * @return The Color for this font according to the specified state.
	 */
	public Color getColor(FontStates state) {
		if (states.containsKey(state)) {
			return states.get(state).getColor();
		}
		return null;
	}

	/**
	 * @param state
	 *            The font state you want to check.
	 * @return The LineThrough for this font according to the specified state.
	 */
	public boolean getLineThrough(FontStates state) {
		if (states.containsKey(state)) {
			return states.get(state).getLineThrough();
		}
		return false;
	}

	/**
	 * @param state
	 *            The font state you want to check.
	 * @return The OffsetX for this font according to the specified state.
	 */
	public int getOffsetX(FontStates state) {
		if (states.containsKey(state)) {
			return states.get(state).getOffsetX();
		}
		return 0;
	}

	/**
	 * @param state
	 *            The font state you want to check.
	 * @return The OffsetY for this font according to the specified state.
	 */
	public int getOffsetY(FontStates state) {
		if (states.containsKey(state)) {
			return states.get(state).getOffsetY();
		}
		return 0;
	}

	/**
	 * @param state
	 *            The font state you want to check.
	 * @return The Underline for this font according to the specified state.
	 */
	public boolean getUnderline(FontStates state) {
		if (states.containsKey(state)) {
			return states.get(state).getUnderline();
		}
		return false;
	}

	/**
	 * @param state
	 *            The font state you want to check.
	 * @return The UnderlineOffset for this font according to the specified
	 *         state.
	 */
	public int getUnderlineOffset(FontStates state) {
		if (states.containsKey(state)) {
			return states.get(state).getUnderlineOffset();
		}
		return 0;
	}

	/**
	 * @param state
	 *            The font state you want to set.
	 * @param col
	 *            The Color you wish to this fontstate to have for this font.
	 */
	public void setColor(FontStates state, Color col) {
		if (states.containsKey(state)) {
			states.get(state).setColor(col);
		}
	}

	/**
	 * @param widget
	 *            The EditField (Or derived class) you wish to set.
	 */
	public void setFont(EditField widget) {
		try {
			setFont((TextWidget) GuiApiFontHelper.editFieldTextRenderer
					.get(widget));
			GuiApiFontHelper.customFontWidgets.put(widget, this);
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param widget
	 *            The TextWidget (Or derived class) you wish to set.
	 */
	public void setFont(TextWidget widget) {
		widget.setFont(myFont);
		GuiApiFontHelper.customFontWidgets.put(widget, this);
	}

	/**
	 * @param widget
	 *            The WidgetText (Or derived class) you wish to set. This will
	 *            set the display label (if it has one) and the edit field.
	 */
	public void setFont(WidgetText widget) {
		if (widget.displayLabel != null) {
			widget.displayLabel.setFont(myFont);
			GuiApiFontHelper.customFontWidgets.put(widget, this);
		}
		setFont(widget.editField);
		GuiApiFontHelper.customFontWidgets.put(widget, this);
	}

	/**
	 * @param state
	 *            The font state you want to set.
	 * @param val
	 *            The LineThrough you wish to this fontstate to have for this
	 *            font.
	 */
	public void setLineThrough(FontStates state, boolean val) {
		if (states.containsKey(state)) {
			states.get(state).setLineThrough(val);
		}
	}

	/**
	 * @param state
	 *            The font state you want to set.
	 * @param i
	 *            The OffsetX you wish to this fontstate to have for this font.
	 */
	public void setOffsetX(FontStates state, int i) {
		if (states.containsKey(state)) {
			states.get(state).setOffsetX(i);
		}
	}

	/**
	 * @param state
	 *            The font state you want to set.
	 * @param i
	 *            The OffsetY you wish to this fontstate to have for this font.
	 */
	public void setOffsetY(FontStates state, int i) {
		if (states.containsKey(state)) {
			states.get(state).setOffsetY(i);
		}
	}

	/**
	 * @param state
	 *            The font state you want to set.
	 * @param val
	 *            The Underline you wish to this fontstate to have for this
	 *            font.
	 */
	public void setUnderline(FontStates state, boolean val) {
		if (states.containsKey(state)) {
			states.get(state).setUnderline(val);
		}
	}

	/**
	 * @param state
	 *            The font state you want to set.
	 * @param i
	 *            The UnderlineOffset you wish to this fontstate to have for
	 *            this font.
	 */
	public void setUnderlineOffset(FontStates state, int i) {
		if (states.containsKey(state)) {
			states.get(state).setUnderlineOffset(i);
		}
	}

}
