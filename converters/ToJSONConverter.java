import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.dfki.hwrformeditor.components.Form;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javafx.stage.Stage;


/**
 * @author Sandra
 */
public class ToJSONConverter {

    boolean flag = false;
    
    public void writeJsonFile(Stage primaryStage , Form form, String path , String fileName) {
        try {
            if (form.getName().equals("")){
                form.setName(fileName);
                flag=true;
            }
            Writer writer = new FileWriter(path +  ".json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(form, writer);
            writer.close();
            primaryStage.setTitle(fileName+" - HWR Form Editor");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(flag==true){
            form.setName("");
            flag=false;
        }
    }
}
