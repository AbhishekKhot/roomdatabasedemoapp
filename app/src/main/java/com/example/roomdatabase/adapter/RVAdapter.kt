package com.example.roomdatabase.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.ListItemBinding
import com.example.roomdatabase.db.Student

class RVAdapter(private val subscribersList:List<Student>, private val clickListener: (Student) -> Unit): RecyclerView.Adapter<RVAdapter.RVViewHolder>() {

    class RVViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subscriber: Student, clickListener: (Student) -> Unit) {
            binding.nameTextView.text = subscriber.name
            binding.emailTextView.text = subscriber.email
            binding.listItemLayout.setOnClickListener {
                clickListener(subscriber)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.rv_item, parent, false)
        return RVViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.bind(subscribersList[position], clickListener)
    }
}