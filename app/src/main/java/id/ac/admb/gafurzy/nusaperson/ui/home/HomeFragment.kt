package id.ac.admb.gafurzy.nusaperson.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

    // 🚻 FILTER GENDER
    private var selectedGender = "male"

    // 🔢 FILTER LIMIT
    private var selectedLimit = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        // 🔍 SEARCH LISTENER
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

        // 🎛 SETUP FILTER
        setupFilter()

        // 📡 LOAD DATA
        getData(selectedLimit, selectedGender)

        return binding.root
    }

    // 🎛 FILTER SPINNER
    private fun setupFilter() {

        // 🚻 GENDER
        val genderList = listOf(
            "male",
            "female"
        )

        val genderAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            genderList
        )

        binding.spGender.adapter = genderAdapter

        binding.spGender.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    selectedGender = genderList[position]

                    getData(
                        selectedLimit,
                        selectedGender
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        // 🔢 LIMIT
        val limitList = listOf(
            5,
            10,
            20,
            50
        )

        val limitAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            limitList
        )

        binding.spLimit.adapter = limitAdapter

        binding.spLimit.setSelection(1)

        binding.spLimit.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    selectedLimit = limitList[position]

                    getData(
                        selectedLimit,
                        selectedGender
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    // 📡 GET DATA API
    private fun getData(
        limit: Int,
        gender: String
    ) {

        binding.progressBar.visibility = View.VISIBLE

        ApiConfig.getApiService()
            .getPersons(
                limit,
                "id_ID",
                gender
            )
            .enqueue(object : Callback<PersonResponse> {

                override fun onResponse(
                    call: Call<PersonResponse>,
                    response: Response<PersonResponse>
                ) {

                    binding.progressBar.visibility = View.GONE

                    if (response.isSuccessful) {

                        val data =
                            response.body()?.data ?: emptyList()

                        // ✅ SIMPAN DATA ASLI
                        personList.clear()
                        personList.addAll(data)

                        // ✅ FILTERED LIST
                        filteredList.clear()
                        filteredList.addAll(personList)

                        adapter = PersonAdapter(filteredList) { person ->

                            val intent = Intent(
                                requireContext(),
                                DetailActivity::class.java
                            )

                            intent.putExtra(
                                "person",
                                person
                            )

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

    // 🔍 SEARCH FILTER
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