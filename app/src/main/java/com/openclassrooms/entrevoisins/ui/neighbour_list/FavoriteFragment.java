package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.stream.Collectors;

import static com.openclassrooms.entrevoisins.model.Constant.KEY_NEIGHBOUR;

public class FavoriteFragment extends Fragment implements RecyclerViewClickInterface{

    private NeighbourApiService mApiService;
    private List<Neighbour> favNeighbours;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Init the List of favorite neighbours
     */
    private void initList() {
        favNeighbours = mApiService.getNeighbours().stream().filter(Neighbour::getIsFavorite).collect(Collectors.toList());
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(favNeighbours, this));
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(KEY_NEIGHBOUR, favNeighbours.get(position));
        getContext().startActivity(intent);
    }

    @Override
    public void onItemDelete(int position) {
        EventBus.getDefault().post(new DeleteNeighbourEvent(favNeighbours.get(position)));
    }
}
