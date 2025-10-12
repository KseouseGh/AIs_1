package org.example;
import java.util.*;
import static java.lang.System.in;

public class DFS {
    private int[][] solution = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
    private int solutionHash = 0;
    int limit=0;
    int rd=0;

    DFS() {
        this.solutionHash = State.hashCode(solution);
    }

    public void search(State state){
        int iterations=0;
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        for(limit=0;limit<=rd;limit++) {
            Stack<State> stack=new Stack<>();
            Set<Integer> visited = new HashSet<>();
            stack.push(state);
            visited.add(state.hashCode());
            int local_iterations=0;
            while (stack.size() > 0) {
                State current = stack.pop();//Taking elem-t from the h-d!
                iterations++;
                local_iterations++;
                if (solutionHash == current.hashCode()) {

                    System.out.println("Fonded solution: " + current.getMove() + " .");
                    current.printTestBoard();
                    System.out.println("DFS iterations = " + iterations);
                    System.out.println("DFS ''local_iterations'' on final depth, needed to find solution = " + local_iterations);
                    System.out.println("DFS depth = " + current.getDepth());
                    long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    long peakMemory = endMemory - startMemory;
                    System.out.println("DFS used memory (app.) = " + peakMemory / 1024 + " KB");
                    return;
                    //System.exit(1);
                }
                for (int i = 0; i < State.SIZE; i++) {
                    State temp = new State(current.getBoard(), current.getMove());
                    temp.MoveColUp(i);
                    if (!visited.contains(temp.hashCode())) {
                        temp.setDepth(current.getDepth() + 1);
                        if (temp.depth < limit) {
                            stack.push(temp);
                            visited.add(temp.hashCode());
                        } else {
                            continue;
                        }
                    }
                    temp = new State(current.getBoard(), current.getMove());
                    temp.MoveRowLeft(i);
                    if (!visited.contains(temp.hashCode())) {
                        temp.setDepth(current.getDepth() + 1);
                        if (temp.depth < limit) {
                            stack.push(temp);
                            visited.add(temp.hashCode());
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        System.out.println("Stack is empty!");
    }
}