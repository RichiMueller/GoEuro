package com.goeuro.csv;

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVWriteProc;
import au.com.bytecode.opencsv.CSVWriter;
import com.goeuro.PropertySingleton;
import com.goeuro.entity.City;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * Wrapper for the csv writer.
 */
public class GoEuroCsvWriter {

    private static final Properties PROPERTIES = PropertySingleton.getInstance().getProperties();
    private static final String     QUOTE      = PROPERTIES.getProperty("csv.quote").trim();
    private static final String     PATH       = PROPERTIES.getProperty("csv.path").trim();
    private static final String     EXTENSION  = PROPERTIES.getProperty("csv.extension").trim();
    private static final String     CHARSET    = PROPERTIES.getProperty("csv.charset").trim();
    private static final String     SEPARATOR  = PROPERTIES.getProperty("csv.separator").trim();

    // csv writer doesn't need to be closed according to documentation
    private static final CSV CSV_WRITER = CSV.separator(SEPARATOR.charAt(0))
                                             .quote(QUOTE.charAt(0))
                                             .charset(CHARSET)
                                             .create();

    /**
     * Write the provided city information to a file.
     *
     * @param fileName the file name
     * @param header   the csv header
     * @param cities   the city information to write to the file
     */
    public void write(String fileName, final String[] header, final List<City> cities) {

        long   start    = System.currentTimeMillis();
        String fullPath = PATH;

        // add trailing slash
        if (!fullPath.endsWith(File.separator)) {
            fullPath += File.separator;
        }

        // check if path exists
        File filePath = new File(fullPath);

        if (!filePath.exists()) {
            if (!filePath.mkdirs()) {
                // something is seriously wrong if we can't create the directory
                throw new RuntimeException("Could not create directory '" + filePath + "'");
            }
        }

        File output = new File(fullPath, fileName + "." + EXTENSION);

        // no requirements specified how to handle existing files, so at least warn
        if (output.exists()) {
            System.out.println("File " + output.getAbsolutePath() + " already exists and will be overwritten.");
        }

        // CSVWriter will be closed after end of processing
        CSV_WRITER.write(output, new CSVWriteProc() {
            public void process(CSVWriter out) {

                out.writeNext(header);

                for (City city : cities) {

                    String id        = String.valueOf(city.getId());
                    String name      = city.getName();
                    String type      = city.getType();
                    String latitude  = "";
                    String longitude = "";

                    // some basic data validation
                    if (city.getGeoPosition() != null) {
                        latitude  = String.valueOf(city.getGeoPosition().getLatitude());
                        longitude = String.valueOf(city.getGeoPosition().getLongitude());
                    }

                    if ((name != null) && name.contains(QUOTE)) {
                        name = name.replace(QUOTE, "");     // remove csv breaking characters
                    }
                    if ((type != null) && type.contains(QUOTE)) {
                        type = type.replace(QUOTE, "");     // remove csv breaking characters
                    }
                    out.writeNext(id, name, type, latitude, longitude);
                }
            }
        });

        System.out.println("Wrote " + cities.size() + " entries to file '" + output.getAbsolutePath() + "' in "
                        + (System.currentTimeMillis() - start) + " ms.");
    }
}
