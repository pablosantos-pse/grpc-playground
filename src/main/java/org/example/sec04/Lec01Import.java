package org.example.sec04;

import com.example.models.common.Address;
import com.example.models.common.BodyStyle;
import com.example.models.common.Car;
import com.example.models.sec04.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01Import {

    private static final Logger log = LoggerFactory.getLogger(Lec01Import.class);

    public static void main(String[] args) {

        var address = Address.newBuilder().setCity("atlanta").build();
        var car = Car.newBuilder().setBodyStyle(BodyStyle.SEDAN).build();
        var person = Person.newBuilder()
                .setName("sam")
                .setAge(12)
                .setAddress(address)
                .setCar(car)
                .build();

        log.info("{}", person);
    }
}
