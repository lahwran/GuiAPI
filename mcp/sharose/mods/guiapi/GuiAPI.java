package sharose.mods.guiapi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;

@Mod(name = "GuiAPI", modid = "GuiAPI", version = "0.15.2", acceptedMinecraftVersions = "1.4.6")
public class GuiAPI implements IFMLLoadingPlugin, ITickHandler {

	Object cacheCheck = null;
	Field controlListField;

	@Init
	public void init(FMLInitializationEvent event) {
		try {
			Field[] fields = GuiScreen.class.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getType() == List.class) {
					controlListField = fields[i];
					controlListField.setAccessible(true);
					break;
				}
			}
			if (controlListField == null) {
				throw new Exception("No fields found on GuiScreen ("
						+ GuiScreen.class.getSimpleName()
						+ ") of type List! This should never happen!");
			}
		} catch (Throwable e) {
			throw new RuntimeException(
					"Unable to get Field reference for GuiScreen.controlList!",
					e);
		}
		TickRegistry.registerTickHandler(this, Side.CLIENT);
	}

	public List getControlList(GuiOptions gui) {
		try {
			return (List) controlListField.get(gui);
		} catch (Throwable e) {
			return null; // This should really print something, but it should
							// never (ever) fire.
		}
	}

	public void processGuiOptions(GuiOptions gui) {
		List controlList = getControlList(gui);
		if (controlList == null) {
			return;
		}
		if (controlList.get(0) == cacheCheck) {
			// Cached so we don't have to check this every frame
			return;
		}
		// First get a list of buttons
		ArrayList<GuiButton> buttonsPreSorted = new ArrayList<GuiButton>();
		for (Object guiButton : controlList) {
			if (!(guiButton instanceof GuiButton))
				continue;
			if (guiButton instanceof GuiSlider)
				continue;
			if (guiButton instanceof GuiSmallButton)
				continue;
			buttonsPreSorted.add((GuiButton) guiButton);
		}
		// Now sort by position. First, get the buttons with IDs of 101 and 100:
		// 101 is left, 100 is right.
		// Any button that doesn't have a position that doesn't fit into those
		// 'columns' is ignored.
		// Likewise with buttons that are too high, so we'll be storing the min
		// y position too.
		int leftPos = -1; // Video settings, aka 101
		int rightPos = -1; // Controls, aka 100
		int minY = -1;
		for (GuiButton guiButton : buttonsPreSorted) {
			if (guiButton.id == 101) {
				leftPos = guiButton.xPosition;
				minY = guiButton.yPosition;
			}
			if (guiButton.id == 100) {
				rightPos = guiButton.xPosition;
				minY = guiButton.yPosition;
			}
		}

		// Now make an array for it. Adding two as a 'just in case' thing: it
		// should have room anyways.

		GuiButton[] sortedArray = new GuiButton[buttonsPreSorted.size() + 2];

		for (GuiButton guiButton : buttonsPreSorted) {
			if (guiButton.yPosition < minY) {
				continue; // Too high
			}
			if (guiButton.xPosition != leftPos
					&& guiButton.xPosition != rightPos) {
				continue; // Not in the columns we are after
			}

			// Now we know it's one of the ones we are after: but where does it
			// go? let's get the X and Y pos needed.
			// All the buttons are 24 pixels away from each other on Y, and we
			// can check whether it's left or right.

			// This means that 0 is on top, 1 is the row below that, 2 is the
			// row below that, etc.
			int position = (guiButton.yPosition - minY) / 24;

			// Wonder what this could possibly mean.
			boolean isRight = guiButton.xPosition == rightPos;

			// Now to get the final index, get the position * 2, and if it's on
			// the right, add 1.

			int index = position * 2;
			if (isRight) {
				index++;
			}

			// Finally, add it to the array.
			sortedArray[index] = guiButton;
		}

		// Finally, add the GuiAPI button in the first available slot. I'm not
		// gonna bother setting up a scroll area.
		// This was for compat with other mods, of which I'm aware there are 2.
		// And adding it would break those anyways.

		for (int i = 0; i < sortedArray.length; i++) {
			if (sortedArray[i] != null)
				continue;
			// Ok! Now to get the positions needed! Is it on the left, or right?
			boolean isRight = (i % 2 == 1);
			int yIndex = i;
			if (isRight) {
				// If it's on the right, lower it by 1.
				yIndex--;
			}
			yIndex /= 2; // and divide by 2

			// now to get the positions!
			int xPos = (isRight ? rightPos : leftPos);
			int yPos = minY + (yIndex * 24);

			// Finally, after all that work we can add the button. Finally.
			controlList.add(new GuiApiButton(300, xPos, yPos, 150, 20,
					"Global Mod Options"));

			// set the cache!
			cacheCheck = controlList.get(0);
			return;
		}
	}

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return null;
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (!type.contains(TickType.RENDER)) {
			return;
		}
		if (Minecraft.getMinecraft() == null) {
			return; // what
		}
		if (Minecraft.getMinecraft().currentScreen == null) {
			return;
		}
		if (Minecraft.getMinecraft().currentScreen instanceof GuiOptions) {
			processGuiOptions((GuiOptions) Minecraft.getMinecraft().currentScreen);
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return "GuiAPI main menu checker";
	}
}
