package ca.judacribz.week6day2_mvp.view.activities.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ca.judacribz.week6day2_mvp.R;
import ca.judacribz.week6day2_mvp.view.activities.animal_list.AnimalList;
import ca.judacribz.week6day2_mvp.view.activities.category_list.CategoryList;
import ca.judacribz.week6day2_mvp.model.datasource.remote.async.MainImageTask;
import ca.judacribz.week6day2_mvp.model.datasource.remote.async.ScheduleTask;

import static ca.judacribz.week6day2_mvp.view.activities.animal_list.Presenter.EXTRA_CATEGORY_NAME;

public class Homepage extends AppCompatActivity implements
        ScheduleTask.ScheduleListener,
        MainImageTask.ImageListener {

    public static final int DURATION_IMAGE_CHANGE = 9000;
    private static final int[] IMG_ID_ANIMALS = new int[]{
            R.drawable.elephant,
            R.drawable.gorilla,
            R.drawable.panda,
            R.drawable.zebra
    };
    private static final int[] STR_ID_HEAD_ANIMALS = new int[]{
            R.string.elephant,
            R.string.gorilla,
            R.string.panda,
            R.string.zebra
    };
    private static final int[] STR_ID_DESC_ANIMALS = new int[]{
            R.string.elephant_desc,
            R.string.gorilla_desc,
            R.string.panda_desc,
            R.string.zebra_desc
    };
    private static final int[] BTN_IDS = new int[]{
            R.id.btnCategories,
            R.id.btnTickets,
            R.id.btnAnimals
    };

    private ImageView ivAnimalImages;
    private TextView
            tvAnimalHeadline,
            tvAnimalDescription,
            tvSchedule,
            tvLastAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Objects.requireNonNull(getSupportActionBar()).hide();

        new MainImageTask(this).execute();
        ScheduleTask scheduleScrape = new ScheduleTask();
        scheduleScrape.setScheduleListener(this);
        scheduleScrape.execute();

        setButtonWidthEqually(getResources().getDisplayMetrics().widthPixels / 3);

        ivAnimalImages = findViewById(R.id.ivAnimalImages);
        tvAnimalHeadline = findViewById(R.id.tvAnimalHeadline);
        tvAnimalDescription = findViewById(R.id.tvAnimalDescription);
        tvLastAdmin = findViewById(R.id.tvLastAdmin);
        tvSchedule = findViewById(R.id.tvSchedule);
    }

    private void setButtonWidthEqually(int btnWidth) {
        for (int btnId : BTN_IDS) {
            findViewById(btnId).getLayoutParams().width = btnWidth;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onScheduleReceived(List<TextNode> schedule) {
        tvSchedule.setText(schedule.get(0).toString().trim());
        tvLastAdmin.setText(schedule.get(1).toString());
    }

    public void goToCategories(View view) {
        startActivity(new Intent(this, CategoryList.class));
    }

    public void goToAnimals(View view) {
        Intent intent =new Intent(this, AnimalList.class);
        intent.putExtra(EXTRA_CATEGORY_NAME, "");
        startActivity(intent);
    }

    List<String> urls = new ArrayList<>();

    @Override
    public void onImageUrlsReceived(final List<String> urls) {
        ivAnimalImages.post(
                new Runnable() {
                    int i = 0;
                    int numImages = urls.size();

                    public void run() {
                        if (numImages > 0) {

                            Glide
                                    .with(getApplicationContext())
                                    .load(urls.get(i))
                                    .into(ivAnimalImages);
//                            ivAnimalImages.setImageResource(IMG_ID_ANIMALS[i]);
                            i = (i + 1) % numImages;
                        }
//                        tvAnimalHeadline.setText(getResources().getString(STR_ID_HEAD_ANIMALS[i]));
//                        tvAnimalDescription.setText(getResources().getString(STR_ID_DESC_ANIMALS[i]));


                        ivAnimalImages.postDelayed(this, DURATION_IMAGE_CHANGE);
                    }
                }
        );
    }
}
