
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;

import java.util.Arrays;


/**
 *
 * @author hrkalona2
 */
public abstract class OrbitTrap {
    protected double distance;
    protected Complex point;
    protected double trapLength;
    protected double trapWidth;
    protected int trapId;
    protected Complex pixel;
    protected int iterations;
    protected int extraIterations;
    protected Complex trappedPoint;
    protected boolean trapped;
    protected boolean countTrapIterations;
    protected int checkType;
    protected boolean isJulia;
    protected boolean usesHighPrecision;
    protected boolean usesStaticInitVal;
    protected boolean doFirstIterationSkipCheck;
    protected int skipTrapCheckForIterations;

    protected OrbitSample[] sampleItems;
    protected int sampleItem;
    protected int lastXItems;
    protected boolean keepLastXItems;
    protected boolean processedLastItems;
    
    protected OrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, double trapWidth, boolean countTrapIterations, int lastXItems) {
        
        point = new Complex(pointRe, pointIm);
        this.trapLength = trapLength;
        this.trapWidth = trapWidth;
        this.countTrapIterations = countTrapIterations;
        this.checkType = checkType;
        this.lastXItems = lastXItems;

        if(lastXItems > 0) {
            keepLastXItems = true;
            sampleItems = new OrbitSample[lastXItems];
            sampleItem = 0;
            processedLastItems = false;
        }
    }
    
    public void initialize(Complex pixel) {
        
        distance = Double.MAX_VALUE;
        trapId = -1;
        this.pixel = new Complex(pixel);
        iterations = 0;
        extraIterations = 0;
        trappedPoint = new Complex();
        trapped = false;
        doFirstIterationSkipCheck = !isJulia && (usesHighPrecision || usesStaticInitVal);

        if(keepLastXItems) {
            Arrays.fill(sampleItems, 0, sampleItems.length, null);
            sampleItem = 0;
            processedLastItems = false;
        }
        
    }

    public void check(Complex val, int iteration) {

        if(keepLastXItems) {
            sampleItems[sampleItem % sampleItems.length] = new OrbitSample(val, iteration);
            sampleItem++;
            return;
        }

        process(val, iteration);
    }

    private void process(Complex val, int iteration) {
        if((doFirstIterationSkipCheck && iteration == 0) || (iteration < skipTrapCheckForIterations)) {
            return;
        }
        checkInternal(val, iteration);
    }

    protected abstract void checkInternal(Complex val, int iteration);

    protected void processLastItems() {
        int start = sampleItem >= sampleItems.length ? sampleItem % sampleItems.length : 0;
        for(int i = start, count = 0; count < sampleItems.length; i++, count++) {
            OrbitSample sam = sampleItems[i % sampleItems.length];

            if(sam != null) {
                process(sam.z_val, sam.iterations);
            }
        }
        processedLastItems = true;
    }
    
    public double getDistance() {

        if(keepLastXItems && !processedLastItems) {
            processLastItems();
        }

        return distance;

    }
    
    public abstract double getMaxValue();

    public double getMinValue() {
        return 0;
    }
    
    public int getColor() {
        
        return 0;
        
    }
    
    public boolean hasColor() {
        
        return false;
        
    }
    
    public int getIteration() {
        
        return iterations + extraIterations;
        
    }
    
    public Complex getTrappedPoint() {
        
        return trappedPoint;
        
    }
    
    public double applyLineFunction(int type, double value) {

        if(type == 0) return 0;
        
        switch (type) {
            case 1:
                return Math.sin(value);
            case 2:
                return Math.cos(value);
            case 3:
                return Math.tan(value);
            case 4:
                return Math.sinh(value);
            case 5:
                return Math.cosh(value);
            case 6:
                return Math.tanh(value);
            case 7:
                return Math.asin(value);
            case 8:
                return Math.acos(value);
            case 9:
                return Math.atan(value);
            case 10:
                return value * value;
            case 11:
                return value * value * value;
            case 12:
                return Math.sqrt(value);
            case 13:
                return Math.cbrt(value);
            case 14:
                return Math.exp(value);
            case 15:
                return Math.log(value);
            case 16:
                return Math.abs(value);      
        }
        
        return 0;
    }
    
    public int getTrapId(){
        
        return trapId;
                
    }

    protected void setTrappedData(Complex val, int iteration) {

        iterations = iteration;
        trappedPoint = new Complex(val);
        trapped = true;
        
        if(countTrapIterations) {
            extraIterations++;
        }

    }
    
    protected void countExtraIterations() {
        if(countTrapIterations) {
            extraIterations++;
        }
    }

    public boolean isTrapped() {

        return trapped;

    }

    public void setJulia(boolean julia) {
        isJulia = julia;
    }

    public void setUsesHighPrecision(boolean usesHighPrecision) {
        this.usesHighPrecision = usesHighPrecision;
    }

    public void setUsesStaticInitVal(boolean usesStaticInitVal) {
        this.usesStaticInitVal = usesStaticInitVal;
    }

    public void setSkipTrapCheckForIterations(int skipTrapCheckForIterations) {
        this.skipTrapCheckForIterations = skipTrapCheckForIterations;
    }


}
