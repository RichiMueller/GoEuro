package com.goeuro.entity;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test case for city entity.
 */
public class CityTest {

    @Test
    public void testValidate() {

        City city = new City();

        assertFalse(city.validate());

        city = new City(376217, "Berlin", "location", 52.52437, 13.41053);

        assertTrue(city.validate());

        city = new City(376217, null, null, 52.52437, 13.41053);

        assertFalse(city.validate());
    }
}
