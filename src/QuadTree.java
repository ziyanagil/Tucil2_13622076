import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    private Node root;
    private BufferedImage originalImage;
    private BufferedImage compressedImage;
    private ErrorCalculator errorCalculator;
    private int minBlockSize;
    private double threshold;
    private int treeDepth;
    private int nodeCount;

    public QuadTree(BufferedImage image, ErrorCalculator errorCalculator, int minBlockSize, double threshold) {
        this.originalImage = image;
        this.compressedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.errorCalculator = errorCalculator;
        this.minBlockSize = minBlockSize;
        this.threshold = threshold;
        this.treeDepth = 0;
        this.nodeCount = 0;

        // Inisialisasi root node dengan dimensi seluruh gambar (mulai dari titik (0,0))
        this.root = new Node(0, 0, image.getWidth(), image.getHeight());
    }

    public void compress() {
        buildQuadTree(root, 0);
        root.applyColorToImage(compressedImage);
    }

    private void buildQuadTree(Node node, int depth) {
        // Memperbaharui statistik pohon
        nodeCount++;
        treeDepth = Math.max(treeDepth, depth);

        // Menghitung rata-rata warna pada node berdasarkan piksel yang ada
        node.calculateAverageColor(originalImage);

        // Menghitung error pada node menggunakan metode yang dipilih oleh pengguna
        double error = errorCalculator.calculateError(originalImage, node);

        // Mengecek apakah node perlu dibagi lebih lanjut
        if (error > threshold && node.getWidth() / 2 >= minBlockSize && node.getHeight() / 2 >= minBlockSize) {
            node.split();
            for (Node child : node.getChildren()) {
                buildQuadTree(child, depth + 1);
            }
        }
    }

    public BufferedImage getCompressedImage() {
        return compressedImage;
    }

    public int getTreeDepth() {
        return treeDepth;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public Node getRoot() {
        return root;
    }

    // Method untuk menghitung persentase kompresi berdasarkan ukuran file asli dan hasil
    public double calculateCompressionPercentage(long originalSize, long compressedSize) {
        return (1.0 - ((double) compressedSize / originalSize)) * 100.0;
    }

    // Method untuk mendapatkan semua leaf node (digunakan misalnya untuk keperluan visualisasi)
    public List<Node> getAllLeafNodes() {
        List<Node> leafNodes = new ArrayList<>();
        collectLeafNodes(root, leafNodes);
        return leafNodes;
    }

    private void collectLeafNodes(Node node, List<Node> leafNodes) {
        if (node.isLeaf()) {
            leafNodes.add(node);
        } else {
            for (Node child : node.getChildren()) {
                collectLeafNodes(child, leafNodes);
            }
        }
    }
}