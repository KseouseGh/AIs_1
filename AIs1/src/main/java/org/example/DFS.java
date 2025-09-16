package org.example;
import java.util.*;

public class DFS {
    private int[][] solution = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
    private int solutionHash = 0;

    DFS() {
        this.solutionHash = State.hashCode(solution);
    }

    public void search(State state){
        Stack<State> stack=new Stack<>();
        Set<Integer> visited = new HashSet<>();
        stack.push(state);
        visited.add(state.hashCode());
        int iterations=0;
        int loops=0;
        while(stack.size()>0){
            loops++;
            State current=stack.pop();//Taking elem-t from the h-d!
            iterations++;
            if(solutionHash == current.hashCode()) {

                System.out.println("Fonded solution: " + current.getMove() + " .");
                current.printTestBoard();
                System.out.println("DFS iterations = " + iterations);
                System.out.println("DFS loops = " + loops);
                return;
                //System.exit(1);
            }
            for(int i = 0; i < State.SIZE; i++){
                State temp=new State(current.getBoard(), current.getMove());
                temp.MoveColUp(i);
                if(!visited.contains(temp.hashCode())){
                    stack.push(temp);
                    visited.add(temp.hashCode());
                }
                temp = new State(current.getBoard(), current.getMove());
                temp.MoveRowLeft(i);
                if(!visited.contains(temp.hashCode())){
                    stack.push(temp);
                    visited.add(temp.hashCode());
                }
            }
        }
        System.out.println("Stack is empty!");
    }
}