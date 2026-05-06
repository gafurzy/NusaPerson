package id.ac.admb.gafurzy.nusaperson.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.admb.gafurzy.nusaperson.databinding.ItemPersonBinding
import id.ac.admb.gafurzy.nusaperson.model.PersonItem

class PersonAdapter(
    private val list: List<PersonItem>,
    private val onClick: (PersonItem) -> Unit   // ✅ TAMBAHAN INI
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPersonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        // data text
        holder.binding.tvName.text = "${data.firstname} ${data.lastname}"
        holder.binding.tvEmail.text = data.email

        // image
        Glide.with(holder.itemView.context)
            .load(data.image)
            .into(holder.binding.imgPerson)

        // ✅ CLICK LISTENER KE DETAIL
        holder.itemView.setOnClickListener {
            onClick(data)
        }
    }
}