package com.goeuro.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Json entity representing a city.
 */
@JsonIgnoreProperties(ignoreUnknown = true)     // we only need _id, name, type, latitude, longitude anyway
public class City {

    /** Default constructor. */
    public City() {
    }

    /** Minimum constructor, primarily use for testing. */
    public City(int id, String name, String type, double latitude, double longitude) {
        this.id                    = id;
        this.name                  = name;
        this.type                  = type;
        this.geoPosition           = new GeoPosition();
        this.geoPosition.latitude  = latitude;
        this.geoPosition.longitude = longitude;
    }

    @JsonProperty("_id")
    private int     id;

    @JsonProperty("key")
    private String  key;

    @JsonProperty("name")
    private String  name;

    @JsonProperty("fullName")
    private String  fullName;

    @JsonProperty("iata_airport_code")
    private String  iataAirportCode;

    @JsonProperty("type")
    private String  type;

    @JsonProperty("country")
    private String  country;

    @JsonProperty("locationId")
    private int     locationId;

    @JsonProperty("inEurope")
    private boolean inEurope;

    @JsonProperty("countryCode")
    private String  countryCode;

    @JsonProperty("coreCountry")
    private boolean coreCountry;

    @JsonProperty("distance")
    private int     distance;

    @JsonProperty("geo_position")
    private GeoPosition geoPosition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIataAirportCode() {
        return iataAirportCode;
    }

    public void setIataAirportCode(String iataAirportCode) {
        this.iataAirportCode = iataAirportCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public boolean isInEurope() {
        return inEurope;
    }

    public void setInEurope(boolean inEurope) {
        this.inEurope = inEurope;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isCoreCountry() {
        return coreCountry;
    }

    public void setCoreCountry(boolean coreCountry) {
        this.coreCountry = coreCountry;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }

    public class GeoPosition {

        @JsonProperty("latitude")
        private double latitude;

        @JsonProperty("longitude")
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return "GeoPosition{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", iataAirportCode='" + iataAirportCode + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", locationId=" + locationId +
                ", inEurope=" + inEurope +
                ", countryCode='" + countryCode + '\'' +
                ", coreCountry=" + coreCountry +
                ", distance=" + distance +
                ", geoPosition=" + geoPosition +
                '}';
    }

    /** Basic validation method. */
    public boolean validate() {
        return ((name != null) && (type != null) && (geoPosition != null));
    }
}
