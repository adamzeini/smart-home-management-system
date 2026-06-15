
package projectoop1;

import java.util.Arrays;

public class Appliance extends Device {

    private int[] powerLevels;
    private int currentLevel;
    private boolean noisy;

    public Appliance(int id, String name, double maxPowerConsumption, int[] powerLevels, boolean noisy) {
        this(id, name, maxPowerConsumption, false, powerLevels, noisy);
    }

    public Appliance(int id, String name, double maxPowerConsumption, boolean critical, int[] powerLevels, boolean noisy) {
        super(id, name, maxPowerConsumption, critical);
        setAppliance(powerLevels, currentLevel, noisy);
    }

    public void setAppliance(int[] powerLevels, int currentLevel, boolean noisy) {
        setPowerLevels(powerLevels);
        setCurrentLevel(currentLevel);
        setNoisy(noisy);
    }

    public void setPowerLevels(int[] powerLevels) {
        if (powerLevels == null || powerLevels.length == 0) {
            this.powerLevels = new int[]{100};
            this.currentLevel = 0;
            return;
        }
        this.powerLevels = new int[powerLevels.length];
        for (int i = 0; i < powerLevels.length; i++) {
            if (powerLevels[i] >= 0 && powerLevels[i] <= 100) {
                this.powerLevels[i] = powerLevels[i];
            } else {
                this.powerLevels[i] = 100;
            }
        }
        Arrays.sort(this.powerLevels);
        if (currentLevel < 0 || currentLevel >= this.powerLevels.length) {
            currentLevel = 0;
        }
    }

    public void setCurrentLevel(int currentLevel) {
        if (powerLevels != null && currentLevel >= 0 && currentLevel < powerLevels.length) {
            this.currentLevel = currentLevel;
        } else {
            this.currentLevel = 0;
        }
    }

    public void setNoisy(boolean noisy) {
        this.noisy = noisy;
    }

    public int[] getPowerLevels() {
        return powerLevels;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public boolean isNoisy() {
        return noisy;
    }

    public void turnOn() {
        super.turnOn();
        setCurrentLevel(0);
    }

    public void turnOn(int level) {
        super.turnOn();
        setCurrentLevel(level);
    }

    public double getCurrentConsumption() {
        if (getStatus() == 1) {
            return getMaxPowerConsumption() * (powerLevels[currentLevel] / 100.0);
        } else {
            return 0;
        }
    }

    public String toString() { //Return a string representation of a device + power levels + current level + noisy status
        StringBuilder printPowerLevels = new StringBuilder("{[");
        for (int i = 0; i < powerLevels.length; i++) {
            printPowerLevels.append(powerLevels[i]);
            if (i < powerLevels.length - 1) {
                printPowerLevels.append(", ");
            }
        }
        printPowerLevels.append("]}");
        String isNoisy;
        if (noisy == true) {
            isNoisy = "noisy";
        } else {
            isNoisy = "not noisy";
        }
        return "Appliance{ " + super.toString() + " , power Levels = " + printPowerLevels.toString() + " , level = [" + currentLevel + "] , " + isNoisy + "]}";
    }
}
