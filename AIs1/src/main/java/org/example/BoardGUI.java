package org.example;
import javax.swing.*;
import java.awt.*;

public class BoardGUI extends JFrame {
    private State state;
    private JPanel gridPanel;
    private CellPanel[][] cells;
    private boolean showingFirst = true;
    private State state2;
    private State current;

    public BoardGUI(State initial, State i2) {
        this.state = initial;
        this.state2=i2;
        this.current=state;
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
        JPanel controlPanel = new JPanel(new GridLayout(3, State.SIZE));
        for (int i = 0; i < State.SIZE; i++) {
            int row = i;
            JButton left = new JButton("Row " + i + " Left");
            left.addActionListener(e -> {
                current.MoveRowLeft(current.getBoard(), row);
                updateBoard();
            });
            controlPanel.add(left);
        }
        for (int i = 0; i < State.SIZE; i++) {
            int col = i;
            JButton up = new JButton("Col " + i + " Up");
            up.addActionListener(e -> {
                current.MoveColUp(current.getBoard(), col);
                updateBoard();
            });
            controlPanel.add(up);
        }
        JButton switchBtn = new JButton("Switch object");

        switchBtn.addActionListener(e -> {
            showingFirst = !showingFirst;
            current = showingFirst ? state : state2;
            updateBoard();
            switchBtn.setText(showingFirst ? "Showobj1DFS" : "ShowobjBFS");
        });
        controlPanel.add(switchBtn);

        add(controlPanel, BorderLayout.SOUTH);
        updateBoard();
    }

    private void updateBoard() {
        int[][] board = current.getBoard();
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