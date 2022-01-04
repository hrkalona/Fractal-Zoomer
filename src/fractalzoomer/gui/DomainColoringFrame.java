/*
 * Copyright (C) 2020 hrkalona
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
import fractalzoomer.main.app_settings.DomainColoringSettings;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.main.Constants.CUSTOM_PALETTE_ID;
import static fractalzoomer.main.Constants.domainAlgNames;

/**
 *
 * @author kaloch
 */
public class DomainColoringFrame extends JFrame {
	private static final long serialVersionUID = -4505878017329986771L;
	private MainWindow ptra2;
    private DomainColoringFrame this_frame;

    public DomainColoringFrame(MainWindow ptra, final Settings s) {

        super();

        ptra2 = ptra;

        this_frame = this;

        ptra2.setEnabled(false);
        int custom_palette_window_width = 750;
        int custom_palette_window_height = 390;
        setTitle("Domain Coloring");
        setIconImage(getIcon("/fractalzoomer/icons/domain_coloring.png").getImage());

        setSize(custom_palette_window_width, custom_palette_window_height);
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (custom_palette_window_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (custom_palette_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        final JCheckBox domain_coloring = new JCheckBox("Domain Coloring");
        domain_coloring.setSelected(s.ds.domain_coloring);
        domain_coloring.setFocusable(false);
        domain_coloring.setToolTipText("Enables domain coloring.");
        domain_coloring.setBackground(MainWindow.bg_color);

        JPanel first_panel = new JPanel();
        first_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        first_panel.setBackground(MainWindow.bg_color);

        first_panel.add(domain_coloring);

        JPanel domain_coloring_panel = new JPanel();

        domain_coloring_panel.setPreferredSize(new Dimension(650, 253));
        domain_coloring_panel.setLayout(new GridLayout(4, 1));
        domain_coloring_panel.setBackground(MainWindow.bg_color);

        final JTextField iterations_textfield = new JTextField(10);
        iterations_textfield.setText("" + s.max_iterations);

        JPanel settings_panel = new JPanel();
        settings_panel.setLayout(new FlowLayout());
        settings_panel.setBackground(MainWindow.bg_color);
        settings_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Settings", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        String[] color_modes = {"HSB", "Current Palette", "LCH", "Cubehelix", "Cubehelix3"};
        final JComboBox use_palette_dc = new JComboBox(color_modes);
        use_palette_dc.setSelectedIndex(s.ds.domain_coloring_mode);
        use_palette_dc.setBackground(MainWindow.bg_color);
        use_palette_dc.setFocusable(false);
        use_palette_dc.setToolTipText("Sets the coloring mode.");
        
        final JTextField offset_textfield = new JTextField(10);
        offset_textfield.setText("" + s.ps.color_cycling_location);

        JPanel s1 = new JPanel();
        s1.setLayout(new FlowLayout());
        s1.setBackground(MainWindow.bg_color);
        
        s1.add(new JLabel("Maximum Iterations: "));
        s1.add(iterations_textfield);
        s1.add(new JLabel("  Color: "));
        s1.add(use_palette_dc);
        s1.add(new JLabel("  Color Offset: "));
        s1.add(offset_textfield);
        
        JPanel processing_panel = new JPanel();
        processing_panel.setLayout(new FlowLayout());
        processing_panel.setBackground(MainWindow.bg_color);
        processing_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Processing", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        
        JPanel s2 = new JPanel();
        s2.setLayout(new FlowLayout());
        s2.setBackground(MainWindow.bg_color);
        
        final JComboBox domain_processing_transfer_opt = new JComboBox(MainWindow.domainProcessingTransferNames);
        domain_processing_transfer_opt.setSelectedIndex(s.ds.domainProcessingTransfer);
        domain_processing_transfer_opt.setFocusable(false);
        domain_processing_transfer_opt.setToolTipText("Sets the domain coloring processing transfer function.");

        final JComboBox domain_height_opt = new JComboBox(MainWindow.domainHeightNames);
        domain_height_opt.setSelectedIndex(s.ds.domain_height_method);
        domain_height_opt.setFocusable(false);
        domain_height_opt.setToolTipText("Sets the domain coloring height method.");


        final JTextField factor_textfield = new JTextField(8);
        factor_textfield.setText("" + s.ds.domainProcessingHeightFactor);

        s2.add(new JLabel("Height: "));
        s2.add(domain_height_opt);
        s2.add(new JLabel("  Transfer Function: "));
        s2.add(domain_processing_transfer_opt);
        s2.add(new JLabel("  Factor: "));
        s2.add(factor_textfield);
        
        settings_panel.add(s1);
        processing_panel.add(s2);
        

        final JComboBox color_domain_algs_opt = new JComboBox(domainAlgNames);
        color_domain_algs_opt.setSelectedIndex(s.ds.domain_coloring_alg);
        color_domain_algs_opt.setFocusable(false);
        color_domain_algs_opt.setToolTipText("Sets the domain coloring algorithm.");

        ButtonGroup color_transfer_group = new ButtonGroup();
        final JRadioButton presetButton = new JRadioButton("Preset");
        presetButton.setBackground(MainWindow.bg_color);
        presetButton.setFocusable(false);
        presetButton.setToolTipText("Uses a preset domain coloring algorithm.");
        final JRadioButton customButton = new JRadioButton("Custom");
        customButton.setToolTipText("Uses a custom domain coloring algorithm.");
        customButton.setBackground(MainWindow.bg_color);
        customButton.setFocusable(false);

        color_transfer_group.add(presetButton);
        color_transfer_group.add(customButton);

        final JButton custom = new JButton("");
        custom.setToolTipText("Sets the custom domain coloring settings.");
        custom.setPreferredSize(new Dimension(30, 30));
        custom.setIcon(getIcon("/fractalzoomer/icons/domain_coloring.png"));
        custom.setFocusable(false);

        CustomDomainColoringFrame.setSettings(new DomainColoringSettings(s.ds));

        custom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomDomainColoringFrame(this_frame);
            }

        });

        if (s.ds.customDomainColoring) {
            customButton.setSelected(true);
            custom.setEnabled(true);
            color_domain_algs_opt.setEnabled(false);
        } else {
            presetButton.setSelected(true);
            custom.setEnabled(false);
            color_domain_algs_opt.setEnabled(true);
        }

        customButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (customButton.isSelected()) {
                    custom.setEnabled(true);
                    color_domain_algs_opt.setEnabled(false);
                } else {
                    custom.setEnabled(false);
                    color_domain_algs_opt.setEnabled(true);
                }
            }

        });

        presetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (presetButton.isSelected()) {
                    custom.setEnabled(false);
                    color_domain_algs_opt.setEnabled(true);
                } else {
                    custom.setEnabled(true);
                    color_domain_algs_opt.setEnabled(false);
                }
            }

        });

        
        JPanel custom_panel = new JPanel();
        custom_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Domain Coloring Algorithm", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        custom_panel.setLayout(new FlowLayout());
        custom_panel.setBackground(MainWindow.bg_color);
        custom_panel.add(presetButton);
        custom_panel.add(color_domain_algs_opt);
        custom_panel.add(customButton);
        custom_panel.add(custom);


        domain_coloring_panel.add(first_panel);
        domain_coloring_panel.add(settings_panel);
        domain_coloring_panel.add(processing_panel);
        domain_coloring_panel.add(custom_panel);

        JPanel buttons = new JPanel();

        buttons.setLayout(new FlowLayout());
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new JButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);
        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                int tempIterations;
                double tempFactor;
                int tempOffset;
                
                try {
                    tempIterations = Integer.parseInt(iterations_textfield.getText());
                    tempFactor = Double.parseDouble(factor_textfield.getText());
                    tempOffset = Integer.parseInt(offset_textfield.getText());

                    if (tempIterations <= 0) {
                        JOptionPane.showMessageDialog(this_frame, "Maximum iterations number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if(tempFactor <= 0) {
                        JOptionPane.showMessageDialog(this_frame, "Processing factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if(tempOffset < 0) {
                        JOptionPane.showMessageDialog(this_frame, "Color offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                s.ds = CustomDomainColoringFrame.getSettings();
                
                s.max_iterations = tempIterations;
                
                if (presetButton.isSelected()) {
                    s.ds.customDomainColoring = false;
                }
                else {
                    s.ds.customDomainColoring = true;
                }
                
                s.ds.domain_coloring_alg = color_domain_algs_opt.getSelectedIndex();
                s.ds.domain_coloring_mode = use_palette_dc.getSelectedIndex();
                s.ds.domainProcessingTransfer = domain_processing_transfer_opt.getSelectedIndex();
                s.ds.domainProcessingHeightFactor = tempFactor;
                s.ds.domain_height_method = domain_height_opt.getSelectedIndex();
                
                s.ps.color_cycling_location = tempOffset;
                
                if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
                    s.temp_color_cycling_location = s.ps.color_cycling_location;
                }

                ptra2.setDomainColoringSettings(domain_coloring.isSelected());
                ptra2.setEnabled(true);
                dispose();

            }

        });

        buttons.add(ok);

        JButton cancel = new JButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        buttons.add(cancel);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(680, 310));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(domain_coloring_panel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

        round_panel.add(buttons, con);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        main_panel.add(round_panel, con);

        JScrollPane scrollPane = new JScrollPane(main_panel);
        add(scrollPane);

        setVisible(true);
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

}
