package ca.judacribz.week6day2_mvp.model.datasource.remote.async;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import ca.judacribz.week6day2_mvp.model.Animal;

public class AnimalDetailsTask extends AsyncTask<Animal, Void, Animal> {
    private Animal animal;
    private AnimalDetailsListener animalDetailsListener;

    public interface AnimalDetailsListener {
        void onAnimalDetailsReceived(Animal animal);
    }

    public AnimalDetailsTask(AnimalDetailsListener animalDetailsListener) {
        this.animalDetailsListener = animalDetailsListener;
    }

    @Override
    protected Animal doInBackground(Animal... animals) {
        Animal animal = animals[0];
        try {
            Document document = Jsoup.connect(
                    "https://zooatlanta.org/animal/" +
                            animal.getName().toLowerCase().replaceAll(" ", "-")
            ).get();

            animal.setDescription(
                    "\t\t\t\t" +
                            document
                                    .getElementsByClass("abstract").get(0)
                                    .getElementsByTag("p").get(0)
                                    .text()
            );

            animal.setHabitat(
                    document
                            .getElementsByClass("main-left-content").get(0)
                            .getElementsByTag("p").get(3)
                            .text().split(":")[1]
            );

            animal.setViewingHints(
                    document
                            .getElementsByClass("main-left-content").get(0)
                            .getElementsByTag("p").get(4)
                            .text()
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return animal;
    }

    @Override
    protected void onPostExecute(Animal animal) {
        animalDetailsListener.onAnimalDetailsReceived(animal);
    }
}
