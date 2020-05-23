import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Sandra
 */
public class ExportXml {
    
    
    public void exportXmlFile (Stage primaryStage , Form form){
   
        FileChooser chooser =  new FileChooser();
        chooser.setTitle("Save XML file");
        File file = chooser.showSaveDialog(primaryStage);
        ToXMLConverter xmlconverter = new ToXMLConverter();
        xmlconverter.writeXmlFile(primaryStage , form ,file.getAbsolutePath() , file.getName());
       
                
    }
}
