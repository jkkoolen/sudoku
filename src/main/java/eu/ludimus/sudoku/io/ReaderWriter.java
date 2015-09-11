package eu.ludimus.sudoku.io;

import java.io.IOException;

public interface ReaderWriter<T> {
    T read() throws IOException;
    void write(T object) throws IOException;
}
