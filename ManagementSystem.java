
package projectoop1;
import java.util.Scanner;

import java.util.ArrayList;
public class ManagementSystem {

    private String adminPassword; // Admin password for the system 
    private String userPassword; // User password for the system
    private ArrayList<Room> rooms; // list of rooms inside of the house
    private double maxAllowedPower; // maximum allowed power consumption
    private boolean day; // represents the time of the day 
    private ArrayList<Device> waitingListDay; // represents the noisy devices waiting to be turned on in the morning 
    private ArrayList<Device> waitingListPower; // represents the devices put on standby until the power consumption allows it

    public static int LOW = 1000; // power mode 1 
    public static int MEDIUM = 4000; // power mode 2 
    public static int HIGH = 10000; // power mode 3 

    public ManagementSystem(String adminPassword, String userPassword) { // with arg constructo that takes String adminPassword and String userPassword as parameteres
        setManagementSystem(adminPassword, userPassword); // calling a method that sets all the parameteres
        waitingListDay = new ArrayList<>();
        waitingListPower = new ArrayList<>();
        rooms = new ArrayList<>();
        this.maxAllowedPower = MEDIUM;
        this.day = true;

    }

    public void setManagementSystem(String adminPassword, String userPassword) { // method that calls the setters for the parameteres of the with arg constructor
        setAdminPassword(adminPassword); // calling the seter for adminPassword
        setUserPassword(userPassword); // calling the seter for userPassword
    }

    public void setAdminPassword(String adminPassword) {
        if (IsValid(adminPassword) == true) {
            this.adminPassword = adminPassword;
        }

    }

    public void setUserPassword(String userPassword) {
        if (IsValid(userPassword) == true) {
            this.userPassword = userPassword;
        }
    }

    public void setDayTime() { // sets the time of the day 
        this.day = true;
        System.out.println("Day mode is now active.");
        checkDayWaitingList();
        askToTurnOffLights();

    }

    public void setNightTime() {
        this.day = false;
        System.out.println("Night mode is now active.");
        checkNoisyRunningDevices();
    }

    public void setPowerMode(String mode) {
        mode = mode.toUpperCase();
        switch (mode) {
            case "LOW":
                this.maxAllowedPower = LOW;
                break;
            case "MEDIUM":
                this.maxAllowedPower = MEDIUM;
                break;
            case "NORMAL":
                this.maxAllowedPower = MEDIUM;
                break;
            case "HIGH":
                this.maxAllowedPower = HIGH;
                break;
        }
        checkPowerAfterModeChange();
    }

    public boolean IsValid(String Password) { // checks the validity of the new password entered 
        if (Password.length() >= 8) {
            boolean hasUpper = false;
            boolean hasLower = false;
            boolean HasDigit = false;
            for (int i = 0; i < Password.length(); i++) {

                char c = Password.charAt(i);
                if (Character.isUpperCase(c)) {
                    hasUpper = true;
                }
                if (Character.isLowerCase(c)) {
                    hasLower = true;
                }
                if (Character.isDigit(c)) {
                    HasDigit = true;
                }
            }
            if (hasUpper && hasLower && HasDigit) {
                return true;
            }
        }
        return false;

    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public double getMaxAllowedPower() {
        return maxAllowedPower;
    }

    public boolean isDay() {
        return day;
    }

    public ArrayList<Device> getWaitingListDay() {
        return waitingListDay;
    }

    public ArrayList<Device> getWaitingListPower() {
        return waitingListPower;
    }

    public String changeAdminPassword(String Password) {
        if (IsValid(Password) == true) { // checking if the entered pass is valid 
            this.adminPassword = Password;
            return "Password has been changed successfully";
        }
        return "Unsuccessful change;Password does not meet requirements";
    }

    public String changeUserPassword(String Password) {
        if (IsValid(Password) == true) {
            this.userPassword = Password;
            return "Password has been changed successfully";
        }
        return "Unsuccessful change";
    }

    public void displaySummaryAllRooms() { // method that prints the details of all the rooms 
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < rooms.size(); i++) {

            s.append(rooms.get(i).toBriefString()); // adding the infos of each room in the arrayList to the StringBuilder
        }
        System.out.println(s.toString());
    }

    public void displayDetailsOneRoom(int index) {
        System.out.println(rooms.get(index).toString()); // printing details of a specific room 

    }

    public void addRoom(Room r) { // adding a room to the arraylisy
        rooms.add(r);

    }

    public boolean addDevice(String roomCode, Device d) { // ading a new device to a specific room from the arraylist
        if (searchDeviceById(d.getId()) != null) {
            return false;
        }
        for (int i = 0; i < rooms.size(); i++) {
            if (roomCode.equals(rooms.get(i).getCode())) {
                Room room = rooms.get(i);
                room.addDevice(d);
                return true;

            }

        }

        return false;
    }

    public boolean removeRoom(String roomCode) { // deletting a specific room from the rooms arraylist
        for (int i = 0; i < rooms.size(); i++) {
            if (roomCode.equals(rooms.get(i).getCode())) {
                for (int j = 0; j < rooms.get(i).getDevicesList().size(); j++) {
                    removeFromWaitingLists(rooms.get(i).getDevicesList().get(j));
                }
                rooms.remove(i);
                checkPowerWaitingList();
                return true;

            }

        }

        return false;
    }

    public boolean removeDevice(String roomCode, int deviceCode) { // deletting a specific device from a specific room in the rooms arraylist
        for (int i = 0; i < rooms.size(); i++) {
            if (roomCode.equals(rooms.get(i).getCode())) {
                Room room = rooms.get(i);
                for (int j = 0; j < room.getDevicesList().size(); j++) {
                    if (deviceCode == room.getDevicesList().get(j).getId()) {
                        Device device = room.getDevicesList().get(j);
                        removeFromWaitingLists(device);
                        room.getDevicesList().remove(j);
                        checkPowerWaitingList();
                        return true;
                    }

                }

            }

        }

        return false;
    }

    public void turnOnDevice(String code, int id) {  // turns on a device if the conditions are met
        Scanner input = new Scanner(System.in);

        Room room = searchRoomByCode(code); // searching if the room that we want to turn on a device inside of it is found or not
        if (room == null) {
            System.out.println("Room not found with code: " + code);
            return;
        }
        Device device = room.searchDeviceById(id); // searching if the device that we want to turn on is found or not
        if (device == null) {
            System.out.println("Device not found with ID: " + id + " in room " + code);
            return;
        }
        if (device.getStatus() == 1) { // checks if the device is on or off
            System.out.println("Device is already on");
            return;
        }
        chooseDeviceLevel(device, input);

        double currentConsumption = getCurrentPowerConsumption();
        double deviceConsumption = getConsumptionIfDeviceIsOn(device);

        if (currentConsumption + deviceConsumption > maxAllowedPower) { // testing if the device we want to turn on exceeds the max allowed power consumption
            System.out.println("Warning: Turning on this device would exceed the power limit.");
            System.out.println("Options:");
            System.out.println("1. Add to power waiting list");
            System.out.println("2. Cancel");

            int choice = input.nextInt();
            if (choice == 1) { // if the device consumption is larger than the allowed the user has a choice to put in on the waiting list
                addToWaitingList(waitingListPower, device);
                device.setStatus(2); // Standby mode
                System.out.println("Device added to power waiting list.");
            }
            return;
        }
        if (!day && device instanceof Appliance) { // now if the device meets the conditions and if it is night time and the device is an appliance
            Appliance appliance = (Appliance) device;
            if (appliance.isNoisy()) { // checking if the appliance is noisy 

                System.out.println("Warning: This is a noisy device and night mode is active.");
                System.out.println("Options:");
                System.out.println("1. Turn on anyway");
                System.out.println("2. Add to day waiting list");
                System.out.println("3. Cancel");

                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        // Continue with turning on
                        break;
                    case 2:
                        addToWaitingList(waitingListDay, device);
                        device.setStatus(2); //put the device into standby mode  
                        System.out.println("Device added to day waiting list.");
                        return;
                    case 3:
                        System.out.println("Operation canceled."); // do not turn on the noisy appliance
                        return;
                    default:
                        System.out.println("Invalid choice. Operation canceled.");
                        return;
                }
            }
        }
        removeFromWaitingLists(device);
        turnOnWithCurrentLevel(device);

        System.out.println("Device turned on successfully.");

        currentConsumption = getCurrentPowerConsumption();
        System.out.println("Current power consumption:" + currentConsumption + "/" + maxAllowedPower + "W");
    }

    public double getCurrentPowerConsumption() { // calculating the total power consumption in the house
        double total = 0;
        for (Room room : rooms) {
            total += room.getCurrentComsuption();
        }
        return total;
    }

    public void turnOffDevice(String code, int id) { // method that takes a room code and an device id , if both are found the devices will be turned off
        Room room = searchRoomByCode(code); // checking if the room is found
        if (room == null) {
            System.out.println("Room not found with code: " + code);
            return;
        }

        Device device = room.searchDeviceById(id); // checking if the device is found
        if (device == null) {
            System.out.println("Device not found with ID: " + id + " in room " + code);
            return;
        }
        if (device.getStatus() == 0) { // checking if the device is on or off
            System.out.println("Device is already OFF");
            return;

        }
        if (confirmCriticalDevice(device) == false) {
            System.out.println("Operation canceled.");
            return;
        }
        device.turnOff(); // if the device is on 
        removeFromWaitingLists(device);
        System.out.println("Device turned off successfully.");

        checkPowerWaitingList();
    }

    public void displayInfo() { // method that prints general infos
        System.out.println("Management System Information:");
        System.out.println("Admin Password: " + adminPassword);
        System.out.println("User Password: " + userPassword);
        System.out.println("Number of Rooms: " + rooms.size());
        System.out.println("Current Mode: " + (day ? "Day" : "Night"));
        System.out.println("Current Power Consumption: " + getCurrentPowerConsumption() + "W");
    }

    public void shutDownOneRoom(String code) { // turns off all devices in a specific room
        Room room = searchRoomByCode(code);
        if (room == null) {
            System.out.println("Room not found with code: " + code);
            return;
        }
        for (int i = 0; i < room.getNbOfDevices(); i++) {
            Device device = room.getDevicesList().get(i);
            if (device.getStatus() != 0 && confirmCriticalDevice(device)) {
                device.turnOff();
                removeFromWaitingLists(device);
            }
        }
        checkPowerWaitingList();
    }

    public void shutDownAllDevices() { // turns off all the house devices
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = 0; j < rooms.get(i).getDevicesList().size(); j++) {
                Device device = rooms.get(i).getDevicesList().get(j);
                if (device.getStatus() != 0 && confirmCriticalDevice(device)) {
                    device.turnOff();
                    removeFromWaitingLists(device);
                }
            }
        }
        checkPowerWaitingList();
    }

    public Device searchDeviceById(int id) { // check if a device is found or no
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            for (int j = 0; j < room.getDevicesList().size(); j++) {
                if (id == room.getDevicesList().get(j).getId()) {
                    return room.getDevicesList().get(j);
                }
            }

        }
        return null;
    }

    public Room searchRoomByCode(String code) { // checks if a room is found or no
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getCode().equals(code)) {
                return rooms.get(i);
            }
        }
        return null;
    }

    public void checkAllDevicesInfos() { // prints all devices infos
        StringBuilder dev = new StringBuilder();
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = 0; j < rooms.get(i).getDevicesList().size(); j++) {
                dev.append(rooms.get(i).getDevicesList().get(j).toString());
                dev.append("\n");
            }
        }
        System.out.println(dev.toString());

    }

    public void checkStandByDevicesWaitingListDay() {  // prints all standby devices infos that are waiting till day time

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < waitingListDay.size(); i++) {
            s.append(waitingListDay.get(i).toString());
            s.append("\n");
        }
        System.out.println(s.toString());

    }

    public void checkAllRunningDevices() { // prints the infos of all on devices
        StringBuilder run = new StringBuilder();
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = 0; j < rooms.get(i).getDevicesList().size(); j++) {
                if (rooms.get(i).getDevicesList().get(j).getStatus() == 1) {
                    run.append(rooms.get(i).getDevicesList().get(j).toString());
                    run.append("\n");
                }
            }
        }
        System.out.println(run.toString());

    }

    public void checkStandByDevicesWaitingPower() {  // prints all standby devices infos that are waiting till power consumption is enough so they can be turned on
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < waitingListPower.size(); i++) {
            s.append(waitingListPower.get(i).toString());
            s.append("\n");

        }
        System.out.println(s.toString());
    }

    public void setDayNightMode() { // changing time mode 
        Scanner input = new Scanner(System.in);
        String mode;
        do {
            System.out.println("do you want to enter in day mode or in night mode ? (enter the word 'day' or 'night' to enter the mode that you want) ");
            mode = input.next();
            mode = mode.toLowerCase();
            if (mode.equals("day")) {
                setDayTime();
            } else if (mode.equals("night")) {
                setNightTime();
            } else {
                System.out.println("invalid input enter again : ");
            }
        } while (!mode.equals("day") && !mode.equals("night"));
    }

    private void chooseDeviceLevel(Device device, Scanner input) { // lets the user choose the wanted level before turning on the device
        if (device instanceof Light) {
            Light light = (Light) device;
            if (light.isAdjustable()) {
                System.out.println("Enter light level between 0 and 100:");
                int level = input.nextInt();
                light.setLevel(level);
            } else {
                light.setLevel(100);
            }
        } else if (device instanceof Appliance) {
            Appliance appliance = (Appliance) device;
            if (appliance.getPowerLevels().length > 1) {
                System.out.println("Available power levels:");
                for (int i = 0; i < appliance.getPowerLevels().length; i++) {
                    System.out.println(i + ") " + appliance.getPowerLevels()[i] + "%");
                }
                System.out.println("Enter level index:");
                int level = input.nextInt();
                appliance.setCurrentLevel(level);
            } else {
                appliance.setCurrentLevel(0);
            }
        }
    }

    private double getConsumptionIfDeviceIsOn(Device device) { // returns the expected consumption if the device is running
        if (device instanceof Light) {
            Light light = (Light) device;
            return (light.getLevel() / 100.0) * light.getMaxPowerConsumption();
        } else if (device instanceof Appliance) {
            Appliance appliance = (Appliance) device;
            return appliance.getMaxPowerConsumption() * (appliance.getPowerLevels()[appliance.getCurrentLevel()] / 100.0);
        }
        return device.getMaxPowerConsumption();
    }

    private void turnOnWithCurrentLevel(Device device) { // turns on a device without losing the chosen level
        if (device instanceof Light) {
            Light light = (Light) device;
            light.turnOn(light.getLevel());
        } else if (device instanceof Appliance) {
            Appliance appliance = (Appliance) device;
            appliance.turnOn(appliance.getCurrentLevel());
        } else {
            device.turnOn();
        }
    }

    private void addToWaitingList(ArrayList<Device> waitingList, Device device) {
        if (waitingList.contains(device) == false) {
            waitingList.add(device);
        }
    }

    private void removeFromWaitingLists(Device device) {
        waitingListDay.remove(device);
        waitingListPower.remove(device);
    }

    private boolean confirmCriticalDevice(Device device) { // critical devices need the admin password before shutdown
        if (device.isCritical() == false) {
            return true;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("This device is critical. Enter admin password to confirm:");
        String pass = input.next();
        return pass.equals(adminPassword);
    }

    private void checkPowerWaitingList() { // tries to turn on standby devices when power becomes available
        for (int i = 0; i < waitingListPower.size(); i++) {
            Device device = waitingListPower.get(i);
            if (!day && device instanceof Appliance && ((Appliance) device).isNoisy()) {
                waitingListPower.remove(i);
                addToWaitingList(waitingListDay, device);
                device.setStatus(2);
                i--;
                continue;
            }
            if (getCurrentPowerConsumption() + getConsumptionIfDeviceIsOn(device) <= maxAllowedPower) {
                turnOnWithCurrentLevel(device);
                waitingListPower.remove(i);
                System.out.println("Device " + device.getId() + " turned on from power waiting list.");
                i--;
            }
        }
    }

    private void checkDayWaitingList() { // turns on noisy devices waiting for day mode
        for (int i = 0; i < waitingListDay.size(); i++) {
            Device device = waitingListDay.get(i);
            waitingListDay.remove(i);
            if (getCurrentPowerConsumption() + getConsumptionIfDeviceIsOn(device) <= maxAllowedPower) {
                turnOnWithCurrentLevel(device);
                System.out.println("Device " + device.getId() + " turned on from day waiting list.");
            } else {
                addToWaitingList(waitingListPower, device);
                device.setStatus(2);
                System.out.println("Device " + device.getId() + " moved to power waiting list.");
            }
            i--;
        }
    }

    private void checkNoisyRunningDevices() { // asks what to do with noisy appliances when night mode starts
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = 0; j < rooms.get(i).getDevicesList().size(); j++) {
                Device device = rooms.get(i).getDevicesList().get(j);
                if (device instanceof Appliance && ((Appliance) device).isNoisy() && device.getStatus() == 1) {
                    System.out.println("Noisy appliance is running: " + device.getName());
                    System.out.println("1) Turn it off");
                    System.out.println("2) Put it in standby until day");
                    System.out.println("3) Keep it on");
                    int choice = input.nextInt();
                    if (choice == 1 && confirmCriticalDevice(device)) {
                        device.turnOff();
                    } else if (choice == 2) {
                        device.setStatus(2);
                        addToWaitingList(waitingListDay, device);
                    }
                }
            }
        }
        checkPowerWaitingList();
    }

    private void askToTurnOffLights() { // gives the user the day mode option mentioned in the project
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to turn off all lights in the house? Enter Y if yes:");
        char choice = input.next().charAt(0);
        choice = Character.toUpperCase(choice);
        if (choice == 'Y') {
            for (int i = 0; i < rooms.size(); i++) {
                for (int j = 0; j < rooms.get(i).getDevicesList().size(); j++) {
                    Device device = rooms.get(i).getDevicesList().get(j);
                    if (device instanceof Light && device.getStatus() != 0) {
                        device.turnOff();
                        removeFromWaitingLists(device);
                    }
                }
            }
            checkPowerWaitingList();
        }
    }

    private void checkPowerAfterModeChange() { // makes sure the new power mode is respected
        while (getCurrentPowerConsumption() > maxAllowedPower) {
            Device device = getLastRunningNonCriticalDevice();
            if (device == null) {
                System.out.println("Warning: current power is above the limit and only critical devices are running.");
                return;
            }
            device.setStatus(2);
            addToWaitingList(waitingListPower, device);
            System.out.println("Device " + device.getId() + " moved to power waiting list to respect the new power mode.");
        }
        checkPowerWaitingList();
    }

    private Device getLastRunningNonCriticalDevice() {
        for (int i = rooms.size() - 1; i >= 0; i--) {
            for (int j = rooms.get(i).getDevicesList().size() - 1; j >= 0; j--) {
                Device device = rooms.get(i).getDevicesList().get(j);
                if (device.getStatus() == 1 && device.isCritical() == false) {
                    return device;
                }
            }
        }
        return null;
    }
}


    

    

