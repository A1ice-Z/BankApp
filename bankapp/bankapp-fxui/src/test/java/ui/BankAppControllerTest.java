package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Unit test for BankAppController
*/
public class BankAppControllerTest extends ApplicationTest {
    private BankAppController controller;
    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Overview.fxml"));
        root = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testProfileButton() throws IOException {
        clickOn("#profileTab");
        assertEquals(this.stage.getScene().getRoot().getId(), "profile");
    }
}