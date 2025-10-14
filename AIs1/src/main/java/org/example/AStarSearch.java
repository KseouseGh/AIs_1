package org.example;
import java.util.*;
import static java.lang.System.in;

public class AStarSearch {
    private final int[][] solution_r = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};

    private int solutionHash = 0;
    //h(n) = cells count frequency of incorrect value for each color, that means p. col-n involves excess. And reversal rule for ro-s gr.!
    AStarSearch() {
        //this.solutionHash = State.hashCode(solution);
    }

    public void search(State state) {
        int iterations = 0;
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();//In queue of search priority better for element with min. value of "f"-tion, and if (x.f = x1.f), then cmpre(<h v-s)!
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        PriorityQueue<State> opened = new PriorityQueue<>(Comparator.comparingInt((State s) -> s.f).thenComparingInt(s -> s.h));
        Set<Integer> visited = new HashSet<>();
        state.g = 0;
        state.h = state.calc_heuristic_free_decision();
        state.f = state.g + state.h;
        opened.add(state);
        visited.add(state.hashCode());
        while (opened.size() > 0) {
            State current = opened.poll();//Taking elem-t from the h-d!
            iterations++;
            if((current.calc_heuristic_free_decision()==0)) {
                System.out.println("Fonded solution: " + current.getMove() + " .");
                current.printTestBoard();
                System.out.println("AStar iterations = " + iterations);
                System.out.println("AStar depth = " + current.getDepth());
                long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long peakMemory = endMemory - startMemory;
                System.out.println("''AStar''s used memory (app.) = " + peakMemory / 1024 + " KB");
                return;
            }
            for (int i = 0; i < State.SIZE; i++) {
                State temp = new State(current.getBoard(), current.getMove());
                temp.MoveColUp(i);
                temp.g = current.g + 1;//Update State.param!
                temp.h = temp.calc_heuristic_free_decision();
                temp.f = temp.g + temp.h;
                if (!visited.contains(temp.hashCode())) {
                    temp.setDepth(current.getDepth() + 1);
                    opened.add(temp);
                    visited.add(temp.hashCode());
                }
                else {
                    continue;
                }
                temp = new State(current.getBoard(), current.getMove());
                temp.MoveRowLeft(i);
                temp.g = current.g + 1;//Update state.param!
                temp.h = temp.calc_heuristic_free_decision();
                temp.f = temp.g + temp.h;//Secondary_priorit. on h may give the nearest node to solution, when f-val-s are equal!
                if (!visited.contains(temp.hashCode())) {
                    temp.setDepth(current.getDepth() + 1);
                    opened.add(temp);
                    visited.add(temp.hashCode());
                }
                else {
                    continue;
                }
            }
        }
    }
}