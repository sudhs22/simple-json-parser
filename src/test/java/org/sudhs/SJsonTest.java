package org.sudhs;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SJsonTest {
    @Test
    public void testToObject(){

        String json = "{\"id\": \"123\", \"name\": \"sudhs\",\"address\": {\"city\": \"cochin\",\"country\": \"india\",\"pincode\": \"400046\"}}";
        SJson sJson = new SJson();
        Person person = sJson.toObject(json, Person.class);
        System.out.println(" Person Object : " + person.toString());

        Assertions.assertNotNull(person);
        Assertions.assertEquals("123",person.getId());
        Assertions.assertEquals("sudhs",person.getName());
        Assertions.assertEquals("cochin",person.getAddress().getCity());
        Assertions.assertEquals("india",person.getAddress().getCountry());
        Assertions.assertEquals("400046",person.getAddress().getPincode());

    }
}
