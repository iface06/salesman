
package de.as.salesman;

import org.junit.Test;


public class JourneyTest {
    
    @Test
    public void testTourWithTwoCities() {
        City erlangen = new City("Erlangen", 49.5977f, 11.0039f);
        City uffenheim = new City("Uffenheim", 49.5333f, 10.25f);
        
        Journey j = new Journey();
        j.addDestination(erlangen);
        j.addDestination(uffenheim);
        
        double distance = j.calculateDistance();

    }


}