package projectoop1;
import java.util.Arrays;
import java.util.Scanner;

public class ProjectOOP1 {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        ManagementSystem syst1 = new ManagementSystem("Admin@123", "User@123");
        g(syst1);
    }
    
    
     public static void g(ManagementSystem syst1) {
        int choice = 0;
        do {
            choice = 0;
            System.out.println("Welcome to your own special smart home system <3 ");
            System.out.println("if you are an admin enter 1.\n"
                    + "if you are a user enter 2. \n"
                    + "if you want to exit the system enter 0.");
            System.out.println("Your choice:");
            int choice1 = input.nextInt();
            switch (choice1) {
                case 0:
                    System.out.println("Goodbye !");
                    System.exit(0);
                    break;
                case 1:
                    enterAdminPass(syst1);
                    break;
                case 2:
                    enterUserPass(syst1);
                    break;
                default:
                    System.out.println("Wrong choice Re-enter any number to retry or enter 0 to exit the system : ");
                    choice = 1;
                    break;

            }
        } while (choice != 0);
    }

    //----------------------------------------------------------------------------------------//
//Admin related code:
    public static void enterAdminPass(ManagementSystem syst1) { //method that check admin password validity
        System.out.println("enter Admin password : ");
        String passInp = input.next();
        passInp = passInp.trim();
        if (!passInp.equals(syst1.getAdminPassword())) {
            do {
                System.out.println("Invalid password , enter your password again please : ");
                passInp = input.next();
            } while (!passInp.equals(syst1.getAdminPassword()));
        }
        if (passInp.equals(syst1.getAdminPassword())) {
            showAdminMenu(syst1);
        }
    }

    public static void showAdminMenu(ManagementSystem syst1) { //display menu specific to admin functions
        System.out.println(" 1) change admin and user passwords \n"
                + " 2) change power mode to one of the three possible modes \n"
                + " 3) Set day/time mode \n"
                + " 4) Add/Delete/Search a room \n"
                + " 5) Add/Delete/Search a device \n"
                + " 6) Exit admin mode \n"
                + " 7) Exit system ");
        int adminMenu = input.nextInt();
        switch (adminMenu) {
            case 1: //change admin and user passwords
                changeAdminAndUserPasswords(syst1);
                showAdminMenu(syst1);
                break;

            case 2: //change power mode to one of the three possible modes
                changePowerMode(syst1);
                showAdminMenu(syst1);

                break;
            case 3: // Set day/time mode
                ChangeDayTime(syst1);
                showAdminMenu(syst1);
                break;

            case 4: // Add/Delete/Search a room
                System.out.println("1)AddRoom\n"
                        + "2)Delete Room\n"
                        + "3)Search for a room");
                int choice2;
                choice2 = input.nextInt();
                switch (choice2) {
                    case 1: // add room
                        AddRoom(syst1);
                        break;

                    case 2: // remove room
                        RemoveRoom(syst1);
                        break;

                    case 3: //search room
                        SearchRoom(syst1);

                        break;
                }

                showAdminMenu(syst1);
                break;

            case 5: //Add/Delete/Search a device 
                System.out.println("1.Add device\n"
                        + "2.Delete device\n"
                        + "3.Search for a Device\n");
                int choice3;
                choice3 = input.nextInt();

                switch (choice3) {
                    case 1: // add device
                        AddDevice(syst1);
                        break;

                    case 2: // remove device
                        RemoveDevice(syst1);
                        break;

                    case 3: // search device
                        SearchDevice(syst1);

                        break;
                }
                showAdminMenu(syst1);
                break;

            case 6: //Exit admin mode
                g(syst1);
                break;
            case 7: //Exit system
                System.exit(0);
                break;
            default:
                System.out.println("Wrong choice please try again");
                showAdminMenu(syst1);
                break;
        }
    }

    public static void changeAdminAndUserPasswords(ManagementSystem syst1) {  
        boolean changed = false;
        do {
            System.out.println("enter new password for admin that contains atleast 8 characters: atleast 1 UpperCaseLetter,/ atleast 1 LowerCaseLetter/ atleast 1 Number : ");
            String newPass1 = input.next();
            if (syst1.IsValid(newPass1)) { //check if pass entered meet conditions
                System.out.println("Re-confirm your password: ");// re-ask for the password to make sure the pass entered above is correct
                String newPass2 = input.next();
                if (syst1.IsValid(newPass2) && newPass1.equals(newPass2)) { 
                    syst1.setAdminPassword(newPass2);
                    changed = true;
                } else {
                    System.out.println("Invalid password please try again");
                }
            }

        } while (changed != true);

        changed = false;
        do {
            System.out.println("enter new password for user that contains atleast 8 characters: atleast 1 UpperCaseLetter,/ atleast 1 LowerCaseLetter/ atleast 1 Number : ");
            String newPass1 = input.next();
            if (syst1.IsValid(newPass1)) { //check if pass entered meet conditions
                System.out.println("Re-confirm your password: "); // re-ask for the password to make sure the pass entered above is correct
                String newPass2 = input.next();
                if (syst1.IsValid(newPass2) && newPass1.equals(newPass2)) {
                    syst1.setUserPassword(newPass2);
                    changed = true;
                } else {
                    System.out.println("Invalid password please try again");
                }
            }

        } while (changed != true);

    }

    public static void changePowerMode(ManagementSystem syst1) { // Changes power mode from default medium to High or Low based on user input
        boolean changed = false;
        do { // keep asking until user enter valid choice 
            System.out.println("Power modes: Low = 1000W / Medium = 4000W / High = 10000W \n"
                    + "Enter low for 1000 W \n"
                    + "Enter medium or normal for 4000 W \n"
                    + "Enter high for 10000 W ");
            String change = input.next();
            if (change.toUpperCase().equals("LOW") || change.toUpperCase().equals("HIGH")
                    || change.toUpperCase().equals("MEDIUM") || change.toUpperCase().equals("NORMAL")) {
                syst1.setPowerMode(change);
                changed = true;
            } else {
                System.out.println("Wrong choice entered,try again please !");
            }

        } while (changed != true);

    }

    public static void ChangeDayTime(ManagementSystem syst1) { //changes day time to Day or night
        boolean changed = false;

        do { // keep asking until entering valid string choice
            System.out.println("Enter please DayTime.\n"
                    + "-Day or Night\n"
                    + "your choice:");
            String choice = input.next();
            if (choice.toUpperCase().equals("DAY")) {
                syst1.setDayTime();
                changed = true;

            } else if (choice.toUpperCase().equals("NIGHT")) {
                syst1.setNightTime();
                changed = true;
            } else {
                System.out.println("Wrong choice please try again");
            }

        } while (changed != true);

    }

    public static void AddRoom(ManagementSystem syst1) { // ask for room code and description to add it to the house
        boolean exists = false;

        System.out.println(" Enter room code:");
        String code1 = input.next();
        input.nextLine();
        System.out.println("Enter room description:");
        String description1 = input.nextLine();
        Room room1 = new Room(code1, description1);
        for (int i = 0; i < syst1.getRooms().size(); i++) { 
            if (syst1.getRooms().get(i).getCode().equals(code1)) { // check if the room already exists in the house
            System.out.println("Room " + code1 + " is already available in your house ");
                exists = true;
                break;
            }
        }
        if (exists == false) { // if room does not exist yet add it to the array list of rooms
            syst1.addRoom(room1);
            System.out.println("Room: " + code1 + " has been added successfully");
        }

    }

    public static void RemoveRoom(ManagementSystem syst1) { //ask for room code to be removed from the array list 

        System.out.println(" Enter room code to be removed:");
        String code = input.next();
        if (syst1.removeRoom(code) == false) { //check if this room is available or not in the array list before removing it
            System.out.println("Room: " + code + " is unavailable in your house");
        } else { 
            System.out.println("Room: " + code + " has been removed successfully");
        }
    }

    public static void SearchRoom(ManagementSystem syst1) { // asks for room code to return a room 
        System.out.println(" Enter room code");
        String code = input.next();
        if (syst1.searchRoomByCode(code) != null) { // checks if this room exist if yes return the room
            System.out.println(syst1.searchRoomByCode(code));

        } else {
            System.out.println("Room doesn't exist in your house");
        }

    }

    public static void AddDevice(ManagementSystem syst1) { // asks for details from the user about the device intended to add
        boolean critical, noisy;
        int levels;
        int powerLevels[];
        boolean adjustable;
        System.out.println("Is the device you want to add an Appliance or a Light. \n"
                + "A for appliance. \n"
                + "L for Light.");
        char c = input.next().charAt(0);
        c = Character.toUpperCase(c);
        if (c == 'A') { // code related to Appliance if user choosed to add an appliance
            System.out.println(" Enter room code:");
            String code = input.next();
            System.out.println("Enter id of the appliance :");
            int id = input.nextInt();

            if (id < 100 || id > 999) {
                System.out.println("Device id must be between 100 and 999");
                return;
            }

            if (syst1.searchRoomByCode(code) != null && syst1.searchDeviceById(id) == null) { //check if the room exist first and the device is unavailable yet

                System.out.println("Enter name of the appliance:"); 
                String name = input.next();
                System.out.println("Enter power Consumption of this appliance");
                double powerConsumption = input.nextDouble();
                
                System.out.println("Is this appliance critical ?/Enter Y if yes /Enter anything char if no");
                char c1 = input.next().charAt(0);
                c1 = Character.toUpperCase(c1);
                if (c1 == 'Y') {
                    critical = true;
                } else {
                    critical = false;
                }
                System.out.println("How many power levels is there for this appliance");
                levels = input.nextInt();
                if (levels <= 0) {
                    System.out.println("Appliance must have at least one power level");
                    return;
                }
                powerLevels = new int[levels];
                System.out.println("Enter the power levels as percentages:");
                for (int i = 0; i < levels; i++) {
                    powerLevels[i] = input.nextInt();
                }
                Arrays.sort(powerLevels);
                System.out.println("Is this device Noisy");
                c1 = input.next().charAt(0);
                c1 = Character.toUpperCase(c1);
                if (c1 == 'Y') {
                    noisy = true;
                } else {
                    noisy = false;
                }
                Appliance a = new Appliance(id, name, powerConsumption, critical, powerLevels, noisy);
                syst1.addDevice(code, a); // add appliance to array list 
                System.out.println("Device:" + id + " " + name + " has been added successfully");
                return;
            }
            if (syst1.searchRoomByCode(code) == null) { //if room doesnt exist print statement
                System.out.println("Room " + code + " is unavailable");

            }
            if (syst1.searchDeviceById(id) != null) { //if device already exist print statement
                System.out.println("Device " + id + " is already available");
            }

        } else if (c == 'L') { // code related to Light if user choosed to add a light
           
            System.out.println(" Enter room code:");
            String code = input.next();
            System.out.println("Enter id of the light :");
            int id = input.nextInt();

            if (id < 100 || id > 999) {
                System.out.println("Device id must be between 100 and 999");
                return;
            }

            if (syst1.searchRoomByCode(code) != null && syst1.searchDeviceById(id) == null) { //check if the room exist first and the device is not avaialable yet

                System.out.println("Enter name of the Light:");
                String name = input.next();
                System.out.println("Enter power Consumption of this Light");
                double powerConsumption = input.nextDouble();
                System.out.println("Is this light critical ?/Enter Y if yes /Enter any char if no");
                char c1 = input.next().charAt(0);
                c1 = Character.toUpperCase(c1);
                if (c1 == 'Y') {
                    critical = true;
                } else {
                    critical = false;
                }
                System.out.println("is this light power adjustable ? Enter Y if yes /Enter any char if no");
                c1 = input.next().charAt(0);
                c1 = Character.toUpperCase(c1);
                if (c1 == 'Y') {
                    adjustable = true;
                } else {
                    adjustable = false;
                }
                Light l = new Light(id , name , powerConsumption , critical , adjustable);
                syst1.addDevice(code, l); // add light to the array list
                System.out.println("Light:" + id + " " + name + " has been added successfully");
            }else
            if (syst1.searchRoomByCode(code) == null) { //if room doesnt exist print statement
                System.out.println("Room " + code + " is unavailable");

            }
            else if (syst1.searchDeviceById(id) != null) { //if device already exist print statement
                System.out.println("Light " + id + " is already available");
            }
        } else {
            System.out.println("Wrong device type");
        }
    }

    public static void RemoveDevice(ManagementSystem syst1) { //asks for device id to be removed from the array list
        System.out.println("Enter Room Code and device id to be removed:");
        String code = input.next();
        int id = input.nextInt();
        if (syst1.removeDevice(code, id) == false) { //checks if the device exist first before removing it
            System.out.println("Device: " + id + " is unavailable in this room");
        } else {
            System.out.println("Device " + id + " has been removed successfully");

        }

    }

    public static void SearchDevice(ManagementSystem syst1) { //asks for device id to be returned
        System.out.println("Enter device id :");
        int id = input.nextInt();
        if (syst1.searchDeviceById(id) == null) { //checks if the device exist first before searching it
            System.out.println("Device " + id + "Unavailable");
        } else {
            System.out.println(syst1.searchDeviceById(id));
        }

    }
    //----------------------------------------------------------------------------------------// 
    //User related Code

    public static void enterUserPass(ManagementSystem syst1) { //checks if user password entered is valid
        System.out.println("enter User password : ");
        String passInp = input.next();
        passInp = passInp.trim();
        if (!passInp.equals(syst1.getUserPassword())) {
            do {
                System.out.println("Invalid password , enter your password again please : ");
                passInp = input.next();
            } while (!passInp.equals(syst1.getUserPassword()));
        }
        showUserMenu(syst1);

    }

    public static void showUserMenu(ManagementSystem syst1) { // display menu for user functions
        System.out.println("1) Check all rooms info \n"
                + "2) Check all devices info \n"
                + "3) Check all running devices \n"
                + "4) Check all standby devices in the day waiting list \n"
                + "5) Check all standby devices in the power waiting list \n"
                + "6) Search for a given room \n"
                + "7) Search for a given device \n"
                + "8) Turn on/ Turn off a device \n"
                + "9) Turn off all devices from one specific room \n"
                + "10) Turn off all devices in the house \n"
                + "11) Check current power consumption  \n"
                + "12) Set day/night mode \n"
                + "13) Exit control mode \n"
                + "14) Exit system ");

        System.out.println("Your Choice:");
        int userMenu = input.nextInt();
        switch (userMenu) {
            case 1: //Check all rooms info
                CheckAllRooms(syst1);
                showUserMenu(syst1);
                break;

            case 2: //Check all devices info
                checkAllDevicesInfos(syst1);
                showUserMenu(syst1);
                break;

            case 3: //Check all running devices
                checkAllRunningDevices(syst1);
                showUserMenu(syst1);
                break;

            case 4: //Check all standby devices in the day waiting list
                checkDevicesWaitingListDay(syst1);
                showUserMenu(syst1);
                break;

            case 5: //Check all standby devices in the power waiting list
                checkDevicesWaitingListPower(syst1);
                showUserMenu(syst1);
                break;
            case 6: // Search for a given room 
                SearchRoom(syst1);
                showUserMenu(syst1);
                break;

            case 7: //Search for a given device
                SearchDevice(syst1);
                showUserMenu(syst1);
                break;

            case 8: //Turn on/ Turn off a device
                TurnOnOFF(syst1);
                showUserMenu(syst1);
                break;
            case 9: //Turn off all devices from one specific room 
                shutDownOneRoom(syst1);
                showUserMenu(syst1);
                break;

            case 10: // Turn off all devices in the hous
                shutDownAllDevices(syst1);
                showUserMenu(syst1);
                break;

            case 11: //Check current power consumption 
                checkCurrenntCons(syst1);
                showUserMenu(syst1);
                break;

            case 12: //Set day/night mode
                setDayNightMode(syst1);
                showUserMenu(syst1);
                break;
            case 13: //Exit control mode
                g(syst1);
                break;
            case 14: //Exit system
                System.exit(0);
                break;
            default:
                System.out.println("Wrong choice please try again");
                showUserMenu(syst1);
                break;
        }

    }

    public static void CheckAllRooms(ManagementSystem syst1) {
        syst1.displaySummaryAllRooms();

    }

    public static void checkAllDevicesInfos(ManagementSystem syst1) {
        syst1.checkAllDevicesInfos();
    }

    public static void checkAllRunningDevices(ManagementSystem syst1) {
        syst1.checkAllRunningDevices();
    }

    public static void shutDownAllDevices(ManagementSystem syst1) {
        syst1.shutDownAllDevices();
    }

    public static void checkCurrenntCons(ManagementSystem syst1) {
        double cons = syst1.getCurrentPowerConsumption();
        System.out.println(cons + "W");
    }
    

    public static void setDayNightMode(ManagementSystem syst1) {
        syst1.setDayNightMode();
    }

    public static void shutDownOneRoom(ManagementSystem syst1) {
        System.out.println("enter room code to turn off it's devices::");
        String code = input.next();
        syst1.shutDownOneRoom(code);
    }

    public static void checkDevicesWaitingListDay(ManagementSystem syst1) {
        syst1.checkStandByDevicesWaitingListDay();
    }

    public static void checkDevicesWaitingListPower(ManagementSystem syst1) {
        syst1.checkStandByDevicesWaitingPower();
    }

    public static void TurnOnOFF(ManagementSystem syst1) { // turn on or off a device
        int choice;
        int id;
        String code;
        System.out.println("Do you want to turn ON or turn OFF a Device? enter 1 for ON ,enter 2 for OFF ");
        choice = input.nextInt();
        if (choice == 1) {
            System.out.println("Enter room code and device id to be turned ON");
            System.out.println("Room Code:");
            code = input.next();
            System.out.println("Device id:");
            id = input.nextInt();
            syst1.turnOnDevice(code, id);
        } else if (choice == 2) {
            System.out.println("Enter room code and device id to be turned OFF");
            System.out.println("Room Code:");
            code = input.next();
            System.out.println("Device id:");
            id = input.nextInt();
            syst1.turnOffDevice(code, id);

        } else {
            System.out.println("Wrong choice");
        }

    }

}
        
        
    


    
