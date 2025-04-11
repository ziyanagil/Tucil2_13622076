import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Entropy implements ErrorCalculator {
    @Override
    public double calculateError(BufferedImage image, Node node) {
        // Initialize frequency maps for each channel
        Map<Integer, Integer> redFreq = new HashMap<>();
        Map<Integer, Integer> greenFreq = new HashMap<>();
        Map<Integer, Integer> blueFreq = new HashMap<>();
        
        int totalPixels = 0;

        // Count frequencies
        for (int j = node.getY(); j < node.getY() + node.getHeight(); j++) {
            for (int i = node.getX(); i < node.getX() + node.getWidth(); i++) {
                if (i < image.getWidth() && j < image.getHeight()) {
                    Color pixelColor = new Color(image.getRGB(i, j));
                    
                    redFreq.put(pixelColor.getRed(), redFreq.getOrDefault(pixelColor.getRed(), 0) + 1);
                    greenFreq.put(pixelColor.getGreen(), greenFreq.getOrDefault(pixelColor.getGreen(), 0) + 1);
                    blueFreq.put(pixelColor.getBlue(), blueFreq.getOrDefault(pixelColor.getBlue(), 0) + 1);
                    
                    totalPixels++;
                }
            }
        }

        if (totalPixels == 0) return 0;

        // Calculate entropy for each channel
        double entropyR = calculateChannelEntropy(redFreq, totalPixels);
        double entropyG = calculateChannelEntropy(greenFreq, totalPixels);
        double entropyB = calculateChannelEntropy(blueFreq, totalPixels);

        // Average entropy across all channels
        return (entropyR + entropyG + entropyB) / 3.0;
    }

    private double calculateChannelEntropy(Map<Integer, Integer> frequencies, int totalPixels) {
        double entropy = 0.0;

        for (Map.Entry<Integer, Integer> entry : frequencies.entrySet()) {
            double probability = (double) entry.getValue() / totalPixels;
            if (probability > 0) {
                entropy -= probability * (Math.log(probability) / Math.log(2));
            }
        }

        return entropy;
    }

    @Override
    public String getMethodName() {
        return "Entropy";
    }
}