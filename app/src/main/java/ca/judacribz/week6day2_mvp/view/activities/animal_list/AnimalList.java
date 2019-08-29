package ca.judacribz.week6day2_mvp.view.activities.animal_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import ca.judacribz.week6day2_mvp.R;
import ca.judacribz.week6day2_mvp.model.Animal;
import ca.judacribz.week6day2_mvp.model.datasource.remote.async.AnimalTask;
import ca.judacribz.week6day2_mvp.view.adapters.AnimalAdapter;

import static ca.judacribz.week6day2_mvp.view.activities.category_list.Presenter.EXTRA_CATEGORY_NAME;

public class AnimalList extends AppCompatActivity implements AnimalTask.AnimalsListener {

    public static final String KEY_CATEGORY = "ca.judacribz.week2weekend.animals.KEY_CATEGORY";
    public static final String ALL_ANIMALS = "All AnimalList";
    RecyclerView rvAnimals;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);


        category = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);
        if (category == null) {
            category = ALL_ANIMALS;
        }

        new AnimalTask(this).execute(category);
        Objects.requireNonNull(getSupportActionBar()).setTitle(category.toUpperCase());

        rvAnimals = findViewById(R.id.rvAnimals);
        rvAnimals.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences.Editor editor = getSharedPreferences(
                getResources().getString(R.string.category_file),
                Context.MODE_PRIVATE
        ).edit();
        editor.putString(KEY_CATEGORY, category);
        editor.apply();
    }

    @Override
    public void onAnimalsReceived(ArrayList<Animal> animals) {
        AnimalAdapter animalAdapter = new AnimalAdapter(this, animals);
        rvAnimals.setAdapter(animalAdapter);
    }
}
