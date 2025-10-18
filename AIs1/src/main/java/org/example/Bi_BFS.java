package org.example;

import java.util.*;

public class Bi_BFS {
    private int[][] solution = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
    private int solutionHash = 0;

    Bi_BFS() {
        this.solutionHash = State.hashCode(solution);
    }

    public void search(State state) {
        Queue<State> queue = new LinkedList<State>();
        Queue<State> queueR = new LinkedList<State>();

        Map<Integer, State> visited = new HashMap<>();
        Map<Integer, State> visitedR = new HashMap<>();
        int iterations = 0;

        queue.add(state);
        visited.put(state.hashCode(), state);
        queueR.add(new State(solution, ""));
        visitedR.put(solutionHash, queueR.peek());

        while (queue.size() > 0 && queueR.size() > 0) {
            iterations++;

            State current = queue.poll();
            if(visitedR.containsKey(current.hashCode())) {
                System.out.println("Fonded solution: " + current.getMove() + "\n" +  visitedR.get(current.hashCode()).getMoveR() +" .");
                System.out.println("Bi_BFS iterations = " + iterations);
                return;
            }

            for (int i = 0; i < State.SIZE; i++) {
                State tmp;
                tmp = new State(current.getBoard(), current.getMove());

                tmp.MoveColUp(i);
                if(!visited.containsKey(tmp.hashCode())){
                    queue.add(tmp);
                    visited.put(tmp.hashCode(), tmp);
                }

                tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveRowLeft(i);
                if(!visited.containsKey(tmp.hashCode())){
                    queue.add(tmp);
                    visited.put(tmp.hashCode(), tmp);
                }
            }

            current = queueR.poll();
            if(visited.containsKey(current.hashCode())) {
                System.out.println("Fonded solution: " + visited.get(current.hashCode()).getMove() + "\n" + current.getMoveR() + " .");
                System.out.println("Bi_BFS iterations = " + iterations);
                return;
            }

            for (int i = 0; i < State.SIZE; i++) {
                State tmp;
                tmp = new State(current.getBoard(), current.getMove());

                tmp.MoveColDown(i);
                if(!visitedR.containsKey(tmp.hashCode())){
                    queueR.add(tmp);
                    visitedR.put(tmp.hashCode(), tmp);
                }

                tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveRowRight(i);
                if(!visitedR.containsKey(tmp.hashCode())){
                    queueR.add(tmp);
                    visitedR.put(tmp.hashCode(), tmp);
                }
            }
        }
        System.out.println("Queue is empty!");
    }
}
