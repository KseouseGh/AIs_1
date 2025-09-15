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
        BoardGUI gui = new BoardGUI(obj);
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
        System.out.println("DFS search!");

    }
}