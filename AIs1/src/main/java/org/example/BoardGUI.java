package org.example;
import javax.swing.*;
import java.awt.*;

public class BoardGUI extends JFrame {
    private State state;
    private JPanel gridPanel;
    private CellPanel[][] cells;

    public BoardGUI(State initial) {
        this.state = initial;
        setTitle("AIs_1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLayout(new BorderLayout());// 4x4 B!
        gridPanel = new JPanel(new GridLayout(State.SIZE, State.SIZE));
        cells = new CellPanel[State.SIZE][State.SIZE];

        for (int i = 0; i < State.SIZE; i++) {
            for (int j = 0; j < State.SIZE; j++) {
                CellPanel cell = new CellPanel();
                cells[i][j] = cell;
                gridPanel.add(cell);
            }
        }

        add(gridPanel, BorderLayout.CENTER);//Control panel!
        JPanel controlPanel = new JPanel(new GridLayout(2, State.SIZE));
        for (int i = 0; i < State.SIZE; i++) {
            int row = i;
            JButton left = new JButton("Row " + i + " Left");
            left.addActionListener(e -> {
                state.MoveRowLeft(state.getBoard(), row);
                updateBoard();
            });
            controlPanel.add(left);
        }
        for (int i = 0; i < State.SIZE; i++) {
            int col = i;
            JButton up = new JButton("Col " + i + " Up");
            up.addActionListener(e -> {
                state.MoveColUp(state.getBoard(), col);
                updateBoard();
            });
            controlPanel.add(up);
        }
        add(controlPanel, BorderLayout.SOUTH);
        updateBoard();
    }

    private void updateBoard() {
        int[][] board = state.getBoard();
        for (int i = 0; i < State.SIZE; i++) {
            for (int j = 0; j < State.SIZE; j++) {
                Color color = switch (board[i][j]) {
                    case 0 -> Color.RED;
                    case 1 -> Color.GREEN;
                    case 2 -> Color.BLUE;
                    case 3 -> Color.YELLOW;
                    default -> Color.GRAY;
                };
                cells[i][j].setColor(color);
            }
        }
    }

    private static class CellPanel extends JPanel {
        private Color color = Color.GRAY;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(color);
            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        }

        public void setColor(Color c) {
            color = c;
            repaint();
        }
    }
}