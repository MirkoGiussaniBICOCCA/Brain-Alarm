package it.unimib.brain_alarm.source;


import java.util.List;

import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.News.NewsApiResponse;

/**
 * Base class to get news from a local source.
 */
public abstract class BaseNewsLocalDataSource {

    protected NewsCallback newsCallback;

    public void setNewsCallback(NewsCallback newsCallback) {
        this.newsCallback = newsCallback;
    }

    //metodi astratti per restituire tutte le news, solo le preferite, per aggiornare news,
    //per cancellare e per inserire news nel database
    public abstract void getNews();
    public abstract void getFavoriteNews();
    public abstract void updateNews(News news);
    public abstract void deleteFavoriteNews();
    public abstract void insertNews(NewsApiResponse newsApiResponse);
}
