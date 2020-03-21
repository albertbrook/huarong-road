import java.awt.*;
import java.awt.event.*;
import java.util.Map;

class Functions {
    private static Functions functions;

    private Paint paint;
    private Map<String, Point> locations;
    private Map<String, Point> sizes;
    private String[][] blockList;

    private boolean move = false;
    private Point mouse;
    private Point origin = new Point();
    private Point location;
    private Point size;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private int unit;

    static Functions getFunctions() {
        if (functions == null) {
            functions = new Functions();
        }
        return functions;
    }

    private Functions() {
        paint = Paint.getPaint();
        locations = Plate.getPlate().getLocations();
        sizes = Plate.getPlate().getSizes();
        blockList = Plate.getPlate().getBlockList();

        unit = Settings.BLOCK_SIZE + Settings.SPACE_SIZE;
    }

    void event() {
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point downPoint = new Point(e.getX(), e.getY());
                for (String[] keys : blockList) {
                    for (String key : keys) {
                        if (paint.drawBlock(key).contains(downPoint)) {
                            move = true;
                            mouse = downPoint;
                            origin.setLocation(locations.get(key).getLocation());
                            location = locations.get(key);
                            size = sizes.get(key);
                            judgeMove();
                            System.out.println();
                            System.out.println(origin.x);
                            System.out.println(origin.y);
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
                location.setLocation(origin.getLocation());
                paint.repaint();
            }
        };
        MouseMotionListener mouseMotionListener = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (move) {
                    int dx = e.getX() - mouse.x;
                    int dy = e.getY() - mouse.y;
                    if (dx < 0 && moveLeft || dx > 0 && moveRight) {
                        location.x = origin.x + dx;
                        moveUp = moveDown = false;
                        moveNext(e);
                    } else {
                        location.x = origin.x;
                    }
                    if (dy < 0 && moveUp || dy > 0 && moveDown) {
                        location.y = origin.y + dy;
                        moveLeft = moveRight = false;
                        moveNext(e);
                    } else {
                        location.y = origin.y;
                    }
                    paint.repaint();
                }
            }
        };
        paint.addMouseListener(mouseListener);
        paint.addMouseMotionListener(mouseMotionListener);
    }

    private void judgeMove() {
        boolean[][] booleans = new boolean[4][4];
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                booleans[i * size.x + j] = judgePoint(origin.x + unit * i, origin.y + unit * j);
            }
        }
        int[] counts = new int[4];
        for (int i = 0; i < 4; i++) {
            int n = 0;
            for (int j = 0; j < 4; j++) {
                if (booleans[j][i]) {
                    n += 1;
                }
            }
            counts[i] = n;
        }
        moveLeft = counts[0] == size.y;
        moveRight = counts[1] == size.y;
        moveUp = counts[2] == size.x;
        moveDown = counts[3] == size.x;
    }

    private boolean[] judgePoint(int x, int y) {
        x += Settings.BLOCK_SIZE >> 1;
        y += Settings.BLOCK_SIZE >> 1;
        boolean left = isMove(x - unit, y);
        boolean right = isMove(x + unit, y);
        boolean up = isMove(x, y - unit);
        boolean down = isMove(x, y + unit);
        return new boolean[] {left, right, up, down};
    }

    private boolean isMove(int x, int y) {
        if (x < 0 || x > paint.getWidth() || y < 0 || y > paint.getHeight()) {
            return false;
        }
        for (String[] keys : blockList) {
            for (String key : keys) {
                if (paint.drawBlock(key).contains(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void moveNext(MouseEvent e) {
        int x = Math.round((location.x - Settings.SPACE_SIZE) / (float) unit) * unit + Settings.SPACE_SIZE;
        int y = Math.round((location.y - Settings.SPACE_SIZE) / (float) unit) * unit + Settings.SPACE_SIZE;
        if (x != origin.x || y != origin.y) {
            mouse.setLocation(e.getX(), e.getY());
            origin.setLocation(x, y);
            location.setLocation(x, y);
            judgeMove();
        }
    }
}
