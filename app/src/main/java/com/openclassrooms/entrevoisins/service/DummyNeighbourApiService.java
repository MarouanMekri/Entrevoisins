package com.openclassrooms.entrevoisins.service;

import android.util.Log;
import android.widget.Toast;

import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.DetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

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
