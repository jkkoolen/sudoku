package eu.ludimus.sudoku;

import java.util.*;

public class SudokuSolver {

    private static final int QUADRANT_SIZE = 3;
    private static final int NO_VALUE = 0;
    private static final List<Integer> POSSIBLE_VALUES = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private List<List<Integer>> values;

    enum Quadrant {
        FIRST(0,0)
        ,SECOND(0,3)
        ,THIRD(0,6)
        ,FOURTH(3,0)
        ,FIFTH(3,3)
        ,SIXTH(3,6)
        ,SEVENTH(6,0)
        ,EIGHTH(6,3)
        ,NINTH(6,6);
        private int row,column;
        Quadrant(int row, int column) {
            this.row = row;
            this.column = column;
        }
        static Quadrant getQuadrant(int row, int column) {
            for(Quadrant q : Quadrant.values()) {
                if(row >= q.row && row < q.row + QUADRANT_SIZE &&
                        column >= q.column && column < q.column + QUADRANT_SIZE) {
                    return q;
                }
            }
            return null;
        }
    }

    public SudokuSolver(List<List<Integer>> values) {
        this.values = values;
    }

    public List<List<Integer>> solve(boolean isOneStep) {
        for(int i = 0; i < values.size(); i++) {
            for(int j = 0; j < values.get(i).size(); j++) {
                if(values.get(i).get(j) == null) { //because controller does not add null values to list
                    values.get(i).set(j, 0);
                }
                if(values.get(i).get(j) == NO_VALUE) {
                    final List<Integer> missing = missing(getMissingInRow(i), getMissingInColumn(j), getMissingInQuadrant(Quadrant.getQuadrant(i, j)));
                    if(missing.size() == 1) {
                        values.get(i).set(j, missing.get(0));
                        if(isOneStep) {
                            return values;
                        }
                        solve(isOneStep);
                    }
                }
            }
        }
        return values;
    }

    public List<List<Integer>> solve() {
        return solve(false);
    }

    private List<Integer> missing(List<Integer>... lists) {
        List<Integer> result = new ArrayList();
        for(int i = 1; i <= values.size(); i++) {
            boolean found = true;
            for(List<Integer> list : lists) {
                found = found && list.contains(i);
            }
            if(found) {
                result.add(i);
            }
        }
        return result;
    }

    private List<Integer> getMissingInRow(int row) {
        Set<Integer> result = new LinkedHashSet<Integer>(POSSIBLE_VALUES);
        for(int i = 0; i < values.get(row).size(); i++) {
            result.remove(values.get(row).get(i));
        }
        return new ArrayList(result);
    }

    private List<Integer> getMissingInColumn(int column) {
        Set<Integer> result = new LinkedHashSet<Integer>(POSSIBLE_VALUES);
        for(int i = 0; i < values.size(); i++) {
            result.remove(values.get(i).get(column));
        }
        return new ArrayList(result);
    }

    private List<Integer> getMissingInQuadrant(Quadrant quadrant) {
        Set<Integer> result = new LinkedHashSet<Integer>(POSSIBLE_VALUES);
        int rowEnd = quadrant.row + QUADRANT_SIZE;
        int columnEnd = quadrant.column + QUADRANT_SIZE;
        for(int i = quadrant.row; i < rowEnd; i++) {
            for(int j = quadrant.column; j < columnEnd; j++) {
                result.remove(values.get(i).get(j));
            }
        }

        return new ArrayList(result);
    }
}
