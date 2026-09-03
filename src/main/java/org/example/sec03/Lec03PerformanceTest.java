package org.example.sec03;

import com.example.models.sec03.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Lec03PerformanceTest {

    private static final Logger log = LoggerFactory.getLogger(Lec03PerformanceTest.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        var protoPerson =  Person.newBuilder()
                .setLastName("sam")
                .setAge(12)
                .setEmail("sam@gmail.com")
                .setEmployed(true)
                .setSalary(1000.2345)
                .setBankAccountNumber(123456789012L)
                .setBalance(-10000)
                .build();

        var jsonPerson = new JsonPerson("sam", 12, "sam@gmail.com", true, 1000.2345, 123456789012L, -10000);

        // Repeat the process several times to prevent a cold start
        for (int i = 0; i < 5; i++) {
            runtTest("json", () -> json(jsonPerson));
            runtTest("proto", () -> proto(protoPerson));
        }
    }

    public static void proto(Person person) {
        try {
            var bytes = person.toByteArray();
            Person.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    public static void json(JsonPerson person) {
        try {
            var bytes = mapper.writeValueAsBytes(person);
            mapper.readValue(bytes, JsonPerson.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void runtTest(String testName, Runnable runnable) {
        var start = System.currentTimeMillis();

        for (int i = 0; i < 1000000; i++) {
            runnable.run();
        }

        var end = System.currentTimeMillis();

        log.info("{} runs in {} ms", testName, end - start);
    }
}
