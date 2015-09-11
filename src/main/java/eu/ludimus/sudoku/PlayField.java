package eu.ludimus.sudoku;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PlayField {
    @JsonProperty
    private List<List<Integer>> values;

    public List<List<Integer>> getValues() {
        return values;
    }

    public void setValues(List<List<Integer>> values) {
        this.values = values;
    }

    public PlayField clone() {
        final PlayField clone = new PlayField();
        clone.setValues(new ArrayList<List<Integer>>());
        for(List<Integer> list : getValues()) {
            final ArrayList<Integer> integers = new ArrayList<>();
            clone.getValues().add(integers);
            for(Integer i : list) {
                integers.add(i);
            }
        }
        return clone;
    }
}
