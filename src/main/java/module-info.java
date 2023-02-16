module com.franco.fszzipergui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.franco.fszzipergui to javafx.fxml;
    exports com.franco.fszzipergui;
    exports com.franco.fszzipergui.zipClasses;
    opens com.franco.fszzipergui.zipClasses to javafx.fxml;
}