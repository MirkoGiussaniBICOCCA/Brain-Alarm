package it.unimib.brain_alarm;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.News.Result;
import it.unimib.brain_alarm.Repository.INewsRepositoryWithLiveData;

//per gestire lista notizie e notizie preferite
//View model permette di salvare lo stato e insieme a live data permette di
//notificare informazioni allo strato di UI

public class NewsViewModel extends ViewModel {

    private static final String TAG = NewsViewModel.class.getSimpleName();

    private final INewsRepositoryWithLiveData newsRepositoryWithLiveData;
    private final int page;
    private MutableLiveData<Result> newsListLiveData;
    private MutableLiveData<Result> favoriteNewsListLiveData;

    public NewsViewModel(INewsRepositoryWithLiveData iNewsRepositoryWithLiveData) {
        this.newsRepositoryWithLiveData = iNewsRepositoryWithLiveData;
        this.page = 1; //per sapere a che pagina sono nello scaficare le notizie
    }

    /**
     * Returns the LiveData object associated with the
     * news list to the Fragment/Activity.
     * @return The LiveData object associated with the news list.
     */

    //questo newsListLiveData è quello che sarà osservato dal fragment o dall'activity
    //se newsListLiveData = null chiamo fetchnews altrimenti uso instanza che ho già
    public MutableLiveData<Result> getNews(String country, long lastUpdate) {
        Log.d(TAG, "NEWS LIST LIVE DATA : " + newsListLiveData );
        if (newsListLiveData == null) {
            fetchNews(country, lastUpdate); //restituisce mutable live data
        }
        Log.d(TAG, "NEWS LIST LIVE DATA : " + newsListLiveData );
        return newsListLiveData;
    }

    /**
     * Returns the LiveData object associated with the
     * list of favorite news to the Fragment/Activity.
     * @return The LiveData object associated with the list of favorite news.
     */
    public MutableLiveData<Result> getFavoriteNewsLiveData() {
        if (favoriteNewsListLiveData == null) {
            getFavoriteNews();
        }
        return favoriteNewsListLiveData;
    }

    /**
     * Updates the news status.
     * @param news The news to be updated.
     */
    public void updateNews(News news) {
        newsRepositoryWithLiveData.updateNews(news);
    }

    /**
     * It uses the Repository to download the news list
     * and to associate it with the LiveData object.
     */
    private void fetchNews(String country, long lastUpdate) {
        newsListLiveData = newsRepositoryWithLiveData.fetchNews(country, page, lastUpdate);
    }

    /**
     * It uses the Repository to get the list of favorite news
     * and to associate it with the LiveData object.
     */
    private void getFavoriteNews() {
        favoriteNewsListLiveData = newsRepositoryWithLiveData.getFavoriteNews();
    }

    /**
     * Removes the news from the list of favorite news.
     * @param news The news to be removed from the list of favorite news.
     */
    public void removeFromFavorite(News news) {
        newsRepositoryWithLiveData.updateNews(news);
    }

    /**
     * Clears the list of favorite news.
     */
    public void deleteAllFavoriteNews() {
        newsRepositoryWithLiveData.deleteFavoriteNews();
    }
}
