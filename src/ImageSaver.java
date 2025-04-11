import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageSaver {
    public static long saveImage(BufferedImage image, String filePath) throws IOException {
        File outputFile = new File(filePath);
        
        // Buat direktori jika belum ada
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        
        // Mendapatkan format file berdasarkan ekstensi (misalnya jpg, png)
        String format = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();
        
        // Menyimpan gambar dengan format yang telah ditentukan
        ImageIO.write(image, format, outputFile);
        
        // Mengembalikan ukuran file dalam bytes
        return outputFile.length();
    }
}