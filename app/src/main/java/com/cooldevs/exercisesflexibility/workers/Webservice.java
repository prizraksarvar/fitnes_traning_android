package com.cooldevs.exercisesflexibility.workers;

import com.cooldevs.exercisesflexibility.entities.UniversalItem;
import com.cooldevs.exercisesflexibility.repositories.MobileappResponseAdapter;
import com.cooldevs.exercisesflexibility.repositories.ResponseAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Webservice {
    /*
     * Список универсальных элементов
     */
    @GET("/api/api_mobileapp/{mobileappId}/api_universal_item_v2/parent/{parentId}")
    @Headers({
            "Authorization: {token}",
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"
    })
    Call<ResponseAdapter<List<UniversalItem>>> getUniversalItems(
            @Header("token") String token,
            @Path("mobileappId") int mobileappId,
            @Path("parentId") int parentId,
            @Query("updatesOnly") int updatesOnly,
            @Query("lang") String lang
    );

    /**
     * Получить универсальный элемент
     */
    @GET("/api/api_mobileapp/{mobileappId}/api_universal_item_v2/{id}")
    @Headers({
            "Authorization: {token}",
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"
    })
    Call<ResponseAdapter<UniversalItem>> getUniversalItem(
            @Header("token") String token,
            @Path("mobileappId") int mobileappId,
            @Path("id") int id,
            @Query("updatesOnly") int updatesOnly,
            @Query("lang") String lang
    );

    /**
     * Получить информацию о мобильном приложении и настройки
     */
    @GET("/api/api_mobileapp/{id}")
    @Headers({
            "Authorization: {token}",
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"
    })
    Call<MobileappResponseAdapter> getMobileapp(
            @Header("token") String token,
            @Path("id") int id,
            @Query("updatesOnly") int updatesOnly,
            @Query("lang") String lang
    );
}
