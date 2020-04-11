package org.sudhs;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SJsonTest {
    @Test
    public void testToObject(){
        String json = "{\"id\":\"1\",\"name\":\"sudhs\",\"address\":\"Lakshadweep\"}";
        SJson sJson = new SJson();
        Person person = sJson.toObject(json, Person.class);
        System.out.println(" Person Object : " + person.toString());
        Assertions.assertNotNull(person);
        Assertions.assertEquals("1",person.getId());
        Assertions.assertEquals("sudhs",person.getName());
        Assertions.assertEquals("Lakshadweep",person.getAddress());

    }
}
