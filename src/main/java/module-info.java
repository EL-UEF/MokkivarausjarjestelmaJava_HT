module student.example.mokkivarausjarjestelmajava_ht {
    requires javafx.controls;
    requires javafx.fxml;


    opens student.example.mokkivarausjarjestelmajava_ht to javafx.fxml;
    exports student.example.mokkivarausjarjestelmajava_ht;
}