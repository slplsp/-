import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuzzleButton extends JButton implements ActionListener {
    private PuzzlePiece piece;
    private SlidePuzzleBoard board;
    private PuzzleFrame frame;

    public PuzzleButton(PuzzlePiece piece, SlidePuzzleBoard board, PuzzleFrame frame) {
        super(piece.getValue() == 0 ? "" : String.valueOf(piece.getValue()));
        this.piece = piece;
        this.board = board;
        this.frame = frame;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (board.isGameFinished()) {
            return; // 如果游戏已完成，不允许移动
        }

        if (board.movePiece(piece)) {
            frame.incrementSteps(); // 每次移动时更新步数
            frame.updateBoard();
            if (board.isSolved()) {
                frame.finish();
            }
        }
    }
}

