package com.example.crudmahasiswa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import database.entity.StudentEntity

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var list = listOf<StudentEntity>()

    fun setData(newList: List<StudentEntity>) {
        list = newList
        notifyDataSetChanged()
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvText: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)

        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = list[position]
        holder.tvText.text = "${student.namaMhs} - ${student.nim}"

        holder.itemView.setOnClickListener {
            val fragment = FormFragment().apply {
                arguments = android.os.Bundle().apply {
                    putInt("studentId", student.idMhs)
                }
            }
            (holder.itemView.context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = list.size
}