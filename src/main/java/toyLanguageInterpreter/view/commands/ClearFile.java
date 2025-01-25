package toyLanguageInterpreter.view.commands;

import java.io.FileWriter;
import java.io.IOException;

public class ClearFile {
    public static void clear(String filename){
        try(FileWriter fw = new FileWriter(filename)){
            fw.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
