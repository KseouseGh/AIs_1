package org.example;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Bi_BFS {
    private int[][] solution = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
    private int solutionHash = 0;

    Bi_BFS() {
        this.solutionHash = State.hashCode(solution);
    }

    public void search(State state) {
        Queue<State> queue = new LinkedList<State>();
        Queue<State> queueR = new LinkedList<State>();

        Set<Integer> visited = new HashSet<>();
        Set<Integer> visitedR = new HashSet<>();
        int iterations=0;

        queue.add(state);
        visited.add(state.hashCode());
        queueR.add(new State(solution, ""));
        visitedR.add(solutionHash);

        while (queue.size() > 0 && queueR.size() > 0) {
            State current = queue.poll();
            State currentR = queueR.poll();
            iterations++;

            for (int i = 0; i < State.SIZE; i++) {
                State tmp;

                tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveColUp(i);
                if(!visited.contains(tmp.hashCode())){
                    queue.add(tmp);
                    visited.add(tmp.hashCode());
                } if (visitedR.contains(tmp.hashCode())) {
                    System.out.println("Fonded solution: " + tmp.getMove() + "\n" + currentR.getMoveR() + " .");
                    current.printTestBoard();
                    System.out.println("Bi_BFS iterations = " + iterations);
                    return;
                }

                tmp = new State(currentR.getBoard(), currentR.getMove());
                tmp.MoveColDown(i);
                if(!visitedR.contains(tmp.hashCode())){
                    queueR.add(tmp);
                    visitedR.add(tmp.hashCode());
                } if (visited.contains(tmp.hashCode())) {
                    System.out.println("Fonded solution: " + current.getMove() + "\n" + tmp.getMoveR() + " .");
                    current.printTestBoard();
                    System.out.println("Bi_BFS iterations = " + iterations);
                    return;
                }

                tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveRowLeft(i);
                if(!visited.contains(tmp.hashCode())){
                    queue.add(tmp);
                    visited.add(tmp.hashCode());
                } if (visitedR.contains(tmp.hashCode())) {
                    System.out.println("Fonded solution: " + tmp.getMove() + "\n" + currentR.getMoveR() + " .");
                    current.printTestBoard();
                    System.out.println("Bi_BFS iterations = " + iterations);
                    return;
                }

                tmp = new State(currentR.getBoard(), currentR.getMove());
                tmp.MoveRowRight(i);
                if(!visitedR.contains(tmp.hashCode())){
                    queueR.add(tmp);
                    visitedR.add(tmp.hashCode());
                } if (visited.contains(tmp.hashCode())) {
                    System.out.println("Fonded solution: " + current.getMove() + "\n" + tmp.getMoveR() + " .");
                    current.printTestBoard();
                    System.out.println("Bi_BFS iterations = " + iterations);
                    return;
                }
            }
        }
        System.out.println("Queue is empty!");
    }
}
