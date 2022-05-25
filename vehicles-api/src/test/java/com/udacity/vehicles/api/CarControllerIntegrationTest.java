package com.udacity.vehicles.api;


import com.udacity.vehicles.VehiclesApiApplication;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VehiclesApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JacksonTester<Car> json;


    private String baseUrl;

    private static String testZip = "";

    @Before
    public void before() {
        baseUrl = "http://localhost:" + port + "/cars";
    }

    @Test
    public void a_createCarTest() {
        Car car = getCar();
        HttpEntity<Car> request = new HttpEntity<>(car);
        ResponseEntity<Car> response = restTemplate.postForEntity(baseUrl, request, Car.class);
        System.out.println("response: " + Objects.requireNonNull(response.getBody()).getCondition());
        assertThat(null, is(not(Objects.requireNonNull(response.getBody()).getCondition())));
    }

    @Test
    public void b_findCarTest() throws Exception {
        ResponseEntity<Car> response = restTemplate.getForEntity(baseUrl + "/1", Car.class);
        assertThat(1L, is(response.getBody().getId()));
        assertThat(null, is(not(response.getBody().getLocation().getZip())));

        testZip = response.getBody().getLocation().getZip();
        System.out.println("Zip: " + testZip);

    }

    @Test
    public void c_checkCarLocationTest() {
        ResponseEntity<Car> response = restTemplate.getForEntity(baseUrl + "/1", Car.class);
        assertThat(response.getBody().getLocation().getZip(), is(testZip));
    }

    @Test
    public void d_updateCarLocationTest() {
        Car car = getCar();
        car.setLocation(new Location(1D, 1D));
        HttpEntity<Car> request = new HttpEntity<>(car);
        restTemplate.put(baseUrl + "/1", request, Car.class);


        ResponseEntity<Car> response = restTemplate.getForEntity(baseUrl + "/1", Car.class);
        assertThat(testZip, is(not(response.getBody().getLocation().getZip())));
    }

    @Test
    public void e_updateCarConditionTest() {
        Car car = getCar();
        car.setCondition(Condition.NEW);
        HttpEntity<Car> request = new HttpEntity<>(car);
        restTemplate.put(baseUrl + "/1", request, Car.class);


        ResponseEntity<Car> response = restTemplate.getForEntity(baseUrl + "/1", Car.class);
        assertThat(Condition.NEW, is(response.getBody().getCondition()));
    }

    private Car getCar() {
        Car car = new Car();

        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}
