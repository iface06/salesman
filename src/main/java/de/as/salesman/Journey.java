package de.as.salesman;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Journey implements Iterable<City> {
    static Comparator<? super Journey> FITTEST_COMPARATOR = new Comparator<Journey>() {

        @Override
        public int compare(Journey o1, Journey o2) {
            return Double.compare(o1.calculateDistance(), o2.calculateDistance());
        }
    };

    List<City> destinations = new LinkedList<>();
    DistanceCalculator c = new DistanceCalculator();
    double distance = 0.0;

    Journey(Journey journey) {
        destinations = new ArrayList<>(journey.destinations);
    }

    public Journey() {
    }
    
    double calculateDistance() {
        if (distance == 0.0) {
            City lastStartCity = null;
            for (City destination : destinations) {
                if (lastStartCity != null) {
                    distance += c.calculateDistance(lastStartCity, destination);
                }
                lastStartCity = destination;

            }
        }
        return distance;
    }

    void addDestination(City city) {
        destinations.add(city);
        distance = 0.0;
    }
    
    

    @Override
    public Iterator<City> iterator() {
        return destinations.iterator();
    }

    void addDestinations(List<City> cities) {
        destinations.addAll(cities);
        distance = 0.0;
    }

}
