/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author DaCodin
 */
public class searchResult {
    private final SimpleStringProperty id;
    private final SimpleStringProperty department;
    private final SimpleStringProperty machine;
    private final SimpleStringProperty equipment;
    private final SimpleStringProperty title;
    private final SimpleStringProperty username;
    private final SimpleStringProperty in_date;
    private final BooleanProperty approval;
    
    

    public searchResult(String id, String department, String machine, String equipment,
            String title, String username, String in_date,boolean approval) {
        this.id = new SimpleStringProperty(id);
        this.department = new SimpleStringProperty(department);
        this.machine = new SimpleStringProperty(machine);
        this.equipment = new SimpleStringProperty(equipment);
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.in_date = new SimpleStringProperty(in_date);
        this.approval = new SimpleBooleanProperty(approval);
    }

    public String getId() {
        return id.get();
    }

    public String getDepartment() {
        return department.get();
    }

    public String getMachine() {
        return machine.get();
    }

    public String getEquipment() {
        return equipment.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getIn_date() {
        return in_date.get();
    }

    public BooleanProperty getApproval() {
        return approval;
    }

   //
      public void setID(String ID){
        id.set(ID);
    }
    public void setDEPARTMENT(String DEPARTMENT){
        department.set(DEPARTMENT);
    }
    public void setMACHINE(String MACHINE){
        machine.set(MACHINE);
    }
    public void setEQUIPMENT(String EQUIPMENT){
        id.set(EQUIPMENT);
    }
    public void setTITLE(String TITLE){
        title.set(TITLE);
    }
    public void setUSERNAME(String USERNAME){
        username.set(USERNAME);
    }
    public void setIN_DATE(String IN_DATE){
        in_date.set(IN_DATE);
    }
   
    
    
}
