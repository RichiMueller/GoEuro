package com.goeuro.csv;

import com.goeuro.PropertySingleton;
import com.goeuro.entity.City;
import org.junit.After;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the csv writer.
 */
public class GoEuroCsvWriterTest {

    private static final String EXTENSION = PropertySingleton.getInstance().getProperties().getProperty("csv.extension").trim();
    private static final String CHARSET   = PropertySingleton.getInstance().getProperties().getProperty("csv.charset").trim();
    private static final String QUOTE     = PropertySingleton.getInstance().getProperties().getProperty("csv.quote").trim();
    private static final String SEPARATOR = PropertySingleton.getInstance().getProperties().getProperty("csv.separator").trim();
    private static final String SEP       = File.separator;
    private static final File   CSV_OK    = new File("target" + SEP + "test-classes" + SEP + "testdata" + SEP + "BerlinTest.csv");
    private static final File   CSV_SC    = new File("target" + SEP + "test-classes" + SEP + "testdata" + SEP + "CharactersTest.csv");

    private GoEuroCsvWriter goEuroCsvWriter = new GoEuroCsvWriter();
    private File            outputBerlin    = new File("Berlin." + EXTENSION);

    @After
    public void tearDown() {

        // clean up created files

        if (outputBerlin.exists()) {
            outputBerlin.delete();
        }
    }

    /** Test general write behaviour. */
    @Test
    public void testWrite() throws Exception {

        String     fileName = "Berlin";
        String[]   header   = new String[]{"_id", "name", "type", "latitude", "longitude"};
        List<City> cities   = new ArrayList<>(6);

        cities.add(new City(376217, "Berlin",                 "location", 52.52437,   13.41053));
        cities.add(new City(448103, "Berlingo",               "location", 45.50298,   10.04366));
        cities.add(new City(425332, "Berlingerode",           "location", 51.45775,   10.2384));
        cities.add(new City(425326, "Bernau bei Berlin",      "location", 52.67982,   13.58708));
        cities.add(new City(314826, "Berlin Tegel",           "airport",  52.5548,    13.28903));
        cities.add(new City(314827, "Berlin Sch\u00F6nefeld", "airport",  52.3887261, 13.5180874));  // encode umlauts in UTF-8

        goEuroCsvWriter.write(fileName, header, cities);

        assertTrue(outputBerlin.exists());

        checkFile(outputBerlin, CSV_OK);
    }

    /** Test some special use cases, where the requirements have not been specified for. */
    @Test
    public void testWriteSpecialCharacters() throws Exception {

        String     fileName = "Berlin";
        String[]   header   = new String[]{"_id", "name", "type", "latitude", "longitude"};
        List<City> cities   = new ArrayList<>(3);

        cities.add(new City(376217,  null,                 null,                               52.52437, 13.41053));
        cities.add(new City(376217, "Ber" + QUOTE + "lin", "location" + SEPARATOR + "airport", 52.52437, 13.41053));

        goEuroCsvWriter.write(fileName, header, cities);

        assertTrue(outputBerlin.exists());

        checkFile(outputBerlin, CSV_SC);
    }

    /** Helper class to read lines from a file. */
    private static List<String> readLinesFromFile(File file) throws IOException {

        final List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), CHARSET))) {

            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }

    /** Helper class to compare files. */
    private static void checkFile(File output, File reference) throws IOException {

        List<String> expectedLines = readLinesFromFile(reference);
        List<String> actualLines   = readLinesFromFile(output);

        checkFile(actualLines, expectedLines);
    }

    /** Helper class to compare lines. */
    private static void checkFile(List<String> actualLines, List<String> expectedLines) {

        assertEquals("Mismatch in number of lines.", expectedLines.size(), actualLines.size());

        for (int i = 0; i < actualLines.size(); i++) {
            assertEquals("Content mismatch in line " + i +".", expectedLines.get(i), actualLines.get(i));
        }
    }
}
