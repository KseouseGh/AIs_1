package org.example;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AStarPatternDB {
    private final int[][] solution_r = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};

    private int solutionHash = 0;
    //h(n) = !
    //static AStarPatternDB.PatternDB pdb01l = new AStarPatternDB.PatternDB(new int[]{0, 1});
    //static AStarPatternDB.PatternDB pdb23r = new AStarPatternDB.PatternDB(new int[]{2, 3});
    static AStarPatternDB.PatternDB pdbCol0 = new AStarPatternDB.PatternDB(new int[]{0});
    static AStarPatternDB.PatternDB pdbCol1 = new AStarPatternDB.PatternDB(new int[]{1});
    static AStarPatternDB.PatternDB pdbCol2 = new AStarPatternDB.PatternDB(new int[]{2});
    static AStarPatternDB.PatternDB pdbCol3 = new AStarPatternDB.PatternDB(new int[]{3});
    static{
        //pdb01l.builder();
        //pdb23r.builder();
        //pdb01l.loadFromFile("PDB_left.txt");
        //pdb23r.loadFromFile("PDB_right.txt");
        pdbCol0.builder();
        pdbCol1.builder();
        pdbCol2.builder();
        pdbCol3.builder();
        pdbCol0.loadFromFile("PDB_col0.txt");
        pdbCol1.loadFromFile("PDB_col1.txt");
        pdbCol2.loadFromFile("PDB_col2.txt");
        pdbCol3.loadFromFile("PDB_col3.txt");
    }

    AStarPatternDB() {
        State solved = new State(solution_r, "");
        this.solutionHash = solved.hashCode();
    }

    public void search(State state) {
        int iterations = 0;
        int peakOpen = 0;
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();//In queue of search priority better for element with min. value of "f"-tion, and if (x.f = x1.f), then cmpre(<h v-s)!
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        PriorityQueue<State> opened = new PriorityQueue<>(Comparator.comparingInt((State s) -> s.f)
                .thenComparingInt(s -> s.h)
        );
        //Set<Integer> visited = new HashSet<>();
        state.g = 0;
        //state.h = state.calc_ordo_heuristic();
        //state.h = state.calc_PDB_method_heuristic(AStarPatternDB.pdb01l, AStarPatternDB.pdb23r);
        state.h = state.calc_PDB_method_heuristic(AStarPatternDB.pdbCol0, AStarPatternDB.pdbCol1, AStarPatternDB.pdbCol2, AStarPatternDB.pdbCol3);
        state.f = state.g + state.h;
        opened.add(state);
        //visited.add(state.hashCode());
        Map<Integer, Integer> optG = new HashMap<>();
        optG.put(state.hashCode(), 0);
        while (opened.size() > 0) {
            State current = opened.poll();//Taking elem-t from the h-d!
            iterations++;
            peakOpen = Math.max(peakOpen, opened.size());
            if(iterations>200000){
                AStarSearch astarextra = new AStarSearch();
                astarextra.search(state);
                System.exit(1);
            }

            int hash = current.hashCode();
            if (current.g > optG.getOrDefault(hash, Integer.MAX_VALUE)) {
                continue;
            }
            //if((current.calc_PDB_method_heuristic(AStarPatternDB.pdb01l, AStarPatternDB.pdb23r)==0)) {
            if (current.hashCode() == solutionHash) {
                System.out.println("Fonded solution: " + current.getMove() + " .");
                current.printTestBoard();
                System.out.println("AStar iterations = " + iterations);
                System.out.println("AStar depth = " + current.getDepth());
                System.out.println("Peak OPENED storage size = " + peakOpen);
                long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long peakMemory = endMemory - startMemory;
                System.out.println("''AStar''s used memory (app.) = " + peakMemory / 1024 + " KB");
                return;
            }
            for (int i = 0; i < State.SIZE; i++) {
                State temp = new State(current.getBoard(), current.getMove());
                temp.MoveColUp(i);
                addSuccessor(temp, current, opened, optG);

                temp = new State(current.getBoard(), current.getMove());
                temp.MoveRowLeft(i);
                addSuccessor(temp, current, opened, optG);
            }
        }
    }

    public void addSuccessor(State temp, State current, PriorityQueue<State> opened, Map<Integer, Integer> bestG) {
        int tempG = current.g + 1;
        int hash = temp.hashCode();

        if (tempG < bestG.getOrDefault(hash, Integer.MAX_VALUE)) {
            temp.g = tempG;
            //temp.h = temp.calc_PDB_method_heuristic(AStarPatternDB.pdb01l, AStarPatternDB.pdb23r);
            temp.h = temp.calc_PDB_method_heuristic(AStarPatternDB.pdbCol0, AStarPatternDB.pdbCol1, AStarPatternDB.pdbCol2, AStarPatternDB.pdbCol3);
            temp.f = temp.g + temp.h;
            bestG.put(hash, tempG);
            temp.setDepth(current.getDepth() + 1);
            opened.add(temp);
        }
    }

    public static class PatternDB{
        public Map<Integer, Integer> pdb = new HashMap<>();

        public int[] cols;

        public PatternDB(int[] cols_) {this.cols=cols_;}

        public void builder(){
            Queue<State> queue = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();
            int[][] solution={{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
            State start = new State(solution, "");
            //int startHash = (cols[0] == 0) ? start.getLeftHash() : start.getRightHash();
            int startHash = start.getColHash(cols[0]);
            queue.add(start);
            pdb.put(startHash, 0);
            visited.add(startHash);
            //String fileName = (cols[0]==0) ? "PDB_left.txt" : "PDB_right.txt";
            String fileName = "PDB_col" + cols[0] + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                while (queue.size() > 0) {
                    State current = queue.poll();
                    //int currentHash = (cols[0] == 0) ? current.getLeftHash() : current.getRightHash();
                    int currentHash = current.getColHash(cols[0]);
                    int currentMinDist = pdb.get(currentHash);
                    writer.write(currentHash + "=" + currentMinDist);

                    writer.newLine();

                    for (int col : cols) {
                        State upchild = new State(current.getBoard(), current.getMove());
                        upchild.MoveColUp(col);
                        int uphash = upchild.getColHash(cols[0]);

                        if (!visited.contains(uphash)) {
                            visited.add(uphash);
                            pdb.put(uphash, currentMinDist + 1);
                            queue.add(upchild);
                        }
                    }

                    for (int row = 0; row < State.SIZE; row++) {
                        State leftchild = new State(current.getBoard(), current.getMove());
                        leftchild.MoveRowLeft(row);
                        int lefthash = leftchild.getColHash(cols[0]);

                        if (!visited.contains(lefthash)) {
                            visited.add(lefthash);
                            pdb.put(lefthash, currentMinDist + 1);
                            queue.add(leftchild);
                        }
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Queue is empty!");
        }

        public void loadFromFile(String fileName) {
            try (Scanner sc = new Scanner(new java.io.File(fileName))) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split("=");

                    if(parts.length == 2) {
                        int key = Integer.parseInt(parts[0]);
                        int value = Integer.parseInt(parts[1]);
                        pdb.put(key, value);
                    }
                }
            } catch (IOException e){e.printStackTrace();}
        }
    };
}