/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author hrkalona2
 */
public class ProcessingMenu extends JMenu {
	private static final long serialVersionUID = 880061806683700045L;
	private MainWindow ptr;
    private JMenuItem bump_map_opt;
    private JMenuItem rainbow_palette_opt;
    private JMenuItem fake_de_opt;
    private JMenuItem entropy_coloring_opt;
    private JMenuItem offset_coloring_opt;
    private JMenuItem greyscale_coloring_opt;
    private JMenuItem smoothing_opt;
    private JMenuItem exterior_de_opt;
    private JMenuItem orbit_traps_opt;
    private JMenuItem contour_coloring_opt;
    private JMenuItem order_opt;
    
    public ProcessingMenu(MainWindow ptr2, String name) {
        super(name);

        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/processing.png"));
        
        smoothing_opt = new JMenuItem("Smoothing", getIcon("/fractalzoomer/icons/smoothing.png"));
        exterior_de_opt = new JMenuItem("Distance Estimation", getIcon("/fractalzoomer/icons/distance_estimation.png"));
        bump_map_opt = new JMenuItem("Bump Mapping", getIcon("/fractalzoomer/icons/bump_map.png"));
        fake_de_opt = new JMenuItem("Fake Distance Estimation", getIcon("/fractalzoomer/icons/fake_distance_estimation.png"));
        entropy_coloring_opt = new JMenuItem("Entropy Coloring", getIcon("/fractalzoomer/icons/entropy_coloring.png"));
        offset_coloring_opt = new JMenuItem("Offset Coloring", getIcon("/fractalzoomer/icons/offset_coloring.png"));
        greyscale_coloring_opt = new JMenuItem("Greyscale Coloring", getIcon("/fractalzoomer/icons/greyscale_coloring.png"));
        rainbow_palette_opt = new JMenuItem("Rainbow Palette", getIcon("/fractalzoomer/icons/rainbow_palette.png"));
        orbit_traps_opt = new JMenuItem("Orbit Traps", getIcon("/fractalzoomer/icons/orbit_traps.png"));
        contour_coloring_opt = new JMenuItem("Contour Coloring", getIcon("/fractalzoomer/icons/contour_coloring.png"));
        order_opt = new JMenuItem("Processing Order", getIcon("/fractalzoomer/icons/list.png"));
        
        smoothing_opt.setToolTipText("Smooths the image's color transitions.");
        exterior_de_opt.setToolTipText("<html>Sets some points near the boundary of<br>the set to the maximum iterations value.</html>");
        bump_map_opt.setToolTipText("Emulates a light source to create a pseudo 3d image.");
        fake_de_opt.setToolTipText("Emulates the effect of distance estimation.");
        entropy_coloring_opt.setToolTipText("Calculates the entropy of neaby points to create a coloring effect.");
        offset_coloring_opt.setToolTipText("Adds a palette offset, into areas with smooth iteration gradients.");
        greyscale_coloring_opt.setToolTipText("Converts the areas with smooth iteration gradients, to greyscale.");
        rainbow_palette_opt.setToolTipText("Creates a pseudo 3d image, by applying the palette colors as a rainbow.");
        orbit_traps_opt.setToolTipText("Applies a coloring effect when an orbit gets trapped under a specific condition.");
        contour_coloring_opt.setToolTipText("Blends the contours that are created by the fractional part of the iterations.");
        order_opt.setToolTipText("Changes the application order of the processing algorithms.");
        
        smoothing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
        exterior_de_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
        bump_map_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0));
        fake_de_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0));
        entropy_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0));
        offset_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0));
        greyscale_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0));
        rainbow_palette_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        orbit_traps_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0));
        contour_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0));
        order_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0));
        
        contour_coloring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setContourColoring();
            }
        });
        
        smoothing_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setSmoothing();
            }
        });

        exterior_de_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setExteriorDistanceEstimation();
            }
        });

        bump_map_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setBumpMap();
            }
        });

        rainbow_palette_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setRainbowPalette();
            }
        });

        fake_de_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setFakeDistanceEstimation();
            }
        });
        
        greyscale_coloring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setGreyScaleColoring();
            }
        });

        entropy_coloring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setEntropyColoring();
            }
        });

        offset_coloring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setOffsetColoring();
            }
        });
        
        orbit_traps_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setOrbitTraps();
            }
        });
        
        order_opt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setProcessingOrder();
            }
        
        });
        
        add(smoothing_opt);
        add(exterior_de_opt);
        add(fake_de_opt);
        add(entropy_coloring_opt);
        add(offset_coloring_opt);
        add(rainbow_palette_opt);
        add(orbit_traps_opt);
        add(greyscale_coloring_opt);
        add(contour_coloring_opt);
        add(bump_map_opt);
        addSeparator();
        add(order_opt);
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
    public JMenuItem getEntropyColoring() {
        
        return entropy_coloring_opt;
        
    }
    
    public JMenuItem getOffsetColoring() {
        
        return offset_coloring_opt;
        
    }
    
    public JMenuItem getGreyScaleColoring() {
        
        return greyscale_coloring_opt;
        
    }
    
    public JMenuItem getRainbowPalette() {
        
        return rainbow_palette_opt;
        
    }
    
    public JMenuItem getBumpMap() {
        
        return bump_map_opt;
        
    }
    
    public JMenuItem getFakeDistanceEstimation() {
        
        return fake_de_opt;
        
    }
    
    public JMenuItem getDistanceEstimation() {
        
        return exterior_de_opt;
        
    }
    
    public JMenuItem getSmoothing() {
        
        return smoothing_opt;
        
    }
    
    public JMenuItem getOrbitTraps() {
        
        return orbit_traps_opt;
        
    }
    
    public JMenuItem getContourColoring() {
        
        return contour_coloring_opt;
        
    }
    
    public JMenuItem getProcessingOrder() {
        
        return order_opt;
        
    }
    
    public void updateIcons(Settings s) {
        
        if(s.fns.smoothing) {
            smoothing_opt.setIcon(getIcon("/fractalzoomer/icons/smoothing_enabled.png"));
        }
        else {
            smoothing_opt.setIcon(getIcon("/fractalzoomer/icons/smoothing.png"));
        }
        
        if(s.bms.bump_map) {
            bump_map_opt.setIcon(getIcon("/fractalzoomer/icons/bump_map_enabled.png"));
        }
        else {
            bump_map_opt.setIcon(getIcon("/fractalzoomer/icons/bump_map.png"));
        }
        
        if(s.rps.rainbow_palette) {
            rainbow_palette_opt.setIcon(getIcon("/fractalzoomer/icons/rainbow_palette_enabled.png"));
        }
        else {
            rainbow_palette_opt.setIcon(getIcon("/fractalzoomer/icons/rainbow_palette.png"));
        }
        
        if(s.ots.useTraps) {
            orbit_traps_opt.setIcon(getIcon("/fractalzoomer/icons/orbit_traps_enabled.png"));
        }
        else {
            orbit_traps_opt.setIcon(getIcon("/fractalzoomer/icons/orbit_traps.png"));
        }
        
        if(s.cns.contour_coloring) {
            contour_coloring_opt.setIcon(getIcon("/fractalzoomer/icons/contour_coloring_enabled.png"));
        }
        else {
            contour_coloring_opt.setIcon(getIcon("/fractalzoomer/icons/contour_coloring.png"));
        }
        
        if(s.gss.greyscale_coloring) {
            greyscale_coloring_opt.setIcon(getIcon("/fractalzoomer/icons/greyscale_coloring_enabled.png"));
        }
        else {
            greyscale_coloring_opt.setIcon(getIcon("/fractalzoomer/icons/greyscale_coloring.png"));
        }
        
        if(s.ofs.offset_coloring) {
            offset_coloring_opt.setIcon(getIcon("/fractalzoomer/icons/offset_coloring_enabled.png"));
        }
        else {
            offset_coloring_opt.setIcon(getIcon("/fractalzoomer/icons/offset_coloring.png"));
        }
        
        if(s.fdes.fake_de) {
            fake_de_opt.setIcon(getIcon("/fractalzoomer/icons/fake_distance_estimation_enabled.png"));
        }
        else {
            fake_de_opt.setIcon(getIcon("/fractalzoomer/icons/fake_distance_estimation.png"));
        }
        
        if(s.ens.entropy_coloring) {
            entropy_coloring_opt.setIcon(getIcon("/fractalzoomer/icons/entropy_coloring_enabled.png"));
        }
        else {
            entropy_coloring_opt.setIcon(getIcon("/fractalzoomer/icons/entropy_coloring.png"));
        }
        
        if(s.exterior_de) {
            exterior_de_opt.setIcon(getIcon("/fractalzoomer/icons/distance_estimation_enabled.png"));
        }
        else {
            exterior_de_opt.setIcon(getIcon("/fractalzoomer/icons/distance_estimation.png"));
        }
        
    }
    
}
