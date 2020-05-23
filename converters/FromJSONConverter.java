import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author Sandra
 */
public class FromJSONConverter {

    public FromJSONConverter() {
    }

    public Form readJsonFile(String path) throws IOException {

        FileReader fileReader = new FileReader(path);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        Form form = gson.fromJson(jsonReader, Form.class);

        fileReader.close();
        jsonReader.close();

        return form;
    }
}
