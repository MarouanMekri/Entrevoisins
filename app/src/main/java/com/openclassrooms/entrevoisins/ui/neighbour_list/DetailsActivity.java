package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.entrevoisins.model.Constant.KEY_NEIGHBOUR;

public class DetailsActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.imgViewProfile)
    ImageView imgViewProfile;
    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;
    @BindView(R.id.txtViewUsername)
    TextView txtViewUsername;
    @BindView(R.id.cardViewInfoUsername)
    TextView cardViewInfoUsername;
    @BindView(R.id.cardViewPhone)
    TextView cardViewPhone;
    @BindView(R.id.cardViewAddress)
    TextView cardViewAddress;
    @BindView(R.id.cardViewWebsite)
    TextView cardViewWebsite;
    @BindView(R.id.cardViewAboutMeDetails)
    TextView cardViewAboutMeDetails;
    @BindView(R.id.fabFavorite)
    FloatingActionButton fabFavorite;

    NeighbourApiService apiService = DI.getNeighbourApiService();
    Neighbour neighbour;

    String neighbour_name, neighbour_avatarUrl, neighbour_phonenumber, neighbour_address, neighbour_aboutme;
    long neighbour_id;
    boolean neighbour_isfavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        getData();
        setData();

        // Display the right status of button
        if (!neighbour.getIsFavorite()){
            fabFavorite.setImageResource(R.drawable.ic_star_border_white_24dp);
        }else{
            fabFavorite.setImageResource(R.drawable.ic_star_white_24dp);
        }
    }

    // Retrieve data from MyNeighbourRecyclerViewAdapter
    void getData() {
        if (getIntent().hasExtra(KEY_NEIGHBOUR)) {
            neighbour = (Neighbour) getIntent().getSerializableExtra(KEY_NEIGHBOUR);
            neighbour_id = neighbour.getId();
            neighbour_name = neighbour.getName();
            neighbour_avatarUrl = neighbour.getAvatarUrl();
            neighbour_address = neighbour.getAddress();
            neighbour_aboutme = neighbour.getAboutMe();
            neighbour_phonenumber = neighbour.getPhoneNumber();
            neighbour_isfavorite = neighbour.getIsFavorite();
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    // Display data in activity_details
    void setData(){
        txtViewUsername.setText(neighbour_name);
        cardViewInfoUsername.setText(neighbour_name);
        cardViewAddress.setText(neighbour_address);
        cardViewWebsite.setText(String.format("www.facebook.fr/%s", neighbour_name).toLowerCase());
        cardViewPhone.setText(neighbour_phonenumber);
        cardViewAboutMeDetails.setText(neighbour_aboutme);
        Glide.with(this).load(neighbour_avatarUrl).centerCrop().into(imgViewProfile);
    }

    // Back button onClick
    @OnClick(R.id.imgViewBack)
    void backToListNeighbour(){
        finish();
    }

    // Add favorite onClick
    @OnClick(R.id.fabFavorite)
    void addToFavorite(){
        // Checking and updating data
        if (neighbour.getIsFavorite()){
            apiService.deleteNeighbourFromFav(neighbour);
            fabFavorite.setImageResource(R.drawable.ic_star_border_white_24dp);
        }else{
            apiService.addNeighbourToFav(neighbour);
            fabFavorite.setImageResource(R.drawable.ic_star_white_24dp);
        }
    }
}