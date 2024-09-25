package com.ismailmesutmujde.kotlinpersonsappretrofit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PersonsResponse(@SerializedName("kisiler")
                           @Expose
                           var persons : List<Persons>,
                           @SerializedName("success")
                           @Expose
                           var success:Int) {

}