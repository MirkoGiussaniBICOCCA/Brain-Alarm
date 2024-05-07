package it.unimib.brain_alarm.source;


import java.util.List;

import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.News.NewsApiResponse;

//interfaccia con una serie di metodi da struttare per
//chiamata a beck end andata bene o male
//chiamata a database andata bene o male
//per aggiornamento dentro una news o per eliminazione di notizie preferite

public interface NewsCallback {
    void onSuccessFromRemote(NewsApiResponse newsApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(List<News> newsList);
    void onFailureFromLocal(Exception exception);
    void onNewsFavoriteStatusChanged(News news, List<News> favoriteNews);
    void onNewsFavoriteStatusChanged(List<News> news);
    void onDeleteFavoriteNewsSuccess(List<News> favoriteNews);
}
