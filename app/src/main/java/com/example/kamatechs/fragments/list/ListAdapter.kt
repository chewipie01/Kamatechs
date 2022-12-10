package com.example.kamatechs.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kamatechs.R
import com.example.kamatechs.database.Storage

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var storageList = emptyList<Storage>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_row,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return storageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = storageList[position]

        holder.itemView.findViewById<TextView>(R.id.temperature_txt).text = currentItem.Temperature.toString()
        holder.itemView.findViewById<TextView>(R.id.humidity_txt).text = currentItem.Humidity.toString()

        holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout).setOnClickListener {
            // warning : Sometime, you need to 'rebuild project' (on the toolbar, click Build > Rebuild Project) to be able to see the 'actionListFragmentToUpdateFragment()' suggestion.
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem) // <- Pass object to Update Fragment
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(storage: List<Storage>) {
        this.storageList = storage
        notifyDataSetChanged()
    }
}