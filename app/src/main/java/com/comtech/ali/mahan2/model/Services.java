package com.comtech.ali.mahan2.model;

/**
 * Created by Techno Service on 7/24/2017.
 */
public class Services {


    String Service;
    String ServiceID;
    String ServiceType;
    String IconURL;

    public Services(String Service,String ServiceID,String ServiceType,String IconURL){
        this.Service=Service;
        this.ServiceID=ServiceID;
        this.ServiceType=ServiceType;
        this.IconURL=IconURL;
    }

    public String getService()          {return Service;}

    public void setService(String Service)         { this.Service=Service;}

    public String getServiceID()           {return ServiceID;}

    public void setServiceID(String Serviseid)           { this.ServiceID=Serviseid; }

    public String getServiceType()             {return ServiceType;}

    public void setServiceType(String servicetype)      { this.ServiceType=servicetype;}

    public String getIconURL()      {return IconURL;}

    public void setIconURL(String IconURL)      {this.IconURL=IconURL;}

}
