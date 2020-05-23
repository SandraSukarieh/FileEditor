import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Sandra
 */
public class LoadImage {
     
    public void loadImageInImageLayer (Form form, ImageView imageView){
        
        FileChoosing chooser = new FileChoosing();
        ImagetoStringConverter imageConverter = new ImagetoStringConverter();
        String imageString;
        String path = chooser.getFilePath();
        if (path == null){
            WarningPopUp errorAlert = new WarningPopUp("Warning", "Please select a file" );
        }
        else{
            String imagePath = "file:///" + path;
            Image image = new Image(imagePath);
            imageView.setImage(image);  
            try {
                imageString = imageConverter.convert(image);
                form.setId("FORM-"+UUID.randomUUID().toString());
                form.setImageString(imageString);
            } catch (IOException ex) {
                Logger.getLogger(HWRFormEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
