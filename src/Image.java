public class Image {
    double DELTA ;
    int K ;
    int L ;
    double A;
    public int width;
    public int height;
    public double[] mGau;
    public double[] mExp;
    Pixel pixels[][];
    public int outputGLEexp[][];
    public int outputGLEgau[][];
    public int outputLLEexp[][];
    public int outputLLEgau[][];

    public Image(int[][] intensity) {
        setM();
        setPixels(intensity);
    }

    public Image(int[][] intensity, int K, int L, double DELTA, double A) {
        this.K = K;
        this.L = L;
        this.A = A;
        this.DELTA = DELTA;
        setM();
        setPixels(intensity);
    }

    public void setM() {
        mGau = new double[256];
        mExp = new double[256];
        for(int i = 0; i < 256; i++) {
            mGau[i] = Math.exp(-(i*i)/(DELTA*DELTA));
            mExp[i] = Math.exp(-i/(DELTA));
        }
    }

    public void setPixels(int[][] intensity) {
        width = intensity.length;
        height = intensity[0].length;
        pixels = new Pixel[width][height];
        outputGLEexp = new int[width][height];
        outputGLEgau = new int[width][height];
        outputLLEexp = new int[width][height];
        outputLLEgau = new int[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                pixels[i][j] = new Pixel(intensity[i][j]);
            }
        }
    }

    public void setPixelsMask() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                int iStart, jStart , iEnd, jEnd;
                int k, l;
                if (2 * i + 1 < K) {
                    iStart = 0;
                    iEnd = i + (K - 1) / 2;
                } else if(K > 2 * (width - i - 1) + 1) {
                    iStart = i - (K - 1) / 2;
                    iEnd = width - 1;
                } else {
                    iStart = i - (K - 1) / 2;
                    iEnd = iStart + K - 1;
                }
                if (2 * j + 1 < L) {
                    jStart = 0;
                    jEnd = j + (L - 1) / 2;
                } else if(L > 2 * (height - j - 1) + 1) {
                    jStart = j - (L - 1) / 2;
                    jEnd = height - 1;
                } else {
                    jStart = j - (L - 1) / 2;
                    jEnd = jStart + L - 1;
                }
                k = iEnd - iStart + 1;
                l = jEnd - jStart + 1;
                double[][] maskGau = new double[k][l];
                double[][] maskExp = new double[k][l];
                int currentIntensity = pixels[i][j].intensity;
                double sumMExp = 0;
                double sumMGau = 0;
                double sumMIntensityExp = 0;
                double sumMIntensityGau = 0;
                int sumIntensity = 0;
                for (int p = iStart; p <= iEnd; p++) {
                    for (int q = jStart; q <= jEnd; q++) {
                        int intensity = pixels[p][q].intensity;
                        sumIntensity += intensity;
                        int x = p - iStart;
                        int y = q - jStart;

                        maskExp[x][y] = mExp[Math.abs(intensity - currentIntensity)];
                        sumMIntensityExp += maskExp[x][y] * intensity;
                        sumMExp += maskExp[x][y];

                        maskGau[x][y] = mGau[Math.abs(intensity - currentIntensity)];
                        sumMIntensityGau += maskGau[x][y] * intensity;
                        sumMGau += maskGau[x][y];
                    }
                }
                pixels[i][j].setmaskExp(maskExp);
                double mAvgExp = sumMExp / (k * l);
                double yLExp = sumMIntensityExp / sumMExp;
                double yHExpGLE = sumMIntensityExp - mAvgExp * sumIntensity;
                outputGLEexp[i][j] = (int) (yLExp + (yLExp * yHExpGLE) / A) ;

                pixels[i][j].setmaskGau(maskGau);
                double mAvgGau = sumMGau / (k * l);
                double yLGau = sumMIntensityGau / sumMGau;
                double yHGauGLE = sumMIntensityGau - mAvgGau * sumIntensity;
                outputGLEgau[i][j] = (int) (yLGau + (yLGau * yHGauGLE) / A) ;

                double sumDiffMgau = 0;
                double sumDiffMexp = 0;
                for (int p = iStart; p <= iEnd; p++) {
                    for (int q = jStart; q <= jEnd; q++) {
                        int x = p - iStart;
                        int y = q - jStart;
                        sumDiffMgau += Math.abs(maskGau[x][y] - mAvgGau);
                        sumDiffMexp += Math.abs(maskExp[x][y] - mAvgExp);
                    }
                }
                int diffIntensity = currentIntensity * k * l - sumIntensity;
                double yHExpLLE = sumDiffMexp * diffIntensity;
                outputLLEexp[i][j] = (int) (yLExp + (yLExp * yHExpLLE) / A) ;
                double yHGauLLE = sumDiffMgau * diffIntensity;
                outputLLEgau[i][j] = (int) (yLGau + (yLGau * yHGauLLE) / A) ;
            }
        }
    }
}
