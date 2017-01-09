package fi.akash;

import java.awt.*;

/**
 * Used to get the size of the primary monitor of the user.
 */
public class ScreenSize {
    /**
     * Fetches primary monitor's size.
     */
    private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public static int getHeight() {
        return gd.getDisplayMode().getHeight();
    }

    public static int getWidth() {
        return gd.getDisplayMode().getWidth();
    }
}
