package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("AIs-1");
        State obj = State.RandomState();
        System.out.println("0 - Красный, 1 - Зелёный, 2 - Синий, 3 - Жёлтый");
        System.out.println("Origin table!!!");
        obj.printTestBoard();
        /*
        for (State child : obj.GenerateChildren()) {
            System.out.println("Movement " + child.getMove());
            child.printTestBoard();
        }
        */
        State obj1=new State(new int[][]{{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {1, 2, 3, 0}}, "");
        BoardGUI gui = new BoardGUI(obj, obj1);
        gui.setVisible(true);
        //System.out.println("0 - row, 1 - column\n" +
        //        "0, 1, 2, 3 - index\n" +
        //        "values storage row/col + index");
        //BFS!
        System.out.println("BFS search!");
        System.out.println("Search start:");
        BFS bfs = new BFS();
        bfs.search(obj);
        System.out.println("Search end");
        //DFS!
        System.out.println("DFS search with other matrix!");
        obj1.printTestBoard();
        System.out.println("Search start:");
        DFS dfs = new DFS();
        dfs.search(obj1);
        System.out.println("Search end");
    }
}