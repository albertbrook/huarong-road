import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

class Paint extends Canvas {
    private static Paint paint;

    private Map<String, Point> locations;
    private Map<String, Point> sizes;
    private String[][] blockList;

    private RoundRectangle2D blockStyle = new RoundRectangle2D.Double();


    static Paint getPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        return paint;
    }

    private Paint() {
        locations = Plate.getPlate().getLocations();
        sizes = Plate.getPlate().getSizes();
        blockList = Plate.getPlate().getBlockList();

        setBackground(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < blockList.length; i++) {
            g2.setColor(Settings.BLOCK_COLOR[i]);
            for (String key : blockList[i]) {
                g2.setClip(drawBlock(key));
                g2.fillRect(0, 0, image.getWidth(), image.getHeight());
            }
        }
        g.drawImage(image, 0, 0, this);
    }

    RoundRectangle2D drawBlock(String key) {
        Point location = locations.get(key);
        Point size = sizes.get(key);
        int width = size.x * Settings.BLOCK_SIZE + (size.x - 1) * Settings.SPACE_SIZE;
        int height = size.y * Settings.BLOCK_SIZE + (size.y - 1) * Settings.SPACE_SIZE;
        this.blockStyle.setRoundRect(location.x, location.y, width, height, Settings.ROUND_SIZE, Settings.ROUND_SIZE);
        return this.blockStyle;
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
}
