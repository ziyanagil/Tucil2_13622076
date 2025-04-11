import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Ambil parameter input dari pengguna
            CompressionParameter params = getUserInputs(scanner);
            
            // Muat gambar
            BufferedImage originalImage = ImageLoader.loadImage(params.getInputImagePath());
            
            // Pilih metode perhitungan error
            ErrorCalculator errorCalculator = selectErrorCalculator(params.getErrorMetricChoice());
            
            System.out.println("Memulai kompresi menggunakan " + errorCalculator.getMethodName() + "...");
            
            // Ukur waktu eksekusi
            long startTime = System.currentTimeMillis();
            
            // Buat dan jalankan quadtree
            QuadTree quadTree = new QuadTree(originalImage, errorCalculator, params.getMinBlockSize(), params.getThreshold());
            quadTree.compress();
            
            // Ambil gambar hasil kompresi
            BufferedImage compressedImage = quadTree.getCompressedImage();
            
            // Hitung waktu eksekusi
            long endTime = System.currentTimeMillis();
            long waktuEksekusi = endTime - startTime;
            
            // Simpan gambar hasil kompresi
            System.out.println("Menyimpan gambar hasil kompresi...");
            long ukuranAsli = new File(params.getInputImagePath()).length();
            long ukuranHasil = ImageSaver.saveImage(compressedImage, params.getOutputImagePath());
            
            // Hitung persentase kompresi
            double persentaseKompresi = quadTree.calculateCompressionPercentage(ukuranAsli, ukuranHasil);
            
            // Tampilkan hasil output
            System.out.println("\n=== Hasil Kompresi ===");
            System.out.println("Waktu eksekusi         : " + waktuEksekusi + " ms");
            System.out.println("Ukuran gambar sebelum   : " + ukuranAsli + " bytes");
            System.out.println("Ukuran gambar setelah   : " + ukuranHasil + " bytes");
            System.out.println("Persentase kompresi     : " + String.format("%.2f%%", persentaseKompresi));
            System.out.println("Kedalaman pohon         : " + quadTree.getTreeDepth());
            System.out.println("Jumlah simpul pada pohon: " + quadTree.getNodeCount());
            System.out.println("Gambar hasil kompresi disimpan di: " + params.getOutputImagePath());
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error tak terduga: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    private static CompressionParameter getUserInputs(Scanner scanner) {
        // Jalur gambar input
        System.out.print("Masukkan alamat absolut gambar yang akan dikompresi: ");
        String inputImagePath = scanner.nextLine();
        
        // Metode perhitungan error
        System.out.println("\nPilih metode perhitungan error:");
        System.out.println("1. Variance");
        System.out.println("2. Mean Absolute Deviation (MAD)");
        System.out.println("3. Max Pixel Difference");
        System.out.println("4. Entropy");
        System.out.println("5. Structural Similarity Index (SSIM)");
        System.out.print("Masukkan pilihan (1-5): ");
        int errorMetricChoice = Integer.parseInt(scanner.nextLine());
        
        // Threshold
        System.out.print("\nMasukkan nilai threshold: ");
        double threshold = Double.parseDouble(scanner.nextLine());
        
        // Ukuran blok minimum
        System.out.print("\nMasukkan ukuran blok minimum: ");
        int minBlockSize = Integer.parseInt(scanner.nextLine());
        
        // Persentase kompresi target (fitur bonus) [not implemented]
        System.out.print("\nMasukkan persentase kompresi target (0 untuk menonaktifkan, misalnya 0.5 untuk 50%) [not implemented]: ");
        double targetCompressionPercentage = Double.parseDouble(scanner.nextLine());
        
        // Jalur output gambar hasil kompresi 
        System.out.print("\nMasukkan alamat absolut untuk gambar hasil kompresi: ");
        String outputImagePath = scanner.nextLine();
        
        // Jalur output GIF proses kompresi (fitur bonus) [not implemented]
        System.out.print("\nMasukkan alamat absolut untuk GIF proses kompresi (kosongkan untuk melewati) [not implemented]: ");
        String outputGifPath = scanner.nextLine();
        
        return new CompressionParameter(
            errorMetricChoice,
            threshold,
            minBlockSize,
            targetCompressionPercentage,
            inputImagePath,
            outputImagePath,
            outputGifPath
        );
    }
    
    private static ErrorCalculator selectErrorCalculator(int choice) {
        switch (choice) {
            case 1:
                return new Variance();
            case 2:
                return new MAD();
            case 3:
                return new MaxPixelDiff();
            case 4:
                return new Entropy();
            case 5:
                return new SSIM();
            default:
                System.out.println("Pilihan tidak valid, menggunakan Variance sebagai default.");
                return new Variance();
        }
    }
}