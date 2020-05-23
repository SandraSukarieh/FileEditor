import javafx.scene.control.Alert;

/**
 *
 * @author Sandra
 */
public class WarningPopUp {

    private String title;
    private String body;

    public String getBody() {
        return body;
        //comment for checking
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WarningPopUp(String title, String body) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(body);
        alert.showAndWait();
    }
    
}
