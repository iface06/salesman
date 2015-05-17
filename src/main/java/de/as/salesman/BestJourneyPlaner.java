package de.as.salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The BestJourneyPlaner encapsulate an algorithmn for the Traveling Salesman
 * Problem. It uses a genetic algorithm to solve the Problem quick and
 * economical. In the first phase it creates a generation of possible journies.
 * Each journey consists of several city tours. Each city tour consist of a
 * start point and a destination. A list of tours builds the whole journey. At
 * the end of the algorithm the most probable short journey was found.
 *
 */
public class BestJourneyPlaner {

    private static final int POPULATIONSIZE_FACTOR = 1000;
    private static final int NUMBER_OF_FITTEST_FOR_REPRODUCTION = 5;

    private List<City> cities;
    private List<Journey> population = new ArrayList<>();

    public Journey plan() {
        population = createPopulationSortedByFittness();
        int generations = population.size() * 2;

        for (int i = 0; i < generations; i++) {
            List<Journey> fittests = findFittestForReproduction();
            List<Journey> children = reproduction(fittests);

            population.addAll(children);
            population.sort(Journey.FITTEST_COMPARATOR);
            dispatchUnfittest(children);
        }
        return population.get(0);
    }

    private void dispatchUnfittest(List<Journey> children) {
        for (int j = 0; j < children.size(); j++) {
            population.remove(population.size() - 1);
        }
    }

    private List<Journey> reproduction(List<Journey> fittests) {
        List<Journey> children = new ArrayList<>();
        Journey fittest = fittests.get(0);
        for (int x = 1; x < fittests.size(); x++) {
            Journey child = crossover(fittest, fittests.get(x));
            children.add(child);
        }
        return children;
    }

    private List<Journey> findFittestForReproduction() {
        List<Journey> fittests = new ArrayList<>();
        for (int j = 0; j < NUMBER_OF_FITTEST_FOR_REPRODUCTION; j++) {
            fittests.add(population.get(j));
        }
        return fittests;
    }

    private List<Journey> createPopulationSortedByFittness() {
        int populationSize = calculatePopulationSize();
        for (int i = 0; i < populationSize; i++) {
            Journey j = new Journey();
            j.addDestinations(cities);
            population.add(j);
            shuffelCities();
        }

        population.sort(Journey.FITTEST_COMPARATOR);
        return population;
    }

    private void shuffelCities() {
        List<City> shuffeledCities = new ArrayList<>(cities);
        Collections.shuffle(shuffeledCities);
        cities = shuffeledCities;
    }

    /**
     * (n-1)! / 2
     *
     * @return
     */
    private int calculatePopulationSize() {
        return cities.size() * POPULATIONSIZE_FACTOR;
    }

    public BestJourneyPlaner destinations(List<City> destinations) {
        this.cities = destinations;
        return this;
    }

    private Journey crossover(Journey fittest, Journey partner) {
        int sizeOfTheFittest = fittest.destinations.size();

        int start = 1;
        int end = sizeOfTheFittest;
        if (sizeOfTheFittest > 3) {
            end = sizeOfTheFittest - 2;
        }

        Journey child1 = new Journey(fittest);
        Journey child2 = new Journey(partner);

        for (int i = start; i < end; i++) {
            child1.destinations.add(i, partner.destinations.get(i));
            child2.destinations.add(i, fittest.destinations.get(i));
        }

        return child1.calculateDistance() > child2.calculateDistance() ? child1 : child2;

    }

}
