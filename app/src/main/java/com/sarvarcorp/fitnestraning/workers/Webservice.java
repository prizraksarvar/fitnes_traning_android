package com.sarvarcorp.fitnestraning.workers;

import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.repositories.MobileappResponseAdapter;
import com.sarvarcorp.fitnestraning.repositories.ResponseAdapter;

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
     * @param token
     * @param parentId
     * @return
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
            @Query("updatesOnly") int updatesOnly
    );

    /**
     * Получить универсальный элемент
     * @param token
     * @param id
     * @param updatesOnly
     * @return
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
            @Query("updatesOnly") int updatesOnly
    );

    /**
     * Получить информацию о мобильном приложении и настройки
     * @param token
     * @param id
     * @param updatesOnly
     * @return
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
            @Query("updatesOnly") int updatesOnly
    );
}
