package fractalzoomer.utils;

public class SplitImagePixelOffset extends PixelOffset {
    private int offsetX;
    private int offsetY;

    private int image_size;
    public SplitImagePixelOffset(int image_size, int offsetX, int offsetY) {
        super();
        this.image_size = image_size;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public int getX(int x) {
        return x + offsetX;
    }

    @Override
    public int getY(int y) {
        return y + offsetY;
    }

    @Override
    public int getImageSize(int image_size_in) {
        return image_size;
    }
}
