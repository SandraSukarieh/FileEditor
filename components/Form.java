import java.util.ArrayList;

/**
 * @author Sandra
 */
public class Form {

    private String id;
    private String name;
    private String language;
    private ArrayList<FormField> fields = new ArrayList<>();
    private ArrayList<FormGroup> groups = new ArrayList<>();
    private ArrayList<MetaDataItem> metaData = new ArrayList<>();
    private String imageString;

    public Form() {
        this.id="";
        this.name="";
        this.imageString="";
        this.language="en";
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FormField> getFields() {
        return fields;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public ArrayList<FormGroup> getGroups() {
        return groups;
    }
    
     public void setFields(ArrayList<FormField> fields) {
        this.fields = fields;
    }

    public void setGroups(ArrayList<FormGroup> groups) {
        this.groups = groups;
    }
    
    
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    public ArrayList<MetaDataItem> getMetaData() {
        return metaData;
    }

    public void setMetaData(ArrayList<MetaDataItem> metaData) {
        this.metaData = metaData;
    }

    public void startOver() {
        this.getFields().clear();
        this.getGroups().clear();
        this.getMetaData().clear();
        this.setName("");
        fieldsMenu.updateRectanglesMenu(this);
        groupMenu.updateGroupMenu(this);
        delta = 1.0;
        System.out.println("form starting over, delta = " + delta);
    }
    
    public String getDescriptionById (String id){
        String description = null;
        String key = id.substring(0, Math.min(id.length(), 5));
        if (key.equals("GROUP")){
            for (FormGroup group : getGroups()){
                if (group.id.equals(id)){
                    description = group.description;
                    break;
                }
            }
        }
        else if (key.equals("FIELD")){
            for (FormField field : getFields()){
                if (field.id.equals(id)){
                    description = field.description;
                    break;
                }
            }
        }
        return description;
    }
}