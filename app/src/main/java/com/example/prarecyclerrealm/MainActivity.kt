package com.example.prarecyclerrealm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import java.util.*

class MainActivity : AppCompatActivity() {
    private val realm: Realm by lazy {
        //Realmインスタンスの取得
        Realm.getDefaultInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskList = readAll()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        // タスクリストが空だったときにダミーデータを生成する
        if (taskList.isEmpty()) {
            createDummyData()
        }

        val adapter = TaskAdapter(this, taskList,object : TaskAdapter.OnItemClickListener{
            override fun onItemCLick(item:Task) {
                Toast.makeText(applicationContext, item.content + "を削除しました。",Toast.LENGTH_SHORT).show()
               // delete(item.id)
                update(item.id,"あいう")
            }
        },true)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()

    }

    fun createDummyData(){
        for (i in 0..10){
            create(R.drawable.ic_launcher_background,"やること$i")
        }
    }

    fun create(imageid:Int,content:String){
        realm.executeTransaction{
            val task = it.createObject(Task::class.java,UUID.randomUUID().toString())
            task.imageId = imageid
            task.content = content
        }
    }

    fun readAll():RealmResults<Task>{
        return realm.where(Task::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    }

    fun update(id:String, content: String){
        realm.executeTransaction {
            val task = realm.where(Task::class.java).equalTo("id",id).findFirst()
                ?: return@executeTransaction
            task.content = content
        }
    }

    fun update(task: Task,content: String){
        realm.executeTransaction {
            task.content = content
        }
    }

    fun delete(id: String){
        realm.executeTransaction {
            val task = realm.where(Task::class.java).equalTo("id",id).findFirst()
                ?: return@executeTransaction
            task.deleteFromRealm()
        }
    }

    fun delete(task: Task){
        realm.executeTransaction {
            task.deleteFromRealm()
        }
    }

    fun deleteAll(){
        realm.executeTransaction {
            realm.deleteAll()
        }
    }
}























