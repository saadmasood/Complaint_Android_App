package pk.com.ke.complaint.model;

public class LocationCoordinates {
    public double latitude;
    public double longitude;
    public String user;

    public LocationCoordinates(String name, double lat, double lon) {
        this.user = name;
        this.latitude = lat;
        this.longitude = lon;
    }


}
