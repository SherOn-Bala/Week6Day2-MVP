package ca.judacribz.week6day2_mvp.view.activities.animal_list;

import ca.judacribz.week6day2_mvp.view.adapters.AnimalAdapter;

public interface Contract {
    void onCategoryReceived(String category);
    void onAdapterCreated(AnimalAdapter adapter);
}
