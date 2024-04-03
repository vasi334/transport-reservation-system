module com.example.javafxmpp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;


    opens com.example.javafxmpp to javafx.fxml;
    exports com.example.javafxmpp;
    exports com.example.javafxmpp.controller;
    opens com.example.javafxmpp.controller to javafx.fxml;
}