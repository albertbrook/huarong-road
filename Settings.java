import java.awt.*;

class Settings {
    static final int BLOCK_SIZE;
    static final int SPACE_SIZE;
    static final int ROUND_SIZE;
    static final Dimension SCREEN_SIZE;

    static final Color[] BLOCK_COLOR;

    static {
        BLOCK_SIZE = 150;
        SPACE_SIZE = 10;
        ROUND_SIZE = 30;
        SCREEN_SIZE = setScreenSize();

        BLOCK_COLOR = new Color[] {Color.RED, Color.GREEN, Color.BLUE};
    }

    private Settings() {}

    private static Dimension setScreenSize() {
        int width = 4 * BLOCK_SIZE + 5 * SPACE_SIZE + 6;
        int height = 5 * BLOCK_SIZE + 6 * SPACE_SIZE + 29;
        return new Dimension(width, height);
    }
}
