package org.example.courier;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.courier.Courier;

public class CourierGenerator {
    public Courier random(){
        return new Courier(RandomStringUtils.randomAlphabetic(10),
                           RandomStringUtils.randomAlphabetic(10),
                           RandomStringUtils.randomAlphabetic(10));
    }
}