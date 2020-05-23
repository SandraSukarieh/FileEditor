import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Sandra
 */
public class FileChoosing {

 public String getFilePath (){
                    FileChooser chooser = new javafx.stage.FileChooser();
                    chooser.setTitle("Choose File");
                    File file = chooser.showOpenDialog(new Stage());
                    if(file != null) {
                        String path =  file.getPath();
                        return path;
                    }
        return null;
 }
 
}
