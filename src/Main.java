import java.io.*;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    static String path;
    static int K = 3;
    static int L = 3;
    static double DELTA = 120;
    static double A = 180;
    public static void main(String[] args) {
        String choice = "1";
        Scanner s = new Scanner(System.in);
        do {
            System.out.println("\nNhập setting vào file setting.txt.");
            readSetting();
            System.out.println();
            writeOutput();
            System.out.println("\n\nNhập 0 để thoát hoặc nhập bất kì để tiếp tục:");
            choice = s.nextLine();
        } while (!choice.equals("0"));

    }

    public static void readSetting() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter để tiếp tục...");
        s.nextLine();
        try {
//            String jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
//                    .toURI()).getParent();
//            File file = new File(jarDir,"setting.txt");
            File file = new File("setting.txt");
            Scanner sc = new Scanner(file);
            path = sc.nextLine();
            K = sc.nextInt();
            L = sc.nextInt();
            DELTA = sc.nextDouble();
            A = sc.nextDouble();
            System.out.println("Setting: path =" + path + ", K =" + K + ", L =" + L + ", DELTA =" + DELTA + ", A =" + A);
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file setting!");
            readSetting();
        } catch (NoSuchElementException e) {
            System.out.println("Setting sai hoặc thiếu, yêu cầu nhập lại!");
            readSetting();
        }
//        catch (URISyntaxException e) {
//            System.out.println("url không hợp lệ");
//        }
    }

    public static void writeOutput() {
        try {
            int matrix[][] = Process.toMatrix(path);
            Image image = new Image(matrix, K, L , DELTA, A);
            image.setPixelsMask();
            Process.toImage(image.outputGLEexp, path.substring(0, path.lastIndexOf('.')) + " GLEexp " + K +"x" + L + "," + DELTA + "," + A);
            Process.toImage(image.outputGLEgau, path.substring(0, path.lastIndexOf('.')) + " GLEgau " + K +"x" + L + "," + DELTA + "," + A);
            Process.toImage(image.outputLLEexp, path.substring(0, path.lastIndexOf('.')) + " LLEexp " + K +"x" + L + "," + DELTA + "," + A);
            Process.toImage(image.outputLLEgau, path.substring(0, path.lastIndexOf('.')) + " LLEgau " + K +"x" + L + "," + DELTA + "," + A);
        } catch (IOException e) {
            System.out.println("Lỗi file hình ảnh!");
            e.printStackTrace();
        }
    }
}
