package com.example.remindme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class UserAdaptor(private val usersList:ArrayList<Users>) :RecyclerView.Adapter<UserAdaptor.UserViewHolder>() {

    var onItemClick : ((Users) -> Unit)? = null

    class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tveventname:TextView = itemView.findViewById(R.id.firstname)
        val tveventdesc:TextView = itemView.findViewById(R.id.lastdescription)
        val tveventloc:TextView = itemView.findViewById(R.id.lastlocation)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return  UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val pos = usersList[position]

        holder.tveventname.text = usersList[position].EventName
        holder.tveventdesc.text = usersList[position].Description
        holder.tveventloc.text = usersList[position].Location

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(pos)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}