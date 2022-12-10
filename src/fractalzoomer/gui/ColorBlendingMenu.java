/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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

import javax.swing.*;

/**
 *
 * @author hrkalona2
 */
public class ColorBlendingMenu extends JMenu {
	private static final long serialVersionUID = 4186992298757883317L;
	private MainWindow ptr;
    private JRadioButtonMenuItem[] color_blending;
    private JMenu darken;
    private JMenu lighten;
    private JMenu contrast;
    private JMenu inversion;
    private JMenu cancelation;
    private JMenu component;

    private JCheckBoxMenuItem revertOrderOfColors;
    
    public static final String[] colorBlendingNames;
    
    static {
        colorBlendingNames = new String[MainWindow.TOTAL_COLOR_BLENDING]; 
        colorBlendingNames[MainWindow.NORMAL_BLENDING] = "Normal";
        colorBlendingNames[MainWindow.MULTIPLY_BLENDING] = "Multiply";
        colorBlendingNames[MainWindow.DIVIDE_BLENDING] = "Divide";
        colorBlendingNames[MainWindow.ADDITION_BLENDING] = "Addition";
        colorBlendingNames[MainWindow.SUBTRACTION_BLENDING] = "Subtraction";
        colorBlendingNames[MainWindow.DIFFERENCE_BLENDING] = "Difference";
        colorBlendingNames[MainWindow.VALUE_BLENDING] = "Value (HSV)";
        colorBlendingNames[MainWindow.SOFT_LIGHT_BLENDING] = "Soft Light";
        colorBlendingNames[MainWindow.SCREEN_BLENDING] = "Screen";
        colorBlendingNames[MainWindow.DODGE_BLENDING] = "Dodge";
        colorBlendingNames[MainWindow.BURN_BLENDING] = "Burn";
        colorBlendingNames[MainWindow.DARKEN_ONLY_BLENDING] = "Darken Only";
        colorBlendingNames[MainWindow.LIGHTEN_ONLY_BLENDING] = "Lighten Only";
        colorBlendingNames[MainWindow.HARD_LIGHT_BLENDING] = "Hard Light";
        colorBlendingNames[MainWindow.GRAIN_EXTRACT_BLENDING] = "Grain Extract";
        colorBlendingNames[MainWindow.GRAIN_MERGE_BLENDING] = "Grain Merge";
        colorBlendingNames[MainWindow.SATURATION_BLENDING] = "Saturation (HSV)";
        colorBlendingNames[MainWindow.COLOR_BLENDING] = "Color (HSV)";
        colorBlendingNames[MainWindow.HUE_BLENDING] = "Hue (HSV)";
        colorBlendingNames[MainWindow.EXCLUSION_BLENDING] = "Exclusion";
        colorBlendingNames[MainWindow.PIN_LIGHT_BLENDING] = "Pin Light";
        colorBlendingNames[MainWindow.LINEAR_LIGHT_BLENDING] = "Linear Light";
        colorBlendingNames[MainWindow.VIVID_LIGHT_BLENDING] = "Vivid Light";
        colorBlendingNames[MainWindow.OVERLAY_BLENDING] = "Overlay";
        colorBlendingNames[MainWindow.LCH_CHROMA_BLENDING] = "Chroma (LCH)";
        colorBlendingNames[MainWindow.LCH_COLOR_BLENDING] = "Color (LCH)";
        colorBlendingNames[MainWindow.LCH_HUE_BLENDING] = "Hue (LCH)";
        colorBlendingNames[MainWindow.LCH_LIGHTNESS_BLENDING] = "Lightness (LCH)";
        colorBlendingNames[MainWindow.LUMINANCE_BLENDING] = "Luminance";
        colorBlendingNames[MainWindow.LINEAR_BURN_BLENDING] = "Linear Burn";
    }

    public ColorBlendingMenu(MainWindow ptr2, String name, int selection, boolean color_blending_revert_colors) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(MainWindow.getIcon("blending.png"));
        
        darken = new JMenu("Darken");
        lighten = new JMenu("Lighten");
        contrast = new JMenu("Contrast");
        inversion = new JMenu("Inversion");
        cancelation = new JMenu("Cancelation");
        component = new JMenu("Component");
        
        color_blending = new JRadioButtonMenuItem[colorBlendingNames.length];
        ButtonGroup color_transfer_group = new ButtonGroup();
        
        color_blending[MainWindow.NORMAL_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.NORMAL_BLENDING]);
        color_blending[MainWindow.NORMAL_BLENDING].setToolTipText("Sets the color blending to normal.");
        color_blending[MainWindow.NORMAL_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.NORMAL_BLENDING));
        add(color_blending[MainWindow.NORMAL_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.NORMAL_BLENDING]);
        
        color_blending[MainWindow.LIGHTEN_ONLY_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.LIGHTEN_ONLY_BLENDING]);
        color_blending[MainWindow.LIGHTEN_ONLY_BLENDING].setToolTipText("Sets the color blending to lighten only.");
        color_blending[MainWindow.LIGHTEN_ONLY_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.LIGHTEN_ONLY_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.LIGHTEN_ONLY_BLENDING]);
        
        color_blending[MainWindow.SCREEN_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.SCREEN_BLENDING]);
        color_blending[MainWindow.SCREEN_BLENDING].setToolTipText("Sets the color blending to screen.");
        color_blending[MainWindow.SCREEN_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.SCREEN_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.SCREEN_BLENDING]);
        
        color_blending[MainWindow.DODGE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.DODGE_BLENDING]);
        color_blending[MainWindow.DODGE_BLENDING].setToolTipText("Sets the color blending to dodge.");
        color_blending[MainWindow.DODGE_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.DODGE_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.DODGE_BLENDING]);
        
        color_blending[MainWindow.ADDITION_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.ADDITION_BLENDING]);
        color_blending[MainWindow.ADDITION_BLENDING].setToolTipText("Sets the color blending to addition.");
        color_blending[MainWindow.ADDITION_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.ADDITION_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.ADDITION_BLENDING]);
        
        color_blending[MainWindow.DARKEN_ONLY_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.DARKEN_ONLY_BLENDING]);
        color_blending[MainWindow.DARKEN_ONLY_BLENDING].setToolTipText("Sets the color blending to darken only.");
        color_blending[MainWindow.DARKEN_ONLY_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.DARKEN_ONLY_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.DARKEN_ONLY_BLENDING]);
        
        color_blending[MainWindow.MULTIPLY_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.MULTIPLY_BLENDING]);
        color_blending[MainWindow.MULTIPLY_BLENDING].setToolTipText("Sets the color blending to multiply.");
        color_blending[MainWindow.MULTIPLY_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.MULTIPLY_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.MULTIPLY_BLENDING]);
        
        color_blending[MainWindow.BURN_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.BURN_BLENDING]);
        color_blending[MainWindow.BURN_BLENDING].setToolTipText("Sets the color blending to burn.");
        color_blending[MainWindow.BURN_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.BURN_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.BURN_BLENDING]);
        
        color_blending[MainWindow.LINEAR_BURN_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.LINEAR_BURN_BLENDING]);
        color_blending[MainWindow.LINEAR_BURN_BLENDING].setToolTipText("Sets the color blending to linear burn.");
        color_blending[MainWindow.LINEAR_BURN_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.LINEAR_BURN_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.LINEAR_BURN_BLENDING]);
        
        color_blending[MainWindow.OVERLAY_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.OVERLAY_BLENDING]);
        color_blending[MainWindow.OVERLAY_BLENDING].setToolTipText("Sets the color blending to overlay.");
        color_blending[MainWindow.OVERLAY_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.OVERLAY_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.OVERLAY_BLENDING]);
        
        color_blending[MainWindow.SOFT_LIGHT_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.SOFT_LIGHT_BLENDING]);
        color_blending[MainWindow.SOFT_LIGHT_BLENDING].setToolTipText("Sets the color blending to soft light.");
        color_blending[MainWindow.SOFT_LIGHT_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.SOFT_LIGHT_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.SOFT_LIGHT_BLENDING]);
        
        color_blending[MainWindow.HARD_LIGHT_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.HARD_LIGHT_BLENDING]);
        color_blending[MainWindow.HARD_LIGHT_BLENDING].setToolTipText("Sets the color blending to hard light.");
        color_blending[MainWindow.HARD_LIGHT_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.HARD_LIGHT_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.HARD_LIGHT_BLENDING]);
        
        color_blending[MainWindow.VIVID_LIGHT_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.VIVID_LIGHT_BLENDING]);
        color_blending[MainWindow.VIVID_LIGHT_BLENDING].setToolTipText("Sets the color blending to vivid light.");
        color_blending[MainWindow.VIVID_LIGHT_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.VIVID_LIGHT_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.VIVID_LIGHT_BLENDING]);
        
        color_blending[MainWindow.PIN_LIGHT_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.PIN_LIGHT_BLENDING]);
        color_blending[MainWindow.PIN_LIGHT_BLENDING].setToolTipText("Sets the color blending to pin light.");
        color_blending[MainWindow.PIN_LIGHT_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.PIN_LIGHT_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.PIN_LIGHT_BLENDING]);
        
        color_blending[MainWindow.LINEAR_LIGHT_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.LINEAR_LIGHT_BLENDING]);
        color_blending[MainWindow.LINEAR_LIGHT_BLENDING].setToolTipText("Sets the color blending to linear light.");
        color_blending[MainWindow.LINEAR_LIGHT_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.LINEAR_LIGHT_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.LINEAR_LIGHT_BLENDING]);
        
        color_blending[MainWindow.DIFFERENCE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.DIFFERENCE_BLENDING]);
        color_blending[MainWindow.DIFFERENCE_BLENDING].setToolTipText("Sets the color blending to difference.");
        color_blending[MainWindow.DIFFERENCE_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.DIFFERENCE_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.DIFFERENCE_BLENDING]);
        
        color_blending[MainWindow.EXCLUSION_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.EXCLUSION_BLENDING]);
        color_blending[MainWindow.EXCLUSION_BLENDING].setToolTipText("Sets the color blending to exclusion.");
        color_blending[MainWindow.EXCLUSION_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.EXCLUSION_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.EXCLUSION_BLENDING]);
        
        color_blending[MainWindow.SUBTRACTION_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.SUBTRACTION_BLENDING]);
        color_blending[MainWindow.SUBTRACTION_BLENDING].setToolTipText("Sets the color blending to subtraction.");
        color_blending[MainWindow.SUBTRACTION_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.SUBTRACTION_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.SUBTRACTION_BLENDING]);
        
        color_blending[MainWindow.GRAIN_EXTRACT_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.GRAIN_EXTRACT_BLENDING]);
        color_blending[MainWindow.GRAIN_EXTRACT_BLENDING].setToolTipText("Sets the color blending to grain extract.");
        color_blending[MainWindow.GRAIN_EXTRACT_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.GRAIN_EXTRACT_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.GRAIN_EXTRACT_BLENDING]);
        
        color_blending[MainWindow.GRAIN_MERGE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.GRAIN_MERGE_BLENDING]);
        color_blending[MainWindow.GRAIN_MERGE_BLENDING].setToolTipText("Sets the color blending to grain merge.");
        color_blending[MainWindow.GRAIN_MERGE_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.GRAIN_MERGE_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.GRAIN_MERGE_BLENDING]);
        
        color_blending[MainWindow.DIVIDE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.DIVIDE_BLENDING]);
        color_blending[MainWindow.DIVIDE_BLENDING].setToolTipText("Sets the color blending to divide.");
        color_blending[MainWindow.DIVIDE_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.DIVIDE_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.DIVIDE_BLENDING]);
        
        color_blending[MainWindow.HUE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.HUE_BLENDING]);
        color_blending[MainWindow.HUE_BLENDING].setToolTipText("Sets the color blending to hue (hsv).");
        color_blending[MainWindow.HUE_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.HUE_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.HUE_BLENDING]);
        
        color_blending[MainWindow.SATURATION_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.SATURATION_BLENDING]);
        color_blending[MainWindow.SATURATION_BLENDING].setToolTipText("Sets the color blending to saturation (hsv).");
        color_blending[MainWindow.SATURATION_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.SATURATION_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.SATURATION_BLENDING]);
        
        color_blending[MainWindow.COLOR_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.COLOR_BLENDING]);
        color_blending[MainWindow.COLOR_BLENDING].setToolTipText("Sets the color blending to color (hsv).");
        color_blending[MainWindow.COLOR_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.COLOR_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.COLOR_BLENDING]);

        color_blending[MainWindow.VALUE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.VALUE_BLENDING]);
        color_blending[MainWindow.VALUE_BLENDING].setToolTipText("Sets the color blending to value (hsv).");
        color_blending[MainWindow.VALUE_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.VALUE_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.VALUE_BLENDING]);
        
        color_blending[MainWindow.LCH_HUE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.LCH_HUE_BLENDING]);
        color_blending[MainWindow.LCH_HUE_BLENDING].setToolTipText("Sets the color blending to hue (lab).");
        color_blending[MainWindow.LCH_HUE_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.LCH_HUE_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.LCH_HUE_BLENDING]);
        
        color_blending[MainWindow.LCH_CHROMA_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.LCH_CHROMA_BLENDING]);
        color_blending[MainWindow.LCH_CHROMA_BLENDING].setToolTipText("Sets the color blending to chroma (lab).");
        color_blending[MainWindow.LCH_CHROMA_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.LCH_CHROMA_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.LCH_CHROMA_BLENDING]);
        
        color_blending[MainWindow.LCH_COLOR_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.LCH_COLOR_BLENDING]);
        color_blending[MainWindow.LCH_COLOR_BLENDING].setToolTipText("Sets the color blending to color (lab).");
        color_blending[MainWindow.LCH_COLOR_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.LCH_COLOR_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.LCH_COLOR_BLENDING]);
        
        color_blending[MainWindow.LCH_LIGHTNESS_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.LCH_LIGHTNESS_BLENDING]);
        color_blending[MainWindow.LCH_LIGHTNESS_BLENDING].setToolTipText("Sets the color blending to lightness (lab).");
        color_blending[MainWindow.LCH_LIGHTNESS_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.LCH_LIGHTNESS_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.LCH_LIGHTNESS_BLENDING]);
        
        color_blending[MainWindow.LUMINANCE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.LUMINANCE_BLENDING]);
        color_blending[MainWindow.LUMINANCE_BLENDING].setToolTipText("Sets the color blending to luminance.");
        color_blending[MainWindow.LUMINANCE_BLENDING].addActionListener(e -> ptr.setColorBlending(MainWindow.LUMINANCE_BLENDING));
        color_transfer_group.add(color_blending[MainWindow.LUMINANCE_BLENDING]);

        revertOrderOfColors = new JCheckBoxMenuItem("Reverse Order of Colors");
        revertOrderOfColors.setToolTipText("Reverts the order of colors in the blending operation");
        revertOrderOfColors.setSelected(color_blending_revert_colors);
        revertOrderOfColors.addActionListener(e -> {
            ptr2.setColorBlendingRevertColors(revertOrderOfColors.isSelected());
        });
        
        darken.add(color_blending[MainWindow.DARKEN_ONLY_BLENDING]);
        darken.add(color_blending[MainWindow.MULTIPLY_BLENDING]);
        darken.add(color_blending[MainWindow.BURN_BLENDING]);
        darken.add(color_blending[MainWindow.LINEAR_BURN_BLENDING]);
        
        lighten.add(color_blending[MainWindow.LIGHTEN_ONLY_BLENDING]);
        lighten.add(color_blending[MainWindow.SCREEN_BLENDING]);
        lighten.add(color_blending[MainWindow.DODGE_BLENDING]);
        lighten.add(color_blending[MainWindow.ADDITION_BLENDING]);
        
        contrast.add(color_blending[MainWindow.OVERLAY_BLENDING]);
        contrast.add(color_blending[MainWindow.SOFT_LIGHT_BLENDING]);
        contrast.add(color_blending[MainWindow.HARD_LIGHT_BLENDING]);
        contrast.add(color_blending[MainWindow.VIVID_LIGHT_BLENDING]);
        contrast.add(color_blending[MainWindow.LINEAR_LIGHT_BLENDING]);
        contrast.add(color_blending[MainWindow.PIN_LIGHT_BLENDING]);
        
        inversion.add(color_blending[MainWindow.DIFFERENCE_BLENDING]);
        inversion.add(color_blending[MainWindow.EXCLUSION_BLENDING]);
        
        cancelation.add(color_blending[MainWindow.SUBTRACTION_BLENDING]);
        cancelation.add(color_blending[MainWindow.GRAIN_EXTRACT_BLENDING]);
        cancelation.add(color_blending[MainWindow.GRAIN_MERGE_BLENDING]);
        cancelation.add(color_blending[MainWindow.DIVIDE_BLENDING]);
        
        component.add(color_blending[MainWindow.HUE_BLENDING]);
        component.add(color_blending[MainWindow.SATURATION_BLENDING]);
        component.add(color_blending[MainWindow.COLOR_BLENDING]);
        component.add(color_blending[MainWindow.VALUE_BLENDING]);
        component.add(color_blending[MainWindow.LCH_HUE_BLENDING]);
        component.add(color_blending[MainWindow.LCH_CHROMA_BLENDING]);
        component.add(color_blending[MainWindow.LCH_COLOR_BLENDING]);
        component.add(color_blending[MainWindow.LCH_LIGHTNESS_BLENDING]);
        component.add(color_blending[MainWindow.LUMINANCE_BLENDING]);
        
        add(darken);
        add(lighten);
        add(contrast);
        add(inversion);
        add(cancelation);
        add(component);
        addSeparator();
        add(revertOrderOfColors);
        
        color_blending[selection].setSelected(true);
    }
    
    public JRadioButtonMenuItem[] getBlendingModes() {
        
        return color_blending;
        
    }
}
