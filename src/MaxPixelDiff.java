import java.awt.Color;
import java.awt.image.BufferedImage;

public class MaxPixelDiff implements ErrorCalculator {
    @Override
    public double calculateError(BufferedImage image, Node node) {
        int maxR = 0, minR = 255;
        int maxG = 0, minG = 255;
        int maxB = 0, minB = 255;

        for (int j = node.getY(); j < node.getY() + node.getHeight(); j++) {
            for (int i = node.getX(); i < node.getX() + node.getWidth(); i++) {
                if (i < image.getWidth() && j < image.getHeight()) {
                    Color pixelColor = new Color(image.getRGB(i, j));
                    
                    // Update max and min for each channel
                    maxR = Math.max(maxR, pixelColor.getRed());
                    minR = Math.min(minR, pixelColor.getRed());
                    
                    maxG = Math.max(maxG, pixelColor.getGreen());
                    minG = Math.min(minG, pixelColor.getGreen());
                    
                    maxB = Math.max(maxB, pixelColor.getBlue());
                    minB = Math.min(minB, pixelColor.getBlue());
                }
            }
        }

        // Calculate differences
        int diffR = maxR - minR;
        int diffG = maxG - minG;
        int diffB = maxB - minB;

        // Average difference across all channels
        double avgDiff = (diffR + diffG + diffB) / 3.0;
        return avgDiff;
    }

    @Override
    public String getMethodName() {
        return "Max Pixel Difference";
    }
}