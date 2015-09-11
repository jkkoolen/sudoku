package eu.ludimus.sudoku.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.ludimus.sudoku.PlayField;
import eu.ludimus.sudoku.PlayFields;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class PlayFieldsReaderWriter implements ReaderWriter<PlayFields> {
    private static final String SUDOKUS_TXT = "sudokus.txt";
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public PlayFields read() throws IOException {
        final PlayFields playFields = new PlayFields();
        List<PlayField> fields = mapper.readValue(PlayFieldsReaderWriter.class.getResourceAsStream(SUDOKUS_TXT), new TypeReference<List<PlayField>>() {});
        playFields.setFields(fields);
        return playFields;
    }

    @Override
    public void write(PlayFields object) throws IOException {
        mapper.writeValue(new FileOutputStream(PlayFieldsReaderWriter.class.getResource(SUDOKUS_TXT).getFile()), object.getFields());

    }
}
