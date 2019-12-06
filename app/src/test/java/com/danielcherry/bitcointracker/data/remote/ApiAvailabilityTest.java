package com.danielcherry.bitcointracker.data.remote;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class ApiAvailabilityTest {

    @Test
    public void testAvailability() throws Exception {

        URLConnection connection = new URL("https://api.blockchain.info/charts/market-price?timespan=30days&format=json").openConnection();
        InputStream response = connection.getInputStream();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, Charset.defaultCharset()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                sb.append(line);
            }
        }

        assertTrue(sb.length() > 0);
    }

}