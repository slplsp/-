import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlidePuzzleBoard {
    private List<PuzzlePiece> pieces;
    private int rows, cols;
    private PuzzlePiece emptyPiece;
    private boolean isGameFinished;

    public SlidePuzzleBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        initializeBoard();
        isGameFinished = false;
    }

    private void initializeBoard() {
        pieces = new ArrayList<>();
        for (int i = 1; i < rows * cols; i++) {
            pieces.add(new PuzzlePiece(i));
        }
        emptyPiece = new PuzzlePiece(0);
        pieces.add(emptyPiece);
    }

    public void shuffleBoard() {
        do {
            Collections.shuffle(pieces);
        } while (!isSolvable() || isSolved());
        isGameFinished = false;
    }

    public List<PuzzlePiece> getPieces() {
        return pieces;
    }

    public boolean movePiece(PuzzlePiece piece) {
        if (isGameFinished) return false;

        int index = pieces.indexOf(piece);
        int emptyIndex = pieces.indexOf(emptyPiece);

        if (isAdjacent(index, emptyIndex)) {
            Collections.swap(pieces, index, emptyIndex);
            return true;
        }
        return false;
    }

    private boolean isAdjacent(int index1, int index2) {
        int row1 = index1 / cols, col1 = index1 % cols;
        int row2 = index2 / cols, col2 = index2 % cols;
        return (Math.abs(row1 - row2) + Math.abs(col1 - col2)) == 1;
    }

    public boolean isSolved() {
        for (int i = 0; i < pieces.size() - 1; i++) {
            if (pieces.get(i).getValue() != i + 1) return false;
        }
        boolean solved = pieces.get(pieces.size() - 1).getValue() == 0;
        if (solved) {
            isGameFinished = true;
        }
        return solved;
    }

    private boolean isSolvable() {
        int inversions = 0;
        for (int i = 0; i < pieces.size() - 1; i++) {
            for (int j = i + 1; j < pieces.size(); j++) {
                if (pieces.get(i).getValue() > 0 && pieces.get(j).getValue() > 0 &&
                        pieces.get(i).getValue() > pieces.get(j).getValue()) {
                    inversions++;
                }
            }
        }
        return inversions % 2 == 0;
    }

    // 添加 isGameFinished 方法
    public boolean isGameFinished() {
        return isGameFinished;
    }
}
