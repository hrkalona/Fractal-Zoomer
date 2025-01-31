package fractalzoomer.main.app_settings;

public class BasicLocationSettings {
    private String centerReal;
    private String centerImaginary;
    private String size;
    private String magnification;
    private String sizeTruncated;
    private String magnificationTruncated;
    private int maxIterations;

    public String getCenterReal() {
        return centerReal;
    }

    public void setCenterReal(String centerReal) {
        this.centerReal = centerReal;
    }

    public String getCenterImaginary() {
        return centerImaginary;
    }

    public void setCenterImaginary(String centerImaginary) {
        this.centerImaginary = centerImaginary;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMagnification() {
        return magnification;
    }

    public void setMagnification(String magnification) {
        this.magnification = magnification;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public String getSizeTruncated() {
        return sizeTruncated;
    }

    public void setSizeTruncated(String sizeTruncated) {
        this.sizeTruncated = sizeTruncated;
    }

    public String getMagnificationTruncated() {
        return magnificationTruncated;
    }

    public void setMagnificationTruncated(String magnificationTruncated) {
        this.magnificationTruncated = magnificationTruncated;
    }
}
