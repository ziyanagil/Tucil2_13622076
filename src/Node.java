import java.awt.Color;
import java.awt.image.BufferedImage;

public class Node {
    private int x, y, width, height;
    private Node[] children;
    private Color averageColor;
    private boolean isLeaf;

    public Node(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isLeaf = true;
        this.children = null;
    }

    public void split() {
        this.isLeaf = false;
        this.children = new Node[4];
        
        int newWidth = width / 2;
        int newHeight = height / 2;
        
        // Atas-kiri
        children[0] = new Node(x, y, newWidth, newHeight);
        // Atas-kanan
        children[1] = new Node(x + newWidth, y, newWidth, newHeight);
        // Bawah-kiri
        children[2] = new Node(x, y + newHeight, newWidth, newHeight);
        // Bawah-kanan
        children[3] = new Node(x + newWidth, y + newHeight, newWidth, newHeight);
    }

    public void calculateAverageColor(BufferedImage image) {
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;
        int pixelCount = 0;

        for (int j = y; j < y + height; j++) {
            for (int i = x; i < x + width; i++) {
                if (i < image.getWidth() && j < image.getHeight()) {
                    Color pixelColor = new Color(image.getRGB(i, j));
                    totalRed += pixelColor.getRed();
                    totalGreen += pixelColor.getGreen();
                    totalBlue += pixelColor.getBlue();
                    pixelCount++;
                }
            }
        }

        if (pixelCount > 0) {
            int avgRed = totalRed / pixelCount;
            int avgGreen = totalGreen / pixelCount;
            int avgBlue = totalBlue / pixelCount;
            this.averageColor = new Color(avgRed, avgGreen, avgBlue);
        } else {
            this.averageColor = Color.BLACK;
        }
    }

    public void applyColorToImage(BufferedImage image) {
        if (isLeaf) {
            for (int j = y; j < y + height; j++) {
                for (int i = x; i < x + width; i++) {
                    if (i < image.getWidth() && j < image.getHeight()) {
                        image.setRGB(i, j, averageColor.getRGB());
                    }
                }
            }
        } else {
            for (Node child : children) {
                child.applyColorToImage(image);
            }
        }
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Node[] getChildren() {
        return children;
    }

    public Color getAverageColor() {
        return averageColor;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setAverageColor(Color averageColor) {
        this.averageColor = averageColor;
    }
}