package it.unimib.brain_alarm.Repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.News.Result;

//interfaccia che utilizza liveData. Associo a liveData un oggetto result
//astraggo il risultato della chiamata utilizzando result, mi permette di avere informazioni sugli eroori

public interface INewsRepositoryWithLiveData {

    MutableLiveData<Result> fetchNews(String country, int page, long lastUpdate);

    MutableLiveData<Result> getFavoriteNews();

    void updateNews(News news);

    void deleteFavoriteNews();
}
