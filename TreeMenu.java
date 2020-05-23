import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.scene.image.Image;

/**
 *
 * @author Sandra
 */
public class TreeMenu {

    public TreeView<node> treeView = new TreeView<>() ;
    public TreeItem<node> rootNode;
    
    public TreeMenu() {
        
        rootNode = new TreeItem<>(new node("dummy", "dummy", new Image("resources/group.png")));
        rootNode.setExpanded(true);
        
        treeView.setShowRoot(false);
        treeView.setCellFactory(new Callback<TreeView<node>, TreeCell<node>>() {
            @Override
            public TreeCell<node> call(TreeView<node> p) {
                return new AlertTreeCell();
            }
        });
    }
    
    public void updateGroupMenu (Form form ){
        treeView.setRoot(null);
        rootNode.getChildren().clear();
        String childDescription = null;

        Image groupImage = new Image("resources/group.png");
        Image fieldImage = new Image("resources/field.png");

        for (FormGroup group : form.getGroups()) {
            TreeItem<node> groupNode = new TreeItem<>(new node(group.description+"/"+group.id, group.description+"/"+group.id, groupImage));
            for (String child : group.children){
                childDescription = form.getDescriptionById(child);
                String key = child.substring(0, Math.min(child.length(), 5));
                if (key.equals("GROUP")){
                    groupNode.getChildren().add(new TreeItem<>(new node(childDescription,childDescription , groupImage)));
                }
                else{
                     groupNode.getChildren().add(new TreeItem<>(new node(childDescription,childDescription , fieldImage)));
                }
            }
            rootNode.getChildren().add(groupNode); 
        }
        treeView.setRoot(rootNode);
    } 
    
    
    
    public void updateRectanglesMenu (Form form ){
        treeView.setRoot(null);
        rootNode.getChildren().clear();
        String Info = null;

        Image fieldImage = new Image("resources/field.png");
        Image dotImage = new Image("resources/dot.png");

        for (FormField field : form.getFields()) {
            TreeItem<node> fieldNode = new TreeItem<>(new node(field.description+"/"+field.id, field.description+"/"+field.id,  fieldImage));
            Info = "Type : " + field.type;
            fieldNode.getChildren().add(new TreeItem<>(new node(Info,Info, dotImage)));
            Info = "Starting Point :("+Double.toString(field.x) +","+ Double.toString(field.y)+")";
            fieldNode.getChildren().add(new TreeItem<>(new node(Info,Info, dotImage)));
            Info ="Width = " +  Double.toString(field.width);
            fieldNode.getChildren().add(new TreeItem<>(new node(Info,Info, dotImage)));
            Info = "Height = " +Double.toString(field.height);
            fieldNode.getChildren().add(new TreeItem<>(new node(Info,Info, dotImage)));
            rootNode.getChildren().add(fieldNode); 
        }
        treeView.setRoot(rootNode);
    }    
    
    
    
    
    private final class AlertTreeCell extends TreeCell<node> {

        private final AnchorPane anchorPane;
        private final Label label;
        private final ImageView icon;
        

        public AlertTreeCell() {
            anchorPane = new AnchorPane();
            label = new Label();
            icon = new ImageView();
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            anchorPane.getChildren().addAll(icon,label);
            anchorPane.setPadding(new javafx.geometry.Insets(1));
            anchorPane.setLeftAnchor(label, 20.0);
        }

        @Override
        public void updateItem(node item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(null);
                label.setText(item.getStatus()); 
                icon.setImage(item.getIcon());
                setGraphic(anchorPane);
            }
        }
    }

    public static class node {

        private final SimpleStringProperty name;
        private final SimpleStringProperty status;
        private Image icon;

    

        private node(String name, String department, Image icon) {
            this.icon = icon;
            this.name = new SimpleStringProperty(name);
            this.status = new SimpleStringProperty(department);

            
            
        }

        public String getName() {
            return name.get();
        }

        public void setName(String fName) {
            name.set(fName);
        }

        public String getStatus() {
            return status.get();
        }

        public void setStatus(String fName) {
            status.set(fName);
        }
        
            public Image getIcon() {
            return icon;
        }

        public void setIcon(Image icon) {
            this.icon = icon;
        }
        

    }
    
}
