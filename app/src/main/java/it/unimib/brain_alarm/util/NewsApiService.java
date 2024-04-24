package it.unimib.brain_alarm.util;

import static it.unimib.brain_alarm.util.Constants.TOP_HEADLINES_COUNTRY_PARAMETER;
import static it.unimib.brain_alarm.util.Constants.TOP_HEADLINES_ENDPOINT;
import static it.unimib.brain_alarm.util.Constants.TOP_HEADLINES_PAGE_SIZE_PARAMETER;

import it.unimib.brain_alarm.News.NewsApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NewsApiService {

    @GET(TOP_HEADLINES_ENDPOINT)
    Call<NewsApiResponse> getNews(
            //qui strutturo la chiamata
            @Query(TOP_HEADLINES_COUNTRY_PARAMETER) String country,  //parametro da passare
            @Query(TOP_HEADLINES_PAGE_SIZE_PARAMETER) int pageSize, //dimensione
            @Header("Authorization") String apiKey);
}
