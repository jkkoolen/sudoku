package eu.ludimus.sudoku;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SudokuSolver {

    private static final int NONET_SIZE = 3;
    private static final int NO_VALUE = 0;
    private static final List<Integer> POSSIBLE_VALUES = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private static final int ARRAY_SIZE = 9;
    private static final int SOLVABLE = 1;

    enum Nonet {
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
        Nonet(int row, int column) {
            this.row = row;
            this.column = column;
        }
        static Nonet getNonet(int row, int column) {
            for(Nonet q : Nonet.values()) {
                if(row >= q.row && row < q.row + NONET_SIZE &&
                        column >= q.column && column < q.column + NONET_SIZE) {
                    return q;
                }
            }
            return null;
        }
    }

    public List<List<Integer>> solve(PlayField playField, boolean isOneStep) {
        List<List<Integer>> values = playField.getValues();
        for(int i = 0; i < values.size(); i++) {
            for(int j = 0; j < values.get(i).size(); j++) {
                if(values.get(i).get(j) == null) { //because controller does not add null values to list
                    values.get(i).set(j, 0);
                }
                if(isNotSolved(values.get(i).get(j))) {
                    final List<Integer> missing = missing(getMissingInRow(values, i), getMissingInColumn(values, j),
                            getMissingInNonet(values, Nonet.getNonet(i, j)));
                    if(isSolvable(missing)) {
                        values.get(i).set(j, missing.get(0));
                        if(isOneStep) {
                            return values;
                        }
                        solve(playField, isOneStep);
                    }
                }
            }
        }
        return values;
    }

    public List<List<Integer>> solve(PlayField playField) {
        return solve(playField, false);
    }

    private boolean isNotSolved(Integer value) {
        return value == NO_VALUE;
    }

    private boolean isSolvable(List<Integer> missingList) {
        return missingList.size() == SOLVABLE;
    }

    private List<Integer> missing(List<Integer>... lists) {
        List<Integer> result = new ArrayList();
        for(int i = 1; i <= ARRAY_SIZE; i++) {
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

    private List<Integer> getMissingInRow(List<List<Integer>> values, int row) {
        Set<Integer> result = new LinkedHashSet<Integer>(POSSIBLE_VALUES);
        for(int i = 0; i < values.get(row).size(); i++) {
            result.remove(values.get(row).get(i));
        }
        return new ArrayList(result);
    }

    private List<Integer> getMissingInColumn(List<List<Integer>> values, int column) {
        Set<Integer> result = new LinkedHashSet<Integer>(POSSIBLE_VALUES);
        for(int i = 0; i < values.size(); i++) {
            result.remove(values.get(i).get(column));
        }
        return new ArrayList(result);
    }

    private List<Integer> getMissingInNonet(List<List<Integer>> values, Nonet nonet) {
        Set<Integer> result = new LinkedHashSet<Integer>(POSSIBLE_VALUES);
        int rowEnd = nonet.row + NONET_SIZE;
        int columnEnd = nonet.column + NONET_SIZE;
        for(int i = nonet.row; i < rowEnd; i++) {
            for(int j = nonet.column; j < columnEnd; j++) {
                result.remove(values.get(i).get(j));
            }
        }

        return new ArrayList(result);
    }
}
