package id.ac.admb.gafurzy.nusaperson.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

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

                        // ✅ FIX: click listener dipisah biar rapi
                        val adapter = PersonAdapter(data) { person: PersonItem ->

                            val intent = Intent(requireContext(), DetailActivity::class.java)
                            intent.putExtra("person", person)
                            startActivity(intent)
                        }

                        binding.recyclerView.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<PersonResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}