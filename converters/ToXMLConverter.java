import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ToXMLConverter {

    boolean flag = false;
    
    public void writeXmlFile(Stage primaryStage , Form form, String path , String fileName) {

        try {

            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();


            Element root = doc.createElement("Form");
            doc.appendChild(root);

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(form.getId()));
            root.appendChild(id);

            if (form.getName().equals("")){
                form.setName(fileName);
                flag=true;
            }
            
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(form.getName()));
            root.appendChild(name);
            
            if(flag==true){
                form.setName("");
                flag=false;
            }
            
            Element language = doc.createElement("language");
            language.appendChild(doc.createTextNode(form.getLanguage()));
            root.appendChild(language);
            
            

            Element formField = doc.createElement("fields");
            root.appendChild(formField);


            if(form.getFields().size()!=0){
                for (int i = 0; i < form.getFields().size(); i++) {
                    FormField item = form.getFields().get(i);
                    Element field = doc.createElement("field");
                    formField.appendChild(field);
                    addFormField(item, doc, field);
                }
            }
           
            
            Element formGroup = doc.createElement("groups");
            root.appendChild(formGroup);

            if (form.getGroups().size()!=0){
                for (int i = 0; i < form.getGroups().size(); i++) {
                    FormGroup item = form.getGroups().get(i);
                    Element group = doc.createElement("group");
                    formGroup.appendChild(group);
                    addGroup(item, doc, group);
                } 
            }
            
            Element metaData = doc.createElement("metaData");
            root.appendChild(metaData);

            if (form.getMetaData().size()!=0){
                for (int i = 0; i < form.getMetaData().size(); i++) {
                    MetaDataItem item = form.getMetaData().get(i);
                    Element metaItem = doc.createElement("pair");
                    metaData.appendChild(metaItem);
                    addMetaItem(item, doc, metaItem);
                } 
            }
           



            Element imageBase64String = doc.createElement("imageString");
            imageBase64String.appendChild(doc.createTextNode(form.getImageString()));
            root.appendChild(imageBase64String);


            // Save the document to the disk file
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            // format the XML nicely
            aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

            aTransformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");


            DOMSource source = new DOMSource((Node) doc);
            try {
                FileWriter fos = new FileWriter(path + ".xml");
                StreamResult result = new StreamResult(fos);
                aTransformer.transform(source, result);
                fos.close();
                primaryStage.setTitle(fileName+" - HWR Form Editor");

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO error");
            }


        } catch (TransformerException ex) {
            System.out.println("Error outputting document");

        } catch (ParserConfigurationException ex) {
            System.out.println("Error building document");
        }
    }

    public void addFormField(FormField item, Document doc, Element field) {

        Element fieldID = doc.createElement("id");
        fieldID.appendChild(doc.createTextNode(item.id));
        field.appendChild(fieldID);

        Element description = doc.createElement("description");
        description.appendChild(doc.createTextNode(item.description));
        field.appendChild(description);

        Element type = doc.createElement("type");
        type.appendChild(doc.createTextNode(item.type));
        field.appendChild(type);

        Element xCoordinate = doc.createElement("x");
        xCoordinate.appendChild(doc.createTextNode(Double.toString(item.x)));
        field.appendChild(xCoordinate);

        Element yCoordinate = doc.createElement("y");
        yCoordinate.appendChild(doc.createTextNode(Double.toString(item.y)));
        field.appendChild(yCoordinate);

        Element width = doc.createElement("width");
        width.appendChild(doc.createTextNode(Double.toString(item.width)));
        field.appendChild(width);

        Element height = doc.createElement("height");
        height.appendChild(doc.createTextNode(Double.toString(item.height)));
        field.appendChild(height);
    }

    public void addGroup(FormGroup item, Document doc, Element group) {

        Element fieldID = doc.createElement("id");
        fieldID.appendChild(doc.createTextNode(item.id));
        group.appendChild(fieldID);

        Element description = doc.createElement("description");
        description.appendChild(doc.createTextNode(item.description));
        group.appendChild(description);

        Element children = doc.createElement("children");
        for (int i=0;i<item.children.size();i++){
            Element child = doc.createElement("child");
            child.appendChild(doc.createTextNode(item.children.get(i)));
            children.appendChild(child);
        }
        group.appendChild(children);

    }
    
    public void addMetaItem (MetaDataItem item, Document doc, Element metaItem){
        
        Element key = doc.createElement("key");
        key.appendChild(doc.createTextNode(item.getKey()));
        metaItem.appendChild(key);

        Element value = doc.createElement("value");
        value.appendChild(doc.createTextNode(item.getValue()));
        metaItem.appendChild(value);
    }
}
