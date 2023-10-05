module com.example.cgtask2 {
    requires javafx.controls;
    requires javafx.fxml;
    //requires commons.math;
    requires commons.math3;



    opens ru.vsu.cs.kg2023.valyalschikov_d_a to javafx.fxml;
    exports ru.vsu.cs.kg2023.valyalschikov_d_a;
    exports ru.vsu.cs.kg2023.valyalschikov_d_a.spline;
    opens ru.vsu.cs.kg2023.valyalschikov_d_a.spline to javafx.fxml;
}