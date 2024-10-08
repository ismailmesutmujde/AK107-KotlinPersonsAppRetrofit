package com.ismailmesutmujde.kotlinpersonsappretrofit.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ismailmesutmujde.kotlinpersonsappretrofit.R
import com.ismailmesutmujde.kotlinpersonsappretrofit.adapter.PersonsRecyclerViewAdapter
import com.ismailmesutmujde.kotlinpersonsappretrofit.dao.PersonsDaoInterface
import com.ismailmesutmujde.kotlinpersonsappretrofit.databinding.ActivityMainScreenBinding
import com.ismailmesutmujde.kotlinpersonsappretrofit.model.CRUDResponse
import com.ismailmesutmujde.kotlinpersonsappretrofit.model.Persons
import com.ismailmesutmujde.kotlinpersonsappretrofit.model.PersonsResponse
import com.ismailmesutmujde.kotlinpersonsappretrofit.service.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainScreenActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var bindingMainScreen : ActivityMainScreenBinding

    private lateinit var personsList : ArrayList<Persons>
    private lateinit var adapterPersons : PersonsRecyclerViewAdapter
    private lateinit var pdi : PersonsDaoInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainScreen = ActivityMainScreenBinding.inflate(layoutInflater)
        val view = bindingMainScreen.root
        setContentView(view)

        bindingMainScreen.toolbar.title = "Persons Application"
        setSupportActionBar(bindingMainScreen.toolbar)

        bindingMainScreen.recyclerView.setHasFixedSize(true)
        bindingMainScreen.recyclerView.layoutManager = LinearLayoutManager(this)

        pdi = ApiUtils.getPersonsDaoInterface()

        /*
        personsList = ArrayList()
        val p1 = Persons(1,"Ahmet", "888888")
        val p2 = Persons(2,"Zeynep", "666666")
        val p3 = Persons(3,"Ece", "333333")

        personsList.add(p1)
        personsList.add(p2)
        personsList.add(p3)

        adapterPersons = PersonsRecyclerViewAdapter(this, personsList)
        bindingMainScreen.recyclerView.adapter = adapterPersons
        */

        allPersons()

        bindingMainScreen.fab.setOnClickListener {
            showAlert()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    fun showAlert() {
        val design = LayoutInflater.from(this).inflate(R.layout.alert_design, null)
        val editTextPersonName = design.findViewById(R.id.editTextPersonName) as EditText
        val editTextPersonPhone = design.findViewById(R.id.editTextPersonPhone) as EditText

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Add Person")
        alertDialog.setView(design)
        alertDialog.setPositiveButton("Add") { dialogInterface, i ->
            val person_name = editTextPersonName.text.toString().trim()
            val person_phone = editTextPersonPhone.text.toString().trim()

            pdi.insertPerson(person_name, person_phone).enqueue(object : Callback<CRUDResponse>{
                override fun onResponse(
                    call: Call<CRUDResponse>,
                    response: Response<CRUDResponse>
                ) {
                    allPersons()
                }

                override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {

                }

            })

            Toast.makeText(applicationContext, "${person_name} - ${person_phone}", Toast.LENGTH_SHORT).show()

        }
        alertDialog.setNegativeButton("Cancel") { dialogInterface, i ->


        }
        alertDialog.show()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchPerson(query)
        Log.e("Sent Search", query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        searchPerson(newText)
        Log.e("As Letters Enter", newText)
        return true
    }

    fun allPersons() {
        pdi.allPersons().enqueue(object : Callback<PersonsResponse>{
            override fun onResponse(
                call: Call<PersonsResponse>,
                response: Response<PersonsResponse>
            ) {

                if (response != null) {
                    val list = response.body()!!.persons
                    adapterPersons = PersonsRecyclerViewAdapter(this@MainScreenActivity, list, pdi)
                    bindingMainScreen.recyclerView.adapter = adapterPersons
                }

            }

            override fun onFailure(call: Call<PersonsResponse>, t: Throwable) {

            }

        })
    }

    fun searchPerson(searchingWord:String) {
        pdi.searchPerson(searchingWord).enqueue(object : Callback<PersonsResponse>{
            override fun onResponse(
                call: Call<PersonsResponse>,
                response: Response<PersonsResponse>
            ) {

                if (response != null) {
                    val list = response.body()!!.persons
                    adapterPersons = PersonsRecyclerViewAdapter(this@MainScreenActivity, list, pdi)
                    bindingMainScreen.recyclerView.adapter = adapterPersons
                }

            }

            override fun onFailure(call: Call<PersonsResponse>, t: Throwable) {

            }

        })
    }
}