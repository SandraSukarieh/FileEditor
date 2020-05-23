import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

/**
 * @author Sandra
 */
public class DrawingRectangles {

    public void drawSelected(Form form, Group imageLayer, int selectedIndex) {
        double x1 = form.getFields().get(selectedIndex).x;
        double y1 = form.getFields().get(selectedIndex).y;
        double width1 = form.getFields().get(selectedIndex).width;
        double height1 = form.getFields().get(selectedIndex).height;
        
        double x=0;
        double y=0;
        double width=0;
        double height=0;
        
        if (delta !=0){
            double zooClickingRatio = delta/10;
            double ratio = Math.pow(1.1, zooClickingRatio);
            x = x1*ratio;
            y=y1*ratio;
            width = width1*ratio;
            height = height1*ratio;
        }
        Rectangle shape = new Rectangle(x, y, width, height);
        shape.setStroke(Color.CRIMSON);
        shape.setStrokeWidth(1);
        shape.setStrokeLineCap(StrokeLineCap.ROUND);
        shape.setFill(Color.CRIMSON.deriveColor(0, 1.2, 1, 0.6));
        imageLayer.getChildren().add(shape);
    }

    public void drawAll(Form form, Group imageLayer) {
        
        double x=0;
        double y=0;
        double width=0;
        double height=0;
        
        for (int i = 0; i < form.getFields().size(); i++) {
            double x1 = form.getFields().get(i).x;
            double y1 = form.getFields().get(i).y;
            double width1 = form.getFields().get(i).width;
            double height1 = form.getFields().get(i).height;
            
            if (delta !=0){
                double zooClickingRatio = delta/10;
                double ratio = Math.pow(1.1, zooClickingRatio);
                x = x1*ratio;
                y=y1*ratio;
                width = width1*ratio;
                height = height1*ratio;
            }
            
            Rectangle shape = new Rectangle(x, y, width, height);
            shape.setStroke(Color.CRIMSON);
            shape.setStrokeWidth(1);
            shape.setStrokeLineCap(StrokeLineCap.ROUND);
            shape.setFill(Color.CRIMSON.deriveColor(0, 1.2, 1, 0.6));
            imageLayer.getChildren().add(shape);
        }
    }

    public void clearLayer(Group imageLayer, ImageView imageView, ScrollPane scroller) {
        allowUpdateScrolling = false;
        imageLayer.getChildren().clear();
        imageLayer.getChildren().add(imageView);
        scroller.setVvalue(currentScrollValue);
        allowUpdateScrolling = true;
        System.out.println("from clear layer method, current scrolling value = "+ currentScrollValue);
    }

}
