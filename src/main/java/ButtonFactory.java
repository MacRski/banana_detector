import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.bytedeco.javacpp.opencv_core.*;

import java.awt.*;
import java.io.File;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

public class ButtonFactory {

    public static Button createButton(String jpgPath, VBox centerMenu) {
        Image img1 = new Image(new File(jpgPath).toURI().toString(), 100, 100, true, true);

        Button button = new Button("Detect", new ImageView(img1));
        button.setStyle("-fx-font: 11 arial; -fx-base: #b6e7c9;");
        button.setOnAction(action -> {
        Integer bananas = showBananas(jpgPath);
        Text msg = new Text("Detected number of Bananas: " + bananas);
            msg.setX(20.0f);
            msg.setY(65.0f);
            msg.setText("Detected number of Bananas:" + bananas);
            msg.setFont(javafx.scene.text.Font.font(Font.BOLD));
        centerMenu.getChildren().add(msg);
        });
        return button;
    }

    private static int showBananas(String jpgPath) {
        IplImage img = cvLoadImage(jpgPath);
        return Detector.detect(img);
    }
}
