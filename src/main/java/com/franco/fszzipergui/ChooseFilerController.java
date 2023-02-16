package com.franco.fszzipergui;

import com.franco.fszzipergui.zipClasses.zziperFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseFilerController implements Initializable {

    @FXML
    private Label addres;
    @FXML
    private ListView allAddress;
    @FXML
    private Button buttonChooseFile;
    @FXML
    private Button buttonZip;

    ObservableList<String> items = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonChooseFile.setOnAction(event -> {
            FileChooser fileChooser =  new FileChooser();
            fileChooser.setTitle("Buscar archivos");

            //filters
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All txt file", "*.*"),
                    new FileChooser.ExtensionFilter("TXT", "*.txt")
            );

            //get files
            List<File> txtFile = fileChooser.showOpenMultipleDialog(null);

            if(txtFile!=null){
                for (File file : txtFile) {
                    items.add(file.getPath());
                }
                allAddress.setItems(items);
            }
            zziperFile zip= new zziperFile();
            try {
                zip.zipFileTxt(Paths.get(items.get(0)).toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttonZip.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                zziperFile zip= new zziperFile();
                for (String link: items ) {
                    try {
                        zip.zipFileTxt(Paths.get(link).toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

    }


}
