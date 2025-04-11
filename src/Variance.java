import java.awt.Color;
import java.awt.image.BufferedImage;

public class Variance implements ErrorCalculator {
    @Override
    public double calculateError(BufferedImage image, Node node) {
        double varianceR = 0;
        double varianceG = 0;
        double varianceB = 0;
        int totalPixels = 0;

        // Hitung rata-rata warna
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

        // Hitung varians untuk setiap channel
        for (int j = node.getY(); j < node.getY() + node.getHeight(); j++) {
            for (int i = node.getX(); i < node.getX() + node.getWidth(); i++) {
                if (i < image.getWidth() && j < image.getHeight()) {
                    Color pixelColor = new Color(image.getRGB(i, j));
                    varianceR += Math.pow(pixelColor.getRed() - avgR, 2);
                    varianceG += Math.pow(pixelColor.getGreen() - avgG, 2);
                    varianceB += Math.pow(pixelColor.getBlue() - avgB, 2);
                }
            }
        }

        varianceR /= totalPixels;
        varianceG /= totalPixels;
        varianceB /= totalPixels;

        // Rata-rata varians untuk semua channel
        double avgVariance = (varianceR + varianceG + varianceB) / 3.0;
        return avgVariance;
    }

    @Override
    public String getMethodName() {
        return "Variance";
    }
}