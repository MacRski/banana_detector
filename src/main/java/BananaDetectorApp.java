import com.google.inject.internal.util.Lists;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class BananaDetectorApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Super Banana Detector");
        VBox centerMenu = new VBox();

        List<Button> buttons = createButtons(centerMenu);

        VBox leftMenu = new VBox();
        leftMenu.getChildren().addAll(buttons);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(leftMenu);
        borderPane.setCenter(centerMenu);
        Scene scene = new Scene(borderPane, 600, 600);
        scene.getStylesheets().add(new File("resources/Viper.css").toURI().toString());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<Button> createButtons(VBox centerMenu) {
        Button multi = ButtonFactory.createButton("resources/multi.jpg", centerMenu);
        Button multi1 = ButtonFactory.createButton("resources/multi1.jpg", centerMenu);
        Button obraz = ButtonFactory.createButton("resources/obraz.jpg", centerMenu);
        Button obraz2 = ButtonFactory.createButton("resources/obraz2.jpg", centerMenu);
        Button test2 = ButtonFactory.createButton("resources/test2.jpg", centerMenu);
        return Lists.newArrayList(multi, multi1, obraz, obraz2, test2);
    }
}