package eu.ludimus.sudoku;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PlayFields {
    private static final String SUDOKUS_TXT = "sudokus.txt";

    @JsonProperty
    private List<PlayField> fields;
    private final ObjectMapper mapper = new ObjectMapper();

    public synchronized PlayFields read() throws IOException {
        fields = mapper.readValue(PlayFields.class.getResourceAsStream(SUDOKUS_TXT),new TypeReference<List<PlayField>>(){} );
        return this;
    }

    public synchronized void  write() throws IOException {
        mapper.writeValue(new FileOutputStream(PlayFields.class.getResource(SUDOKUS_TXT).getFile()), fields);
    }

    public List<PlayField> getFields() {
        return fields;
    }
}
