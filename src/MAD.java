import java.awt.Color;
import java.awt.image.BufferedImage;

public class MAD implements ErrorCalculator {
    @Override
    public double calculateError(BufferedImage image, Node node) {
        double madR = 0;
        double madG = 0;
        double madB = 0;
        int totalPixels = 0;

        int avgR = 0, avgG = 0, avgB = 0;
        for (int j = node.getY(); j < node.getY() + node.getHeight(); j++) {
            for (int i = node.getX(); i < node.getX() + node.getWidth(); i++) {
                if (i < image.getWidth() && j < image.getHeight()) {
                    Color pixelColor = new Color(image.getRGB(i, j));
                    avgR += pixelColor.getRed();
                    avgG += pixelColor.getGreen();
                    avgB += pixelColor.getBlue();
                    totalPixels++;
                }
            }
        }

        if (totalPixels == 0) return 0;

        avgR /= totalPixels;
        avgG /= totalPixels;
        avgB /= totalPixels;

        for (int j = node.getY(); j < node.getY() + node.getHeight(); j++) {
            for (int i = node.getX(); i < node.getX() + node.getWidth(); i++) {
                if (i < image.getWidth() && j < image.getHeight()) {
                    Color pixelColor = new Color(image.getRGB(i, j));
                    madR += Math.abs(pixelColor.getRed() - avgR);
                    madG += Math.abs(pixelColor.getGreen() - avgG);
                    madB += Math.abs(pixelColor.getBlue() - avgB);
                }
            }
        }

        madR /= totalPixels;
        madG /= totalPixels;
        madB /= totalPixels;

        double avgMAD = (madR + madG + madB) / 3.0;
        return avgMAD;
    }

    @Override
    public String getMethodName() {
        return "Mean Absolute Deviation (MAD)";
    }
}