package id.ac.admb.gafurzy.nusaperson.retrofit

import id.ac.admb.gafurzy.nusaperson.response.PersonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("person")
    fun getPersons(
        @Query("_quantity") quantity: Int = 10,
        @Query("_locale") locale: String = "id_ID",
        @Query("_gender") gender: String = "male"
    ): Call<PersonResponse>
}