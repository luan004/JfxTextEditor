module CapiFX {
	requires javafx.controls;
	requires javafx.web;
	requires javafx.fxml;
	requires java.desktop;
	requires jdk.jsobject;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
