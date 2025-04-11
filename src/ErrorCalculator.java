import java.awt.image.BufferedImage;

public interface ErrorCalculator {
    double calculateError(BufferedImage image, Node node);
    String getMethodName();
}