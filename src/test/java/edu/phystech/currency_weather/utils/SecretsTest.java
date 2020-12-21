package edu.phystech.currency_weather.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecretsTest {
    @Test
    void keyTest(){
        assertTrue(Secrets.WEATHER_API_KEY.startsWith("f8"));
    }

}