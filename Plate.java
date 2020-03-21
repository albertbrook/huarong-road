import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class Plate {
    private static Plate plate;

    private Map<String, Point> locations = new HashMap<>();
    private Map<String, Point> sizes = new HashMap<>();
    private String[][] blockList = new String[][] {
            {"caocao"},
            {"zhangfei", "zhaoyun", "machao", "huangzhong", "guanyu"},
            {"bin1", "bin2", "bin3", "bin4"}
    };

    static Plate getPlate() {
        if (plate == null) {
            plate = new Plate();
        }
        return plate;
    }

    Map<String, Point> getLocations() {
        return locations;
    }

    Map<String, Point> getSizes() {
        return sizes;
    }

    String[][] getBlockList() {
        return blockList;
    }

    private Plate() {
        locations.put(blockList[0][0], setLocation(1, 0));
        locations.put(blockList[1][0], setLocation(0, 0));
        locations.put(blockList[1][1], setLocation(3, 0));
        locations.put(blockList[1][2], setLocation(0, 2));
        locations.put(blockList[1][3], setLocation(3, 2));
        locations.put(blockList[1][4], setLocation(1, 2));
        locations.put(blockList[2][0], setLocation(0, 4));
        locations.put(blockList[2][1], setLocation(1, 3));
        locations.put(blockList[2][2], setLocation(2, 3));
        locations.put(blockList[2][3], setLocation(3, 4));

        sizes.put(blockList[0][0], new Point(2, 2));
        sizes.put(blockList[1][4], new Point(2, 1));
        for (int i = 0; i < 4; i++) {
            sizes.put(blockList[1][i], new Point(1, 2));
            sizes.put(blockList[2][i], new Point(1, 1));
        }
    }

    private Point setLocation(int x, int y) {
        x = x * Settings.BLOCK_SIZE + (x + 1) * Settings.SPACE_SIZE;
        y = y * Settings.BLOCK_SIZE + (y + 1) * Settings.SPACE_SIZE;
        return new Point(x, y);
    }
}
