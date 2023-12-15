package com.androeddy.satellites.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androeddy.satellites.databinding.RowSatelliteBinding
import com.androeddy.satellites.ui.main.models.SatelliteState
import com.androeddy.satellites.ui.main.models.SatelliteUIModel
import com.androeddy.satellites.util.Extensions.orFalse

class SatelliteListAdapter(private val satellites: ArrayList<SatelliteUIModel>) : RecyclerView.Adapter<SatelliteListAdapter.SatelliteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteHolder {
        val itemBinding = RowSatelliteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SatelliteHolder(itemBinding)
    }

    var itemClickListener: ItemClickListener? = null


    override fun getItemCount(): Int {
        return satellites.size
    }

    override fun onBindViewHolder(holder: SatelliteListAdapter.SatelliteHolder, position: Int) {
        if (satellites.isEmpty()) {
            return
        }

        val currentItem = satellites[position]
        with(holder.binding) {
            apimStatus.isEnabled = currentItem.active.orFalse()
            tvName.text = currentItem.name
            tvStatus.text = SatelliteState.getFromBoolean(currentItem.active.orFalse()).strValue
        }

        holder.binding.root.setOnClickListener {
            itemClickListener?.onClick(it, position, currentItem)
        }
    }

    inner class SatelliteHolder(val binding: RowSatelliteBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int, item: SatelliteUIModel)
    }
}


