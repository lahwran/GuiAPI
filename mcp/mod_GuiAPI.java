package net.minecraft.src;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class mod_GuiAPI extends BaseMod {

	boolean cacheCheck = false;
	
	@Override
	public String getVersion() {
		return "0.15.1 for 1.4.5";
	}

	@Override
	public void load() {
		ModLoader.setInGUIHook(this, true, false);
	}

	@Override
	public boolean onTickInGUI(float tick, Minecraft game, GuiScreen gui)
    {
		if(gui instanceof GuiOptions)
		{
			if(cacheCheck)
			{
				// Cached so we don't have to check this every frame
				return true;
			}
			// First get a list of buttons
			ArrayList<GuiButton> buttonsPreSorted = new ArrayList<GuiButton>();
			for (Object guiButton : gui.controlList) {
				if(!(guiButton instanceof GuiButton))
					continue;
				if(guiButton instanceof GuiSlider)
					continue;
				if(guiButton instanceof GuiSmallButton)
					continue;
				buttonsPreSorted.add((GuiButton)guiButton);
			}
			// Now sort by position. First, get the buttons with IDs of 101 and 100: 101 is left, 100 is right.
			// Any button that doesn't have a position that doesn't fit into those 'columns' is ignored.
			// Likewise with buttons that are too high, so we'll be storing the min y position too.
			int leftPos = -1; // Video settings, aka 101
			int rightPos = -1; // Controls, aka 100
			int minY = -1;
			for (GuiButton guiButton : buttonsPreSorted) {
				if(guiButton.id == 101)
				{
					leftPos = guiButton.xPosition;
					minY = guiButton.yPosition;
				}
				if(guiButton.id == 100)
				{
					rightPos = guiButton.xPosition;
					minY = guiButton.yPosition;
				}
			}
			
			// Now make an array for it. Adding two as a 'just in case' thing: it should have room anyways.
			
			GuiButton[] sortedArray = new GuiButton[buttonsPreSorted.size() + 2];
			
			for (GuiButton guiButton : buttonsPreSorted) {
				if(guiButton.yPosition < minY)
				{
					continue; // Too high
				}
				if(guiButton.xPosition != leftPos && guiButton.xPosition != rightPos)
				{
					continue; // Not in the columns we are after
				}
				
				// Now we know it's one of the ones we are after: but where does it go? let's get the X and Y pos needed.
				// All the buttons are 24 pixels away from each other on Y, and we can check whether it's left or right.
				
				// This means that 0 is on top, 1 is the row below that, 2 is the row below that, etc.
				int position = (guiButton.yPosition - minY) / 24;
				
				// Wonder what this could possibly mean.
				boolean isRight = guiButton.xPosition == rightPos;
				
				// Now to get the final index, get the position * 2, and if it's on the right, add 1.
				
				int index = position * 2;
				if(isRight)
				{
					index++;
				}
				
				// Finally, add it to the array.
				sortedArray[index] = guiButton;
			}
			
			// Finally, add the GuiAPI button in the first available slot. I'm not gonna bother setting up a scroll area.
			// This was for compat with other mods, of which I'm aware there are 2. And adding it would break those anyways.
			
			for (int i = 0; i < sortedArray.length; i++) {
				if(sortedArray[i] != null)
					continue;
				// Ok! Now to get the positions needed! Is it on the left, or right?
				boolean isRight = (i % 2 == 1);
				int yIndex = i;
				if(isRight)
				{
					// If it's on the right, lower it by 1.
					yIndex--;
				}
				yIndex /= 2; // and divide by 2
				
				// now to get the positions!
				int xPos = (isRight ? rightPos : leftPos);
				int yPos = minY + (yIndex * 24);
				
				// Finally, after all that work we can add the button. Finally.
				gui.controlList.add(new GuiApiButton(300, xPos, yPos, 150, 20, "Global Mod Options"));
				
				// set the cache!
				cacheCheck = true;
				return true;
			}
		}
		else
		{
			cacheCheck = false;
		}
        return true;
    }
}
