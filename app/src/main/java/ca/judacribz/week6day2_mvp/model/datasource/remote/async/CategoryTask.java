package ca.judacribz.week6day2_mvp.model.datasource.remote.async;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import ca.judacribz.week6day2_mvp.model.animal.Category;

import static ca.judacribz.week6day2_mvp.model.Constants.*;

public class CategoryTask extends AsyncTask<Void, Void, ArrayList<Category>> {

    private CategoriesListener categoriesListener;

    public interface CategoriesListener {
        void onCategoriesReceived(ArrayList<Category> categories);
    }

    public CategoryTask(CategoriesListener categoriesListener) {
        this.categoriesListener = categoriesListener;
    }

    @Override
    protected ArrayList<Category> doInBackground(Void... voids) {
        ArrayList<Category> categories = new ArrayList<>();

        try {
            String categoryName;
            int numSpecies;

            Document document = Jsoup.connect(URL_ZOO).get();

            for (Element el : document.getElementsByClass(CLASS_CATEGORY)) {

                categoryName = el
                        .getElementsByTag(E_TAG_LABEL).get(0)
                        .getElementsByTag(E_TAG_INPUT).get(0)
                        .val();

                /* https://zooatlanta.org/animals/?wpvtypeofanimal%5B%5D={categoryName} */
                document = Jsoup.connect(String.format(
                        Locale.US,
                        URL_ZOO_QUERY_CATEGORY,
                        categoryName
                )).get();
                numSpecies = document.getElementsByClass(CLASS_ANIMAL).size();

                document = Jsoup.connect(String.format(
                        Locale.US,
                        URL_DICT_DEFINITION,
                        categoryName
                )).get();

                categories.add(new Category(
                        categoryName,
                        document.getElementsByClass(CLASS_SHORT).get(0).text(),
                        numSpecies
                ));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> categories) {
        if (!categories.isEmpty()) {
            categoriesListener.onCategoriesReceived(categories);
        }
    }
}
