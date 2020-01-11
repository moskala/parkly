package pw.react.backend.parklybackend.model;

import java.io.Serializable;

public class Filter implements Serializable {
    private String city;
    private String street;
    private int workingHoursFrom;
    private int workingHoursTo;
    public String getCity(){ return this.city;}
    public void setCity(String city){this.city = city;}
    public String getStreet(){ return this.street;}
    public void setStreet(String street){this.street = street;}
    public int getWorkingHoursFrom(){return this.workingHoursFrom;}
    public void setWorkingHoursFrom(int workingHoursFrom){this.workingHoursFrom=workingHoursFrom;}
    public int getWorkingHoursTo(){return this.workingHoursTo;}
    public void setWorkingHoursTo(int workingHoursTo){this.workingHoursTo=workingHoursTo;}
}
