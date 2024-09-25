package com.ismailmesutmujde.kotlinpersonsappretrofit.service

import com.ismailmesutmujde.kotlinpersonsappretrofit.dao.PersonsDaoInterface
import com.ismailmesutmujde.kotlinpersonsappretrofit.retrofit.RetrofitClient

class ApiUtils {
    companion object {
        val BASE_URL = "http://kasimadalan.pe.hu/"
        fun getPersonsDaoInterface() : PersonsDaoInterface {
            return  RetrofitClient.getClient(BASE_URL).create(PersonsDaoInterface::class.java)
        }

    }
}