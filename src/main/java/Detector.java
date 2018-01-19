import static org.bytedeco.javacpp.opencv_highgui.destroyAllWindows;
import static org.bytedeco.javacpp.opencv_highgui.imshow;
import static org.bytedeco.javacpp.opencv_highgui.waitKey;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

import java.util.LinkedList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class Detector {
    private static OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
    private static final String XML_FILE = "resources/banana_classifier.xml";

    @SuppressWarnings("resource")
    public static int detect(IplImage src) {
        CascadeClassifier cascade = new CascadeClassifier(XML_FILE);
        Mat mat = new Mat();
        mat = convertImgToMat(src);
        Mat grayImg = new Mat();
        convertToGray(mat, grayImg);
        List<Rect> bananas = detectBananas(cascade, mat, grayImg);
        System.out.println("Detected bananas number: " + bananas.size());
        System.out.println("To end loop press ESC...");
        while (true) {
            imshow("Detected bananas", mat);
            char key = (char) waitKey(20);
            if (key == 27) {
                destroyAllWindows();
                break;
            }
        }
        return bananas.size();
    }

    private static List<Rect> detectBananas(CascadeClassifier cascade, Mat mat, Mat grayImg) {
        RectVector vector = new RectVector();

        cascade.detectMultiScale(grayImg, vector);

        return drawDetectedObjects(mat, vector);
    }

    private static Mat convertImgToMat(IplImage src) {
        Frame frame = converterToMat.convert(src);
        return converterToMat.convert(frame);
    }

    private static List<Rect> drawDetectedObjects(Mat mat, RectVector vector) {
        List<Rect> bananas = vectorToList(vector);
        for (Rect banana : bananas) {
            rectangle(mat, banana, new Scalar(0, 255, 0, 1));
        }
        return bananas;
    }

    private static void convertToGray(Mat mat, Mat videoMatGray) {
        cvtColor(mat, videoMatGray, COLOR_BGRA2GRAY);
        equalizeHist(videoMatGray, videoMatGray);
    }

    private static List<Rect> vectorToList(RectVector vector) {
        List<Rect> rectangles = new LinkedList<Rect>();
        for (int i = 0; i < vector.size(); i++)
            rectangles.add(vector.get(i));
        return rectangles;
    }
}
