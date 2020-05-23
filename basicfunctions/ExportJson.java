import java.io.File;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 *
 * @author Sandra
 */
public class ExportJson {
    
    public void exportJsonFile(Stage primaryStage, Form form ){
        
                
                FileChooser chooser =  new FileChooser();
                chooser.setTitle("Save JSON file");
                File file = chooser.showSaveDialog(primaryStage);
                ToJSONConverter jsonconverter = new ToJSONConverter();
                jsonconverter.writeJsonFile(primaryStage , form ,file.getAbsolutePath(), file.getName() ); 
                
                
    }
}
