package com.goeuro;

import com.goeuro.api.GoEuroRestClient;
import com.goeuro.csv.GoEuroCsvWriter;
import com.goeuro.entity.City;

import java.util.List;

/**
 *  Logic class, bringing all things together.
 */
public final class BusinessLogic {

    private GoEuroRestClient goEuroRestClient = new GoEuroRestClient();
    private GoEuroCsvWriter  goEuroCsvWriter  = new GoEuroCsvWriter();

    /**
     * Executes the main business logic.
     * Calls the rest client for the specified parameter and processes the result with the csv writer.
     *
     * @param cities the city names to retrieve the information and write to a file for
     */
    public void execute(String[] cities) {

        // could be parallelized for performance, if necessary
        for (String city : cities) {
            execute(city);
        }
    }

    /**
     * Executes the main business logic.
     * Calls the rest client for the specified parameter and processes the result with the csv writer.
     *
     * @param city the city name to retrieve the information and write to a file for
     */
    public void execute(String city) {

        city = city.trim();

        if (city.isEmpty()) {
            throw new IllegalArgumentException("Provided city name is empty.");
        }

        // retrieve the data from the rest api
        List<City> cityEntries = goEuroRestClient.getCityData(city);

        if (!cityEntries.isEmpty()) {

            // there is no requirement specified how to handle incomplete entries,
            // so we write these entries incomplete to the csv file.
            // entries could als be removed from the list here using an iterator
            for (City cityEntry : cityEntries) {
                if (!cityEntry.validate()) {
                    System.out.println("Incomplete entry received from api: '" +  cityEntry + "' for query: '" + city + "'.");
                }
            }

            // remove all special character from the file name, just to be safe
            String   fileName = city.replaceAll("[^a-zA-Z0-9.-]", "_");
            String[] header   = new String[] {"_id", "name", "type", "latitude", "longitude"};

            // write to file
            goEuroCsvWriter.write(fileName, header, cityEntries);
        }
    }

    /** Free all rest client and csv writer. */
    public void close() {
        goEuroRestClient.close();   // free resources
        // csv writer does need to be closed
    }
}

