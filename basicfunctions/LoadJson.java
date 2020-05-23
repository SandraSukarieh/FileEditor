import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Sandra
 */
public class LoadJson {

    public Form loadJsonFile(Stage primaryStage , ImageView imageView) throws IOException {
        FileChoosing chooser = new FileChoosing();
        String jsonPath = chooser.getFilePath();
        String lastPartOfPath = jsonPath.substring(jsonPath.lastIndexOf("\\") + 1);
        String fileName = lastPartOfPath.substring(0, lastPartOfPath.length()-5);
        if (jsonPath == null) {
            WarningPopUp warningAlert = new WarningPopUp("Warning", "Please select a file");
            throw new NotImplementedException();
        } else {
            FromJSONConverter converter = new FromJSONConverter();
            Form form = converter.readJsonFile(jsonPath);
            Image convertedImage;
            StringtoImageConverter stringImageConverter = new StringtoImageConverter();
            convertedImage = stringImageConverter.convert(form.getImageString());
            imageView.setImage(convertedImage);
            primaryStage.setTitle(fileName+" - HWR Form Editor");
            return form;
        }
    }

}
