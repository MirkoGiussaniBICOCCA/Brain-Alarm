package it.unimib.brain_alarm.Repository;


import static it.unimib.brain_alarm.util.Constants.FRESH_TIMEOUT;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_COUNTRY_OF_INTEREST;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.News.NewsApiResponse;
import it.unimib.brain_alarm.News.NewsResponse;
import it.unimib.brain_alarm.News.Result;
import it.unimib.brain_alarm.source.BaseNewsLocalDataSource;
import it.unimib.brain_alarm.source.BaseNewsRemoteDataSource;
import it.unimib.brain_alarm.source.NewsCallback;


/**
 * Repository class to get the news from local or from a remote source.
 */
public class NewsRepositoryWithLiveData implements INewsRepositoryWithLiveData, NewsCallback {

    private static final String TAG = NewsRepositoryWithLiveData.class.getSimpleName();

    //un liveData associato a tutte le notizie e uno associato solo alle notizie preferite
    private final MutableLiveData<Result> allNewsMutableLiveData;
    private final MutableLiveData<Result> favoriteNewsMutableLiveData;

    private final BaseNewsRemoteDataSource newsRemoteDataSource;
    private final BaseNewsLocalDataSource newsLocalDataSource;

    //costruttore che inizializza mutableLiveData e callback per notioficare aggiornamenti
    public NewsRepositoryWithLiveData(BaseNewsRemoteDataSource newsRemoteDataSource,
                                      BaseNewsLocalDataSource newsLocalDataSource) {

        allNewsMutableLiveData = new MutableLiveData<>();
        favoriteNewsMutableLiveData = new MutableLiveData<>();
        this.newsRemoteDataSource = newsRemoteDataSource;
        this.newsLocalDataSource = newsLocalDataSource;
        this.newsRemoteDataSource.setNewsCallback(this);
        this.newsLocalDataSource.setNewsCallback(this);
    }

    //istanzio mutable live data. lo instanzio una volta sola in modo da avere un solo osservatore
    @Override
    public MutableLiveData<Result> fetchNews(String country, int page, long lastUpdate) {
        long currentTime = System.currentTimeMillis();

        // controllo se il tempo dall'ultimo aggiornamento è maggiore di FRESH_TIMEOUT
        //se è maggiore mi appoggio a backend altrimenti leggo dati da locale
        if (currentTime - lastUpdate > FRESH_TIMEOUT) {
            newsRemoteDataSource.getNews(country);
        } else {
            newsLocalDataSource.getNews();
        }
        return allNewsMutableLiveData; //restituisce instanza liveData
    }

    @Override
    public MutableLiveData<Result> getFavoriteNews() {
        newsLocalDataSource.getFavoriteNews();
        return favoriteNewsMutableLiveData;
    }

    @Override
    public void updateNews(News news) {
        newsLocalDataSource.updateNews(news);
    }

    @Override
    public void deleteFavoriteNews() {
        newsLocalDataSource.deleteFavoriteNews();
    }

    @Override
    public void onSuccessFromRemote(NewsApiResponse newsApiResponse, long lastUpdate) {
        newsLocalDataSource.insertNews(newsApiResponse.getNewsList());
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allNewsMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(List<News> newsList) {
        Result.Success result = new Result.Success(new NewsResponse(newsList));
        allNewsMutableLiveData.postValue(result); //notifico cambiamento
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allNewsMutableLiveData.postValue(resultError);
        favoriteNewsMutableLiveData.postValue(resultError);
    }

    @Override
    public void onNewsFavoriteStatusChanged(News news, List<News> favoriteNews) {
        Result allNewsResult = allNewsMutableLiveData.getValue();

        if (allNewsResult != null && allNewsResult.isSuccess()) {
            List<News> oldAllNews = ((Result.Success)allNewsResult).getData().getNewsList();
            if (oldAllNews.contains(news)) {
                oldAllNews.set(oldAllNews.indexOf(news), news);
                allNewsMutableLiveData.postValue(allNewsResult);
            }
        }
        favoriteNewsMutableLiveData.postValue(new Result.Success(new NewsResponse(favoriteNews)));
    }

    @Override
    public void onNewsFavoriteStatusChanged(List<News> news) {
        favoriteNewsMutableLiveData.postValue(new Result.Success(new NewsResponse(news)));
    }

    @Override
    public void onDeleteFavoriteNewsSuccess(List<News> favoriteNews) {
        Result allNewsResult = allNewsMutableLiveData.getValue();

        if (allNewsResult != null && allNewsResult.isSuccess()) {
            List<News> oldAllNews = ((Result.Success)allNewsResult).getData().getNewsList();
            for (News news : favoriteNews) {
                if (oldAllNews.contains(news)) {
                    oldAllNews.set(oldAllNews.indexOf(news), news);
                }
            }
            allNewsMutableLiveData.postValue(allNewsResult);
        }

        if (favoriteNewsMutableLiveData.getValue() != null &&
                favoriteNewsMutableLiveData.getValue().isSuccess()) {
            favoriteNews.clear();
            Result.Success result = new Result.Success(new NewsResponse(favoriteNews));
            favoriteNewsMutableLiveData.postValue(result);
        }
    }
}
