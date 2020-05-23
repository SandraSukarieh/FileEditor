import java.io.ByteArrayInputStream;
import java.io.IOException;
import javafx.scene.image.Image;
import sun.misc.BASE64Decoder;


public class StringtoImageConverter {
    
    public Image convert (String base64String) throws IOException{

        byte[] imageByte;

        // create a buffered image
        BASE64Decoder decoder = new BASE64Decoder();
        imageByte = decoder.decodeBuffer(base64String);

        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        Image image = new Image(bis);
        bis.close();

        return image;
    }
    
}
