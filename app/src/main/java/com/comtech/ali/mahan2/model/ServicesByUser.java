package com.comtech.ali.mahan2.model;

/**
 * Created by Techno Service on 7/24/2017.
 */
public class ServicesByUser {
    String Service;
    String ServiceID;
    String ServiceType;

    public ServicesByUser(String Service,String ServiceID,String ServiceType){
        this.Service=Service;
        this.ServiceID=ServiceID;
        this.ServiceType=ServiceType;
    }

    public String getService()          {return Service;}

    public void setService(String Service)         { this.Service=Service;}

    public String getServiceID()           {return ServiceID;}

    public void setServiceID(String Serviseid)           { this.ServiceID=Serviseid; }

    public String getServiceType()             {return ServiceType;}

    public void setServiceType(String servicetype)      { this.ServiceType=servicetype;}




}
