package ca.judacribz.week6day2_mvp.view.activities.animal_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Objects;

import ca.judacribz.week6day2_mvp.R;
import ca.judacribz.week6day2_mvp.view.adapters.AnimalAdapter;

public class AnimalList extends AppCompatActivity implements Contract {

    RecyclerView rvAnimals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);


        rvAnimals = findViewById(R.id.rvAnimals);
        rvAnimals.setLayoutManager(new LinearLayoutManager(this));


        new Presenter(this, getIntent()).getAnimals();

//        SharedPreferences.Editor editor = getSharedPreferences(
//                getResources().getString(R.string.category_file),
//                Context.MODE_PRIVATE
//        ).edit();
//        editor.putString(Presenter.KEY_CATEGORY, category);
//        editor.apply();
    }

    @Override
    public void onCategoryReceived(String category) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(category.toUpperCase());
    }

    @Override
    public void onAdapterCreated(AnimalAdapter adapter) {
        rvAnimals.setAdapter(adapter);
    }
}
