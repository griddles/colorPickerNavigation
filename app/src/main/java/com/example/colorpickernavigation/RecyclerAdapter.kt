package com.example.colorpickernavigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.colorpickernavigation.database.color.Color
import com.example.colorpickernavigation.database.color.ColorDao
import com.example.colorpickernavigation.model.SharedViewModel
import com.example.colorpickernavigation.ui.saved.SavedFragment

class RecyclerAdapter(var colorList: List<Color>, val sharedViewModel: SharedViewModel, val colorDao: ColorDao, val fragment: SavedFragment): RecyclerView.Adapter<RecyclerAdapter.viewHolder>()
{
    var offset = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.color_item, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int)
    {
        var valid = false
        while (!valid)
        {
            if (colorList[position + offset].uid == sharedViewModel.currentUid)
            {
                valid = true
            }
            else
            {
                offset++
            }
        }
        val colorString = colorList[position + offset].color
        holder.colorText.text = colorString
        val colorInt = android.graphics.Color.parseColor(colorString)
        holder.colorText.setBackgroundColor(colorInt)
        holder.colorText.setTextColor(sharedViewModel.textVisible(colorInt))

        holder.deleteButton.setBackgroundColor(colorInt)
        holder.deleteButton.setColorFilter(sharedViewModel.textVisible(colorInt))

        holder.colorText.setOnClickListener()
        {
            sharedViewModel.hexCode = colorString
            fragment.refresh()
        }

        holder.deleteButton.setOnClickListener()
        {
            colorDao.remove(colorList[position])
            fragment.refresh()
        }
    }

    override fun getItemCount(): Int
    {
        var size = 0
        for (color in colorList)
        {
            if (color.uid == sharedViewModel.currentUid)
            {
                size++
            }
        }
        return size
    }

    class viewHolder (itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val colorText: TextView = itemView.findViewById<TextView>(R.id.colorText)
        val deleteButton: ImageView = itemView.findViewById<ImageView>(R.id.deleteButton)
    }
}

