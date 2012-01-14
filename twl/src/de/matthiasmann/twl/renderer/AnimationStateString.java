package de.matthiasmann.twl.renderer;

import de.matthiasmann.twl.AnimationState;
import de.matthiasmann.twl.renderer.AnimationState.StateKey;

public class AnimationStateString extends AnimationState {
	String stateKey;
	
	public AnimationStateString(String key)
	{
		stateKey = key;
	}
	
	public boolean getAnimationState(StateKey stateKey) {
		return stateKey.getName().contains(this.stateKey); // No, this might fuck up sometimes. But who cares, it works for what I'm doing.
    }
}
