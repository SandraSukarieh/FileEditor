import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import javax.imageio.ImageIO;


/**
 *
 * @author Sandra
 */
public class LoadPdf  {
    
    public void loadPdfInImageLayer (Form form, ImageView imageView) throws IOException {
        
        FileChoosing chooser = new FileChoosing();
        ImagetoStringConverter imageConverter = new ImagetoStringConverter();
        String imageString;
        String path = chooser.getFilePath();
        
        String password = "";
        String outputPrefix = "pdftoImage";
        String selectedImage;
        int startPage = 1;
        int endPage = Integer.MAX_VALUE;
        int dpi = 300;
      
        Image convertedImage;
        PDDocument document = null;
        
        int i=0;
          
        try {
            document = PDDocument.load(new File(path), password);
        } catch (IOException ex) {
            Logger.getLogger(LoadPdf.class.getName()).log(Level.SEVERE, null, ex);
        }

        ImageType imageType = ImageType.RGB;    
        endPage = Math.min(endPage, document.getNumberOfPages());
        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage image = null;
        for (i = startPage - 1; i < endPage; i++)
        {
            try {
                image = renderer.renderImageWithDPI(i, dpi, imageType);
            } catch (IOException ex) {
                Logger.getLogger(LoadPdf.class.getName()).log(Level.SEVERE, null, ex);
            }
            String fileName = outputPrefix + (i + 1) + ".jpg";
            ImageIO.write(image, "jpg", new File(fileName));
        }  
   
         selectedImage = outputPrefix + "1.jpg";
         convertedImage = new Image(new FileInputStream(selectedImage));
         imageView.setImage(convertedImage); 
            try {
                imageString = imageConverter.convert(convertedImage);
                form.setId("FORM-"+UUID.randomUUID().toString());
                form.setImageString(imageString);
            } catch (IOException ex) {
                Logger.getLogger(HWRFormEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
     }
}
    

