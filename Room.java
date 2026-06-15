
package projectoop1;

import java.util.ArrayList;

public class Room {
    private String code; 
    private String description;
    private ArrayList <Device> devicesList;

    public Room(String code, String description) {
        setRoom(code,description);
        this.devicesList=new ArrayList <>();
       
        
    }
    
    public void setRoom(String code, String description){
        setCode(code);
        setDescription(description);
        
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

     public int getNbLights(){
         int count=0;
         for(int i=0;i<devicesList.size();i++){
             if(devicesList.get(i)instanceof Light){
                 count++;   
             }
             
         }
         
                 return count;
     }
    
     public int getNbAppliances(){
         int count=0;
         for(int i=0;i<devicesList.size();i++){
             if(devicesList.get(i)instanceof Appliance){
                 count++;   
             }
             
         }
         
                 return count;
     }
    
    public double getCurrentComsuption (){
        double consumption=0;
         for(int i=0;i<devicesList.size();i++){
             consumption+=devicesList.get(i).getCurrentConsumption();
         } 
         return consumption;
    }
  
    public void addDevice(Device d){
        devicesList.add(d);
       
   }
    public boolean removeDevice(Device d){
        if (d == null) {
            return false;
        }
        for(int i=0;i<devicesList.size();i++){
            if(d.equals(devicesList.get(i))){
                devicesList.remove(i);
                return true;
            }
        }
        
       return false;
       
   }
    
    public Device searchDeviceById(int id){
        for(int i=0;i<devicesList.size();i++){
        if(devicesList.get(i).getId()==id){
            return devicesList.get(i);
        }
    }
    return null;
    }
    
    public int getNbOfDevices(){
        return devicesList.size();
    } 

    public ArrayList<Device> getDevicesList() {
        return devicesList;
    }

    public void setDevicesList(ArrayList<Device> devicesList) {
        this.devicesList = devicesList;
    }
    
    

    @Override
    public String toString() { // return a details of each room devices
        StringBuilder s = new StringBuilder();
         for(int i=0;i<devicesList.size();i++){
             s.append(devicesList.get(i)).append("\n");
         }
          return "Room: "+ description +"\n"+
                 "code=" + code +"\n"+
                 "DevicesList: "+"\n" + s.toString();
        
    }
    
    public String toBriefString(){ // return a brief summary of each room devices
        return "Room: "+ description +"\n"+
                "code:" + code +"\n"+
                "Total Number of Lights:"+getNbLights()+"\n"+
                "Total Number of Appliances:"+getNbAppliances()+"\n"+
                "Total Number of Devices:"+getNbOfDevices()+"\n";
                
                
               
    }

    public String toBreifString(){ // kept because the project document used this spelling
        return toBriefString();
    }
    
    
    
    

    
}
