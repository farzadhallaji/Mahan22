package com.comtech.ali.mahan2.model;

/**
 * Created by Techno Service on 8/5/2017.
 */
public class HalatiKeYekNist {

    String ID;
    String ContentType;
    String Lead;
    String Title;
    String PreTitle;
    String PTime;
    String Sort;
    String PicURL;

    public HalatiKeYekNist(String ID,
                           String ContentType,
                           String Lead,
                           String Title,
                           String PreTitle,
                           String PTime,
                           String Sort,
                           String PicURL){

        this.ContentType=ContentType;
        this.ID=ID;
        this.Lead=Lead;
        this.Title=Title;
        this.PicURL=PicURL;
        this.PreTitle=PreTitle;
        this.Sort=Sort;
        this.PTime=PTime;
    }


    public String getTitle()          {return Title;}

    public void setTitle(String Title)         { this.Title=Title;}


    public String getID()          {return ID;}

    public void setID(String Picurl)         { this.ID=Picurl; }


    public String getLead()          {return Lead;}

    public void setLead(String Picurl)         { this.Lead=Picurl; }


    public String getPreTitle()          {return PreTitle;}

    public void setPreTitle(String Picurl)         { this.PreTitle=Picurl; }


    public String getPTime()          {return PTime;}

    public void setPTime(String Picurl)         { this.PTime=Picurl; }


    public String getSort()          {return Sort;}

    public void setSort(String Picurl)         { this.Sort=Picurl; }


    public String getContentType()          {return ContentType;}

    public void setContentType(String Picurl)         { this.ContentType=Picurl; }


    public String getPicURL()          {return PicURL;}

    public void setPicURL(String Picurl)         { this.PicURL=Picurl; }
}