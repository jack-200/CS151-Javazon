module edu.sjsu.cs.cs151javazon {
    requires javafx.controls;
    requires javafx.fxml;
    opens edu.sjsu.cs.cs151javazon to javafx.fxml;
    exports edu.sjsu.cs.cs151javazon;
    exports edu.sjsu.cs.cs151javazon.exceptions;
    opens edu.sjsu.cs.cs151javazon.exceptions to javafx.fxml;
}