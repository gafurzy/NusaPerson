package id.ac.admb.gafurzy.nusaperson.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.admb.gafurzy.nusaperson.adapter.PersonAdapter
import id.ac.admb.gafurzy.nusaperson.databinding.FragmentHomeBinding
import id.ac.admb.gafurzy.nusaperson.model.PersonItem
import id.ac.admb.gafurzy.nusaperson.response.PersonResponse
import id.ac.admb.gafurzy.nusaperson.retrofit.ApiConfig
import id.ac.admb.gafurzy.nusaperson.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PersonAdapter

    private var personList = mutableListOf<PersonItem>()
    private var filteredList = mutableListOf<PersonItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        // 🔍 Listener Search
        binding.etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                filterData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        getData()

        return binding.root
    }

    private fun getData() {

        binding.progressBar.visibility = View.VISIBLE

        ApiConfig.getApiService()
            .getPersons(10, "id_ID", "male")
            .enqueue(object : Callback<PersonResponse> {

                override fun onResponse(
                    call: Call<PersonResponse>,
                    response: Response<PersonResponse>
                ) {

                    binding.progressBar.visibility = View.GONE

                    if (response.isSuccessful) {

                        val data = response.body()?.data ?: emptyList()

                        // ✅ simpan data asli
                        personList.clear()
                        personList.addAll(data)

                        // ✅ tampilkan data awal
                        filteredList.clear()
                        filteredList.addAll(personList)

                        adapter = PersonAdapter(filteredList) { person ->

                            val intent =
                                Intent(requireContext(), DetailActivity::class.java)

                            intent.putExtra("person", person)

                            startActivity(intent)
                        }

                        binding.recyclerView.adapter = adapter
                    }
                }

                override fun onFailure(
                    call: Call<PersonResponse>,
                    t: Throwable
                ) {

                    binding.progressBar.visibility = View.GONE
                }
            })
    }

    // 🔍 Fungsi Filter Search
    private fun filterData(keyword: String) {

        filteredList.clear()

        if (keyword.isEmpty()) {

            filteredList.addAll(personList)

        } else {

            val result = personList.filter {

                "${it.firstname} ${it.lastname}"
                    .contains(keyword, ignoreCase = true)
            }

            filteredList.addAll(result)
        }

        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}