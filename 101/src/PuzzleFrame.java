import javax.swing.*;
import java.awt.*;

public class PuzzleFrame extends JFrame {
    private SlidePuzzleBoard board;
    private JPanel boardPanel;
    private StartButton startButton;
    private JLabel stepsLabel;
    private JLabel timerLabel;
    private JLabel scoreLabel;

    private int steps;
    private Timer timer;
    private int seconds;
    private int score;

    public PuzzleFrame() {
        setTitle("Slide Puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLayout(new BorderLayout());

        // 初始化计时器和步数
        steps = 0;
        seconds = 0;
        score = 0;

        // 难度选择对话框
        int[] dimensions = chooseDifficulty();
        int rows = dimensions[0];
        int cols = dimensions[1];

        board = new SlidePuzzleBoard(rows, cols); // 根据选择的难度生成拼图板
        boardPanel = new JPanel(new GridLayout(rows, cols));
        updateBoard();

        startButton = new StartButton(board, this);

        // 步数和计时器面板
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(1, 3));
        stepsLabel = new JLabel("Steps: 0");
        timerLabel = new JLabel("Time: 0s");
        scoreLabel = new JLabel("Score: 0");
        infoPanel.add(stepsLabel);
        infoPanel.add(timerLabel);
        infoPanel.add(scoreLabel);

        add(boardPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.NORTH);
        add(startButton, BorderLayout.SOUTH);

        initTimer();
    }

    private int[] chooseDifficulty() {
        String[] options = {"3x3", "4x4", "5x5"};
        int choice = JOptionPane.showOptionDialog(this, "Choose difficulty:", "Difficulty",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        switch (choice) {
            case 0:
                return new int[]{3, 3};
            case 2:
                return new int[]{5, 5};
            default:
                return new int[]{4, 4}; // 默认 4x4
        }
    }

    private void initTimer() {
        timer = new Timer(1000, e -> {
            seconds++;
            timerLabel.setText("Time: " + seconds + "s");
        });
    }

    public void updateBoard() {
        boardPanel.removeAll();
        for (PuzzlePiece piece : board.getPieces()) {
            boardPanel.add(new PuzzleButton(piece, board, this));
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public void incrementSteps() {
        steps++; // 增加步数
        stepsLabel.setText("Steps: " + steps); // 更新步数标签
    }

    public void resetStats() {
        steps = 0;
        seconds = 0;
        stepsLabel.setText("Steps: 0");
        timerLabel.setText("Time: 0s");
        scoreLabel.setText("Score: 0");
    }

    public void startGame() {
        timer.start();
        resetStats();
    }

    public void finish() {
        timer.stop();
        calculateScore();
        JOptionPane.showMessageDialog(this, "Congratulations! You solved the puzzle!\nSteps: " + steps +
                "\nTime: " + seconds + " seconds\nScore: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private void calculateScore() {
        if (seconds > 0) {
            score = 10000 / (steps + seconds);
        } else {
            score = 10000 / steps;
        }
        scoreLabel.setText("Score: " + score);
    }
}
