package it.unimib.brain_alarm.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.brain_alarm.News.News;


@Dao
public interface NewsDao {
    //interfaccia con una serie di metodi che posso poi utilizzare chiamandole

    //getAll restituisce tutte le notizie poi riordinate in base alla data
        @Query("SELECT * FROM news ORDER BY published_at DESC")
    List<News> getAll();

    @Query("SELECT * FROM news WHERE id = :id")
    News getNews(long id);

    //restituisce notizie in base a se il parametro favorite è true o false
    @Query("SELECT * FROM news WHERE is_favorite = 1 ORDER BY published_at DESC")
    List<News> getFavoriteNews();

    //se c'è conflitto per la chiave primaria sostituisco notizia vecchia con quella nuova
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertNewsList(List<News> newsList);

    @Insert
    void insertAll(News... news);

    @Delete
    void delete(News news);

    //cancella tutte le notizie
    @Query("DELETE FROM news")
    void deleteAll();

    //cancella tutte le notizie tranne le preferite
    @Query("DELETE FROM news WHERE is_favorite = 0")
    void deleteNotFavoriteNews();

    @Delete
    void deleteAllWithoutQuery(News... news);

    //aggiornamneto notizie
    @Update
    int updateSingleFavoriteNews(News news);

    @Update
    int updateListFavoriteNews(List<News> news);
}
