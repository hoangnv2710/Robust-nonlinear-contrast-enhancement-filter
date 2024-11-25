public class Pixel {
    int intensity ;
    int K = 3;
    int L = 3;
    public double[][] maskGLEgau;
    public double[][] maskGLEexp;

    Pixel(int intensity, int K, int L) {
        this.intensity = intensity;
        this.K = K;
        this.L = L;
    }

    public Pixel(int intensity) {
        this.intensity = intensity;
    }

    public void setmaskGLEgau(double[][] maskGLEgau) {
        this.maskGLEgau = maskGLEgau;
    }

    public void setmaskGLEexp(double[][] maskGLEexp) {
        this.maskGLEexp = maskGLEexp;
    }
}
