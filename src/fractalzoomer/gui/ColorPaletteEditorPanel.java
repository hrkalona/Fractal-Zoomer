package fractalzoomer.gui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import fractalzoomer.core.TaskRender;
import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.CosinePaletteSettings;
import fractalzoomer.utils.ColorSpaceConverter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class ColorPoint implements Comparable<ColorPoint> {
    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;
    @JsonProperty("anchor")
    private boolean anchor;

    @JsonProperty("can_be_deleted")
    private boolean can_be_deleted = true;

    public ColorPoint() {

    }

    public ColorPoint(int x, int y, boolean anchor, boolean can_be_deleted) {
        this.x = x;
        this.y = y;
        this.anchor = anchor;
        this.can_be_deleted = can_be_deleted;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if(anchor) {
            return;
        }
        this.x = x;
    }

    public boolean isAnchor() {
        return anchor;
    }

    public boolean canBeDeleted() {
        return can_be_deleted;
    }

    public void setCanBeDeleted(boolean val) {
        can_be_deleted = val;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int compareTo(ColorPoint o) {
        return Integer.compare(x, o.x);
    }
}

   class ColorDataPoints {
        @JsonProperty("dataPointsRed")
        private ArrayList<ColorPoint> dataPointsRed;
        @JsonProperty("dataPointsGreen")
        private ArrayList<ColorPoint> dataPointsGreen;
        @JsonProperty("dataPointsBlue")
        private ArrayList<ColorPoint> dataPointsBlue;
        @JsonProperty("reversedPalette")
        private boolean reversedPalette;
        @JsonProperty("offset")
        private int offset;
        @JsonProperty("wrapAround")
        private boolean wrapAround;
        @JsonProperty("interpolation")
        private int interpolation;

       @JsonProperty("interpolation_color_mode")
       private int interpolation_color_mode;

       @JsonProperty("intermediate_color_red")
       private int intermediate_color_red;

       @JsonProperty("intermediate_color_green")
       private int intermediate_color_green;

       @JsonProperty("intermediate_color_blue")
       private int intermediate_color_blue;
       @JsonProperty("use_contrast")
       private boolean use_contrast = false;
       @JsonProperty("contrast_range_min")
       private double contrast_range_min = 0;
       @JsonProperty("contrast_range_max")
       private double contrast_range_max = 1;
       @JsonProperty("contrast_period")
       private double contrast_period = 3;
       @JsonProperty("contrast_offset")
       private double contrast_offset = 0;
       @JsonProperty("contrast_merging")
       private double contrast_merging = 1;
       @JsonProperty("contrast_algorithm")
       private int contrast_algorithm;

        public ColorDataPoints() {}
        public ColorDataPoints(ArrayList<ColorPoint> dataPointsRed, ArrayList<ColorPoint> dataPointsGreen, ArrayList<ColorPoint> dataPointsBlue, int factor, boolean reversedPalette, int offset, boolean wrapAround, int interpolation, int interpolation_color_mode, Color intermediate_color, boolean use_contrast, double contrast_range_min, double contrast_range_max, double contrast_period, double contrast_offset, double contrast_merging, int contrast_algorithm) {
            this.dataPointsRed = new ArrayList<>();
            this.dataPointsGreen = new ArrayList<>();
            this.dataPointsBlue = new ArrayList<>();
            this.reversedPalette = reversedPalette;
            this.offset = offset;
            this.wrapAround = wrapAround;
            this.interpolation = interpolation;
            this.interpolation_color_mode = interpolation_color_mode;
            this.intermediate_color_red = intermediate_color.getRed();
            this.intermediate_color_green = intermediate_color.getGreen();
            this.intermediate_color_blue = intermediate_color.getBlue();
            this.use_contrast = use_contrast;
            this.contrast_range_min = contrast_range_min;
            this.contrast_range_max = contrast_range_max;
            this.contrast_offset = contrast_offset;
            this.contrast_period = contrast_period;
            this.contrast_merging = contrast_merging;
            this.contrast_algorithm = contrast_algorithm;

            for(int i = 0; i < dataPointsRed.size(); i++) {
                this.dataPointsRed.add(new ColorPoint(dataPointsRed.get(i).getX(), (int)((dataPointsRed.get(i).getY() / ((double)factor)) * 255 + 0.5), dataPointsRed.get(i).isAnchor(), dataPointsRed.get(i).canBeDeleted()));
            }

            for(int i = 0; i < dataPointsGreen.size(); i++) {
                this.dataPointsGreen.add(new ColorPoint(dataPointsGreen.get(i).getX(), (int)((dataPointsGreen.get(i).getY() / ((double)factor)) * 255 + 0.5), dataPointsGreen.get(i).isAnchor(), dataPointsGreen.get(i).canBeDeleted()));
            }

            for(int i = 0; i < dataPointsBlue.size(); i++) {
                this.dataPointsBlue.add(new ColorPoint(dataPointsBlue.get(i).getX(), (int)((dataPointsBlue.get(i).getY() / ((double)factor)) * 255 + 0.5), dataPointsBlue.get(i).isAnchor(), dataPointsBlue.get(i).canBeDeleted()));
            }
        }

       public ArrayList<ColorPoint> getDataPointsRed(int factor, int width) {
           ArrayList<ColorPoint> dataPointsRed = new ArrayList<>();

           for(int i = 0; i < this.dataPointsRed.size(); i++) {
               int val = this.dataPointsRed.get(i).getY();
               val = val > 255 ? 255 : val;
               val = val < 0 ? 0 : val;

               int x = this.dataPointsRed.get(i).getX();

               if(x > width){
                   break;
               }
               dataPointsRed.add(new ColorPoint(x, (int)((val / ((double)255)) * factor + 0.5), this.dataPointsRed.get(i).isAnchor(), this.dataPointsRed.get(i).canBeDeleted()));
           }

           if(!dataPointsRed.isEmpty()) {
               int i = dataPointsRed.size() - 1;
               if(dataPointsRed.get(i).canBeDeleted() && dataPointsRed.get(i).isAnchor() && dataPointsRed.get(i).getX() != 0) {
                   dataPointsRed.get(i).setCanBeDeleted(false);
               }
           }

           return dataPointsRed;
       }

       public ArrayList<ColorPoint> getDataPointsGreen(int factor, int width) {
           ArrayList<ColorPoint> dataPointsGreen = new ArrayList<>();

           for(int i = 0; i < this.dataPointsGreen.size(); i++) {
               int val = this.dataPointsGreen.get(i).getY();
               val = val > 255 ? 255 : val;
               val = val < 0 ? 0 : val;

               int x = this.dataPointsGreen.get(i).getX();

               if(x > width){
                   break;
               }
               dataPointsGreen.add(new ColorPoint(x, (int)((val / ((double)255)) * factor + 0.5), this.dataPointsGreen.get(i).isAnchor(), this.dataPointsGreen.get(i).canBeDeleted()));
           }

           if(!dataPointsGreen.isEmpty()) {
               int i = dataPointsGreen.size() - 1;
               if(dataPointsGreen.get(i).canBeDeleted() && dataPointsGreen.get(i).isAnchor() && dataPointsGreen.get(i).getX() != 0) {
                   dataPointsGreen.get(i).setCanBeDeleted(false);
               }
           }

           return dataPointsGreen;
       }

       public ArrayList<ColorPoint> getDataPointsBlue(int factor, int width) {
           ArrayList<ColorPoint> dataPointsBlue = new ArrayList<>();

           for(int i = 0; i < this.dataPointsBlue.size(); i++) {
               int val = this.dataPointsBlue.get(i).getY();
               val = val > 255 ? 255 : val;
               val = val < 0 ? 0 : val;
               int x = this.dataPointsBlue.get(i).getX();

               if(x > width){
                   break;
               }
               dataPointsBlue.add(new ColorPoint(x, (int)((val / ((double)255)) * factor + 0.5), this.dataPointsBlue.get(i).isAnchor(), this.dataPointsBlue.get(i).canBeDeleted()));
           }

           if(!dataPointsBlue.isEmpty()) {
               int i = dataPointsBlue.size() - 1;
               if(dataPointsBlue.get(i).canBeDeleted() && dataPointsBlue.get(i).isAnchor() && dataPointsBlue.get(i).getX() != 0) {
                   dataPointsBlue.get(i).setCanBeDeleted(false);
               }
           }

           return dataPointsBlue;
       }

       @JsonIgnore
       public ArrayList<ColorPoint> getDataPointsRed() {
           return dataPointsRed;
       }

       public void setDataPointsRed(ArrayList<ColorPoint> dataPointsRed) {
           this.dataPointsRed = dataPointsRed;
       }

       @JsonIgnore
       public ArrayList<ColorPoint> getDataPointsGreen() {
           return dataPointsGreen;
       }

       public void setDataPointsGreen(ArrayList<ColorPoint> dataPointsGreen) {
           this.dataPointsGreen = dataPointsGreen;
       }

       @JsonIgnore
       public ArrayList<ColorPoint> getDataPointsBlue() {
           return dataPointsBlue;
       }

       public void setDataPointsBlue(ArrayList<ColorPoint> dataPointsBlue) {
           this.dataPointsBlue = dataPointsBlue;
       }

       @JsonIgnore
       public boolean isReversedPalette() {
           return reversedPalette;
       }

       public void setReversedPalette(boolean reversedPalette) {
           this.reversedPalette = reversedPalette;
       }

       @JsonIgnore
       public int getOffset() {
           return offset;
       }

       public void setOffset(int offset) {
           this.offset = offset;
       }

       @JsonIgnore
       public boolean isWrapAround() {
           return wrapAround;
       }

       public void setWrapAround(boolean wrapAround) {
           this.wrapAround = wrapAround;
       }

       @JsonIgnore
       public int getInterpolation() {
           return interpolation;
       }

       public void setInterpolation(int interpolation) {
           this.interpolation = interpolation;
       }

       @JsonIgnore
       public int getInterpolationColorMode() {
           return interpolation_color_mode;
       }

       public void setInterpolationColorMode(int interpolation_color_mode) {
           this.interpolation_color_mode = interpolation_color_mode;
       }


       @JsonIgnore
       public Color getIntermediateColor() {
           return new Color(intermediate_color_red, intermediate_color_green, intermediate_color_blue);
       }

       @JsonIgnore
       public boolean getUseContrast() {
           return use_contrast;
       }

       @JsonIgnore
       public double getContrastRangeMin() {
           return contrast_range_min;
       }

       @JsonIgnore
       public double getContrastRangeMax() {
           return contrast_range_max;
       }

       @JsonIgnore
       public double getContrastPeriod() {
           return contrast_period;
       }

       @JsonIgnore
       public double getContrastOffset() {
           return contrast_offset;
       }

       @JsonIgnore
       public double getContrastMerging() {
           return contrast_merging;
       }

       @JsonIgnore
       public int getContrastAlgorithm() {
           return contrast_algorithm;
       }

   }



    public class ColorPaletteEditorPanel extends JPanel {
        private static ColorComponent redComponent;
        private static ColorComponent greenComponent;
        private static ColorComponent blueComponent;
        private static BufferedImage palette_preview;

        private static JCheckBox addAnchorAtTheEnd;

        private static JCheckBox lockPoints;

        private static JCheckBox wrapAround;

        private static MyJSpinner offset_textfield;

        private static JCheckBox addOnAllComponents;

        private static JComboBox<String> backgroundMode;

        private static JComboBox<String> interpolationMode;
        private static JComboBox<String> interpolationColorMode;

        private static JLabel paletteLength;

        private static ImageLabel im;

        private static JLabel customColorLabel;

        private static int mouse_color_label_x;

        private Cursor grab_cursor;
        private Cursor grabbing_cursor;
        private static int procedural_length = 500;

        private static CosinePaletteSettings procedural_cps = new CosinePaletteSettings();
        private static int procedural_step = 10;
        private static int contrast_algorithm = 0;
        private static boolean use_contrast = false;
        private static double contrast_range_min = 0;
        private static double contrast_range_max = 1;
        private static double contrast_period = 3;
        private static double contrast_offset = 0;

        private static double contrast_merging = 1;

        JComboBox<String> combo_box_random_palette_alg;
        JCheckBox same_hues;

        JCheckBox pastel;

        private static JCheckBox check_box_reveres_palette;

        private static final int[][] reds = new int[][] {{0, 0}, {100, 31}, {200, 234}, {300, 255}, {400, 5}, {500, 0}};
        private static final int[][] greens = new int[][] {{0, 8}, {100, 107}, {200, 253}, {300, 171}, {400, 5}, {500, 7}};
        private static final int[][] blues = new int[][] {{0, 103}, {100, 202}, {200, 253}, {300, 2}, {400, 0}, {500, 97}};

        private ColorPaletteEditorDialog frame;
        private int height;
        private void reset() {
            addAnchorAtTheEnd.setSelected(false);
            check_box_reveres_palette.setSelected(false);
            offset_textfield.setValue(0);
            interpolationMode.setSelectedIndex(0);
            interpolationColorMode.setSelectedIndex(0);
            customColorLabel.setBackground(Color.BLACK);
            wrapAround.setSelected(false);
            redComponent.setWrapAround(false);
            greenComponent.setWrapAround(false);
            blueComponent.setWrapAround(false);
            redComponent.setData(reds);
            greenComponent.setData(greens);
            blueComponent.setData(blues);

            use_contrast = false;
            contrast_range_min = 0;
            contrast_range_max = 1;
            contrast_period = 3;
            contrast_offset = 0;
            contrast_merging = 1;
            contrast_algorithm = 0;

            paintPalette();
        }

        private void help() {
            JEditorPane textArea = new JEditorPane();

            textArea.setEditable(false);
            textArea.setContentType("text/html");
            textArea.setPreferredSize(new Dimension(400, 160));

            JScrollPane scroll_pane_2 = new JScrollPane(textArea);

            String help = "<html><center><font size='5' face='arial' color='blue'><b><u>Custom Direct Palette Editor</u></b></font></center><br>"
                    + "<font size='4' face='arial'>"
                    + "<li>Left click to move control points.<br>"
                    + "<li>Right click to add or remove control points.<br>"
                    + "<li>Anchor points (Filled with black) cannot be moved left or right.<br>"
                    + "<li>The last anchor point (if exists) cannot be deleted.<br>"
                    + "<li>If the waveform of a color component does not start from the beginning or extend to match the other waveforms, then filler points (Filled with orange) " +
                    "will be added. These point cannot be moved or deleted."
                   + "</font></html>";

            textArea.setText(help);

            Object[] message = {
                    scroll_pane_2,};

            textArea.setCaretPosition(0);

            JOptionPane.showMessageDialog(this, message, "Help", JOptionPane.QUESTION_MESSAGE);
        }

        public ColorPaletteEditorPanel(int width, int height, int color_space, ColorPaletteEditorDialog frame) {

            grab_cursor = Toolkit.getDefaultToolkit().createCustomCursor(MainWindow.getIcon("cursor_grab.gif").getImage(), new Point(16, 16), "grab");
            grabbing_cursor = Toolkit.getDefaultToolkit().createCustomCursor(MainWindow.getIcon("cursor_grabbing.gif").getImage(), new Point(16, 16), "grabbing");


            this.height = height;
            this.frame = frame;
            if(redComponent == null) {
                redComponent = new ColorComponent(Color.RED, new Color(110, 70, 70), "R", width, height, reds, this);
                greenComponent = new ColorComponent(Color.GREEN, new Color(70, 110, 70), "G", width, height, greens, this);
                blueComponent = new ColorComponent(Color.BLUE, new Color(70, 70, 110), "B", width, height, blues, this);
            }
            else {
                redComponent.setEditor(this);
                greenComponent.setEditor(this);
                blueComponent.setEditor(this);
            }

            if(addAnchorAtTheEnd == null) {
                addAnchorAtTheEnd = new JCheckBox("Anchor last");
                addAnchorAtTheEnd.setFocusable(false);
                addAnchorAtTheEnd.setSelected(false);
                addAnchorAtTheEnd.setToolTipText("Add a final point at the end.");
                addAnchorAtTheEnd.setBackground(MainWindow.bg_color);

                addAnchorAtTheEnd.addActionListener(e -> {
                    redComponent.addAnchorAtTheEnd(addAnchorAtTheEnd.isSelected());
                    greenComponent.addAnchorAtTheEnd(addAnchorAtTheEnd.isSelected());
                    blueComponent.addAnchorAtTheEnd(addAnchorAtTheEnd.isSelected());
                    paintPalette();
                });
            }

            if(lockPoints == null) {
                lockPoints = new JCheckBox("Add bounds");
                lockPoints.setFocusable(false);
                lockPoints.setSelected(false);
                lockPoints.setToolTipText("Points can no longer move past other points.");
                lockPoints.setBackground(MainWindow.bg_color);
            }

            if(wrapAround == null) {
                wrapAround = new JCheckBox("Wrap Around");
                wrapAround.setFocusable(false);
                wrapAround.setSelected(false);
                wrapAround.setToolTipText("The last point will be interpolated with the first.");
                wrapAround.setBackground(MainWindow.bg_color);


                wrapAround.addActionListener(e -> {
                    paintPalette();
                    redComponent.setWrapAround(wrapAround.isSelected());
                    greenComponent.setWrapAround(wrapAround.isSelected());
                    blueComponent.setWrapAround(wrapAround.isSelected());
                    redComponent.repaint();
                    greenComponent.repaint();
                    blueComponent.repaint();
                });
            }

            if(addOnAllComponents == null) {
                addOnAllComponents = new JCheckBox("Add to all");
                addOnAllComponents.setFocusable(false);
                addOnAllComponents.setSelected(false);
                addOnAllComponents.setToolTipText("When clicking to add on one color channel, the same value will be added to all channels.");
                addOnAllComponents.setBackground(MainWindow.bg_color);
            }

            if(backgroundMode == null) {
                String[] bg_mode = {"White", "Gradient"};
                backgroundMode = new JComboBox<>(bg_mode);
                backgroundMode.setSelectedIndex(0);
                backgroundMode.setFocusable(false);
                backgroundMode.setToolTipText("Changes the color channel background.");

                backgroundMode.addActionListener( e -> {
                    redComponent.repaint();
                    greenComponent.repaint();
                    blueComponent.repaint();
                });
            }

            if(interpolationMode == null) {
                String[] color_interp_str = {"Linear", "Cosine", "Acceleration", "Deceleration", "Exponential", "Catmull-Rom", "Catmull-Rom 2", "Sigmoid", "Sine", "Square Root", "3rd Degree Poly", "5th Degree Poly", "Exponential 2", "Cube Root", "Fourth Root", "Smooth Transition", "Quarter Sine"};
                interpolationMode = new JComboBox<>(color_interp_str);
                interpolationMode.setSelectedIndex(0);
                interpolationMode.setFocusable(false);
                interpolationMode.setToolTipText("Sets the color interpolation method.");

                interpolationMode.addActionListener( e -> {
                    redComponent.repaint();
                    greenComponent.repaint();
                    blueComponent.repaint();
                    paintPalette();
                });
            }

            if(interpolationColorMode == null) {
                String[] color_interp_str = {"Normal",
                        "Fade out", "Fade in", "Neon",
                        "Brighten out", "Brighten in", "Neon 2",
                        "Custom out", "Custom in", "Neon 3", "1 - x", "1 - (2*x - 1)^2", "(2*x - 1)^2", "1 - (2*x - 1)^4", "(2*x - 1)^4", "sin(x*pi)", "1 - sin(x*pi)", "Triangle", "1 - Triangle", "0.5 - 0.5*cos(2*x*pi)", "0.5 + 0.5*cos(2*x*pi)", "Sqrt Triangle", "1 - Sqrt Triangle"};
                interpolationColorMode = new JComboBox<>(color_interp_str);
                interpolationColorMode.setSelectedIndex(0);
                interpolationColorMode.setFocusable(false);
                interpolationColorMode.setToolTipText("Sets the color mode.");

                interpolationColorMode.addActionListener( e -> {
                    redComponent.repaint();
                    greenComponent.repaint();
                    blueComponent.repaint();
                    paintPalette();
                });
            }

            if(customColorLabel == null) {

                customColorLabel = new ColorLabel();

                customColorLabel.setPreferredSize(new Dimension(22, 22));
                customColorLabel.setBackground(Color.BLACK);
                customColorLabel.setToolTipText("Changes the intermediate color.");

                customColorLabel.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {


                        new ColorChooserDialog(frame, "Intermediate Color", customColorLabel, -1, -1);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }

                });
            }

            if(check_box_reveres_palette == null) {
                check_box_reveres_palette = new JCheckBox("Reverse Palette");
                check_box_reveres_palette.setSelected(false);
                check_box_reveres_palette.setFocusable(false);
                check_box_reveres_palette.setToolTipText("Reverses the current palette.");
                check_box_reveres_palette.setBackground(MainWindow.bg_color);

                check_box_reveres_palette.addActionListener(e -> paintPalette());
            }

            JPanel tools = new JPanel();
            tools.setBackground(MainWindow.bg_color);
            tools.setBorder(LAFManager.createTitledBorder("Tools"));

            JPanel tools_in = new JPanel();
            tools_in.setBackground(MainWindow.bg_color);
            tools_in.setLayout(new GridLayout(1, 8));


            if(paletteLength == null) {
                paletteLength = new JLabel("" + getTotalColors());
            }

            String[] random_palette_alg_str = {"Golden Ratio", "Waves", "Distance", "Triad", "Tetrad", "Google Material", "ColorBrewer 1", "ColorBrewer 2", "Google-ColorBrewer", "Cubehelix", "IQ-Cosines", "Perlin", "Simplex", "Perlin+Simplex", "Random Walk", "Simple Random"};


            combo_box_random_palette_alg = new JComboBox<>(random_palette_alg_str);
            combo_box_random_palette_alg.setSelectedIndex(CustomPaletteEditorDialog.random_palette_algorithm);
            combo_box_random_palette_alg.setFocusable(false);
            combo_box_random_palette_alg.setToolTipText("Sets the random palette algorithm.");

            same_hues = new JCheckBox("Equal Hues");
            same_hues.setSelected(CustomPaletteEditorDialog.equal_hues);
            same_hues.setFocusable(false);
            same_hues.setToolTipText("Every color will have the same numbers of hues.");
            same_hues.setBackground(MainWindow.bg_color);

            pastel = new JCheckBox("Pastel");
            pastel.setSelected(CustomPaletteEditorDialog.pastel);
            pastel.setFocusable(false);
            pastel.setToolTipText("Create a random pastel palette.");
            pastel.setBackground(MainWindow.bg_color);

            JButton help = new MyButton();
            help.setIcon(MainWindow.getIcon("palette_help.png"));
            help.setFocusable(false);
            help.setPreferredSize(new Dimension(28, 28));

            help.addActionListener( e-> help());

            tools_in.add(help);

            JButton reset = new MyButton();
            reset.setIcon(MainWindow.getIcon("palette_reset.png"));
            reset.setFocusable(false);
            reset.setPreferredSize(new Dimension(28, 28));

            reset.addActionListener( e-> reset());

            tools_in.add(reset);


            JButton clear_palette = new MyButton();
            clear_palette.setIcon(MainWindow.getIcon("palette_clear.png"));
            clear_palette.setFocusable(false);
            clear_palette.setToolTipText("Clears the palette.");
            clear_palette.setPreferredSize(new Dimension(28, 28));

            clear_palette.addActionListener(e -> {
                redComponent.clear();
                greenComponent.clear();
                blueComponent.clear();

                boolean hasLastAnchor = redComponent.hasLastAnchor() && greenComponent.hasLastAnchor() && blueComponent.hasLastAnchor();

                addAnchorAtTheEnd.setSelected(hasLastAnchor);

                redComponent.repaint();
                greenComponent.repaint();
                blueComponent.repaint();
                paintPalette();
            });

            tools_in.add(clear_palette);

            JButton save_palette = new MyButton();
            save_palette.setIcon(MainWindow.getIcon("palette_save.png"));
            save_palette.setFocusable(false);
            save_palette.setToolTipText("Saves a user made palette.");
            save_palette.setPreferredSize(new Dimension(28, 28));


            save_palette.addActionListener(e -> {

                JFileChooser file_chooser = new JFileChooser(MainWindow.SaveSettingsPath.isEmpty() ? "." : MainWindow.SaveSettingsPath);
                file_chooser.setAcceptAllFileFilterUsed(false);
                file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

                file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Base RGB Points (*.json)", "json"));
                file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("RGB Triplets (*.map)", "map"));

                String name = "palette " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".json";
                file_chooser.setSelectedFile(new File(name));

                file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
                    FileNameExtensionFilter filter = (FileNameExtensionFilter) evt.getNewValue();

                    String extension = filter.getExtensions()[0];

                    String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();

                    int index = file_name.lastIndexOf(".");

                    if (index != -1) {
                        file_name = file_name.substring(0, index);
                    }

                    file_chooser.setSelectedFile(new File(file_name + "." + extension));
                });

                int returnVal = file_chooser.showDialog(this, "Save Palette");

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = file_chooser.getSelectedFile();

                    if(getTotalColors() == 0) {
                        JOptionPane.showMessageDialog(this, "The palette must contain at least one color!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    FileNameExtensionFilter filter = (FileNameExtensionFilter) file_chooser.getFileFilter();

                    String extension = filter.getExtensions()[0];

                    if (extension.equalsIgnoreCase("map")) {

                        int[] palette = getPalette();

                        PrintWriter writer;
                        try {
                            writer = new PrintWriter(file.toString());

                            for (int l = 0; l < palette.length; l++) {

                                writer.println((palette[l] >> 16 & 0xff) + " " + (palette[l] >> 8 & 0xff) + " " + (palette[l] & 0xff));
                            }

                            writer.close();

                            MainWindow.SaveSettingsPath = file.getParent();
                        } catch (FileNotFoundException ex) {

                        }
                    }
                    else if (extension.equalsIgnoreCase("json")) {

                        int offset  = 0;
                        try {
                            offset = Integer.parseInt(offset_textfield.getText());
                            offset = offset < 0 ? 0 : offset;
                        }
                        catch (Exception ex) {}
                        ColorDataPoints d = new ColorDataPoints(redComponent.getColorPoints(), greenComponent.getColorPoints(), blueComponent.getColorPoints(), height, check_box_reveres_palette.isSelected(), offset, wrapAround.isSelected(), interpolationMode.getSelectedIndex(), interpolationColorMode.getSelectedIndex(), customColorLabel.getBackground(), use_contrast, contrast_range_min, contrast_range_max, contrast_period, contrast_offset, contrast_merging, contrast_algorithm);

                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            objectMapper.writeValue(file, d);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }

            });


            tools_in.add(save_palette);

            JButton load_palette = new MyButton();
            load_palette.setIcon(MainWindow.getIcon("palette_load.png"));
            load_palette.setFocusable(false);
            load_palette.setToolTipText("Loads a user made palette.");
            load_palette.setPreferredSize(new Dimension(28, 28));

            load_palette.addActionListener(e -> {
                JFileChooser file_chooser = new JFileChooser(MainWindow.SaveSettingsPath.isEmpty() ? "." : MainWindow.SaveSettingsPath);

                file_chooser.setAcceptAllFileFilterUsed(false);
                file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

                file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Base RGB Points (*.json)", "json"));

                file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
                    String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
                    file_chooser.setSelectedFile(new File(file_name));
                });

                int returnVal = file_chooser.showDialog(this, "Load Palette");

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = file_chooser.getSelectedFile();

                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        ColorDataPoints dp = objectMapper.readValue(file, ColorDataPoints.class);
                        redComponent.setColorPoints(dp.getDataPointsRed(height, width));
                        greenComponent.setColorPoints(dp.getDataPointsGreen(height, width));
                        blueComponent.setColorPoints(dp.getDataPointsBlue(height, width));

                        boolean hasLastAnchor = redComponent.hasLastAnchor() && greenComponent.hasLastAnchor() && blueComponent.hasLastAnchor();

                        addAnchorAtTheEnd.setSelected(hasLastAnchor);
                        check_box_reveres_palette.setSelected(dp.isReversedPalette());
                        wrapAround.setSelected(dp.isWrapAround());

                        redComponent.setWrapAround(wrapAround.isSelected());
                        greenComponent.setWrapAround(wrapAround.isSelected());
                        blueComponent.setWrapAround(wrapAround.isSelected());

                        int offset = dp.getOffset();

                        if(offset >= 0) {
                            offset_textfield.setValue(offset);
                        }
                        else {
                            offset_textfield.setValue(0);
                        }

                        if(dp.getInterpolation() < interpolationMode.getItemCount()) {
                            interpolationMode.setSelectedIndex(dp.getInterpolation());
                        }
                        if(dp.getInterpolationColorMode() < interpolationColorMode.getItemCount()) {
                            interpolationColorMode.setSelectedIndex(dp.getInterpolationColorMode());
                        }

                        customColorLabel.setBackground(dp.getIntermediateColor());

                        if(dp.getContrastAlgorithm() < 2) {
                            contrast_algorithm = dp.getContrastAlgorithm();
                        }
                        use_contrast = dp.getUseContrast();

                        if(dp.getContrastRangeMin() >= 0 && dp.getContrastRangeMin() <= 1
                        && dp.getContrastRangeMax() >= 0 && dp.getContrastRangeMax() <= 1
                        && dp.getContrastRangeMin() <= dp.getContrastRangeMax()) {
                            contrast_range_min = dp.getContrastRangeMin();
                            contrast_range_max = dp.getContrastRangeMax();
                        }

                        if(dp.getContrastPeriod() > 0) {
                            contrast_period = dp.getContrastPeriod();
                        }
                        contrast_offset = dp.getContrastOffset();

                        if(dp.getContrastMerging() >= 0 && dp.getContrastMerging() <= 1) {
                            contrast_merging = dp.getContrastMerging();
                        }

                        redComponent.repaint();
                        greenComponent.repaint();
                        blueComponent.repaint();
                        paintPalette();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            tools_in.add(load_palette);

            JButton random_palette = new MyButton();
            random_palette.setIcon(MainWindow.getIcon("palette_random.png"));
            random_palette.setFocusable(false);
            random_palette.setToolTipText("Randomizes the palette.");
            random_palette.setPreferredSize(new Dimension(28, 28));

            random_palette.addActionListener(e -> {

                int[][] temp_custom_palette = new int[64][4];

                CustomPaletteEditorDialog.randomPalette(TaskRender.generator, false, temp_custom_palette, combo_box_random_palette_alg.getSelectedIndex(), same_hues.isSelected(), 0, color_space, false, 0, 0, 0, pastel.isSelected());

                ArrayList<ColorPoint> reds = new ArrayList<>();
                ArrayList<ColorPoint> greens = new ArrayList<>();
                ArrayList<ColorPoint> blues = new ArrayList<>();
                int x = 0;
                for(int i = 0; i < temp_custom_palette.length; i++) {
                    reds.add(new ColorPoint(x, (int)((temp_custom_palette[i][1] / ((double)255)) * height + 0.5), i == 0, true));
                    greens.add(new ColorPoint(x, (int)((temp_custom_palette[i][2] / ((double)255)) * height + 0.5), i == 0, true));
                    blues.add(new ColorPoint(x, (int)((temp_custom_palette[i][3] / ((double)255)) * height + 0.5), i == 0, true));
                    x += temp_custom_palette[i][0];

                    if(x > width) {
                        break;
                    }
                }

                redComponent.setColorPoints(reds);
                greenComponent.setColorPoints(greens);
                blueComponent.setColorPoints(blues);

                boolean hasLastAnchor = redComponent.hasLastAnchor() && greenComponent.hasLastAnchor() && blueComponent.hasLastAnchor();

                addAnchorAtTheEnd.setSelected(hasLastAnchor);

                redComponent.repaint();
                greenComponent.repaint();
                blueComponent.repaint();
                paintPalette();

            });


            tools_in.add(random_palette);

            JButton procedural_palette = new MyButton();
            procedural_palette.setIcon(MainWindow.getIcon("palette_settings.png"));
            procedural_palette.setFocusable(false);
            procedural_palette.setToolTipText("Creates a procedural palette.");
            procedural_palette.setPreferredSize(new Dimension(28, 28));

            procedural_palette.addActionListener(e -> new ProceduralPaletteDialog(frame, procedural_length, procedural_cps, width, procedural_step));


            tools_in.add(procedural_palette);

            JButton contrast_variation = new MyButton();
            contrast_variation.setIcon(MainWindow.getIcon("contrast.png"));
            contrast_variation.setFocusable(false);
            contrast_variation.setPreferredSize(new Dimension(28, 28));
            contrast_variation.setToolTipText("Adds contrast to the palette.");

            contrast_variation.addActionListener( e-> setContrastVariation());

            tools_in.add(contrast_variation);

            tools.add(tools_in);

            tools.add(Box.createRigidArea(new Dimension(5, 10)));

            tools.add(new JLabel("Preset: "));

            JComboBox<String> extra_presets_box = new JComboBox<>(CustomPaletteEditorDialog.extraPalettes.getNames());
            extra_presets_box.setToolTipText("Loads additional presets to the editor.");

            tools.add(extra_presets_box);

            extra_presets_box.addActionListener(e -> {

                ArrayList<ArrayList<Integer>> palette1 = CustomPaletteEditorDialog.extraPalettes.getPalette(extra_presets_box.getSelectedIndex(), Integer.MAX_VALUE);

                redComponent.eraseAll();
                greenComponent.eraseAll();
                blueComponent.eraseAll();

                int max = width / 16;
                if(palette1.size() > max) {
                    ArrayList<ArrayList<Integer>> scaledPalette = new ArrayList<>();
                    double s = palette1.size() / ((double)max);
                    for(double k = 0; (int)(k + 0.5) < palette1.size(); k+= s) {
                        scaledPalette.add(palette1.get((int)(k + 0.5)));
                    }
                    palette1 = scaledPalette;
                }

                int x = 0;
                for(int m = 0; m < palette1.size(); m++) {
                    ArrayList<Integer> rgb = palette1.get(m);
                    if(x >= width) {
                        break;
                    }

                    if (rgb.size() == 3) {
                        redComponent.addWithFirstAnchor(x, rgb.get(0));
                        greenComponent.addWithFirstAnchor(x, rgb.get(1));
                        blueComponent.addWithFirstAnchor(x, rgb.get(2));
                        x += 16;
                    }
                    else if(rgb.size() == 4) {
                        redComponent.addWithFirstAnchor(x, rgb.get(1));
                        greenComponent.addWithFirstAnchor(x, rgb.get(2));
                        blueComponent.addWithFirstAnchor(x, rgb.get(3));
                        x += rgb.get(0);
                    }
                }

                redComponent.update();
                greenComponent.update();
                blueComponent.update();

                boolean hasLastAnchor = redComponent.hasLastAnchor() && greenComponent.hasLastAnchor() && blueComponent.hasLastAnchor();

                addAnchorAtTheEnd.setSelected(hasLastAnchor);

                redComponent.repaint();
                greenComponent.repaint();
                blueComponent.repaint();
                paintPalette();

            });


            JPanel p1 = new JPanel();
            p1.setLayout(new GridLayout(2, 1));
            p1.setBackground(MainWindow.bg_color);



            JPanel p12 = new JPanel();
            p12.setBackground(MainWindow.bg_color);
            p12.add(combo_box_random_palette_alg);

            p1.add(p12);

            JPanel p11 = new JPanel();
            p11.setBackground(MainWindow.bg_color);
            p11.add(same_hues);
            p11.add(pastel);

            p1.add(p11);

            tools.add(Box.createRigidArea(new Dimension(5, 10)));
            tools.add(new JLabel("Random: "));
            tools.add(p1);


            JPanel options_panel = new JPanel();
            options_panel.setBackground(MainWindow.bg_color);
            options_panel.setBorder(LAFManager.createTitledBorder( "Options"));

            options_panel.add(check_box_reveres_palette);
            options_panel.add(wrapAround);

            JLabel offset_label = new JLabel(" Palette Offset");

            options_panel.add(offset_label);

            if(offset_textfield == null) {
                offset_textfield = new MyJSpinner(5, new SpinnerNumberModel( 0, 0, Integer.MAX_VALUE, 1));
                offset_textfield.setToolTipText("Adds an offset to the current palette.");

                ((JSpinner.DefaultEditor) offset_textfield.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {

                        paintPalette();

                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {

                        paintPalette();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {

                        paintPalette();

                    }
                });
            }

            options_panel.add(offset_textfield);

            JPanel color_interp_panel = new JPanel();
            color_interp_panel.setLayout(new GridLayout(2, 1));
            color_interp_panel.setBackground(MainWindow.bg_color);
            color_interp_panel.setBorder(LAFManager.createTitledBorder("Color Interpolation"));

            JPanel p1a = new JPanel();
            p1a.setBackground(MainWindow.bg_color);

            JPanel p1b = new JPanel();
            p1b.setBackground(MainWindow.bg_color);

            p1a.add(interpolationMode);
            p1b.add(interpolationColorMode);
            p1b.add(customColorLabel);

            color_interp_panel.add(p1a);
            color_interp_panel.add(p1b);

            add(tools);
            add(color_interp_panel);
            add(options_panel);

            JPanel p3 = new JPanel();
            p3.setBackground(MainWindow.bg_color);
            p3.setBorder(LAFManager.createTitledBorder("Graph Options"));


            p3.add(addAnchorAtTheEnd);
            p3.add(addOnAllComponents);
            p3.add(lockPoints);
            p3.add(new JLabel(" Background: "));
            p3.add(backgroundMode);


            add(p3);

            JPanel p4 = new JPanel();
            p4.setBackground(MainWindow.bg_color);
            p4.setBorder(LAFManager.createTitledBorder("Palette"));

            p4.setPreferredSize(new Dimension(920, 420));

            p4.add(redComponent);
            p4.add(greenComponent);
            p4.add(blueComponent);

            if(im == null) {
                im = new ImageLabel();

                im.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        mouse_color_label_x = (int) im.getMousePosition().getX();

                        if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                            im.setCursor(grabbing_cursor);
                        } else if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
                            im.setCursor(grab_cursor);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                            im.setCursor(grab_cursor);
                        } else if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
                            im.setCursor(grab_cursor);
                        }
                    }
                });

                im.addMouseMotionListener(new MouseAdapter() {
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            try {
                                if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                                    im.setCursor(grabbing_cursor);
                                } else if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
                                    im.setCursor(grab_cursor);
                                }

                                int diff = (int) im.getMousePosition().getX() - mouse_color_label_x;

                                mouse_color_label_x = (int) im.getMousePosition().getX();

                                int newval = Integer.parseInt(offset_textfield.getText()) + diff;

                                if(newval < 0) {
                                    offset_textfield.setValue(0);
                                }
                                else {
                                    offset_textfield.setValue(newval);
                                }


                            } catch (Exception ex) {
                            }

                            paintPalette();

                        }

                    @Override
                    public void mouseMoved(MouseEvent e) {
                        try {
                            im.setCursor(grab_cursor);
                            Color temp_color = new Color(palette_preview.getRGB((int) im.getMousePosition().getX(), (int) im.getMousePosition().getY()));
                            String rr = Integer.toHexString(temp_color.getRed());
                            String bb = Integer.toHexString(temp_color.getBlue());
                            String gg = Integer.toHexString(temp_color.getGreen());

                            rr = rr.length() == 1 ? "0" + rr : rr;
                            gg = gg.length() == 1 ? "0" + gg : gg;
                            bb = bb.length() == 1 ? "0" + bb : bb;

                            im.setToolTipText("<html>R: " + temp_color.getRed() + " G: " + temp_color.getGreen() + " B: " + temp_color.getBlue() + "<br>"
                                    + "#" + rr + gg + bb + "</html>");

                            repaint();
                            im.repaint();
                        }
                        catch (Exception ex) {}
                    }
                });


            }

            if(palette_preview == null) {
                palette_preview = new BufferedImage(width, 50, BufferedImage.TYPE_INT_ARGB);
                im.setIcon(new ImageIcon(palette_preview));
            }

            p4.add(im);

            p4.add(new JLabel("Palette Length: "));
            p4.add(paletteLength);

            add(p4);

            paintPalette();
        }

        public void Ok() {
            CustomPaletteEditorDialog.random_palette_algorithm = combo_box_random_palette_alg.getSelectedIndex();
            CustomPaletteEditorDialog.equal_hues = same_hues.isSelected();
            CustomPaletteEditorDialog.pastel = pastel.isSelected();
        }

        public void paintPalette() {

            Graphics2D g = palette_preview.createGraphics();

            int total = getTotalColors();

            g.setColor(new Color(240, 240, 240));
            g.fillRect(0, 0, palette_preview.getWidth(), palette_preview.getHeight());
            
            int[] palette = getPalette();

            if(total <= palette_preview.getWidth()) {
                for (int j = 0; j < palette.length; j++) {
                    g.setColor(new Color(palette[j]));
                    g.fillRect(j, 0, 1, palette_preview.getHeight());
                }
            }
            else {
                double drawStep =  ((double)total) / palette_preview.getWidth();

                double m = 0;
                for (int j = 0; (int)(m + 0.5) < palette.length; j++, m += drawStep) {
                    g.setColor(new Color(palette[(int)(m + 0.5)]));
                    g.fillRect(j, 0, 1, palette_preview.getHeight());
                }
            }

            if(MainWindow.useCustomLaf){
                palette_preview = CommonFunctions.makeRoundedCorner(palette_preview, 5);
                im.setIcon(new ImageIcon(palette_preview));
            }
            else {
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, palette_preview.getWidth()-1, palette_preview.getHeight()-1);
            }

            paletteLength.setText("" + getTotalColors());
            paletteLength.repaint();

            repaint();
            im.repaint();
        }

        public int getWrapStep() {
            return (int)((redComponent.getAverageStep() + greenComponent.getAverageStep() + blueComponent.getAverageStep()) / 3.0);
        }

        public static int getMaxX() {
            int maxX = Math.max(Math.max(redComponent.getMaxX(), greenComponent.getMaxX()), blueComponent.getMaxX());

            if (maxX != 0) {
                return maxX;
            }

            return Math.max(Math.max(redComponent.getColorPoints().size(), greenComponent.getColorPoints().size()), blueComponent.getColorPoints().size());
        }

        public int getTotalColors() {
            int minLength = Math.min(Math.min(redComponent.getLength(), greenComponent.getLength()), blueComponent.getLength());

            if(minLength == 0) {
                return 0;
            }

            if(wrapAround.isSelected()) {
                return getWrapStep() + getMaxX();
            }
            else {
                return getMaxX();
            }
        }

        public int[] getPalette() {
            int total = getTotalColors();

            int[] palette = new int[total];

            int offset = 0;
            try {
                offset=Integer.parseInt(offset_textfield.getText());
                offset = offset < 0 ? 0 : offset;
            }
            catch (Exception ex) {}

            int step = getWrapStep();
            int maxX = getMaxX();
            for (int j = 0; j < total; j++) {
                int red = redComponent.getValue(j, wrapAround.isSelected(), step, maxX);
                int green = greenComponent.getValue(j, wrapAround.isSelected(), step, maxX);
                int blue = blueComponent.getValue(j, wrapAround.isSelected(), step, maxX);
                palette[j] = 0xff000000 | (red << 16) | (green << 8) | blue;
            }

            if(use_contrast) {

                double contrast_step = contrast_period / palette.length;

                double t = 0;
                for(int i = 0; i < palette.length; i++) {

                    int red = (palette[i] >> 16) & 0xff;
                    int green = (palette[i] >> 8) & 0xff;
                    int blue = (palette[i]) & 0xff;
                    if(contrast_algorithm == 0) {
                        double[] vals = ColorSpaceConverter.RGBtoLAB(red, green, blue);
                        int[] rgb = ColorSpaceConverter.LABtoRGB(vals[0] * (1 - contrast_merging) + contrast_merging * ((Math.sin(2 * Math.PI * t + contrast_offset) + 1) * 0.5 * (contrast_range_max - contrast_range_min) + contrast_range_min ) * 100, vals[1], vals[2]);

                        palette[i] = 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | (rgb[2]);
                    }
                    else if(contrast_algorithm == 1) {
                        double[] vals = ColorSpaceConverter.RGBtoOKLAB(red, green, blue);
                        int[] rgb = ColorSpaceConverter.OKLABtoRGB(vals[0] * (1 - contrast_merging) + contrast_merging * ((Math.sin(2 * Math.PI * t + contrast_offset) + 1) * 0.5 * (contrast_range_max - contrast_range_min) + contrast_range_min ), vals[1], vals[2]);

                        palette[i] = 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | (rgb[2]);
                    }

                    t += contrast_step;
                }

            }

            int[] final_palette = new int[palette.length];
            if(check_box_reveres_palette.isSelected()) {
                for (int j = 0; j < total; j++) {
                    final_palette[(total - 1 - j + offset) % final_palette.length] = palette[j];
                }
            }
            else {
                for (int j = 0; j < total; j++) {
                    final_palette[(j + offset) % final_palette.length] = palette[j];
                }
            }

            return final_palette;
        }

        public JCheckBox getLockPoints() {
            return lockPoints;
        }

        public JCheckBox getAddOnAllComponents() {
            return addOnAllComponents;
        }

        public void addOnAllComponents(int x, int y, int factor) {
            new ColorChooserDialog(frame, "Choose Color", this, x, (int)((y / (double)factor) * 255 + 0.5));
        }

        public void doAdd(int x, Color color) {
            redComponent.add(x, color.getRed());
            greenComponent.add(x, color.getGreen());
            blueComponent.add(x, color.getBlue());
            redComponent.update();
            greenComponent.update();
            blueComponent.update();
        }

        public int getBackgroundMode() {
            return backgroundMode.getSelectedIndex();
        }
        public int getInterpolationMode() { return interpolationMode.getSelectedIndex();}

        public int getInterpolationColorMode() { return interpolationColorMode.getSelectedIndex();}

        public Color getIntermediateColor() {return customColorLabel.getBackground();}

        public void colorChanged() {
            redComponent.repaint();
            greenComponent.repaint();
            blueComponent.repaint();
            paintPalette();
        }

        public void setProceduralPalettePost(int length, CosinePaletteSettings iqps, int step) {

            procedural_length = length;
            procedural_cps = iqps;
            procedural_step = step;

            ArrayList<ColorPoint> redscp = new ArrayList<>();
            ArrayList<ColorPoint> greenscp = new ArrayList<>();
            ArrayList<ColorPoint> bluescp = new ArrayList<>();

            double twoPi = Math.PI * 2;
            for(int x = 0, i = 0; x<= length; x += step, i++) {


                double t = ((double)x) / length;
                int red, green, blue;

                red = (int)(255.0 * (iqps.redA + iqps.redB * Math.cos( twoPi * (iqps.redC * t + iqps.redD) + iqps.redG)) + 0.5);
                green = (int)(255.0 * (iqps.greenA + iqps.greenB * Math.cos( twoPi * (iqps.greenC * t + iqps.greenD) + iqps.greenG)) + 0.5);
                blue = (int)(255.0 * (iqps.blueA + iqps.blueB * Math.cos( twoPi * (iqps.blueC * t + iqps.blueD) + iqps.blueG)) + 0.5);

                red = ColorSpaceConverter.clamp(red);
                green = ColorSpaceConverter.clamp(green);
                blue = ColorSpaceConverter.clamp(blue);

                redscp.add(new ColorPoint(x, (int)((red / ((double)255)) * height + 0.5), i == 0, true));
                greenscp.add(new ColorPoint(x, (int)((green / ((double)255)) * height + 0.5), i == 0, true));
                bluescp.add(new ColorPoint(x, (int)((blue / ((double)255)) * height + 0.5), i == 0, true));
            }

            redComponent.setColorPoints(redscp);
            greenComponent.setColorPoints(greenscp);
            blueComponent.setColorPoints(bluescp);

            boolean hasLastAnchor = redComponent.hasLastAnchor() && greenComponent.hasLastAnchor() && blueComponent.hasLastAnchor();

            addAnchorAtTheEnd.setSelected(hasLastAnchor);

            redComponent.repaint();
            greenComponent.repaint();
            blueComponent.repaint();
            paintPalette();

        }

        public void setContrastVariationPost(boolean contrast_variation, double range_min, double range_max, int contrast_method, double period, double offset, double merging) {

            use_contrast = contrast_variation;
            contrast_range_min = range_min;
            contrast_range_max = range_max;
            contrast_algorithm = contrast_method;
            contrast_period = period;
            contrast_offset = offset;
            contrast_merging = merging;

            paintPalette();

        }

        private void setContrastVariation() {
            new ContrastVariationDialog(frame, use_contrast, contrast_range_min, contrast_range_max, contrast_algorithm, contrast_period, contrast_offset, contrast_merging);
        }
}