import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class FormEditor extends Application {

    private Form form = new Form();

    public static String lastSelectedFieldType;
    public static String lastSelectedGroupType;
    public static ArrayList<String> fieldTypes = new ArrayList<>();
    public static ArrayList<String> groupTypes = new ArrayList<>();
  
    public static TreeMenu fieldsMenu = new TreeMenu();
    public static TreeMenu groupMenu = new TreeMenu();
    
    public static boolean selectionFlag = false;
    public static boolean drawAllFlag = false;
    public static boolean allowUpdateScrolling = true;
    public static boolean highlightedField = false;
    public static boolean highlightedGroup = false;
    
    public static Double delta = 1.0;
    public static double currentScrollValue;
    
    private Zooming zoomElement = new Zooming();
    private DrawingRectangles drawingRects = new DrawingRectangles();
    private LoadImage imageLoader = new LoadImage();
    private LoadPdf pdfLoader = new LoadPdf();
    private LoadJson jsonLoader = new LoadJson();
    private ExportXml xmlExporter = new ExportXml();
    private ExportJson jsonExporter = new ExportJson();
    private SelectMenuItem itemSelecter = new SelectMenuItem();
    private RectangleAdder rectangleAdder = new RectangleAdder();
    private RectangleEditor rectangleEditor = new RectangleEditor();
    private RectangleDeleter rectangleDeleter = new RectangleDeleter();

    private MenuItem addMenuItem = new MenuItem("Add");
    private MenuItem editMenuItem = new MenuItem("Edit");
    private MenuItem deleteMeuItem = new MenuItem("Delete");
    
    private MenuItem editFieldMenuItem = new MenuItem("Edit");
    private MenuItem editGroupMenuItem = new MenuItem("Edit");
    private MenuItem deleteGroupMenuItem = new MenuItem("Delete");

   
    private int selectedIndex = -40;
    private int index = -40;
    private int groupSelectedIndex = -40;
    private int groupIndex = -40;

    private ImageView imageView = new ImageView();


    @Override
    public void start(Stage primaryStage) {

        // Setting type values --------------------------------------------------------------------------------
        fieldTypes.add("Text Field");
        fieldTypes.add("Check Box");
        fieldTypes.add("Image");
        fieldTypes.add("Numeric Field");
        fieldTypes.add("Date");
        fieldTypes.add("Other");

        lastSelectedFieldType = "Text Field";
        
        groupTypes.add("Container");
        groupTypes.add("Radio Group");
        groupTypes.add("Other");
        
        lastSelectedGroupType = "Container";

        // view elements --------------------------------------------------------------------------------------

        VBox sideMenu = new VBox();
        VBox sideContainer = new VBox();
   
        primaryStage.setTitle("Untitled - HWR Form Editor");
        primaryStage.getIcons().add(new Image("resources/editorIcon.png"));
       

        BorderPane container = new BorderPane();
        container.setPadding(new Insets(0, 20, 20, 20));

        sideMenu.getChildren().add(fieldsMenu.treeView);
        sideMenu.getChildren().add(groupMenu.treeView);
        sideMenu.setSpacing(10);
       

        sideContainer.setSpacing(10);
        sideContainer.setPadding(new Insets(10));
        Separator separatingLine = new Separator();
        separatingLine.setOrientation(Orientation.HORIZONTAL);
        sideContainer.getChildren().add(sideMenu);
        sideContainer.getChildren().add(1, separatingLine);
        sideMenu.getChildren().add(1, separatingLine);


        // create context menu and menu items
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(addMenuItem);
        contextMenu.getItems().add(editMenuItem);
        contextMenu.getItems().add(deleteMeuItem);
        editMenuItem.setDisable(true);
        deleteMeuItem.setDisable(true); 
        
        ContextMenu fieldContextMenu = new ContextMenu();
        fieldContextMenu.getItems().add(editFieldMenuItem);
        
        ContextMenu groupContextMenu = new ContextMenu();
        groupContextMenu.getItems().add(editGroupMenuItem);
        groupContextMenu.getItems().add(deleteGroupMenuItem);

        // container for image layers
        ScrollPane scroller = new ScrollPane();
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
 
        // image layer: a group of images
        Group imageLayer = new Group();
        RubberBandSelection rubberBandSelection = new RubberBandSelection(form, imageLayer, imageView);

        // add image to layer
        imageLayer.getChildren().add(imageView);

        // use scrollpane for image view in case the image is large
        scroller.setContent(imageLayer);
        

        // Create top menu
        ImageView  imageIcon = new ImageView(new Image("resources/image.png"));
        imageIcon.setFitHeight(20);
        imageIcon.setFitWidth(20);
        ImageView pdfIcon = new ImageView (new Image("resources/pdf.png"));
        pdfIcon.setFitHeight(20);
        pdfIcon.setFitWidth(20);
        ImageView importIcon = new ImageView (new Image("resources/folder.png"));
        importIcon.setFitHeight(20);
        importIcon.setFitWidth(20);
        ImageView exportIcon = new ImageView (new Image("resources/save.png"));
        exportIcon.setFitHeight(20);
        exportIcon.setFitWidth(20);
        ImageView exportIcon1 = new ImageView (new Image("resources/save.png"));
        exportIcon1.setFitHeight(20);
        exportIcon1.setFitWidth(20);
        ImageView exportIcon2 = new ImageView (new Image("resources/save.png"));
        exportIcon2.setFitHeight(20);
        exportIcon2.setFitWidth(20);
        ImageView exportIcon3 = new ImageView (new Image("resources/save.png"));
        exportIcon3.setFitHeight(20);
        exportIcon3.setFitWidth(20);
        ImageView createGroupIcon = new ImageView (new Image("resources/createGroup.png"));
        createGroupIcon.setFitHeight(20);
        createGroupIcon.setFitWidth(20);
        ImageView addToGroupIcon = new ImageView (new Image("resources/addToGroup.png"));
        addToGroupIcon.setFitHeight(20);
        addToGroupIcon.setFitWidth(20);
        ImageView zoomInIcon = new ImageView (new Image("resources/zoomIn.png"));
        zoomInIcon.setFitHeight(20);
        zoomInIcon.setFitWidth(20);
        ImageView zoomOutIcon = new ImageView (new Image("resources/zoomOut.png"));
        zoomOutIcon.setFitHeight(20);
        zoomOutIcon.setFitWidth(20);
        ImageView zoomInIcon1 = new ImageView (new Image("resources/zoomIn.png"));
        zoomInIcon1.setFitHeight(20);
        zoomInIcon1.setFitWidth(20);
        ImageView zoomOutIcon1 = new ImageView (new Image("resources/zoomOut.png"));
        zoomOutIcon1.setFitHeight(20);
        zoomOutIcon1.setFitWidth(20);
        ImageView drawAllIcon = new ImageView (new Image("resources/drawAll.png"));
        drawAllIcon.setFitHeight(20);
        drawAllIcon.setFitWidth(20);
        ImageView settingsIcon = new ImageView (new Image("resources/settings.png"));
        settingsIcon.setFitHeight(20);
        settingsIcon.setFitWidth(20);
        
        MenuBar topMenu = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu viewMenu = new Menu("View");
        topMenu.getMenus().addAll(fileMenu, editMenu, viewMenu);
        
        //add items to file menu
        MenuItem openImageMenuButton = new MenuItem("Open Image");
        openImageMenuButton.setGraphic(imageIcon);
        MenuItem openPdfMenuButton = new MenuItem("Open PDF");
        openPdfMenuButton.setGraphic(pdfIcon);
        MenuItem importJsonMenuButton = new MenuItem("Import JSON");
        importJsonMenuButton.setGraphic(importIcon);
        MenuItem exportJsonMenuButton = new MenuItem("Export as JSON");
        exportJsonMenuButton.setGraphic(exportIcon);
        MenuItem exportXmlMenuButton = new MenuItem("Export as XML");
        exportXmlMenuButton.setGraphic(exportIcon1);
        MenuItem settingsMenuButton = new MenuItem("Settings");
        settingsMenuButton.setGraphic(settingsIcon);
        fileMenu.getItems().addAll(openImageMenuButton,openPdfMenuButton,importJsonMenuButton,exportJsonMenuButton,exportXmlMenuButton,settingsMenuButton);
        
        //add items to edit menu
        MenuItem createGroupMenuButton = new MenuItem("Create Group");
        createGroupMenuButton.setGraphic(createGroupIcon);
        MenuItem groupingMenuButton = new MenuItem("Add to Group");
        groupingMenuButton.setGraphic(addToGroupIcon);
        editMenu.getItems().addAll(createGroupMenuButton,groupingMenuButton);
        
        //add items to view menu
        MenuItem zoomInMenuButton = new MenuItem("Zoom IN");
        zoomInMenuButton.setGraphic(zoomInIcon);
        MenuItem zoomOutMenuButton = new MenuItem("Zoom OUT");
        zoomOutMenuButton.setGraphic(zoomOutIcon);
        CheckMenuItem drawAllMenuButton = new CheckMenuItem("Draw All");
        drawAllMenuButton.setGraphic(drawAllIcon);
        viewMenu.getItems().addAll(zoomInMenuButton,zoomOutMenuButton,drawAllMenuButton);
        

        //create buttons bar
        Button exportJsonIconsMenuButton = new Button("JSON");
        exportJsonIconsMenuButton.setGraphic(exportIcon2);
        Button exportXmlIconsMenuButton = new Button("XML");
        exportXmlIconsMenuButton.setGraphic(exportIcon3);
        Button zoomINIconsMenuButton = new Button("Zoom IN");
        zoomINIconsMenuButton.setGraphic(zoomInIcon1);
        Button zoomOUTIconsMenuButton = new Button("Zoom OUT");
        zoomOUTIconsMenuButton.setGraphic(zoomOutIcon1);
        
        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.setPadding(new Insets(0,0,0,30));
        buttonsBox.getChildren().addAll(exportJsonIconsMenuButton,exportXmlIconsMenuButton,zoomINIconsMenuButton,zoomOUTIconsMenuButton);

        
        // set menu bars
        VBox menuBars = new VBox();
        menuBars.getChildren().addAll(topMenu,buttonsBox);
        menuBars.setSpacing(10);
        

        // put scrollpane in scene
        container.setCenter(scroller);
        container.setLeft(sideContainer);
       
        //set margins for container
        BorderPane.setMargin(scroller, new Insets(10));
        BorderPane.setMargin(sideMenu, new Insets(10));
        
        //add top menu and container to root element
        VBox root = new VBox();
        root.getChildren().addAll(menuBars, container);
        root.setSpacing(10);

        
        //set the primary stage
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();


        // buttons functions --------------------------------------------------------------------------------------
        
        openImageMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                delta = 1.0;
                if (form.getFields().size() != 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Loading a new image");
                    alert.setContentText("Are you sure you want to load a new image? your data will be lost if it's not saved");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        imageLoader.loadImageInImageLayer(form, imageView);
                        form.startOver();
                        primaryStage.setTitle("Untitled - HWR Form Editor");
                    }
                } else {
                    imageLoader.loadImageInImageLayer(form, imageView);
                }
                drawingRects.clearLayer(imageLayer, imageView, scroller); 
                imageView.setFitWidth(imageView.getImage().getWidth());
                imageView.setFitHeight(imageView.getImage().getHeight());
                imageView.preserveRatioProperty().set(true);
                drawAllFlag = false;
                drawAllMenuButton.setSelected(false);
                
            }
        });
        
        openPdfMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                delta = 1.0;
                if (form.getFields().size() != 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Loading a new PDF");
                    alert.setContentText("Are you sure you want to load a new PDF? your data will be lost if it's not saved");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        try {
                            pdfLoader.loadPdfInImageLayer(form, imageView);
                        } catch (IOException ex) {
                            Logger.getLogger(HWRFormEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        form.startOver();
                        primaryStage.setTitle("Untitled - HWR Form Editor");
                    }
                } else {
                    try {
                        pdfLoader.loadPdfInImageLayer(form, imageView);
                    } catch (IOException ex) {
                        Logger.getLogger(HWRFormEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                drawingRects.clearLayer(imageLayer, imageView, scroller);
                imageView.setFitWidth(imageView.getImage().getWidth());
                imageView.setFitHeight(imageView.getImage().getHeight());
                imageView.preserveRatioProperty().set(true);
                drawAllFlag = false;
                drawAllMenuButton.setSelected(false);
            }

        });
        
        importJsonMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                delta = 1.0;
                if (form.getFields().size() != 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Loading a new JSON");
                    alert.setContentText("Are you sure you want to load a new JSON? your data will be lost if it's not saved");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        try {     
                            Form form1 = jsonLoader.loadJsonFile(primaryStage , imageView);
                            form.setFields(form1.getFields());
                            form.setGroups(form1.getGroups());
                            form.setId(form1.getId());
                            form.setImageString(form1.getImageString());
                            form.setName(form1.getName());
                            form.setLanguage(form1.getLanguage());
                            form.setMetaData(form1.getMetaData());
                        } catch (IOException ex) {
                            Logger.getLogger(HWRFormEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        fieldsMenu.updateRectanglesMenu(form);
                        groupMenu.updateGroupMenu(form);
                    }
                } else {
                    try {
                        Form form1 = jsonLoader.loadJsonFile(primaryStage , imageView);
                        form.setFields(form1.getFields());
                        form.setGroups(form1.getGroups());
                        form.setId(form1.getId());
                        form.setImageString(form1.getImageString());
                        form.setName(form1.getName());
                        form.setLanguage(form1.getLanguage());
                        form.setMetaData(form1.getMetaData());
                    } catch (IOException ex) {
                        Logger.getLogger(HWRFormEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                drawingRects.clearLayer(imageLayer, imageView, scroller);
                fieldsMenu.updateRectanglesMenu(form);
                groupMenu.updateGroupMenu(form); 
                imageView.setFitWidth(imageView.getImage().getWidth());
                imageView.setFitHeight(imageView.getImage().getHeight());
                imageView.preserveRatioProperty().set(true);
                drawAllFlag = false;
                drawAllMenuButton.setSelected(false);
            }
        });

        zoomINIconsMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                zoomElement.zoom(imageView, scroller, delta, delta + 10);
                delta = delta + 10;
                if (drawAllFlag){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    drawingRects.drawAll(form, imageLayer);
                    highlightedField = false;
                    highlightedGroup = false;
                }
                else if (highlightedField){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    drawingRects.drawSelected(form,imageLayer,selectedIndex );
                    highlightedGroup = false;
                    drawAllFlag = false;
                }
                else if(highlightedGroup){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    form.getGroups().get(groupIndex).highlightChildren(form, imageLayer);
                    
                     highlightedField = false;
                    drawAllFlag = false;
                }
                
              
            }

        });
        
        zoomInMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                zoomElement.zoom(imageView, scroller, delta, delta + 10);
                delta = delta + 10;
                if (drawAllFlag){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    drawingRects.drawAll(form, imageLayer);
                    highlightedField = false;
                    highlightedGroup = false;
                }
                else if (highlightedField){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    drawingRects.drawSelected(form,imageLayer,selectedIndex );
                    highlightedGroup = false;
                    drawAllFlag = false;
                }
                 else if(highlightedGroup){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    form.getGroups().get(groupIndex).highlightChildren(form, imageLayer);
                    highlightedField = false;
                    drawAllFlag = false;
                }
              
            }

        });

   
        zoomOUTIconsMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                zoomElement.zoom(imageView, scroller, delta, delta - 10);
                delta = delta - 10;
                if (drawAllFlag){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    drawingRects.drawAll(form, imageLayer);
                    highlightedField = false;
                    highlightedGroup = false;
                }
                else if (highlightedField){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    drawingRects.drawSelected(form,imageLayer,selectedIndex );
                    highlightedGroup = false;
                    drawAllFlag = false;
                }
                else if(highlightedGroup){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    form.getGroups().get(groupIndex).highlightChildren(form, imageLayer);
                    highlightedField = false;
                    drawAllFlag = false;
                }
                  

            }
        });
        
       
        zoomOutMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                zoomElement.zoom(imageView, scroller, delta, delta - 10);
                delta = delta - 10;
                if (drawAllFlag){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    drawingRects.drawAll(form, imageLayer);
                    highlightedField = false;
                    highlightedGroup = false;
                }
                else if (highlightedField){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    drawingRects.drawSelected(form,imageLayer,selectedIndex );
                    highlightedGroup = false;
                    drawAllFlag = false;
                }
                  else if(highlightedGroup){
                    imageLayer.getChildren().clear();
                    imageLayer.getChildren().add(imageView);
                    form.getGroups().get(groupIndex).highlightChildren(form, imageLayer);
                    highlightedField = false;
                    drawAllFlag = false;
                }
               
            }
        });
        
        createGroupMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                GroupInformationPopUp moreInfo = new GroupInformationPopUp(form);
            }
        });
        
        groupingMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (form.getGroups().size()==0){
                    WarningPopUp noGroupPopUp = new WarningPopUp("Warning", "Please create a group before grouping items");
                }
                else{
                    AddToGroupPopUp addingPopUp = new AddToGroupPopUp(form);
                }
                
            }

        });
        
        drawAllMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (drawAllFlag){
                    drawingRects.clearLayer(imageLayer, imageView, scroller);
                    drawAllFlag = false;
                }
                else{
                    drawingRects.drawAll(form, imageLayer);
                    drawAllFlag = true;
                    highlightedField = false;
                    highlightedGroup = false;
                }
            }
        });
        

        exportXmlIconsMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                xmlExporter.exportXmlFile(primaryStage, form);
            }
        });
        
        exportXmlMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                xmlExporter.exportXmlFile(primaryStage, form);
            }
        });

        
        exportJsonIconsMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                jsonExporter.exportJsonFile(primaryStage, form);
            }
        });
        
        exportJsonMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                jsonExporter.exportJsonFile(primaryStage, form);
            }
        });
        
        settingsMenuButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FormSettingsPopUp settingsPopUp = new FormSettingsPopUp(form);
            }
        });


        // clickable items functions --------------------------------------------------------------------------------------

        fieldsMenu.treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                TreeItem<TreeMenu.node> selectedItem = fieldsMenu.treeView.getSelectionModel().getSelectedItem();
                String selectedItemValue = selectedItem.getValue().getName();
                String segments[] = selectedItemValue.split("/");
                String selectedItemId = segments[segments.length - 1];
                if(selectedItem.isLeaf()){
                    System.out.println("leaf node, no highlighting");
                }
                else{ 
                   for (int i=0;i<form.getFields().size();i++){
                       if (form.getFields().get(i).id.equals(selectedItemId)){
                           index = i;
                           break;
                       }
                   }
                   selectedIndex = itemSelecter.handleSelection(form, fieldsMenu.treeView, drawingRects, selectedIndex, imageLayer, imageView,index, scroller); 
                   if (drawAllFlag){
                       drawAllMenuButton.setSelected(false);
                       drawAllFlag = false;
                   }
                }
                
            }
        });
        
        fieldsMenu.treeView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
       
                if (event.isSecondaryButtonDown()) {
                    fieldContextMenu.show(imageLayer, event.getScreenX(), event.getScreenY());
                }
            }
        });
        
        
        
                
        groupMenu.treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
             
                TreeItem<TreeMenu.node> selectedItem = groupMenu.treeView.getSelectionModel().getSelectedItem();
                String selectedItemValue = selectedItem.getValue().getName();
                String segments[] = selectedItemValue.split("/");
                String selectedItemId = segments[segments.length - 1];
                if(selectedItem.isLeaf()){
                    System.out.println("leaf node, no highlighting");
                }
                else{ 
                   for (int i=0;i<form.getGroups().size();i++){
                       if (form.getGroups().get(i).id.equals(selectedItemId)){
                           groupIndex = i;
                           break;
                       }
                   }
                   groupSelectedIndex = itemSelecter.handleGroupSelection(form, groupMenu.treeView, drawingRects, groupSelectedIndex, imageLayer, imageView,groupIndex, scroller); 
                   if (drawAllFlag){
                       drawAllMenuButton.setSelected(false);
                       drawAllFlag = false;
                   }
                }
            }
            
        });
        
        groupMenu.treeView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
       
                if (event.isSecondaryButtonDown()) {
                    groupContextMenu.show(imageLayer, event.getScreenX(), event.getScreenY());
                }
            }
        });

        imageLayer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
                if(fieldsMenu.treeView.getSelectionModel().getSelectedIndex() !=-1){
                    editMenuItem.setDisable(false);
                    deleteMeuItem.setDisable(false);
                    selectionFlag = true;
                } else {
                    editMenuItem.setDisable(true);
                    deleteMeuItem.setDisable(true);
                    selectionFlag = false;
                }
                if (event.isSecondaryButtonDown()) {
                    contextMenu.show(imageLayer, event.getScreenX(), event.getScreenY());
                }
            }
        });

        addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                rectangleAdder.addRectangle(form, rubberBandSelection);
            }
        });

        editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                rectangleEditor.editRectangle(form, rubberBandSelection, imageLayer, imageView, selectedIndex, drawingRects, scroller);
            }
        });

        deleteMeuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                rectangleDeleter.deleteRectangle(form, imageLayer, imageView, selectedIndex, drawingRects, scroller);
            }
        });
        
        editFieldMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                EditFieldInformationPopUp editPopUp = new EditFieldInformationPopUp(form, selectedIndex);
            }
        });
        
        editGroupMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                TreeItem<TreeMenu.node> selectedItem = groupMenu.treeView.getSelectionModel().getSelectedItem();
                if (!selectedItem.isLeaf()){
                    EditGroupInformationPopup editPopUp = new EditGroupInformationPopup(form, groupSelectedIndex, drawingRects , imageLayer,imageView, scroller);
                }   
            }
        });
        
         deleteGroupMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                TreeItem<TreeMenu.node> selectedItem = groupMenu.treeView.getSelectionModel().getSelectedItem();
                if (!selectedItem.isLeaf()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Deleting the selected group");
                    alert.setContentText("Are you sure to delete this group?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        form.getGroups().remove(groupSelectedIndex);
                        drawingRects.clearLayer(imageLayer, imageView, scroller);
                        fieldsMenu.updateRectanglesMenu(form);
                        groupMenu.updateGroupMenu(form);

                    }
                }
                
            }
        });
         
         
         scroller.vvalueProperty().addListener(new ChangeListener<Number>() 
        {
          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
          {
            if (allowUpdateScrolling){
                currentScrollValue = scroller.vvalueProperty().getValue();
                System.out.println("current scrolling = " + scroller.vvalueProperty().getValue());
            }
            
          }
        });

    }

    // main function --------------------------------------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }

}