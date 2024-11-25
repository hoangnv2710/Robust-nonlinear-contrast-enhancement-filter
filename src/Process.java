import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Process {
    static String path = "int_gray.png";
    static String grayPath;
    static String outPath;

    public static int[][] toMatrix() throws IOException {
        String fileName = path.substring(0, path.lastIndexOf('.'));
        File input = new File(path);
        BufferedImage image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();
        int grayMatrix[][] = new int[width][height];

        if (image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            grayPath = fileName + "_gray.png";
            System.out.println("Ảnh không phải 8-bit grayscale.");
            System.out.println("Thực hiện chuyển hình ảnh sang 8-bit grayscale..." + path + " --> " + grayPath);
            BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            WritableRaster raster = grayImage.getRaster();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int rgb = image.getRGB(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                    grayMatrix[x][y] = gray;
                    raster.setSample(x, y, 0, gray);
                }
            }
            File output = new File(grayPath);
            ImageIO.write(grayImage, "png", output);
            System.out.println("Ảnh grayscale <" + grayPath + "> đã được lưu thành công!");
//            for (int x = 0; x < width; x++) {for (int y = 0; y < height; y++) {System.out.print(grayMatrix[x][y] + "\t");}System.out.println();}
            return grayMatrix;
        }

        Raster raster = image.getRaster();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) { grayMatrix[x][y] = raster.getSample(x, y, 0);}
        }
        return grayMatrix;
    }


    public static void toImage(int[][] matrix, String fileName) throws IOException {
        int width = matrix.length;
        int height = matrix[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = image.getRaster();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int grayValue = matrix[x][y];
                raster.setSample(x, y, 0, grayValue);
            }
        }

        outPath = fileName + "_output.png";
        File output = new File(outPath);
        ImageIO.write(image, "png", output);
        System.out.println("ảnh thành công!");
    }

}
