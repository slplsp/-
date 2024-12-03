import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButton extends JButton implements ActionListener {
    private SlidePuzzleBoard board;
    private PuzzleFrame frame;

    public StartButton(SlidePuzzleBoard board, PuzzleFrame frame) {
        super("Start");
        this.board = board;
        this.frame = frame;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        board.shuffleBoard();
        frame.updateBoard();
        frame.resetStats(); // 重置步数和计时器
        frame.startGame();
    }
}
