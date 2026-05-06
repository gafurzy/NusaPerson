package id.ac.admb.gafurzy.nusaperson.model

import java.io.Serializable

data class Address(
    val id: Int,
    val street: String,
    val streetName: String,
    val buildingNumber: String,
    val city: String,
    val zipcode: String,
    val country: String,
    val country_code: String,
    val latitude: Double,
    val longitude: Double
) : Serializable