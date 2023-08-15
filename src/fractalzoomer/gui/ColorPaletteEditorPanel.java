package fractalzoomer.gui;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

class ColorPoint implements Comparable<ColorPoint> {
    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;
    @JsonProperty("anchor")
    private boolean anchor;

    public ColorPoint() {

    }

    public ColorPoint(int x, int y, boolean anchor) {
        this.x = x;
        this.y = y;
        this.anchor = anchor;
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

        public ColorDataPoints() {}
        public ColorDataPoints(ArrayList<ColorPoint> dataPointsRed, ArrayList<ColorPoint> dataPointsGreen, ArrayList<ColorPoint> dataPointsBlue, int factor, boolean reversedPalette, int offset, boolean wrapAround, int interpolation) {
            this.dataPointsRed = new ArrayList<>();
            this.dataPointsGreen = new ArrayList<>();
            this.dataPointsBlue = new ArrayList<>();
            this.reversedPalette = reversedPalette;
            this.offset = offset;
            this.wrapAround = wrapAround;
            this.interpolation = interpolation;

            for(int i = 0; i < dataPointsRed.size(); i++) {
                this.dataPointsRed.add(new ColorPoint(dataPointsRed.get(i).getX(), (int)((dataPointsRed.get(i).getY() / ((double)factor)) * 255 + 0.5), dataPointsRed.get(i).isAnchor()));
            }

            for(int i = 0; i < dataPointsGreen.size(); i++) {
                this.dataPointsGreen.add(new ColorPoint(dataPointsGreen.get(i).getX(), (int)((dataPointsGreen.get(i).getY() / ((double)factor)) * 255 + 0.5), dataPointsGreen.get(i).isAnchor()));
            }

            for(int i = 0; i < dataPointsBlue.size(); i++) {
                this.dataPointsBlue.add(new ColorPoint(dataPointsBlue.get(i).getX(), (int)((dataPointsBlue.get(i).getY() / ((double)factor)) * 255 + 0.5), dataPointsBlue.get(i).isAnchor()));
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
               dataPointsRed.add(new ColorPoint(x, (int)((val / ((double)255)) * factor + 0.5), this.dataPointsRed.get(i).isAnchor()));
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
               dataPointsGreen.add(new ColorPoint(x, (int)((val / ((double)255)) * factor + 0.5), this.dataPointsGreen.get(i).isAnchor()));
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
               dataPointsBlue.add(new ColorPoint(x, (int)((val / ((double)255)) * factor + 0.5), this.dataPointsBlue.get(i).isAnchor()));
           }

           return dataPointsBlue;
       }

       public ArrayList<ColorPoint> getDataPointsRed() {
           return dataPointsRed;
       }

       public void setDataPointsRed(ArrayList<ColorPoint> dataPointsRed) {
           this.dataPointsRed = dataPointsRed;
       }

       public ArrayList<ColorPoint> getDataPointsGreen() {
           return dataPointsGreen;
       }

       public void setDataPointsGreen(ArrayList<ColorPoint> dataPointsGreen) {
           this.dataPointsGreen = dataPointsGreen;
       }

       public ArrayList<ColorPoint> getDataPointsBlue() {
           return dataPointsBlue;
       }

       public void setDataPointsBlue(ArrayList<ColorPoint> dataPointsBlue) {
           this.dataPointsBlue = dataPointsBlue;
       }

       public boolean isReversedPalette() {
           return reversedPalette;
       }

       public void setReversedPalette(boolean reversedPalette) {
           this.reversedPalette = reversedPalette;
       }

       public int getOffset() {
           return offset;
       }

       public void setOffset(int offset) {
           this.offset = offset;
       }
       public boolean isWrapAround() {
           return wrapAround;
       }

       public void setWrapAround(boolean wrapAround) {
           this.wrapAround = wrapAround;
       }

       public int getInterpolation() {
           return interpolation;
       }

       public void setInterpolation(int interpolation) {
           this.interpolation = interpolation;
       }


   }



    public class ColorPaletteEditorPanel extends JPanel {
        private volatile static ColorComponent redComponent;
        private volatile static ColorComponent greenComponent;
        private volatile static ColorComponent blueComponent;
        private volatile static BufferedImage palette_preview;

        private volatile static JCheckBox addAnchorAtTheEnd;

        private volatile static JCheckBox lockPoints;

        private volatile static JCheckBox wrapAround;

        private volatile static MyJSpinner offset_textfield;

        private volatile static JCheckBox addOnAllComponents;

        private volatile static JComboBox<String> backgroundMode;

        private volatile static JComboBox<String> interpolationMode;

        private volatile static JLabel paletteLength;

        private volatile static ImageLabel im;

        private static int mouse_color_label_x;

        private Cursor grab_cursor;
        private Cursor grabbing_cursor;

        JComboBox<String> combo_box_random_palette_alg;
        JCheckBox same_hues;

        private volatile static JCheckBox check_box_reveres_palette;

        private static final int[][] reds = new int[][] {{0, 0}, {100, 31}, {200, 234}, {300, 255}, {400, 5}, {500, 0}};
        private static final int[][] greens = new int[][] {{0, 8}, {100, 107}, {200, 253}, {300, 171}, {400, 5}, {500, 7}};
        private static final int[][] blues = new int[][] {{0, 103}, {100, 202}, {200, 253}, {300, 2}, {400, 0}, {500, 97}};

        private ColorPaletteEditorFrame frame;
        private void reset() {
            addAnchorAtTheEnd.setSelected(false);
            check_box_reveres_palette.setSelected(false);
            offset_textfield.setValue(0);
            interpolationMode.setSelectedIndex(0);
            wrapAround.setSelected(false);
            redComponent.setWrapAround(false);
            greenComponent.setWrapAround(false);
            blueComponent.setWrapAround(false);
            redComponent.setData(reds);
            greenComponent.setData(greens);
            blueComponent.setData(blues);

            paintPalette();
        }

        private void help() {
            JEditorPane textArea = new JEditorPane();

            textArea.setEditable(false);
            textArea.setContentType("text/html");
            textArea.setPreferredSize(new Dimension(400, 120));

            JScrollPane scroll_pane_2 = new JScrollPane(textArea);

            String help = "<html><center><font size='5' face='arial' color='blue'><b><u>Custom Direct Palette Editor</u></b></font></center><br>"
                    + "<font size='4' face='arial'>"
                    + "<li>Left click to move control points.<br>"
                    + "<li>Right click to add or remove control points.<br>"
                   + "</font></html>";

            textArea.setText(help);

            Object[] message = {
                    scroll_pane_2,};

            textArea.setCaretPosition(0);

            JOptionPane.showMessageDialog(this, message, "Help", JOptionPane.QUESTION_MESSAGE);
        }

        public ColorPaletteEditorPanel(int width, int height, int color_space, ColorPaletteEditorFrame frame) {

            grab_cursor = Toolkit.getDefaultToolkit().createCustomCursor(MainWindow.getIcon("cursor_grab.gif").getImage(), new Point(16, 16), "grab");
            grabbing_cursor = Toolkit.getDefaultToolkit().createCustomCursor(MainWindow.getIcon("cursor_grabbing.gif").getImage(), new Point(16, 16), "grabbing");


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
                String[] color_interp_str = {"Linear", "Cosine", "Acceleration", "Deceleration", "Exponential", "Catmull-Rom", "Catmull-Rom 2", "Sigmoid", "Sine", "Square Root", "3rd Degree Poly", "5th Degree Poly", "Exponential 2", "Cube Root", "Fourth Root", "Smooth Transition"};
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

            if(check_box_reveres_palette == null) {
                check_box_reveres_palette = new JCheckBox("Reverse Palette");
                check_box_reveres_palette.setSelected(false);
                check_box_reveres_palette.setFocusable(false);
                check_box_reveres_palette.setToolTipText("Reverses the current palette.");
                check_box_reveres_palette.setBackground(MainWindow.bg_color);

                check_box_reveres_palette.addActionListener(e -> {
                    paintPalette();
                });
            }

            JPanel tools = new JPanel();
            tools.setBackground(MainWindow.bg_color);
            tools.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Tools", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


            if(paletteLength == null) {
                paletteLength = new JLabel("" + getTotalColors());
            }

            String[] random_palette_alg_str = {"Golden Ratio", "Waves", "Distance", "Triad", "Tetrad", "Google Material", "ColorBrewer 1", "ColorBrewer 2", "Google-ColorBrewer", "Cubehelix", "Cosines"};


            combo_box_random_palette_alg = new JComboBox<>(random_palette_alg_str);
            combo_box_random_palette_alg.setSelectedIndex(CustomPaletteEditorFrame.random_palette_algorithm);
            combo_box_random_palette_alg.setFocusable(false);
            combo_box_random_palette_alg.setToolTipText("Sets the random palette algorithm.");

            same_hues = new JCheckBox("Equal Hues");
            same_hues.setSelected(CustomPaletteEditorFrame.equal_hues);
            same_hues.setFocusable(false);
            same_hues.setToolTipText("Every color will have the same numbers of hues.");
            same_hues.setBackground(MainWindow.bg_color);

            JButton help = new MyButton();
            help.setIcon(MainWindow.getIcon("palette_help.png"));
            help.setFocusable(false);
            help.setPreferredSize(new Dimension(28, 28));

            help.addActionListener( e-> help());

            tools.add(help);

            JButton reset = new MyButton();
            reset.setIcon(MainWindow.getIcon("palette_reset.png"));
            reset.setFocusable(false);
            reset.setPreferredSize(new Dimension(28, 28));

            reset.addActionListener( e-> reset());

            tools.add(reset);


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

            tools.add(clear_palette);

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
                        ColorDataPoints d = new ColorDataPoints(redComponent.getColorPoints(), greenComponent.getColorPoints(), blueComponent.getColorPoints(), height, check_box_reveres_palette.isSelected(), offset, wrapAround.isSelected(), interpolationMode.getSelectedIndex());

                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            objectMapper.writeValue(file, d);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }

            });


            tools.add(save_palette);

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

                        interpolationMode.setSelectedIndex(dp.getInterpolation());

                        redComponent.repaint();
                        greenComponent.repaint();
                        blueComponent.repaint();
                        paintPalette();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            tools.add(load_palette);

            JButton random_palette = new MyButton();
            random_palette.setIcon(MainWindow.getIcon("palette_random.png"));
            random_palette.setFocusable(false);
            random_palette.setToolTipText("Randomizes the palette.");
            random_palette.setPreferredSize(new Dimension(28, 28));

            random_palette.addActionListener(e -> {

                int[][] temp_custom_palette = new int[64][4];

                CustomPaletteEditorFrame.randomPalette(new Random(), false, temp_custom_palette, combo_box_random_palette_alg.getSelectedIndex(), same_hues.isSelected(), 0, color_space, false, 0, 0, 0);

                ArrayList<ColorPoint> reds = new ArrayList<>();
                ArrayList<ColorPoint> greens = new ArrayList<>();
                ArrayList<ColorPoint> blues = new ArrayList<>();
                int x = 0;
                for(int i = 0; i < temp_custom_palette.length; i++) {
                    reds.add(new ColorPoint(x, (int)((temp_custom_palette[i][1] / ((double)255)) * height + 0.5), i == 0));
                    greens.add(new ColorPoint(x, (int)((temp_custom_palette[i][2] / ((double)255)) * height + 0.5), i == 0));
                    blues.add(new ColorPoint(x, (int)((temp_custom_palette[i][3] / ((double)255)) * height + 0.5), i == 0));
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


            tools.add(random_palette);

            tools.add(Box.createRigidArea(new Dimension(5, 10)));

            tools.add(new JLabel("Preset: "));

            JComboBox<String> extra_presets_box = new JComboBox<>(CustomPaletteEditorFrame.extraPalettes.getNames());
            extra_presets_box.setToolTipText("Loads additional presets to the editor.");

            tools.add(extra_presets_box);

            extra_presets_box.addActionListener(e -> {

                ArrayList<ArrayList<Integer>> palette1 = CustomPaletteEditorFrame.extraPalettes.getPalette(extra_presets_box.getSelectedIndex(), Integer.MAX_VALUE);

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

            p1.add(combo_box_random_palette_alg);
            p1.add(same_hues);

            tools.add(Box.createRigidArea(new Dimension(5, 10)));
            tools.add(new JLabel("Random: "));
            tools.add(p1);


            JPanel options_panel = new JPanel();
            options_panel.setBackground(MainWindow.bg_color);
            options_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Options", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

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
            color_interp_panel.setLayout(new FlowLayout());
            color_interp_panel.setBackground(MainWindow.bg_color);
            color_interp_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Interpolation", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

            color_interp_panel.add(interpolationMode);

            add(tools);
            add(color_interp_panel);
            add(options_panel);

            JPanel p3 = new JPanel();
            p3.setBackground(MainWindow.bg_color);
            p3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Graph Options", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


            p3.add(addAnchorAtTheEnd);
            p3.add(addOnAllComponents);
            p3.add(lockPoints);
            p3.add(new JLabel(" Background: "));
            p3.add(backgroundMode);


            add(p3);

            JPanel p4 = new JPanel();
            p4.setBackground(MainWindow.bg_color);
            p4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Palette", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

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
            CustomPaletteEditorFrame.random_palette_algorithm = combo_box_random_palette_alg.getSelectedIndex();
            CustomPaletteEditorFrame.equal_hues = same_hues.isSelected();
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

            g.setColor(Color.BLACK);
            g.drawRect(0, 0, palette_preview.getWidth()-1, palette_preview.getHeight()-1);

            paletteLength.setText("" + getTotalColors());
            paletteLength.repaint();

            repaint();
            im.repaint();
        }

        public int getWrapStep() {
            return (int)((redComponent.getAverageStep() + greenComponent.getAverageStep() + blueComponent.getAverageStep()) / 3.0);
        }

        public int getTotalColors() {
            int maxX = Math.max(Math.max(redComponent.getMaxX(), greenComponent.getMaxX()), blueComponent.getMaxX());

            if(wrapAround.isSelected()) {
                int step = getWrapStep();
                maxX += step;

                if (maxX != 0) {
                    return maxX;
                }

                return Math.max(Math.max(redComponent.getColorPoints().size(), greenComponent.getColorPoints().size()), blueComponent.getColorPoints().size()) + step;
            }
            else {
                if (maxX != 0) {
                    return maxX;
                }

                return Math.max(Math.max(redComponent.getColorPoints().size(), greenComponent.getColorPoints().size()), blueComponent.getColorPoints().size());
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
            if(check_box_reveres_palette.isSelected()) {
                for (int j = 0; j < total; j++) {
                    int red = redComponent.getValue(j, wrapAround.isSelected(), step);
                    int green = greenComponent.getValue(j, wrapAround.isSelected(), step);
                    int blue = blueComponent.getValue(j, wrapAround.isSelected(), step);
                    palette[(total - 1 - j + offset) % palette.length] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
            }
            else {
                for (int j = 0; j < total; j++) {
                    int red = redComponent.getValue(j, wrapAround.isSelected(), step);
                    int green = greenComponent.getValue(j, wrapAround.isSelected(), step);
                    int blue = blueComponent.getValue(j, wrapAround.isSelected(), step);
                    palette[(j + offset) % palette.length] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
            }

            return palette;
        }

        public JCheckBox getLockPoints() {
            return lockPoints;
        }

        public JCheckBox getAddOnAllComponents() {
            return addOnAllComponents;
        }

        public void addOnAllComponents(int x, int y, int factor) {
            new ColorChooserFrame(frame, "Choose Color", this, x, (int)((y / (double)factor) * 255 + 0.5));
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
}