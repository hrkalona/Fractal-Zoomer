package fractalzoomer.utils;

public class SplitImagePixelOffset extends PixelOffset {
    private int offsetX;
    private int offsetY;

    private int image_width;
    private int image_height;
    public SplitImagePixelOffset(int image_width, int image_height, int offsetX, int offsetY) {
        super();
        this.image_width = image_width;
        this.image_height = image_height;
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
    public int getWidth(int width) {
        return image_width;
    }
    @Override
    public int getHeight(int height) {
        return image_height;
    }
}
