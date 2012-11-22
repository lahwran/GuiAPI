package net.minecraft.src;

/**
 * This is a small example for how to make a user dialog. This part is just
 * creating the item. Please view {@link ItemGuiApiExample} for the real code.
 * 
 * @author ShaRose
 */
public class mod_GuiApiIngameMessageExample extends BaseMod {

	/**
	 * A reference to the Example item.
	 */
	Item exampleItem;

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void load() {
		exampleItem = new ItemGuiApiExample(28000).setItemName(
				"GuiApiExampleItem").setIconCoord(10, 2);
		// Create the example item, set the Name, and give it the sign's icon.
		ModLoader.addName(exampleItem, "GuiAPI Example Item");
		// Give it a 'real' name.
		ModLoader.addShapelessRecipe(new ItemStack(exampleItem), new Object[] {
				new ItemStack(Item.sign), new ItemStack(Item.paper) });
		// Just a shapeless recipe. Normal sign and some paper.
	}

}
