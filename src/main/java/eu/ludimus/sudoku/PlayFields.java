package eu.ludimus.sudoku;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class PlayFields {

    @JsonProperty
    private List<PlayField> fields;

    public List<PlayField> getFields() {
        return fields;
    }

    public void setFields(List<PlayField> fields) {
        this.fields = fields;
    }


}
