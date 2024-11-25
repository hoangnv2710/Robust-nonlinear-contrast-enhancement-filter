public class Pixel {
    int intensity ;
    public double[][] maskGau;
    public double[][] maskExp;

    public Pixel(int intensity) {
        this.intensity = intensity;
    }

    public void setmaskGau(double[][] maskGau) {
        this.maskGau = maskGau;
    }

    public void setmaskExp(double[][] maskExp) {
        this.maskExp = maskExp;
    }
}
