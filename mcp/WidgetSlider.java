package net.minecraft.src;

import de.matthiasmann.twl.ValueAdjusterFloat;
import de.matthiasmann.twl.model.FloatModel;

/**
 * This is a simple extension of ValueAdjusterFloat so that it always updates
 * the setting. Used internally.
 * 
 * @author lahwran
 */
public class WidgetSlider extends ValueAdjusterFloat {
	/**
	 * This is the basic constructor. It just calls the ValueAdjusterFloat
	 * constructor.
	 * 
	 * @param f
	 *            The FloatModel to use.
	 */
	public WidgetSlider(FloatModel f) {
		super(f);
	}

	@Override
	public void startEdit() {
		cancelEdit();
	}
}
