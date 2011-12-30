package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;

/**
 * This is a widget designed to render Minecraft objects. It has a MINIMUM size
 * of 16X16 by default. It also (Again, by default) renders a background and
 * border as per the theme for progressbars. This can be changed via setTheme,
 * see mod_GuiApiTWLExamples.SetUpColouringWindow comments for details.
 * 
 * @author Shawn
 * 
 */
public class WidgetItem2DRender extends Widget {

	private static RenderItem renderer = new RenderItem();

	private int renderID;

	private int scaleType = 0;

	/**
	 * This sets up the Widget to render no object (Air, specifically).
	 */
	public WidgetItem2DRender() {
		this(0);
	}

	/**
	 * This makes the widget render the Item that is in the slot dictated by
	 * renderID. Note, if that ID slot is empty it will render as if you pass 0.
	 * 
	 * @param renderID
	 */
	public WidgetItem2DRender(int renderID) {
		setMinSize(16, 16);
		setTheme("/progressbar");
		setRenderID(renderID);
	}

	/**
	 * This gets the current ID this Widget is supposed to render.
	 * 
	 * @return The current ID to render.
	 */
	public int getRenderID() {
		return renderID;
	}

	/**
	 * This returns an integer that specifies what kind of Scale type it is.
	 * 
	 * @return The scale type.
	 */
	public int getScaleType() {
		return scaleType;
	}

	@Override
	protected void paintWidget(GUI gui) {

		Minecraft minecraft = ModSettings.getMcinst();

		int x = getX();
		int y = getY();
		float scalex = 1f;
		float scaley = 1f;

		int maxWidth = getInnerWidth();
		int maxHeight = getInnerHeight();

		int scaleType = getScaleType();

		if ((scaleType == -1) && ((maxWidth < 16) || (maxHeight < 16))) {
			scaleType = 0;
		}

		switch (scaleType) {
		case 0: {
			// largest square
			int size = 0;
			if (maxWidth > maxHeight) {
				size = maxHeight;
			} else {
				size = maxWidth;
			}

			x += ((maxWidth - size) / 2);
			y += ((maxHeight - size) / 2);

			scalex = size / 16f;
			scaley = scalex;
			x /= scalex;
			y /= scaley;
			break;
		}

		case -1: {
			// default size in middle
			int size = maxWidth - 16;
			x += size / 2;

			size = maxHeight - 16;
			y += size / 2;
			break;
		}

		case 1: {
			// fill / stretch
			scalex = maxWidth / 16f;
			scaley = maxHeight / 16f;
			x /= scalex;
			y /= scaley;
			break;
		}

		default:
			throw new IndexOutOfBoundsException(
					"Scale Type is out of bounds! This should never happen!");
		}

		if ((minecraft == null) || (Item.itemsList[getRenderID()] == null)) {
			// draw black or something? Maybe NULL?
			return;
		}

		GL11.glPushMatrix();
		GL11.glDisable(3042 /* GL_BLEND *//* GL_BLEND */);
		GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT *//* GL_RESCALE_NORMAL_EXT */);
		RenderHelper.enableStandardItemLighting();
		GL11.glScalef(scalex, scaley, 1);
		ItemStack stack = new ItemStack(getRenderID(), 1, 0);
		WidgetItem2DRender.renderer.renderItemIntoGUI(minecraft.fontRenderer,
				minecraft.renderEngine, stack, x, y);
		WidgetItem2DRender.renderer.renderItemOverlayIntoGUI(
				minecraft.fontRenderer, minecraft.renderEngine, stack, x, y);

		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT *//* GL_RESCALE_NORMAL_EXT */);
		GL11.glPopMatrix();
	}

	/**
	 * This sets the current ID to render. This checks bounds.
	 * 
	 * @param renderID
	 *            The ID you want this widget to render.
	 */
	public void setRenderID(int renderID) {
		if ((renderID >= Item.itemsList.length) || (renderID < 0)) {
			throw new IndexOutOfBoundsException(
					String.format(
							"Render ID must be within the possible bounds of an Item ID! (%s - %s)",
							0, Item.itemsList.length - 1));
		}
		this.renderID = renderID;
	}

	/**
	 * This sets what kind of scaling to use for this widget. Possible types
	 * are:
	 * 
	 * -1: This doesn't scale it at all. It will be rendered right in the
	 * middle, at 16x16 size if possible. If it needs to be smaller, it will
	 * scale.
	 * 
	 * 0: This is the default mode. It scales to the biggest square it can, at
	 * stays in the middle.
	 * 
	 * 1: This scales to fill all the space, whether or not that space is
	 * square.
	 * 
	 * @param scaleType
	 */
	public void setScaleType(int scaleType) {
		if (scaleType > 1) {
			scaleType = 1;
		}
		if (scaleType < -1) {
			scaleType = -1;
		}
		this.scaleType = scaleType;
	}
}
