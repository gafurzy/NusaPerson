package id.ac.admb.gafurzy.nusaperson.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.admb.gafurzy.nusaperson.R
import id.ac.admb.gafurzy.nusaperson.databinding.ItemPersonBinding
import id.ac.admb.gafurzy.nusaperson.model.PersonItem

class PersonAdapter(
    private val list: List<PersonItem>,
    private val onClick: (PersonItem) -> Unit
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemPersonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val data = list[position]

        // 👤 Nama
        holder.binding.tvName.text =
            "${data.firstname} ${data.lastname}"

        // 📧 Email
        holder.binding.tvEmail.text =
            data.email

        // 🖼 Foto
        Glide.with(holder.itemView.context)
            .load(data.image)
            .placeholder(R.drawable.ic_profile_placeholder)
            .error(R.drawable.ic_profile_placeholder)
            .circleCrop()
            .into(holder.binding.imgProfile)

        // 👆 Click ke detail
        holder.itemView.setOnClickListener {
            onClick(data)
        }
    }
}