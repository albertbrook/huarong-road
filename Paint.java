import java.awt.*;
import java.awt.image.BufferedImage;

class Paint extends Canvas {
    private static Paint paint;

    private Plate plate;
    private String[][] blockList;

    static Paint getPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        return paint;
    }

    private Paint() {
        plate = Plate.getPlate();
        blockList = plate.getBlockList();

        setBackground(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < blockList.length; i++) {
            g2.setColor(Settings.BLOCK_COLOR[i]);
            for (String key : blockList[i]) {
                g2.setClip(plate.getBlock(key));
                g2.fillRect(0, 0, image.getWidth(), image.getHeight());
            }
        }
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
}
