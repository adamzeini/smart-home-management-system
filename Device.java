
package projectoop1;


public class Device {

    private int id = 0;
    private String name;
    private int status;
    private double maxPowerConsumption = 50;
    private boolean critical;

    public Device(int id, String name, double maxPowerConsumption) { 
        this(id, name, maxPowerConsumption, false);
    }

    public Device(int id, String name, double maxPowerConsumption, boolean critical) {
        setDevice(id, name, maxPowerConsumption, critical);
    }

    public void setDevice(int id, String name, double maxPowerConsumption, boolean critical) {
        setId(id);
        setName(name);
        setMaxPowerConsumption(maxPowerConsumption);
        setCritical(critical);
    }

    public void setId(int id) {
        if (id >= 100 && id <= 999) {
            this.id = id;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(int status) {
        if (status == 1 || status == 2 || status == 0) {
            this.status = status;
        }
    }

    public void setMaxPowerConsumption(double maxPowerConsumtion) {
        if (maxPowerConsumtion > 0) {
            this.maxPowerConsumption = maxPowerConsumtion;
        }
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public double getMaxPowerConsumption() {
        return maxPowerConsumption;
    }

    public boolean isCritical() {
        return critical;
    }

    public void turnOn() {
        setStatus(1);
    }

    public void turnOff() {
        setStatus(0);
    }

    public double getCurrentConsumption() {
        if (status == 1) {
            return maxPowerConsumption;
        } else {
            return 0;
        }
    }

    public String toString() { //convert  status value to string message
        String st, cr;
        if (status == 1) { //convert  status to string message
            st = "On";
        } else if (status == 2) {
            st = "Standby";
        } else {
            st = "Off";
        }
        if (critical == true) { //convert critical state to string message
            cr = "critical";
        } else {
            cr = "not critical";
        }
        return "id=[" + id + "] ,name = [" + name + "],[" + st + "] , max power consumption= [" + maxPowerConsumption + "],[" + cr + "]";
    }
}
