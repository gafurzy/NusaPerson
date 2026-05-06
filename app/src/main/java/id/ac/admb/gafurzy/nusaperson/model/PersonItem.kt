package id.ac.admb.gafurzy.nusaperson.model

import java.io.Serializable

data class PersonItem(
    val firstname: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val birthday: String,
    val gender: String,
    val website: String,
    val image: String,
    val address: Address   // ✅ pakai Address buatan kita
) : Serializable