package net.minecraft.src;

import de.matthiasmann.twl.Widget;

/**
 * This is a small example for displaying widgets in game. When you use this
 * item, it simply shows a text display with a back button.
 * 
 * @author ShaRose
 */
public class ItemGuiApiExample extends Item {

	/**
	 * Default constructor for Items.
	 * 
	 * @param itemID
	 */
	protected ItemGuiApiExample(int itemID) {
		super(itemID);
	}

	@Override
	public int getColorFromDamage(int par1, int par2) {
		// This simply tints the sign black to differentiate it. This way I
		// didn't have to use a sprite. (This isn't a part of GuiAPI)
		return 986895;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		Widget textDisplay = GuiApiHelper
				.makeTextDisplayAndGoBack(
						"An example message.",
						"This is a message that should be displayed when you right click with the test item selected.",
						"Go back to the game.", false);
		// I'm going to be creating a simple text display widget. To keep things
		// small, I'll be using an existing feature from GuiApiHelper:
		// makeTextDisplayAndGoBack. It makes a window with a text bar on top, a
		// message (that will size itself, and if needed will use scrollbars),
		// and a button to go back to whatever was open previously. It should be
		// easy to see what's what for this method.
		GuiModScreen.show(textDisplay);
		// Finally, 'show' your new widget.
		return par1ItemStack;
		// Return the ItemStack without any changes (we aren't using it up).
	}
}
