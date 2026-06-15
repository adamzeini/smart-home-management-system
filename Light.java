
package projectoop1;


public class Light extends Device {

    private boolean adjustable;
    private int level = 100;

    public Light(int id, String name, double maxPowerConsumption) {
        this(id, name, maxPowerConsumption,false);
    }

    public Light(int id, String name, double maxPowerConsumption, boolean adjustable) {
        this(id, name, maxPowerConsumption,false,adjustable);

    }

    public Light(int id, String name, double maxPowerConsumption, boolean critical, boolean adjustable) { 
        super(id, name, maxPowerConsumption, critical);
        setAdjustable(adjustable);
    }

    public boolean isAdjustable() {
        return adjustable;
    }

    public void setAdjustable(boolean adjustable) {
        this.adjustable = adjustable;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level >= 0 && level <= 100) {
            this.level = level;
        }
    }

    @Override
    public void turnOn() {
        super.turnOn();
        setLevel(100);
    }

    public void turnOn(int lev) {
        super.turnOn();
        if (adjustable) {
            setLevel(lev);
        } else {
            setLevel(100);
        }
    }

    public double getCurrentConsumption() { //return the current power consumption of a Light based on level
        if (getStatus() == 1) {

            return (level / 100.0) * getMaxPowerConsumption();
        } else {

            return 0;
        }

    }

    @Override
    public String toString() {
        String adj;
        if (adjustable == true) {
            adj = "adjustable";
        } else {
            adj = "not adjustable";
        }
        return "Light{ " + super.toString() + ", [" + adj + "], level = [" + level + "] }";
    }
    
}
    
    

