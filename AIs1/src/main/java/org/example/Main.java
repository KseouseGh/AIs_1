package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("AIs-1");
        //State obj = State.RandomState();
        State obj=new State(new int[][]{
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {1, 2, 3, 0}
        }, "");
        System.out.println("0 - Red, 1 - Green, 2 - Blue, 3 - Yellow");
        System.out.println("Origin table!!!");
        obj.printTestBoard();
        /*
        for (State child : obj.GenerateChildren()) {
            System.out.println("Movement " + child.getMove());
            child.printTestBoard();
        }
        */
        State obj1=new State(new int[][]{
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {1, 2, 3, 0}
        }, "");
        BoardGUI gui = new BoardGUI(obj, obj1);
        gui.setVisible(true);
        //BFS!
        System.out.println("BFS search!");
        System.out.println("Search start:");
        BFS bfs = new BFS();
        bfs.search(obj);
        System.out.println("Search end");
        //Bi_DFS
        System.out.println("\n");
        System.out.println("Bi_BFS search!");
        System.out.println("Search start:");
        Bi_BFS bi_bfs = new Bi_BFS();
        bi_bfs.search(obj);
        System.out.println("Search end");
        //DFS(BetterOne)!
        System.out.println("\n");
        System.out.println("DFS search with other matrix!");
        obj1.printTestBoard();
        System.out.println("Search start:");
        DFS dfs = new DFS();
        System.out.println("Set depth restriction for search = ");
        Scanner scanner;
        scanner = new Scanner(System.in);
        dfs.rd = scanner.nextInt();
        dfs.search(obj1);
        System.out.println("Search end");
        System.out.println("\n");
        //AStar!
        System.out.println("AStar search!");
        System.out.println("Search start:");
        AStarSearch astar = new AStarSearch();
        astar.search(obj1);
        System.out.println("Heuristic-search end");
    }
}