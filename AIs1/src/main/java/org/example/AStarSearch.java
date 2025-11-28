package org.example;
import java.util.*;
import static java.lang.System.in;

public class AStarSearch {
    private final int[][] solution_r = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};

    private int solutionHash = 0;
    //h(n) = cells count frequency of incorrect value for each color, that means p. col-n involves excess. And reversal rule for ro-s gr.!
    AStarSearch() {
        this.solutionHash = State.hashCode(solution_r);
    }

    public void search(State state) {
        if (!isSolvable(state.getBoard())) {
            System.out.println("STATE ERROR: Puzzle is unsolvable. Incorrect token distribution.!!!");
            return;
        }
        int iterations = 0;
        int peakOpen = 0;
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        PriorityQueue<State> opened = new PriorityQueue<>(
                Comparator.comparingInt((State s) -> s.f).thenComparingInt((State s) -> s.h)
        );//On min. f that equals, compare to h!
        Map<Integer, Integer> optG = new HashMap<>();
        state.g = 0;
        state.h = state.calc_ordo_heuristic();
        state.f = state.g + state.h;
        opened.add(state);

        optG.put(state.hashCode(), 0);
        while (!opened.isEmpty()) {
            State current = opened.poll();
            iterations++;
            peakOpen = Math.max(peakOpen, opened.size());
            int hash = current.hashCode();
            if (current.g > optG.getOrDefault(hash, Integer.MAX_VALUE)) {
                continue;
            }
            if (current.hashCode() == solutionHash) {
                System.out.println("Fonded solution: " + current.getMove() + " .");
                current.printTestBoard();
                System.out.println("AStar iterations = " + iterations);
                System.out.println("AStar depth = " + current.getDepth());
                System.out.println("Peak OPENED storage size = " + peakOpen);
                long endMemory = runtime.totalMemory() - runtime.freeMemory();
                long peakMemory = endMemory - startMemory;
                System.out.println("''AStar''s used memory (app.) = " + peakMemory / 1024 + " KB");
                return;
            }
            for (int i = 0; i < 4; i++) {
                State temp = new State(current.getBoard(), current.getMove());
                temp.MoveColUp(i);
                addSuccessor(temp, current, opened, optG);

                temp = new State(current.getBoard(), current.getMove());
                temp.MoveRowLeft(i);
                addSuccessor(temp, current, opened, optG);
            }
        }
        System.out.println("Pr.-Queue is empty!");
    }

    public void addSuccessor(State temp, State current, PriorityQueue<State> opened, Map<Integer, Integer> bestG) {
        int tempG = current.g + 1;
        int hash = temp.hashCode();

        if (tempG < bestG.getOrDefault(hash, Integer.MAX_VALUE)) {
            temp.g = tempG;
            temp.h = temp.calc_ordo_heuristic();
            temp.f = temp.g + temp.h;
            bestG.put(hash, tempG);
            temp.setDepth(current.getDepth() + 1);
            opened.add(temp);
        }
    }

    public static boolean isSolvable(int[][] board) {
        int[] freq = new int[4];
        for (int[] row : board) {
            for (int v : row) {

                if (v < 0 || v > 3) return false;

                freq[v]++;
            }
        }
        for (int i = 0; i < 4; i++) {

            if (freq[i] != 4) return false;

        }
        return true;
    }
}