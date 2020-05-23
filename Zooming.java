import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;


/**
 *
 * @author Sandra
 */
public class Zooming {
    
    
    public void zoom (ImageView imageView, ScrollPane scroller , double delta , double nextDelta){
    

       if (delta < nextDelta) {
           imageView.setFitWidth(imageView.getFitWidth()*1.1);
           imageView.setFitHeight(imageView.getFitHeight()*1.1);
       } else if (delta > nextDelta) {
            imageView.setFitWidth(imageView.getFitWidth()/1.1);
            imageView.setFitHeight(imageView.getFitHeight()/1.1);
       }
    }
    
}
