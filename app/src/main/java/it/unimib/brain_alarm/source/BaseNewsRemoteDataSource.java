package it.unimib.brain_alarm.source;

//classe per chiamata a backend
public abstract class BaseNewsRemoteDataSource {
    protected NewsCallback newsCallback;

    //inizializzare Callback
    public void setNewsCallback(NewsCallback newsCallback) {
        this.newsCallback = newsCallback;
    }

    //per ottenere lista news in base alla nazione
    public abstract void getNews(String country);
}
