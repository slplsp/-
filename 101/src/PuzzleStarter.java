import javax.swing.SwingUtilities;

public class PuzzleStarter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PuzzleFrame frame = new PuzzleFrame();
            frame.setVisible(true);
        });
    }
}

