package id.ac.admb.gafurzy.nusaperson.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.ac.admb.gafurzy.nusaperson.R
import id.ac.admb.gafurzy.nusaperson.databinding.ActivityDetailBinding
import id.ac.admb.gafurzy.nusaperson.model.PersonItem

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔙 Tombol Back
        binding.btnBack.setOnClickListener {
            finish()
        }

        // 📦 Ambil data person
        val person = intent.getSerializableExtra("person") as PersonItem

        // 📄 Tampilkan data
        showData(person)
    }

    private fun showData(person: PersonItem) {

        // 👤 Nama Lengkap
        binding.tvName.text =
            "${person.firstname} ${person.lastname}"

        // 📧 Email
        binding.tvEmail.text =
            person.email

        // 📱 Phone
        binding.tvPhone.text =
            person.phone

        // 🎂 Birthday
        binding.tvBirthday.text =
            person.birthday

        // 🚻 Gender
        binding.tvGender.text =
            person.gender.replaceFirstChar {
                it.uppercase()
            }

        // 🌐 Website
        binding.tvWebsite.text =
            person.website

        // 📍 Address
        binding.tvAddress.text =
            "${person.address.street}, " +
                    "${person.address.city}, " +
                    "${person.address.country} " +
                    "(${person.address.zipcode})"

        // 🖼 FOTO PROFILE
        Glide.with(this)
            .load(person.image)
            .placeholder(R.drawable.ic_profile_placeholder)
            .error(R.drawable.ic_profile_placeholder)
            .circleCrop()
            .into(binding.imgProfile)
    }
}