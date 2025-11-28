package org.example;
import java.util.Scanner;

public class Main {//Max. кол-во узлов из списка O + C, проверить отриц-ные h, двусторонний A*
    public static void main(String[] args) {
        String choice = "";
        State obj = null;
        Scanner sc = new Scanner(System.in);
        int[][] staticMatrix = {
                {1,2,3,0},
                {1,2,3,0},
                {1,2,3,0},
                {0,1,2,3}
        };
        /*
        int[][] staticMatrix = {
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {3, 0, 1, 2}
                1 5 4 t t

                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {2, 3, 0, 1}
                2

                {2, 0, 1, 2},
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {3, 0, 1, 3}
                3

                {0, 1, 2, 3},
                {0, 1, 2, 0},
                {0, 1, 2, 3},
                {1, 2, 3, 3}
                5

                {3, 3, 0, 2},
                {0, 0, 1, 3},
                {3, 1, 2, 2},
                {0, 1, 1, 2}
                8

                {2, 3, 3, 0},
                {0, 2, 2, 1},
                {1, 3, 3, 0},
                {1, 2, 0, 1}
                12

                {1, 1, 1, 1},
                {2, 2, 2, 3},
                {3, 0, 3, 2},
                {3, 0, 0, 0}
                de1!

                {1,2,3,0},
                {1,2,3,0},
                {1,2,3,0},
                {1,2,3,0}
                de2!
        };
        */
        BoardGUI gui = null;
        while(!choice.equals("0")) {
            System.out.println("\n\n");
            System.out.println("=== Menu ===");
            System.out.println("Searchers");
            System.out.println("1 - BFS");
            System.out.println("2 - DFS");
            System.out.println("3 - Bi_BFS");
            System.out.println("4 - AStar I (struct heuristic)");
            System.out.println("7 - AStar II (PDB-method heuristic)");
            System.out.println("8 - AStar III (balls-pos heuristic)");
            System.out.println("Board matrix initializers");
            System.out.println("5 - Generate a random state with depth specification");
            System.out.println("6 - Use static matrix");
            System.out.println("0 - Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextLine();

            switch (choice.charAt(0)) {
                case '1', '2', '3', '4', '7', '8' -> {
                    if (obj == null) {
                        System.out.println("You must to init state");
                        continue;
                    }

                    System.out.println("\n\n\n\n");
                    System.out.println("0 - Red, 1 - Green, 2 - Blue, 3 - Yellow");
                    System.out.println("Origin table!!!");
                    obj.printTestBoard();
                    System.gc();
                    Utils.printMemoryUsage();
                    System.out.println("\n");

                    switch (choice.charAt(0)) {
                        case '1':
                            System.out.println("BFS search!");
                            BFS bfs = new BFS();
                            bfs.search(obj);
                            System.out.println("\n");
                            Utils.printMemoryUsage();
                            bfs = null;
                            break;

                        case '2':
                            System.out.println("DFS search with other matrix!");
                            System.out.println("DFS with iter. up-e!");
                            System.out.println("Search start:");
                            DFS dfs = new DFS();
                            System.out.println("Set depth restriction for search = ");
                            Scanner scanner;
                            scanner = new Scanner(System.in);
                            dfs.rd = scanner.nextInt();
                            dfs.search(obj);
                            System.out.println("Search end");
                            Utils.printMemoryUsage();
                            dfs = null;
                            break;

                        case '3':
                            System.out.println("Bi_BFS search!");
                            Bi_BFS bi_bfs = new Bi_BFS();
                            bi_bfs.search(obj);
                            System.out.println("\n");
                            Utils.printMemoryUsage();
                            bi_bfs = null;
                            break;

                        case '4':
                            System.out.println("AStar search!");
                            System.out.println("Search start:");
                            AStarSearch astar = new AStarSearch();
                            astar.search(obj);
                            System.out.println("Heuristic-search end");
                            Utils.printMemoryUsage();
                            astar = null;
                            break;

                        case '7':
                            System.out.println("AStarPatternDB search!");
                            System.out.println("Search start:");
                            AStarPatternDB astar2 = new AStarPatternDB();
                            astar2.search(obj);
                            System.out.println("Heuristic-search end");
                            Utils.printMemoryUsage();
                            astar2 = null;
                            break;

                        case '8':
                            System.out.println("SMA* search!");
                            System.out.println("Search start:");
                            SMAstar astar3 = new SMAstar();
                            astar3.search(obj);
                            System.out.println("Heuristic-search end");
                            Utils.printMemoryUsage();
                            astar3 = null;
                            break;
                    }
                }

                case '5' -> {
                    int tmp = 0;
                    System.out.print("Enter count of shifts: ");

                    tmp = Integer.parseInt(sc.nextLine());
                    int[][] matrix = Utils.shuffleMatrix(tmp);

                    obj = new State(matrix, "");
                    System.out.println("0 - Red, 1 - Green, 2 - Blue, 3 - Yellow");
                    System.out.println("Origin table!!!");
                    obj.printTestBoard();

                    gui = new BoardGUI(obj, obj);
                    gui.setVisible(true);
                }

                case '6' -> {
                    obj = new State(staticMatrix, "");
                    System.out.println("0 - Red, 1 - Green, 2 - Blue, 3 - Yellow");
                    System.out.println("Origin table!!!");
                    obj.printTestBoard();

                    gui = new BoardGUI(obj, obj);
                    gui.setVisible(true);
                }

                case '0' -> System.out.println("Program terminated.");

                default -> System.out.println("Invalid input.");
            }
        }
    }
}