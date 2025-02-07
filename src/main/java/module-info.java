

module org.example.bibliotecafx {
        requires javafx.controls;
        requires javafx.fxml;
        requires javafx.web;

        requires org.controlsfx.controls;
        requires com.dlsc.formsfx;
        requires net.synedra.validatorfx;
        requires org.kordamp.ikonli.javafx;
        requires org.kordamp.bootstrapfx.core;
        requires com.almasb.fxgl.all;
        requires org.hibernate.orm.core;
        requires jakarta.persistence;
        requires annotations;

        opens org.example.GestionLibros to javafx.fxml;
        exports org.example.bibliotecahiberfx;

        opens org.example.bibliotecafx.entities to org.hibernate.orm.core;
        }