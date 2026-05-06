package id.ac.admb.gafurzy.nusaperson.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import id.ac.admb.gafurzy.nusaperson.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}