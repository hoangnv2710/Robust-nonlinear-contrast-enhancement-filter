import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) {
//        Process.createGrayCircle(151);
        try {
            int matrix[][] = Process.toMatrix();
            Image image = new Image(matrix);
            image.setPixelsMask();
            Process.toImage(image.outputGLEexp, "GLEexp");
            Process.toImage(image.outputGLEgau, "GLEgau");


            Pixel p[][] = image.pixels;
            int width = image.width;
            int height = image.height;
            //for (int x = 0; x < width; x++) {for (int y = 0; y < height; y++) {System.out.print(Pi[x][y] + "  ");}System.out.println();}
            double Pi[][] = p[0][0].maskGLEexp;
//            width = Pi.length;
//            height = Pi[0].length;
//            for (int x = 0; x < width; x++) {for (int y = 0; y < height; y++) {System.out.print(Pi[x][y] + "  ");}System.out.println();}

            int gau[][] = image.outputGLEgau;
            for (int x = 0; x < width; x++) {for (int y = 0; y < height; y++) {System.out.print(gau[x][y] + "\t");}System.out.println();}

//            Pixel p[][] = image.pixels;
//            int width = image.width;
//            int height = image.height;

//
//            double Pi[][] = p[width - 1][height - 1].mExponential;
//            width = Pi.length;
//            height = Pi[0].length;
//            for (int x = 0; x < width; x++) {for (int y = 0; y < height; y++) {System.out.print(Pi[x][y] + "  ");}System.out.println();}

//            for(int i = 0; i <= 5; i++) {
//                Process.writeMatrixToFile(Pi,"mexp" + i + ".txt");
//            }


//            for (int x = 0; x < width; x++) {for (int y = 0; y < height; y++) {System.out.print(p[x][y].intensity + "\t");}System.out.println();}
//            image.setPixelsMask();
//
//            Process.toImage(image.out);


//            double m[] = image.mExp;
//            for (int j = 0; j < m.length; j++) {
//                System.out.println(j + "  " + m[j]);
//            }

//            int matrix[][] = Process.toMatrix();
//            Process.toImage(matrix,"graymatrix");
//            Process.toImage(Process.toMatrix(),"rbgmatrix");
//            for (int i = 0; i < matrix.length; i++) {
//                for (int j = 0; j < matrix[i].length; j++) {
//                    System.out.print(matrix[i][j] - Process.rbgMatrix[i][j] + "\t");
//                }
//                System.out.println();
//            }
//            for (int[] row : matrix) {
//                for (int value : row) {
//                    System.out.print(value + "\t");
//                }
//                System.out.println();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
