package ca.judacribz.week6day2_mvp.view.activities.animal_details;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import ca.judacribz.week6day2_mvp.R;
import ca.judacribz.week6day2_mvp.model.animal.Animal;
import ca.judacribz.week6day2_mvp.model.datasource.remote.async.AnimalDetailsTask;

import static ca.judacribz.week6day2_mvp.view.adapters.AnimalAdapter.ViewHolder.EXTRA_ANIMAL;
import static ca.judacribz.week6day2_mvp.view.activities.animal_list.Presenter.KEY_CATEGORY;

public class AnimalDetails extends AppCompatActivity implements
        AnimalDetailsTask.AnimalDetailsListener{

    private static final String BIRDS = "birds";

    private TextView
            tvAnimalName,
            tvScientificName,
            tvDiet,
            tvStatus,
            tvRange,
            tvHabitat,
            tvViewingHints,
            tvDescription;
    private ImageView ivAnimalImage;
    private Animal animal;

    private MediaPlayer animalSound = null;

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details);
        Objects.requireNonNull(getSupportActionBar()).hide();


        tvAnimalName = findViewById(R.id.tvAnimalName);
        tvScientificName = findViewById(R.id.tvScientificName);
        tvDiet = findViewById(R.id.tvDiet);
        tvStatus = findViewById(R.id.tvStatus);
        tvRange = findViewById(R.id.tvRange);
        tvHabitat = findViewById(R.id.tvHabitat);
        tvViewingHints = findViewById(R.id.tvViewingHints);
        tvDescription = findViewById(R.id.tvAnimalDescription);

        ivAnimalImage = findViewById(R.id.ivAnimalImage);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            setViewData(animal = bundle.getParcelable(EXTRA_ANIMAL));
            new AnimalDetailsTask(this).execute(animal);
        }

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.category_file), Context.MODE_PRIVATE);
        category = sharedPref.getString(KEY_CATEGORY, "");
    }



    void setViewData(Animal animal) {
        Glide.with(this).load(animal.getImgUrl()).into(ivAnimalImage);
        tvAnimalName.setText(animal.getName());
        tvScientificName.setText(animal.getScientificName());
        tvDiet.setText(animal.getDiet());
        tvStatus.setText(animal.getStatus());
        tvRange.setText(animal.getRange());
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseSound();
    }

    public void makeAnimalSound(View view) {
        releaseSound();

        String name = animal.getName().toLowerCase();
        animalSound = MediaPlayer.create(this, R.raw.king);

        if (BIRDS.equals(category)) {
            animalSound = MediaPlayer.create(this, R.raw.bird);
        } else if (name.contains("lion")) {
            animalSound = MediaPlayer.create(this, R.raw.lion);
        } else if (name.contains("elephant")) {
            animalSound = MediaPlayer.create(this, R.raw.elephant);
        }

        animalSound.start();
    }

    private void releaseSound() {
        if (animalSound != null) {
            animalSound.stop();
            animalSound.release();
        }
    }

    @Override
    public void onAnimalDetailsReceived(Animal animal) {
        tvDescription.setText(animal.getDescription());
        tvHabitat.setText(animal.getHabitat());
        tvViewingHints.setText(animal.getViewingHints());
    }
}
