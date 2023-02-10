package com.example.colorpickernavigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.colorpickernavigation.database.color.Color
import com.example.colorpickernavigation.database.color.ColorDao
import com.example.colorpickernavigation.model.SharedViewModel

class RecyclerAdapter(var colorList: List<Color>, val sharedViewModel: SharedViewModel, val colorDao: ColorDao): RecyclerView.Adapter<RecyclerAdapter.viewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.color_item, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int)
    {
        val colorString = colorList[position].color
        holder.colorText.text = colorString
        val colorInt = android.graphics.Color.parseColor(colorString)
        holder.colorText.setBackgroundColor(colorInt)
        holder.colorText.setTextColor(sharedViewModel.textVisible(colorInt))

        holder.deleteButton.setBackgroundColor(colorInt)
        holder.deleteButton.setColorFilter(sharedViewModel.textVisible(colorInt))

        holder.colorText.setOnClickListener()
        {
            sharedViewModel.setHex(colorString)
            holder.addButt.setBackgroundColor(colorInt)
            holder.addButt.setTextColor(sharedViewModel.textVisible(colorInt))
        }

        holder.deleteButton.setOnClickListener()
        {
            colorDao.remove(colorList[position])
        }
    }

    override fun getItemCount(): Int
    {
        return colorList.size
    }

    class viewHolder (itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val colorText: TextView = itemView.findViewById<TextView>(R.id.colorText)
        val deleteButton: ImageView = itemView.findViewById<ImageView>(R.id.deleteButton)
        var addButt: Button = itemView.findViewById<Button>(R.id.addButt)
    }
}

