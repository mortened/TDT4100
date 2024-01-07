package Ligatabell;

import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public interface IFileHandler {
    
    LigaSamling readFile(String filename) throws FileNotFoundException, NoSuchFileException;

    void writeFile(String filename, LigaSamling ligaSamling);

    Path getFilePath(String string) throws NoSuchFileException, IllegalArgumentException;

}
