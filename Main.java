import javax.swing.*;

public class Main extends JFrame {
    private Main() {
        setTitle("Huarong Road - AlbertBrook");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Settings.SCREEN_SIZE);

        add(Paint.getPaint());
        Functions.getFunctions().event();
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}
