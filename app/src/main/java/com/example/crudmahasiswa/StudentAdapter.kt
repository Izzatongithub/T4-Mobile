package com.example.crudmahasiswa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crudmahasiswa.database.entity.StudentEntity

class StudentAdapter(
    private val onEdit: (StudentEntity) -> Unit,
    private val onDelete: (StudentEntity) -> Unit,
    private val onClick: (StudentEntity) -> Unit
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    private var list = listOf<StudentEntity>()

    fun setData(newList: List<StudentEntity>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tvNama)
        val tvNim = view.findViewById<TextView>(R.id.tvNim)
        val btnEdit = view.findViewById<Button>(R.id.btnEdit)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mhs, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = list[position]

        holder.tvNama.text = student.namaMhs
        holder.tvNim.text = student.nim

        holder.itemView.setOnClickListener {
            onClick(student)
        }

        holder.btnEdit.setOnClickListener {
            onEdit(student)
        }

        holder.btnDelete.setOnClickListener {
            onDelete(student)
        }
    }

    override fun getItemCount(): Int = list.size
}