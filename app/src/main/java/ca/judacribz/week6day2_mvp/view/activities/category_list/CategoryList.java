package ca.judacribz.week6day2_mvp.view.activities.category_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.judacribz.week6day2_mvp.R;
import ca.judacribz.week6day2_mvp.view.adapters.CategoryAdapter;

public class CategoryList extends AppCompatActivity implements Contract {

    @BindView(R.id.rvCategories)
    RecyclerView rvCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.animal_classifications);

        rvCategories.setLayoutManager(new LinearLayoutManager(this));
        new Presenter(this).getCategories();
    }


    @Override
    public void onAdapterCreated(CategoryAdapter adapter) {
        rvCategories.setAdapter(adapter);
    }
}