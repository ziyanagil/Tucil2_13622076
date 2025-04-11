public class CompressionParameter {
    private int errorMetricChoice;
    private double threshold;
    private int minBlockSize;
    private double targetCompressionPercentage;
    private String inputImagePath;
    private String outputImagePath;
    private String outputGifPath;

    public CompressionParameter(int errorMetricChoice, double threshold, int minBlockSize, 
                                double targetCompressionPercentage, String inputImagePath, 
                                String outputImagePath, String outputGifPath) {
        this.errorMetricChoice = errorMetricChoice;
        this.threshold = threshold;
        this.minBlockSize = minBlockSize;
        this.targetCompressionPercentage = targetCompressionPercentage;
        this.inputImagePath = inputImagePath;
        this.outputImagePath = outputImagePath;
        this.outputGifPath = outputGifPath;
    }

    public int getErrorMetricChoice() {
        return errorMetricChoice;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public int getMinBlockSize() {
        return minBlockSize;
    }

    public double getTargetCompressionPercentage() {
        return targetCompressionPercentage;
    }

    public String getInputImagePath() {
        return inputImagePath;
    }

    public String getOutputImagePath() {
        return outputImagePath;
    }

    public String getOutputGifPath() {
        return outputGifPath;
    }
}