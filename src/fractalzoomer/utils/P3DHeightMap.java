package fractalzoomer.utils;

import com.jogamp.nativewindow.WindowClosingProtocol;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.gui.MyJSpinner;
import fractalzoomer.main.MainWindow;
import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.opengl.PJOGL;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class P3DHeightMap extends PApplet {

    private MainWindow ptra;
    private int details;
    private int image_size;
    private int window_size;
    private boolean aa;

    private boolean isValid;

    private float scl;
    private boolean reset = true;

    private double oldX = 0;
    private double oldY = 0;
    private double x = 0;
    private double y = 0;
    private double change = 0.01;
    private double scale_change  = 0.05;

    private float rotX;
    private float rotZ;
    private float scale;

    private int aa_size;
    private boolean readyToDraw;

    private JFrame optionsframe;

    private JCheckBox invertcb;
    private MyJSpinner scaling;
    private MyJSpinner shininess_slid;
    private MyJSpinner specular_slid_v1;
    private MyJSpinner specular_slid_v2;
    private MyJSpinner specular_slid_v3;

    /*private MyJSpinner ambient_slid_v1;
    private MyJSpinner ambient_slid_v2;
    private MyJSpinner ambient_slid_v3;

    private MyJSpinner emmisive_slid_v1;
    private MyJSpinner emmisive_slid_v2;
    private MyJSpinner emmisive_slid_v3;*/

    /*private JCheckBox useShinines;
    private JCheckBox useSpecular;
    private JCheckBox useAmbient;

    private JCheckBox useEmmisive;*/


    private JCheckBox useDirectionalLight;
    private MyJSpinner directional_light_slid_v1;
    private MyJSpinner directional_light_slid_v2;
    private MyJSpinner directional_light_slid_v3;

    private JCheckBox useAmbientLight;
    private MyJSpinner ambient_light_slid_v1;
    private MyJSpinner ambient_light_slid_v2;
    private MyJSpinner ambient_light_slid_v3;


    private JCheckBox usePointLight;
    private MyJSpinner point_light_slid_v1;
    private MyJSpinner point_light_slid_v2;
    private MyJSpinner point_light_slid_v3;


    private JCheckBox useSpotLight;
    private MyJSpinner spotlight_light_slid_v1;
    private MyJSpinner spotlight_light_slid_v2;
    private MyJSpinner spotlight_light_slid_v3;

    private MyJSpinner light_specular_slid_v1;
    private MyJSpinner light_specular_slid_v2;
    private MyJSpinner light_specular_slid_v3;

    private MyJSpinner light_fallof_slid_v1;

    private MyJSpinner dir_light_nx;
    private MyJSpinner dir_light_ny;
    private MyJSpinner dir_light_nz;

    private MyJSpinner point_x;
    private MyJSpinner point_y;
    private MyJSpinner point_z;

    private MyJSpinner spot_x;
    private MyJSpinner spot_y;
    private MyJSpinner spot_z;

    private MyJSpinner spot_angle;
    private MyJSpinner spot_concentration;

    private MyJSpinner spot_nx;
    private MyJSpinner spot_ny;
    private MyJSpinner spot_nz;

    public P3DHeightMap(MainWindow ptra, int window_size, int image_size, int details, boolean aa, int aa_size) {
        super();
        this.image_size = image_size;
        this.window_size = window_size;
        this.details = details;
        this.aa = aa;
        isValid = true;
        this.aa_size = aa_size;
        readyToDraw = true;
        this.ptra = ptra;
        reset();
    }

    public void update(int image_size, int details) {
        this.image_size = image_size;
        this.details = details;

        scl = (float) image_size / details;

        bringToFront();
    }

    @Override
    public void exitActual() {
        readyToDraw = false;
        isValid = false;
        optionsframe.setVisible(false);
        dispose();
    }

    public void close() {
        readyToDraw = false;
        if (getGraphics().isGL()) {
            final com.jogamp.newt.Window w = (com.jogamp.newt.Window) getSurface().getNative();
            w.destroy();
        }
        exitActual();
    }

    public boolean isValid() {
        return isValid;
    }

    @Override
    public void settings(){
        size(window_size, window_size, P3D);

        if(aa) {
            smooth(aa_size);
        }

        scl = (float) image_size / details;

        PJOGL.setIcon("fractalzoomer/icons/mandel2.png");

    }

    private static final int directionalLightV1 = 204;
    private static final int directionalLightV2 = 204;
    private static final int directionalLightV3 = 204;

    private static final int pointLightV1 = 204;
    private static final int pointLightV2 = 204;
    private static final int pointLightV3 = 204;

    private static final int spotLightV1 = 204;
    private static final int spotLightV2 = 204;
    private static final int spotLightV3 = 204;

    private static final int ambientLightV1 = 102;
    private static final int ambientLightV2 = 102;
    private static final int ambientLightV3 = 102;

    private static final int ambientV1 = 102;
    private static final int ambientV2 = 102;
    private static final int ambientV3 = 102;

    private static final int specularV1 = 127;
    private static final int specularV2 = 127;
    private static final int specularV3 = 127;

    private static final int lightSpecularV1 = 127;
    private static final int lightSpecularV2 = 127;
    private static final int lightSpecularV3 = 127;

    private static final float dirNx = 0;
    private static final float dirNy = 0;

    private static final float dirNz = -1;

    private static final float pX = 0;
    private static final float pY = 0;

    private static final float pZ = 0;

    private static final float spotX = 0;
    private static final float spotY = 0;

    private static final float spotZ = 0;

    private static final float spotNx = 1;
    private static final float spotNy = 1;

    private static final float spotNz = 0;

    private static final float Angle = PI;

    private static final float Concentration = 2;

    private static final float Scaling = 1;

    private static final float lightFalloffV1 = 1;

    private static final float shininess = 5;

    private static final int emmisiveV1 = 50;
    private static final int emmisiveV2 = 50;
    private static final int emmisiveV3 = 50;


    @Override
    public void setup() {
        if (getGraphics().isGL()) {
            final com.jogamp.newt.Window w = (com.jogamp.newt.Window) getSurface().getNative();
            w.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
        }

        surface.setTitle("Processing 3D");
        surface.setCursor(Cursor.MOVE_CURSOR);
        surface.setLocation((int)(ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (width / 2), (int)(ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (height / 2));

        optionsframe = new JFrame("3D Options");
        optionsframe.setSize(470 + 30 + (MainWindow.runsOnWindows ? 0 : 40), 320);
        optionsframe.setPreferredSize(new Dimension(470 + 30 + (MainWindow.runsOnWindows ? 0 : 40), 320));
        optionsframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        optionsframe.setLocation((int)(ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (optionsframe.getWidth() / 2), (int)(ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (optionsframe.getHeight() / 2));
        optionsframe.setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        optionsframe.setResizable(false);


        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(470 + (MainWindow.runsOnWindows ? 0 : 40), 220));
        tabbedPane.setFocusable(false);



        JPanel directionalLight = new JPanel();
        JPanel ambientLight = new JPanel();
        JPanel pointLight = new JPanel();
        JPanel spotLight = new JPanel();
        JPanel materialsPanel = new JPanel();
        JPanel optionsPanel = new JPanel();

        tabbedPane.addTab("Directional", directionalLight);
        tabbedPane.addTab("Ambient", ambientLight);
        tabbedPane.addTab("Point", pointLight);
        tabbedPane.addTab("Spot", spotLight);
        tabbedPane.addTab("Materials", materialsPanel);
        tabbedPane.addTab("Options", optionsPanel);

        tabbedPane.setIconAt(0, MainWindow.getIcon("lightbulb.png"));
        tabbedPane.setIconAt(1, MainWindow.getIcon("lightbulb.png"));
        tabbedPane.setIconAt(2, MainWindow.getIcon("lightbulb.png"));
        tabbedPane.setIconAt(3, MainWindow.getIcon("lightbulb.png"));
        tabbedPane.setIconAt(4, MainWindow.getIcon("material.png"));
        tabbedPane.setIconAt(5, MainWindow.getIcon("gear.png"));

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(true); //content panes must be opaque

        optionsframe.setContentPane(contentPanel);

        invertcb = new JCheckBox("Invert Orientation");
        invertcb.setFocusable(false);
        invertcb.setSelected(false);

        /*useShinines = new JCheckBox("Shininess");
        useShinines.setFocusable(false);
        useShinines.setSelected(true);

        useSpecular = new JCheckBox("Specular");
        useSpecular.setFocusable(false);
        useSpecular.setSelected(true);

        useAmbient = new JCheckBox("Ambient");
        useAmbient.setFocusable(false);
        useAmbient.setSelected(false);

        useEmmisive = new JCheckBox("Emissive");
        useEmmisive.setFocusable(false);
        useEmmisive.setSelected(false);*/

        shininess_slid = new MyJSpinner(6, new SpinnerNumberModel(shininess, 0, 10, 0.1), 3);
        shininess_slid.setToolTipText("Sets the material shininess.");

        specular_slid_v1 = new MyJSpinner(6, new SpinnerNumberModel(specularV1, 0, 255, 1), 3);
        specular_slid_v1.setToolTipText("Sets the specular color of the materials.");


        specular_slid_v2 = new MyJSpinner(6, new SpinnerNumberModel(specularV2, 0, 255, 1), 3);
        specular_slid_v2.setToolTipText("Sets the specular color of the materials.");


        specular_slid_v3 = new MyJSpinner(6, new SpinnerNumberModel(specularV3, 0, 255, 1), 3);
        specular_slid_v3.setToolTipText("Sets the specular color of the materials.");

        /*

        ambient_slid_v1 = new MyJSpinner(6, new SpinnerNumberModel(ambientV1, 0, 255, 1), 3);
        ambient_slid_v1.setToolTipText("Sets the ambient color of the materials.");


        ambient_slid_v2 = new MyJSpinner(6, new SpinnerNumberModel(ambientV2, 0, 255, 1), 3);
        ambient_slid_v2.setToolTipText("Sets the ambient color of the materials.");


        ambient_slid_v3 = new MyJSpinner(6, new SpinnerNumberModel(ambientV3, 0, 255, 1), 3);
        ambient_slid_v3.setToolTipText("Sets the ambient color of the materials.");


        emmisive_slid_v1  = new MyJSpinner(6, new SpinnerNumberModel(emmisiveV1, 0, 255, 1), 3);
        emmisive_slid_v1.setToolTipText("Sets the emissive color of the materials.");

        emmisive_slid_v2  = new MyJSpinner(6, new SpinnerNumberModel(emmisiveV2, 0, 255, 1), 3);
        emmisive_slid_v2.setToolTipText("Sets the emissive color of the materials.");

        emmisive_slid_v3  = new MyJSpinner(6, new SpinnerNumberModel(emmisiveV3, 0, 255, 1), 3);
        emmisive_slid_v3.setToolTipText("Sets the emissive color of the materials.");
*/
        light_specular_slid_v1 = new MyJSpinner(6, new SpinnerNumberModel(lightSpecularV1, 0, 255, 1), 3);
        light_specular_slid_v1.setToolTipText("Sets the specular color of lights.");


        light_specular_slid_v2 = new MyJSpinner(6, new SpinnerNumberModel(lightSpecularV2, 0, 255, 1), 3);
        light_specular_slid_v2.setToolTipText("Sets the specular color of lights.");

        light_specular_slid_v3 = new MyJSpinner(6, new SpinnerNumberModel(lightSpecularV3, 0, 255, 1), 3);
        light_specular_slid_v3.setToolTipText("Sets the specular color of lights.");



        light_fallof_slid_v1 = new MyJSpinner(6, new SpinnerNumberModel(lightFalloffV1, 0, 20, 0.1), 3);
        light_fallof_slid_v1.setToolTipText("Sets the constant value or determining light falloff.");


        directional_light_slid_v1 = new MyJSpinner(6, new SpinnerNumberModel(directionalLightV1, 0, 255, 1), 3);
        directional_light_slid_v1.setToolTipText("Sets the directional light red.");

        directional_light_slid_v2 = new MyJSpinner(6, new SpinnerNumberModel(directionalLightV2, 0, 255, 1), 3);
        directional_light_slid_v2.setToolTipText("Sets the directional light green.");

        directional_light_slid_v3 = new MyJSpinner(6, new SpinnerNumberModel(directionalLightV3, 0, 255, 1), 3);
        directional_light_slid_v3.setToolTipText("Sets the directional light blue.");

        useDirectionalLight = new JCheckBox("Directional Light");
        useDirectionalLight.setSelected(true);
        useDirectionalLight.setFocusable(false);

        useAmbientLight = new JCheckBox("Ambient Light");
        useAmbientLight.setSelected(true);
        useAmbientLight.setFocusable(false);

        ambient_light_slid_v1 = new MyJSpinner(6, new SpinnerNumberModel(ambientLightV1, 0, 255, 1), 3);
        ambient_light_slid_v1.setToolTipText("Sets the ambient light red.");


        ambient_light_slid_v2 = new MyJSpinner(6, new SpinnerNumberModel(ambientLightV2, 0, 255, 1), 3);
        ambient_light_slid_v2.setToolTipText("Sets the ambient light green.");

        ambient_light_slid_v3 = new MyJSpinner(6, new SpinnerNumberModel(ambientLightV3, 0, 255, 1), 3);
        ambient_light_slid_v3.setToolTipText("Sets the ambient light blue.");


        point_light_slid_v1 = new MyJSpinner(6, new SpinnerNumberModel(pointLightV1, 0, 255, 1), 3);
        point_light_slid_v1.setToolTipText("Sets the point light red.");


        point_light_slid_v2 = new MyJSpinner(6, new SpinnerNumberModel(pointLightV2, 0, 255, 1), 3);
        point_light_slid_v2.setToolTipText("Sets the point light green.");

        point_light_slid_v3 = new MyJSpinner(6, new SpinnerNumberModel(pointLightV3, 0, 255, 1), 3);
        point_light_slid_v3.setToolTipText("Sets the point light blue.");

        usePointLight = new JCheckBox("Point Light");
        usePointLight.setSelected(false);
        usePointLight.setFocusable(false);



        spotlight_light_slid_v1 = new MyJSpinner(6, new SpinnerNumberModel(spotLightV1, 0, 255, 1), 3);
        spotlight_light_slid_v1.setToolTipText("Sets the spot light red.");


        spotlight_light_slid_v2 = new MyJSpinner(6, new SpinnerNumberModel(spotLightV2, 0, 255, 1), 3);
        spotlight_light_slid_v2.setToolTipText("Sets the spot light green.");

        spotlight_light_slid_v3 = new MyJSpinner(6, new SpinnerNumberModel(spotLightV3, 0, 255, 1), 3);
        spotlight_light_slid_v3.setToolTipText("Sets the spot light blue.");

        useSpotLight = new JCheckBox("Spot Light");
        useSpotLight.setSelected(false);
        useSpotLight.setFocusable(false);

        dir_light_nx = new MyJSpinner(6, new SpinnerNumberModel(dirNx, -50, 50, 0.1), 3);
        dir_light_nx.setToolTipText("Set direction of light on X.");
        dir_light_ny = new MyJSpinner(6, new SpinnerNumberModel(dirNy, -50, 50, 0.1), 3);
        dir_light_ny.setToolTipText("Set direction of light on Y.");
        dir_light_nz = new MyJSpinner(6, new SpinnerNumberModel(dirNz, -50, 50, 0.1), 3);
        dir_light_nz.setToolTipText("Set direction of light on Z.");

        point_x = new MyJSpinner(6, new SpinnerNumberModel(pX, -1000, 1000, 0.5), 3);
        point_x.setToolTipText("Set coordinate X.");
        point_y = new MyJSpinner(6, new SpinnerNumberModel(pY, -1000, 1000, 0.5), 3);
        point_y.setToolTipText("Set coordinate Y.");
        point_z = new MyJSpinner(6, new SpinnerNumberModel(pZ, -1000, 1000, 0.5), 3);
        point_z.setToolTipText("Set coordinate Z.");

        scaling = new MyJSpinner(6, new SpinnerNumberModel(Scaling, 0, 10, 0.1), 3);

        scaling.setToolTipText("Sets the height scaling.");

        spot_x = new MyJSpinner(6, new SpinnerNumberModel(spotX, -1000, 1000, 0.5), 3);
        spot_x.setToolTipText("Set coordinate X.");
        spot_y = new MyJSpinner(6, new SpinnerNumberModel(spotY, -1000, 1000, 0.5), 3);
        spot_y.setToolTipText("Set coordinate Y.");
        spot_z = new MyJSpinner(6, new SpinnerNumberModel(spotZ, -1000, 1000, 0.5), 3);
        spot_z.setToolTipText("Set coordinate Z.");


        spot_nx = new MyJSpinner(6, new SpinnerNumberModel(spotNx, -50, 50, 0.1), 3);
        spot_nx.setToolTipText("Set direction of light on X.");
        spot_ny = new MyJSpinner(6, new SpinnerNumberModel(spotNy, -50, 50, 0.1), 3);
        spot_ny.setToolTipText("Set direction of light on Y.");
        spot_nz = new MyJSpinner(6, new SpinnerNumberModel(spotNz, -50, 50, 0.1), 3);
        spot_nz.setToolTipText("Set direction of light on Z.");

        spot_angle = new MyJSpinner(6, new SpinnerNumberModel(Angle, -50, 50, 0.5), 3);
        spot_angle.setToolTipText("Set the angle.");

        spot_concentration = new MyJSpinner(6, new SpinnerNumberModel(Concentration, 0, 10000, 0.5), 3);
        spot_concentration.setToolTipText("Set the concentration.");

        JPanel p1 = new JPanel();
        //p1.add(useShinines);
        p1.add(new JLabel(" Shininess: "));
        p1.add(shininess_slid);
        materialsPanel.add(p1);


        JPanel p2 = new JPanel();
        //p2.add(useSpecular);
        p2.add(new JLabel("Specular Red: "));
        p2.add(specular_slid_v1);
        p2.add(new JLabel(" Green: "));
        p2.add(specular_slid_v2);
        p2.add(new JLabel(" Blue: "));
        p2.add(specular_slid_v3);

        /*JPanel p40 = new JPanel();
        p40.add(useAmbient);
        p40.add(new JLabel(" Red: "));
        p40.add(ambient_slid_v1);
        p40.add(new JLabel(" Green: "));
        p40.add(ambient_slid_v2);
        p40.add(new JLabel(" Blue: "));
        p40.add(ambient_slid_v3);

        JPanel p41 = new JPanel();
        p41.add(useEmmisive);
        p41.add(new JLabel(" Red: "));
        p41.add(emmisive_slid_v1);
        p41.add(new JLabel(" Green: "));
        p41.add(emmisive_slid_v2);
        p41.add(new JLabel(" Blue: "));
        p41.add(emmisive_slid_v3);*/


        JPanel p5 = new JPanel();
        p5.add(new JLabel("Light Specular Red: "));
        p5.add(light_specular_slid_v1);

        p5.add(new JLabel(" Green: "));
        p5.add(light_specular_slid_v2);

        p5.add(new JLabel(" Blue: "));
        p5.add(light_specular_slid_v3);


        JPanel p8 = new JPanel();
        p8.add(new JLabel("Falloff Constant: "));
        p8.add(light_fallof_slid_v1);



        JPanel p11 = new JPanel();
        p11.add(new JLabel("Red: "));
        p11.add(directional_light_slid_v1);

        p11.add(new JLabel(" Green: "));
        p11.add(directional_light_slid_v2);

        p11.add(new JLabel(" Blue: "));
        p11.add(directional_light_slid_v3);

        JPanel p23 = new JPanel();
        p23.add(new JLabel("Nx: "));
        p23.add(dir_light_nx);
        p23.add(new JLabel(" Ny: "));
        p23.add(dir_light_ny);
        p23.add(new JLabel(" Nz: "));
        p23.add(dir_light_nz);

        JPanel p26 = new JPanel();
        p26.add(new JLabel("X: "));
        p26.add(spot_x);
        p26.add(new JLabel(" Y: "));
        p26.add(spot_y);
        p26.add(new JLabel(" Z: "));
        p26.add(spot_z);

        JPanel p25 = new JPanel();
        p25.add(new JLabel("Nx: "));
        p25.add(spot_nx);
        p25.add(new JLabel(" Ny: "));
        p25.add(spot_ny);
        p25.add(new JLabel(" Nz: "));
        p25.add(spot_nz);

        JPanel p27 = new JPanel();
        p27.add(new JLabel("Angle: "));
        p27.add(spot_angle);
        p27.add(new JLabel(" Concentration: "));
        p27.add(spot_concentration);


        JPanel p14 = new JPanel();
        p14.add(new JLabel("Red: "));
        p14.add(ambient_light_slid_v1);

        p14.add(new JLabel(" Green: "));
        p14.add(ambient_light_slid_v2);

        p14.add(new JLabel(" Blue: "));
        p14.add(ambient_light_slid_v3);

        JPanel p24 = new JPanel();
        p24.add(new JLabel("X: "));
        p24.add(point_x);
        p24.add(new JLabel(" Y: "));
        p24.add(point_y);
        p24.add(new JLabel(" Z: "));
        p24.add(point_z);

        JPanel p17 = new JPanel();
        p17.add(new JLabel("Red: "));
        p17.add(point_light_slid_v1);

        p17.add(new JLabel(" Green: "));
        p17.add(point_light_slid_v2);

        p17.add(new JLabel(" Blue: "));
        p17.add(point_light_slid_v3);


        JPanel p20 = new JPanel();
        p20.add(new JLabel("Red: "));
        p20.add(spotlight_light_slid_v1);

        p20.add(new JLabel(" Green: "));
        p20.add(spotlight_light_slid_v2);

        p20.add(new JLabel(" Blue: "));
        p20.add(spotlight_light_slid_v3);

        JPanel p33 = new JPanel();
        p33.add(useDirectionalLight);
        p33.setPreferredSize(new Dimension(200, 30));
        directionalLight.add(p33);
        directionalLight.add(p11);
        directionalLight.add(p23);

        JPanel p30 = new JPanel();
        p30.add(useAmbientLight);
        p30.setPreferredSize(new Dimension(200, 30));

        ambientLight.add(p30);
        ambientLight.add(p14);


        JPanel p31 = new JPanel();
        p31.add(usePointLight);
        p31.setPreferredSize(new Dimension(200, 30));

        pointLight.add(p31);
        pointLight.add(p17);
        pointLight.add(p24);


        JPanel p32 = new JPanel();
        p32.add(useSpotLight);
        p32.setPreferredSize(new Dimension(200, 30));
        spotLight.add(p32);
        spotLight.add(p20);
        spotLight.add(p26);
        spotLight.add(p25);
        spotLight.add(p27);

        materialsPanel.add(p2);
//        materialsPanel.add(p40);
//        materialsPanel.add(p41);

        optionsPanel.add(p5);
        optionsPanel.add(p8);

        JPanel top_panel = new JPanel();

        JButton defaults = new JButton("Defaults");
        defaults.setFocusable(true);
        defaults.setIcon(MainWindow.getIcon("reset_small.png"));

        defaults.addActionListener(e->{

            scaling.setValue(Scaling);

            usePointLight.setSelected(false);
            useDirectionalLight.setSelected(true);
            useAmbientLight.setSelected(true);
            useSpotLight.setSelected(false);
            invertcb.setSelected(false);

            light_specular_slid_v1.setValue(lightSpecularV1);
            light_specular_slid_v2.setValue(lightSpecularV2);
            light_specular_slid_v3.setValue(lightSpecularV3);

            specular_slid_v1.setValue(specularV1);
            specular_slid_v2.setValue(specularV2);
            specular_slid_v3.setValue(specularV3);

            shininess_slid.setValue(shininess);
            light_fallof_slid_v1.setValue(lightFalloffV1);

            spot_angle.setValue(Angle);
            spot_concentration.setValue(Concentration);
            spot_x.setValue(spotX);
            spot_y.setValue(spotY);
            spot_z.setValue(spotZ);
            spot_nx.setValue(spotNx);
            spot_ny.setValue(spotNy);
            spot_nz.setValue(spotNz);
            spotlight_light_slid_v1.setValue(spotLightV1);
            spotlight_light_slid_v2.setValue(spotLightV2);
            spotlight_light_slid_v3.setValue(spotLightV3);

            directional_light_slid_v1.setValue(directionalLightV1);
            directional_light_slid_v2.setValue(directionalLightV2);
            directional_light_slid_v3.setValue(directionalLightV3);
            dir_light_nx.setValue(dirNx);
            dir_light_ny.setValue(dirNy);
            dir_light_nz.setValue(dirNz);

            ambient_light_slid_v1.setValue(ambientLightV1);
            ambient_light_slid_v2.setValue(ambientLightV2);
            ambient_light_slid_v3.setValue(ambientLightV3);

            point_light_slid_v1.setValue(pointLightV1);
            point_light_slid_v2.setValue(pointLightV2);
            point_light_slid_v3.setValue(pointLightV3);
            point_x.setValue(pX);
            point_y.setValue(pY);
            point_z.setValue(pZ);

            reset();

//            ambient_slid_v1.setValue(ambientV1);
//            ambient_slid_v2.setValue(ambientV2);
//            ambient_slid_v3.setValue(ambientV3);

//            useShinines.setSelected(true);
//            useSpecular.setSelected(true);
//            useAmbient.setSelected(false);
//            useEmmisive.setSelected(false);

//            emmisive_slid_v1.setValue(emmisiveV1);
//            emmisive_slid_v2.setValue(emmisiveV2);
//            emmisive_slid_v3.setValue(emmisiveV3);
        });

        JButton save_image = new JButton("Save Image", MainWindow.getIcon("save_image_small.png"));
        save_image.setFocusable(false);
        save_image.setToolTipText("Saves a png image.");

        save_image.addActionListener(e->saveImage());

        top_panel.add(invertcb);
        top_panel.add(new JLabel(" Scaling: "));
        top_panel.add(scaling);
        top_panel.add(new JLabel(" "));
        top_panel.add(defaults);
        top_panel.add(save_image);
        contentPanel.add(top_panel);
        contentPanel.add(tabbedPane);



        //Display the window.
        optionsframe.setVisible(true);

    }

    @Override
    public void mouseDragged() {

        if(mouseButton == LEFT) {
            x = mouseX;
            y = mouseY;

            if (!reset) {
                rotX -= (y - oldY) * change;
                rotZ -= (x - oldX) * change;
            }

            oldX = x;
            oldY = y;
        }

    }

    private String saveImagePath;

    private void saveImage() {

        JFileChooser file_chooser = new JFileChooser(MainWindow.SaveImagesPath.isEmpty() ? "." : MainWindow.SaveImagesPath);
        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));

        String name = "fractal " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".png";
        file_chooser.setSelectedFile(new File(name));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(optionsframe, "Save Image");

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = file_chooser.getSelectedFile();

            if (!file.getAbsolutePath().endsWith(".png")) {
                saveImagePath = file.getAbsolutePath() + ".png";
            }
            else {
                saveImagePath = file.getAbsolutePath();
            }

            MainWindow.SaveImagesPath = file.getParent();

        }
    }

    @Override
    public void mousePressed() {
        if(mouseButton == LEFT) {
            oldX = mouseX;
            oldY = mouseY;
            reset = false;
        }
        else {
            reset();
        }
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        float e = event.getCount();
        if(e > 0) {
            if(scale < 0.01) {
                return;
            }
        }
        else  {
            if(scale > 100) {
                return;
            }
        }

        scale -= e * scale_change;
    }

    @Override
    public void mouseReleased() {
        reset = true;
    }

    @Override
    public void keyPressed() {
        if(key == '=') {

            if(scale < 0.01) {
                return;
            }

            scale += scale_change;
        }
        else if(key == '-') {

            if(scale > 100) {
                return;
            }

            scale -= scale_change;
        }
    }

    public void reset() {
        rotX = PI / 3;
        rotZ = -PI / 4;
        scale = 0.8f;
    }

    public void bringToFront() {


        try {
            if (getGraphics().isGL()) {
                final com.jogamp.newt.Window w = (com.jogamp.newt.Window) getSurface().getNative();
                w.requestFocus();
            }

            optionsframe.requestFocus();
            optionsframe.toFront();
            surface.setCursor(Cursor.MOVE_CURSOR);
        }
        catch (Exception ex) {}

    }


    @Override
    public void draw() {

        float[][][] data = ThreadDraw.vert;

        try {

            background(0);

            scale(scale);
            translate(width / (2 * scale), height / (2 * scale));
            rotateX(rotX);
            rotateZ(rotZ);
            translate(-image_size / 2, -image_size / 2);

            if(data == null) {
                return;
            }

            if(!readyToDraw) {
                return;
            }

            noStroke();

            //if(useShinines.isSelected()) {
            shininess(Float.parseFloat(shininess_slid.getText()));
//            }
//            else {
//                shininess(1);
//            }

            //if(useSpecular.isSelected()) {
            specular(Float.parseFloat(specular_slid_v1.getText()), Float.parseFloat(specular_slid_v2.getText()), Float.parseFloat(specular_slid_v3.getText()));
//            }
//            else {
//                specular(0.49019608f, 0.49019608f, 0.49019608f);
//            }

            /*if(useAmbient.isSelected()) {
                ambient(Float.parseFloat(ambient_slid_v1.getText()), Float.parseFloat(ambient_slid_v2.getText()), Float.parseFloat(ambient_slid_v3.getText()));
            }
            else {
                ambient(1, 1, 1);
            }*/

            /*if(useEmmisive.isSelected()) {
                emissive(Float.parseFloat(emmisive_slid_v1.getText()), Float.parseFloat(emmisive_slid_v2.getText()), Float.parseFloat(emmisive_slid_v3.getText()));
            }
            else {
                emissive(0, 0, 0);
            }*/

            lightSpecular(Float.parseFloat(light_specular_slid_v1.getText()), Float.parseFloat(light_specular_slid_v2.getText()), Float.parseFloat(light_specular_slid_v3.getText()));
            lightFalloff(Float.parseFloat(light_fallof_slid_v1.getText()), 0, 0);

            if(useDirectionalLight.isSelected()) {
                directionalLight(Float.parseFloat(directional_light_slid_v1.getText()), Float.parseFloat(directional_light_slid_v2.getText()), Float.parseFloat(directional_light_slid_v3.getText()), Float.parseFloat(dir_light_nx.getText()), Float.parseFloat(dir_light_ny.getText()), Float.parseFloat(dir_light_nz.getText()));
            }

            if(useAmbientLight.isSelected()) {
                ambientLight(Float.parseFloat(ambient_light_slid_v1.getText()), Float.parseFloat(ambient_light_slid_v2.getText()), Float.parseFloat(ambient_light_slid_v3.getText()));
            }

            if(usePointLight.isSelected()) {
                pointLight(Float.parseFloat(point_light_slid_v1.getText()), Float.parseFloat(point_light_slid_v2.getText()), Float.parseFloat(point_light_slid_v3.getText()), Float.parseFloat(point_x.getText()), Float.parseFloat(point_y.getText()), Float.parseFloat(point_z.getText()));
            }

            if(useSpotLight.isSelected()) {
                spotLight(Float.parseFloat(spotlight_light_slid_v1.getText()), Float.parseFloat(spotlight_light_slid_v2.getText()), Float.parseFloat(spotlight_light_slid_v3.getText()),
                        Float.parseFloat(spot_x.getText()), Float.parseFloat(spot_y.getText()), Float.parseFloat(spot_z.getText()),
                        Float.parseFloat(spot_nx.getText()), Float.parseFloat(spot_ny.getText()), Float.parseFloat(spot_nz.getText()),
                        Float.parseFloat(spot_angle.getText()), Float.parseFloat(spot_concentration.getText()));


            }

            float scale_factor = Float.parseFloat(scaling.getText());

            if(ThreadDraw.D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 1) {
                int red, green, blue;
                for (int y = 0; y < details - 1; y++) {
                    int yp1 = y + 1;
                    beginShape(TRIANGLE_STRIP);
                    for (int x = 0; x < details; x++) {
                        red = ((int) data[x][y][0] >> 16) & 0xff;
                        green = ((int) data[x][y][0] >> 8) & 0xff;
                        blue = ((int) data[x][y][0]) & 0xff;

                        fill(red, green, blue);

                        vertex(x * scl, y * scl,  scale_factor * (invertcb.isSelected() ? -data[x][y][1] : data[x][y][1]));
                        red = ((int) data[x][yp1][0] >> 16) & 0xff;
                        green = ((int) data[x][yp1][0] >> 8) & 0xff;
                        blue = ((int) data[x][yp1][0]) & 0xff;

                        fill(red, green, blue);

                        vertex(x * scl, yp1 * scl, scale_factor * (invertcb.isSelected() ? -data[x][yp1][1] : data[x][yp1][1]));
                    }
                    endShape();
                }
            }
            else {

                int red, green, blue;

                for (int x = 0; x < details - 1; x++) {
                    int xp1 = x + 1;
                    for (int y = 0; y < details - 1; y++) {

                        int red1 = ((int) data[x][y][0] >> 16) & 0xff;
                        int green1 = ((int) data[x][y][0] >> 8) & 0xff;
                        int blue1 = ((int) data[x][y][0]) & 0xff;

                        int yp1 = y + 1;

                        if(ThreadDraw.D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 0) {
                            red = red1;
                            green = green1;
                            blue = blue1;
                        }
                        else {
                            int red2 = ((int) data[xp1][y][0] >> 16) & 0xff;
                            int green2 = ((int) data[xp1][y][0] >> 8) & 0xff;
                            int blue2 = ((int) data[xp1][y][0]) & 0xff;

                            int red3 = ((int) data[x][yp1][0] >> 16) & 0xff;
                            int green3 = ((int) data[x][yp1][0] >> 8) & 0xff;
                            int blue3 = ((int) data[x][yp1][0]) & 0xff;

                            red = (int)((red1 + red2 + red3) / 3.0 + 0.5);
                            green = (int)((green1 + green2 + green3) / 3.0 + 0.5);
                            blue = (int)((blue1 + blue2 + blue3) / 3.0 + 0.5);
                        }


                        beginShape(TRIANGLE);
                        fill(red, green, blue);

                        vertex(x * scl, y * scl, scale_factor * (invertcb.isSelected() ? -data[x][y][1] : data[x][y][1]));
                        vertex(xp1 * scl, y * scl, scale_factor * (invertcb.isSelected() ? -data[xp1][y][1] : data[xp1][y][1]));
                        vertex(x * scl, yp1 * scl, scale_factor * (invertcb.isSelected() ? -data[x][yp1][1] : data[x][yp1][1]));
                        endShape();


                        red1 = ((int) data[xp1][y][0] >> 16) & 0xff;
                        green1 = ((int) data[xp1][y][0] >> 8) & 0xff;
                        blue1 = ((int) data[xp1][y][0]) & 0xff;

                        if(ThreadDraw.D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 0) {
                            red = red1;
                            green = green1;
                            blue = blue1;
                        }
                        else {
                            int red2 = ((int) data[xp1][yp1][0] >> 16) & 0xff;
                            int green2 = ((int) data[xp1][yp1][0] >> 8) & 0xff;
                            int blue2 = ((int) data[xp1][yp1][0]) & 0xff;

                            int red3 = ((int) data[x][yp1][0] >> 16) & 0xff;
                            int green3 = ((int) data[x][yp1][0] >> 8) & 0xff;
                            int blue3 = ((int) data[x][yp1][0]) & 0xff;

                            red = (int)((red1 + red2 + red3) / 3.0 + 0.5);
                            green = (int)((green1 + green2 + green3) / 3.0 + 0.5);
                            blue = (int)((blue1 + blue2 + blue3) / 3.0 + 0.5);
                        }


                        beginShape(TRIANGLE);
                        fill(red, green, blue);

                        vertex(xp1 * scl, y * scl, scale_factor * (invertcb.isSelected() ? -data[xp1][y][1] : data[xp1][y][1]));
                        vertex(xp1 * scl, yp1 * scl, scale_factor * (invertcb.isSelected() ? -data[xp1][yp1][1] : data[xp1][yp1][1]) );
                        vertex(x * scl, yp1 * scl, scale_factor * (invertcb.isSelected() ? -data[x][yp1][1] : data[x][yp1][1]));

                        endShape();
                    }
                }
            }


        }
        catch (Exception ex) {

        }

        if(saveImagePath != null) {
            save(saveImagePath);
            saveImagePath = null;
        }
    }

    public void setReadyToDraw(boolean readyToDraw) {
        this.readyToDraw = readyToDraw;
    }
}
