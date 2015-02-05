package com.goeuro.api;

import com.goeuro.PropertySingleton;
import com.goeuro.entity.City;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Wrapper for the rest client.
 */
public class GoEuroRestClient {

    private static final String CHARSET   = PropertySingleton.getInstance().getProperties().getProperty("rest.charset").trim();
    private static final String ENDPOINT = PropertySingleton.getInstance().getProperties().getProperty("rest.endpoint").trim();

    // client is thread-safe
    private Client client = ClientBuilder.newClient().register(JacksonFeature.class);

    /**
     * Calls the rest client to retrieve the city information for the specified city name.
     *
     * @param  city the city name to retrieve the data for
     * @return      <code>list</code> of city entities
     */
    public List<City> getCityData(String city) {

        long   start = System.currentTimeMillis();
        String encodedCity;

        GenericType<List<City>> cityType = new GenericType<List<City>>() {
	    };

        try {
            encodedCity = URLEncoder.encode(city, CHARSET);

            if (encodedCity.contains("+")) {
                // GoEuro api does not handle url with encoded spaces correctly, we have to replace "+" with "%20"
                encodedCity = encodedCity.replace("+", "%20");
            }

        } catch (UnsupportedEncodingException use) {
            System.out.println("Encoding failed for '" + city + "'. (" + use.getMessage() + ").");

            // we failed to encode, we will try it anyway. worst case we get 0 results.
            encodedCity = city;
        }

        URI endpoint;

        // some basic endpoint validation
        try {
            endpoint = new URI(ENDPOINT);
        } catch (URISyntaxException use) {
            throw new RuntimeException("URI " + ENDPOINT + " not valid.");
        }

        // More validation could be done here (i.e. catching HTTP error codes), but the result is basically the same
        // without. An exception is thrown and we don't write a csv file. No requirements have been specified,
        // so for now we leave it as it is.
        List<City> cityData = client.target(endpoint)
                                    .path(encodedCity)
                                    .request()
                                    .get(cityType);

        System.out.println("Retrieved " + cityData.size() + " entries for input '" + city + "' in "
                        + (System.currentTimeMillis() - start) + " ms.");

        return cityData;
    }

    /** Closes the jersey rest client and frees resources. */
    public void close() {
        client.close();
    }
}
