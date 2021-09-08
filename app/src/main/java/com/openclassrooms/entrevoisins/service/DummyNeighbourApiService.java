package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private final List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) { neighbours.add(neighbour); }

    @Override
    public void addNeighbourToFav(Neighbour neighbour) {
        neighbour.setFavorite(true);
        neighbours.set(neighbours.indexOf(neighbour), neighbour);
    }

    @Override
    public void deleteNeighbourFromFav(Neighbour neighbour) {
        neighbour.setFavorite(false);
        // Check if index is valid
        if (neighbours.contains(neighbour)) {
            neighbours.set(neighbours.indexOf(neighbour), neighbour);
        }
    }
}
