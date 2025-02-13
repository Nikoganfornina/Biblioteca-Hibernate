module org.example.bibliotecafx {
        requires javafx.controls;
        requires javafx.fxml;
        requires javafx.graphics;

        requires org.hibernate.orm.core;
        requires jakarta.persistence;
        requires java.naming;
    requires jdk.compiler;

    opens GestionLibros to javafx.fxml;
        opens entities to org.hibernate.orm.core;

        exports GestionLibros;
        exports entities;
        exports GestionLibros.Controladores;
        opens GestionLibros.Controladores to javafx.fxml;
}
