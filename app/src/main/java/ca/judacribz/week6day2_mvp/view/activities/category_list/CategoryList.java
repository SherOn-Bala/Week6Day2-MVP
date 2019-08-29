package ca.judacribz.week6day2_mvp.view.activities.category_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Objects;

import ca.judacribz.week6day2_mvp.R;
import ca.judacribz.week6day2_mvp.view.adapters.CategoryAdapter;

public class CategoryList extends AppCompatActivity implements Contract {

    RecyclerView rvCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.animal_classifications);

        initViews();

        new Presenter(this).getCategories();
    }

    private void initViews() {
        rvCategories = findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onAdapterCreated(CategoryAdapter adapter) {
        rvCategories.setAdapter(adapter);
    }
}