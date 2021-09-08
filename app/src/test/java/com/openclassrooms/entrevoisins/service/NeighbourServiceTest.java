package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void addNeighbourWithSuccess() {
        Neighbour neighbourToAdd = new DummyNeighbourApiService().getNeighbours().get(0);
        service.createNeighbour(neighbourToAdd);
        assertTrue(service.getNeighbours().contains(neighbourToAdd));
    }

    @Test
    public void getFavNeighboursWithSuccess() {
        List<Neighbour> favNeighbours = service.getNeighbours().stream().filter(Neighbour::getIsFavorite).collect(Collectors.toList());
        assertTrue(favNeighbours.isEmpty());
    }

    @Test
    public void addThenDeleteNeighbourFromFavoritesWithSuccess() {
        // Get favorite neighbours list
        List<Neighbour> favNeighbours = service.getNeighbours().stream().filter(Neighbour::getIsFavorite).collect(Collectors.toList());
        // Select first neighbour from neighbour list
        Neighbour neighbourToFav = new DummyNeighbourApiService().getNeighbours().get(0);
        // Add neighbour to favorite list
        service.addNeighbourToFav(neighbourToFav);
        // Check if neighbour is added to favorite list
        assertTrue(service.getNeighbours().stream().filter(Neighbour::getIsFavorite).collect(Collectors.toList()).contains(neighbourToFav));
        // Delete neighbour from favorite list
        service.deleteNeighbourFromFav(neighbourToFav);
        // Check if neighbour is removed to favorite list
        assertFalse(favNeighbours.contains(neighbourToFav));
    }
}
