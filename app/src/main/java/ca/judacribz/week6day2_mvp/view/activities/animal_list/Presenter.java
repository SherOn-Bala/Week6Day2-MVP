package ca.judacribz.week6day2_mvp.view.activities.animal_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import ca.judacribz.week6day2_mvp.R;
import ca.judacribz.week6day2_mvp.model.animal.Animal;
import ca.judacribz.week6day2_mvp.model.datasource.remote.async.AnimalTask;
import ca.judacribz.week6day2_mvp.view.adapters.AnimalAdapter;

public class Presenter implements AnimalTask.AnimalsListener {

    public static final String KEY_CATEGORY =
            "ca.judacribz.week6day2_mvp.view.activities.animal_list.KEY_CATEGORY";
    public static final String EXTRA_CATEGORY_NAME =
            "ca.judacribz.week6day2_mvp.view.activities.animal_list.EXTRA_CATEGORY_NAME";

    private Contract animalContract;
    private Context context;
    private String category;

    Presenter(Context context) {
        this.animalContract = (Contract) (this.context = context);
        category = ((Activity)context).getIntent().getStringExtra(EXTRA_CATEGORY_NAME);

        Log.d("YOOO", "onClick: " + category);
        animalContract.onCategoryReceived(
                !category.isEmpty() ?
                        category :
                        context.getString(R.string.all_animals)
        );
    }

    void getAnimals() {
        new AnimalTask(this).execute(category);
    }

    @Override
    public void onAnimalsReceived(ArrayList<Animal> animals) {
        animalContract.onAdapterCreated(new AnimalAdapter(context, animals));
    }
}
