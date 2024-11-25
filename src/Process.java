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
//                    int grayRgb = (gray << 16) | (gray << 8) | gray; grayImage.setRGB(x, y, grayRgb);
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

//    public static void toGrayscale(BufferedImage image) {
//        try {
//            BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
//            int width = image.getWidth();
//            int height = image.getHeight();
//            rbgMatrix = new int[width][height];
//            WritableRaster raster = grayImage.getRaster();
//            for (int x = 0; x < image.getWidth(); x++) {
//                for (int y = 0; y < image.getHeight(); y++) {
//                    int rgb = image.getRGB(x, y);
//                    int r = (rgb >> 16) & 0xFF;
//                    int g = (rgb >> 8) & 0xFF;
//                    int b = rgb & 0xFF;
//                    int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
//                    int grayRgb = (gray << 16) | (gray << 8) | gray;
//                    rbgMatrix[x][y] = gray;
//                    raster.setSample(x, y, 0, gray);
//                    //grayImage.setRGB(x, y, grayRgb);
//                }
//            }
//            File output = new File(grayPath);
//            ImageIO.write(grayImage, "png", output);
//
//            System.out.println("Ảnh grayscale <" + grayPath + "> đã được lưu thành công!");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

    public static void createGrayCircle(int size){
        try {

            int[][] grayMatrix = new int[size][size];

            // Tọa độ tâm và bán kính
            int centerX = size / 2;
            int centerY = size / 2;
            double radius = size / 2;

            // Xuất ma trận ra ảnh
            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_GRAY);
            WritableRaster raster = image.getRaster();
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                    int grayValue = (distance <= radius) ? (int) (255 - (distance *  255/ radius) ) : 0;
                    raster.setSample(x, y, 0, grayValue); // Đặt giá trị grayscale trực tiếp
                }
            }

            // Lưu ảnh ra file

            File output = new File("circle_gray.png");
            ImageIO.write(image, "png", output);

            System.out.println("Đã tạo ma trận và lưu ảnh thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeMatrixToFile(int[][] matrix,String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            int width = matrix.length;
            int height = matrix[0].length;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
//                    writer.write(matrix[x][y] + "\t");
                    writer.write(String.format("%-4d ", matrix[x][y]));
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMatrixToFile(double[][] matrix,String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            int width = matrix.length;
            int height = matrix[0].length;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
//                    writer.write(matrix[x][y] + "\t");
                    writer.write(String.format("%-4.2f ", matrix[x][y]));
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
