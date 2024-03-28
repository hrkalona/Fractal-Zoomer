package fractalzoomer.gui;

import java.awt.*;

public class Hint
{
    public final String message;
    public final Component owner;
    public final int position;
    public final String prefsKey;
    public final Hint nextHint;

    public boolean shouldDisplay;

    public Hint( String message, Component owner, int position, String prefsKey, Hint nextHint, boolean shouldDisplay) {
        this.message = message;
        this.owner = owner;
        this.position = position;
        this.prefsKey = prefsKey;
        this.nextHint = nextHint;
        this.shouldDisplay = shouldDisplay;
    }
}

