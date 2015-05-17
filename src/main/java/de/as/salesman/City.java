
package de.as.salesman;


public class City {
    private final String name;
    private float lat;
    private float lng;

    public City(String name, float lat, float lng) {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
    }

    public String getName() {
        return name;
    }
    
    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

}
