package com.example.prarecyclerrealm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter (
    private val context: Context,
    private var taskList: OrderedRealmCollection<Task>?,
    private var listener: TaskAdapter.OnItemClickListener,
    private val autoUpdate: Boolean
):
//　　　　　　　　　　　　　　　　　　　　　　↓これの中の ↓これを取り出している（TaskViewHolderだけでは、エラーが起きる）
    RealmRecyclerViewAdapter<Task, TaskAdapter.TaskViewHolder>(taskList, autoUpdate) {

    override fun getItemCount(): Int = taskList?.size?:0

    override fun onBindViewHolder(holder:TaskViewHolder, position:Int){
        val task:Task = taskList?.get(position)?:return

        holder.container.setOnClickListener {
            listener.onItemCLick(task)
        }

        holder.imageView.setImageResource(task.imageId)
        holder.contentTextView.text = task.content
        holder.dateTextView.text =  SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(task.createdAt)
    }

    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_practice_data_cell,viewgroup,false)
        return TaskViewHolder(v)
    }

//                                      　　↓これの中の ↓これを取り出している
    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view){

//                                ↓これの中の ↓これを取り出している(下２つも同じ仕組み)
//        val imageView:ImageView = view.imageView
//        val contentTextView:TextView = view.contentTextView
//        val dateTextView:TextView = view.dataTextView
//        val dataTextView:TextView = view.dataTextVIew

        val container:LinearLayout=view.findViewById(R.id.container)
        val imageView:ImageView=view.findViewById(R.id.imageView)
        val contentTextView:TextView=view.findViewById(R.id.contentTextView)
        val dateTextView:TextView=view.findViewById(R.id.dateTextView)
    }

    interface OnItemClickListener{
        fun onItemCLick(item:Task)
    }
}