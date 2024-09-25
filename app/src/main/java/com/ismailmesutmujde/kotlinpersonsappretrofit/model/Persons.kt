package com.ismailmesutmujde.kotlinpersonsappretrofit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Persons (@SerializedName("kisi_id")
                    @Expose
                    var person_id : Int,
                    @SerializedName("kisi_ad")
                    @Expose
                    var person_name : String,
                    @SerializedName("kisi_tel")
                    @Expose
                    var person_phone : String) {
}