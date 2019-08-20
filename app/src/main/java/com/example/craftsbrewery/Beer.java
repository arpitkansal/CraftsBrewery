package com.example.craftsbrewery;

public class Beer {
    public String abv;
    public String ibu;

    public int id;
    public String name;
    public String style;
    public double ounces;

    public Beer(String abv, String ibu, int id, String name, String style,double ounces)
    {
        this.abv= abv;
        this.ibu = ibu;
        this.id= id;
        this.name = name;
        this.style = style;
        this.ounces = ounces;
    }

    public String getAbv(){
        return abv;
    }
    public String getIbu(){
        return ibu;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getStyle(){
        return style;
    }
    public double getOunces(){
        return ounces;
    }

}
