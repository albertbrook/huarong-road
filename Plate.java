import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

class Plate {
    private static Plate plate;

    private RoundRectangle2D Block = new RoundRectangle2D.Double();
    private Map<String, Point> locations = new HashMap<>();
    private Map<String, Point> blocks = new HashMap<>();
    private String[][] blockList = new String[][] {
            {"caocao"},
            {"zhangfei", "zhaoyun", "machao", "guanyu", "huangzhong"},
            {"bin1", "bin2", "bin3", "bin4"}
    };


    static Plate getPlate() {
        if (plate == null) {
            plate = new Plate();
        }
        return plate;
    }

    RoundRectangle2D getBlock(String key) {
        Point location = locations.get(key);
        Point block = blocks.get(key);
        int width = block.x * Settings.BLOCK_SIZE + (block.x - 1) * Settings.SPACE_SIZE;
        int height = block.y * Settings.BLOCK_SIZE + (block.y - 1) * Settings.SPACE_SIZE;
        Block.setRoundRect(location.x, location.y, width, height, Settings.ROUND_SIZE, Settings.ROUND_SIZE);
        return Block;
    }

    Map<String, Point> getLocations() {
        return locations;
    }

    Map<String, Point> getBlocks() {
        return blocks;
    }

    String[][] getBlockList() {
        return blockList;
    }

    private Plate() {
        locations.put(blockList[0][0], setPoint(1, 0));
        locations.put(blockList[1][0], setPoint(0, 0));
        locations.put(blockList[1][1], setPoint(3, 0));
        locations.put(blockList[1][2], setPoint(0, 2));
        locations.put(blockList[1][3], setPoint(1, 2));
        locations.put(blockList[1][4], setPoint(3, 2));
        locations.put(blockList[2][0], setPoint(0, 4));
        locations.put(blockList[2][1], setPoint(1, 3));
        locations.put(blockList[2][2], setPoint(2, 3));
        locations.put(blockList[2][3], setPoint(3, 4));

        blocks.put(blockList[0][0], new Point(2, 2));
        blocks.put(blockList[1][0], new Point(1, 2));
        blocks.put(blockList[1][1], new Point(1, 2));
        blocks.put(blockList[1][2], new Point(1, 2));
        blocks.put(blockList[1][3], new Point(2, 1));
        blocks.put(blockList[1][4], new Point(1, 2));
        blocks.put(blockList[2][0], new Point(1, 1));
        blocks.put(blockList[2][1], new Point(1, 1));
        blocks.put(blockList[2][2], new Point(1, 1));
        blocks.put(blockList[2][3], new Point(1, 1));
    }

    private Point setPoint(int x, int y) {
        x = x * Settings.BLOCK_SIZE + (x + 1) * Settings.SPACE_SIZE;
        y = y * Settings.BLOCK_SIZE + (y + 1) * Settings.SPACE_SIZE;
        return new Point(x, y);
    }
}
