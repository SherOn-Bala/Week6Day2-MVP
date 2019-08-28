package ca.judacribz.week6day2_mvp.model.datasource.remote.async;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainImageTask extends AsyncTask<Void, Void, List<String>> {

    ImageListener imageListener;

    public interface ImageListener {
        void onImageUrlsReceived(List<String> urls);
    }

    public MainImageTask(ImageListener imageListener) {
        this.imageListener = imageListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> urls = null;
        try {
            Document document = Jsoup.connect("https://zooatlanta.org/").get();

            List<Element> listNodes = document.getElementsByClass("hero-image");
            urls = new ArrayList<>();
            String str;
            for (Element element : listNodes) {
                urls.add((str = element.attr("style")).substring(str.indexOf("(") + 1, str.indexOf(")")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }

    @Override
    protected void onPostExecute(@Nullable List<String> urls) {
        if (urls != null) {
            imageListener.onImageUrlsReceived(urls);
        }
    }
}
