package de.matthiasmann.twl.renderer.lwjgl;

import org.lwjgl.opengl.GL11;

public class RenderScale {

	public static int scale = 2;
	
	public static void doscale()
	{
		GL11.glPushMatrix();
		GL11.glScalef((float)scale, (float)scale, (float)scale);
	}
	public static void descale()
	{
		GL11.glPopMatrix();
	}
}
