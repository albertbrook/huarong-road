import java.awt.*;
import java.awt.event.*;
import java.util.Map;

class Functions {
    private static Functions functions;

    private Paint paint;
    private Plate plate;
    private Map<String, Point> locations;
    private Map<String, Point> blocks;
    private String[][] blockList;

    private boolean move = false;
    private Point mouseLocation;
    private Point blockLocation = new Point();
    private String block;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private Point blockPoint;

    static Functions getFunctions() {
        if (functions == null) {
            functions = new Functions();
        }
        return functions;
    }

    private Functions() {
        paint = Paint.getPaint();
        plate = Plate.getPlate();

        locations = plate.getLocations();
        blocks = plate.getBlocks();
        blockList = plate.getBlockList();
    }

    void event() {
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point downPoint = new Point(e.getX(), e.getY());
                for (String[] keys : blockList) {
                    for (String key : keys) {
                        if (plate.getBlock(key).contains(downPoint)) {
                            move = true;
                            mouseLocation = downPoint;
                            blockLocation.setLocation(locations.get(key).getLocation());
                            block = key;
                            judgeMove();
                            System.out.println();
                            System.out.println(moveLeft);
                            System.out.println(moveRight);
                            System.out.println(moveUp);
                            System.out.println(moveDown);
                            return;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                move = false;
                locations.get(block).x = blockLocation.x;
                locations.get(block).y = blockLocation.y;
                paint.repaint();
            }
        };
        MouseMotionListener mouseMotionListener = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (move) {
                    blockPoint = locations.get(block);
                    int dx = e.getX() - mouseLocation.x;
                    int dy = e.getY() - mouseLocation.y;
                    if (dx < 0 && moveLeft || dx > 0 && moveRight) {
                        blockPoint.x = blockLocation.x + dx;
                        move(e);
                    } else {
                        blockPoint.x = blockLocation.x;
                    }
                    if (dy < 0 && moveUp || dy > 0 && moveDown) {
                        blockPoint.y = blockLocation.y + dy;
                        move(e);
                    } else {
                        blockPoint.y = blockLocation.y;
                    }
                    paint.repaint();
                }
            }
        };
        paint.addMouseListener(mouseListener);
        paint.addMouseMotionListener(mouseMotionListener);
    }

    private void judgeMove() {
        boolean[][] b = new boolean[4][4];
        int unit = Settings.BLOCK_SIZE + Settings.SPACE_SIZE;
        for (int i = 0; i < blocks.get(block).x; i++) {
            for (int j = 0; j < blocks.get(block).y; j++) {
                b[i * blocks.get(block).x + j] = judgePointMove(blockLocation.x + unit * i, blockLocation.y + unit * j);
            }
        }
        int[] b2 = new int[4];
        for (int i = 0; i < 4; i++) {
            int n = 0;
            for (int j = 0; j < 4; j++) {
                if (b[j][i]) {
                    n += 1;
                }
            }
            b2[i] = n;
            System.out.println(b2[i]);
        }
        moveLeft = b2[0] == blocks.get(block).y;
        moveRight = b2[1] == blocks.get(block).y;
        moveUp = b2[2] == blocks.get(block).x;
        moveDown = b2[3] == blocks.get(block).x;
    }

    private boolean[] judgePointMove(int x, int y) {
        int unit = Settings.BLOCK_SIZE + Settings.SPACE_SIZE;
        int leftX = x - unit + Settings.ROUND_SIZE;
        int rightX = x + unit + Settings.ROUND_SIZE;
        int upY = y - unit + Settings.ROUND_SIZE;
        int downY = y + unit + Settings.ROUND_SIZE;
        boolean left = isCanMove(leftX, y);
        boolean right = isCanMove(rightX, y);
        boolean up = isCanMove(x, upY);
        boolean down = isCanMove(x, downY);
        return new boolean[] {left, right, up, down};
    }

    private boolean isCanMove(int x, int y) {
        if (x < 0 || x > paint.getWidth() || y < 0 || y > paint.getHeight()) {
            return false;
        }
        for (String[] keys : blockList) {
            for (String key : keys) {
                if (plate.getBlock(key).contains(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void move(MouseEvent e) {
        blockPoint = locations.get(block);
        int unit = Settings.BLOCK_SIZE + Settings.SPACE_SIZE;
        int x = Math.round((blockPoint.x - Settings.SPACE_SIZE) / (float) unit) * unit + Settings.SPACE_SIZE;
        int y = Math.round((blockPoint.y - Settings.SPACE_SIZE) / (float) unit) * unit + Settings.SPACE_SIZE;
        if (x != blockLocation.x || y != blockLocation.y) {
            mouseLocation.setLocation(e.getX(), e.getY());
            blockLocation.x = x;
            blockLocation.y = y;
            locations.get(block).x = blockLocation.x;
            locations.get(block).y = blockLocation.y;
            judgeMove();
        }
    }
}
