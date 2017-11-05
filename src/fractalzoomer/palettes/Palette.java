/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.palettes;

/**
 *
 * @author kaloch
 */
public class Palette {
    protected PaletteColor palette_color;
    
    public Palette() 
    {
        
    }
    
    public int getPaletteColor(double result) {
        
        return palette_color.getPaletteColor(result);
        
    }
    
    public int getPaletteLength() {
        
        return palette_color.getPaletteLength();
        
    }
}
