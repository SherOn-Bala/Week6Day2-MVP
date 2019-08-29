package ca.judacribz.week6day2_mvp.view.activities.category_list;

import java.util.ArrayList;

import ca.judacribz.week6day2_mvp.model.Category;
import ca.judacribz.week6day2_mvp.model.datasource.remote.async.CategoryTask;
import ca.judacribz.week6day2_mvp.view.adapters.CategoryAdapter;

public class Presenter implements CategoryTask.CategoriesListener {

    public static final String EXTRA_CATEGORY_NAME =
            "ca.judacribz.week2weekend.categories.EXTRA_CATEGORY_NAME";

    Contract categoryContract;

    public Presenter(Contract categoryContract) {
        this.categoryContract = categoryContract;
    }

    void getCategories() {
        new CategoryTask(this).execute();
    }

    @Override
    public void onCategoriesReceived(ArrayList<Category> categories) {
        categoryContract.onAdapterCreated(new CategoryAdapter(categories));
    }
}
