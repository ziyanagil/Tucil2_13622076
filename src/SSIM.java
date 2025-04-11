import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SSIM implements ErrorCalculator {
    // Konstanta perhitungan SSIM
    private static final double C1 = Math.pow(0.01 * 255, 2);
    private static final double C2 = Math.pow(0.03 * 255, 2);
    
    // Bobot untuk setiap channel warna
    private static final double WEIGHT_R = 0.33;
    private static final double WEIGHT_G = 0.33;
    private static final double WEIGHT_B = 0.34;

    @Override
    public double calculateError(BufferedImage image, Node node) {
        // Membuat gambar sementara untuk menyimpan warna rata-rata
        BufferedImage avgImage = new BufferedImage(node.getWidth(), node.getHeight(), BufferedImage.TYPE_INT_RGB);
        Color avgColor = node.getAverageColor();
        
        if (avgColor == null) {
            node.calculateAverageColor(image);
            avgColor = node.getAverageColor();
        }
        
        // Isi gambar sementara dengan warna rata-rata
        int avgRGB = avgColor.getRGB();
        for (int j = 0; j < node.getHeight(); j++) {
            for (int i = 0; i < node.getWidth(); i++) {
                avgImage.setRGB(i, j, avgRGB);
            }
        }
        
        // Ekstrak pixel values dari gambar asli
        List<Integer> redX = new ArrayList<>();
        List<Integer> greenX = new ArrayList<>();
        List<Integer> blueX = new ArrayList<>();
        
        // Ekstrak pixel values dari warna rata-rata
        List<Integer> redY = new ArrayList<>();
        List<Integer> greenY = new ArrayList<>();
        List<Integer> blueY = new ArrayList<>();
        
        for (int j = 0; j < node.getHeight(); j++) {
            for (int i = 0; i < node.getWidth(); i++) {
                int x_i = node.getX() + i;
                int y_j = node.getY() + j;
                
                if (x_i < image.getWidth() && y_j < image.getHeight()) {
                    Color pixelColorX = new Color(image.getRGB(x_i, y_j));
                    redX.add(pixelColorX.getRed());
                    greenX.add(pixelColorX.getGreen());
                    blueX.add(pixelColorX.getBlue());
                    
                    redY.add(avgColor.getRed());
                    greenY.add(avgColor.getGreen());
                    blueY.add(avgColor.getBlue());
                }
            }
        }
        
        // Hitung SSIM untuk setiap channel warna
        double ssimR = calculateChannelSSIM(redX, redY);
        double ssimG = calculateChannelSSIM(greenX, greenY);
        double ssimB = calculateChannelSSIM(blueX, blueY);
        
        // Hitung total SSIM dengan bobot
        double ssimTotal = WEIGHT_R * ssimR + WEIGHT_G * ssimG + WEIGHT_B * ssimB;
        
        return (1.0 - ssimTotal) / 2.0 * 255.0; // 
    }
    
    private double calculateChannelSSIM(List<Integer> x, List<Integer> y) {
        if (x.isEmpty() || y.isEmpty()) return 1.0; 
        
        // Hitung rata-rata
        double muX = mean(x);
        double muY = mean(y);
        
        // Hitung varians dan kovarians
        double sigmaX2 = variance(x, muX);
        double sigmaY2 = variance(y, muY);
        double sigmaXY = covariance(x, y, muX, muY);
        
        // Hitung SSIM
        double numerator = (2 * muX * muY + C1) * (2 * sigmaXY + C2);
        double denominator = (muX * muX + muY * muY + C1) * (sigmaX2 + sigmaY2 + C2);
        
        if (denominator == 0) return 1.0; 
        
        return numerator / denominator;
    }
    
    private double mean(List<Integer> values) {
        double sum = 0;
        for (int value : values) {
            sum += value;
        }
        return sum / values.size();
    }
    
    private double variance(List<Integer> values, double mean) {
        double sum = 0;
        for (int value : values) {
            sum += Math.pow(value - mean, 2);
        }
        return sum / values.size();
    }
    
    private double covariance(List<Integer> x, List<Integer> y, double meanX, double meanY) {
        double sum = 0;
        for (int i = 0; i < x.size(); i++) {
            sum += (x.get(i) - meanX) * (y.get(i) - meanY);
        }
        return sum / x.size();
    }

    @Override
    public String getMethodName() {
        return "Structural Similarity Index (SSIM)";
    }
}