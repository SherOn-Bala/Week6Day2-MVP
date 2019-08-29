package ca.judacribz.week6day2_mvp.view.activities.category_list;

import java.util.ArrayList;

import ca.judacribz.week6day2_mvp.model.animal.Category;
import ca.judacribz.week6day2_mvp.model.datasource.remote.async.CategoryTask;
import ca.judacribz.week6day2_mvp.view.adapters.CategoryAdapter;

public class Presenter implements CategoryTask.CategoriesListener {

    private Contract categoryContract;

    Presenter(Contract categoryContract) {
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
