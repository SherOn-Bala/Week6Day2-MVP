package ca.judacribz.week6day2_mvp.model.datasource.remote.async;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import ca.judacribz.week6day2_mvp.model.animal.Animal;

import static ca.judacribz.week6day2_mvp.model.Constants.DIET;
import static ca.judacribz.week6day2_mvp.model.Constants.RANGE;
import static ca.judacribz.week6day2_mvp.model.Constants.READ_MORE;
import static ca.judacribz.week6day2_mvp.model.Constants.STATUS;
import static ca.judacribz.week6day2_mvp.model.Constants.URL_ZOO;
import static ca.judacribz.week6day2_mvp.model.Constants.URL_ZOO_QUERY_CATEGORY;

public class AnimalTask extends AsyncTask<String, Void, ArrayList<Animal>> {

    private AnimalsListener animalsListener;

    public interface AnimalsListener {
        void onAnimalsReceived(ArrayList<Animal> animals);
    }

    public AnimalTask(AnimalsListener animalsListener) {
        this.animalsListener = animalsListener;
    }

    @Override
    protected ArrayList<Animal> doInBackground(String... categoryName) {

        ArrayList<Animal> animals = new ArrayList<>();

        try {
            Document document = Jsoup.connect((
                    categoryName[0]).isEmpty() ?
                    URL_ZOO :
                    String.format(Locale.US,
                            "%s?%s=%s",
                            URL_ZOO,
                            URL_ZOO_QUERY_CATEGORY,
                            categoryName[0]
                    )
            ).get();

            String styleStr;
            String url;
            String backCard;
            String name, scientificName;
            String diet, status, range;

            for (Element aniEl : document.getElementsByClass("animal card")) {

                styleStr = aniEl.getElementsByClass("featured-image").get(0).attr("style");
                url = styleStr.substring(styleStr.indexOf('(') + 1, styleStr.indexOf(')'));
                name = aniEl.getElementsByTag("h3").get(0).text();
                scientificName = aniEl.getElementsByTag("em").get(0).text();

                backCard = aniEl
                        .getElementsByClass("flipper").get(0)
                        .getElementsByClass("back").get(0)
                        .getElementsByClass("container").get(0)
                        .text();

                diet = backCard.substring(
                        backCard.indexOf(DIET) + DIET.length(),
                        backCard.indexOf(STATUS)
                );
                status = backCard.substring(
                        backCard.indexOf(STATUS) + STATUS.length(),
                        backCard.indexOf(RANGE)
                );
                range = backCard.substring(
                        backCard.indexOf(RANGE) + RANGE.length(),
                        backCard.indexOf(READ_MORE)
                );

                animals.add(new Animal(name, scientificName, diet, status, range, url));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return animals;
    }

    @Override
    protected void onPostExecute(ArrayList<Animal> animals) {
        if (!animals.isEmpty()) {
            animalsListener.onAnimalsReceived(animals);
        }
    }
}
