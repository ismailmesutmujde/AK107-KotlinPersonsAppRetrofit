package com.ismailmesutmujde.kotlinpersonsappretrofit.dao

import com.ismailmesutmujde.kotlinpersonsappretrofit.model.CRUDResponse
import com.ismailmesutmujde.kotlinpersonsappretrofit.model.PersonsResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface PersonsDaoInterface {

    @GET("kisiler/tum_kisiler.php")
    fun allPersons() : Call<PersonsResponse>

    @POST("kisiler/tum_kisiler_arama.php")
    @FormUrlEncoded
    fun searchPerson(@Field("kisi_ad") person_name:String) : Call<PersonsResponse>

    @POST("kisiler/delete_kisiler.php")
    @FormUrlEncoded
    fun deletePerson(@Field("kisi_id") person_id:Int) : Call<CRUDResponse>

    @POST("kisiler/insert_kisiler.php")
    @FormUrlEncoded
    fun insertPerson(@Field("kisi_ad") person_name:String,
                     @Field("kisi_tel") person_phone:String) : Call<CRUDResponse>

    @POST("kisiler/update_kisiler.php")
    @FormUrlEncoded
    fun updatePerson(@Field("kisi_id") person_id:Int,
                     @Field("kisi_ad") person_name:String,
                     @Field("kisi_tel") person_phone:String) : Call<CRUDResponse>

}