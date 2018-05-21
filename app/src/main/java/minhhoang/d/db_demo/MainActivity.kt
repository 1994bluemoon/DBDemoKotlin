package minhhoang.d.db_demo

import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import minhhoang.d.db_demo.dao.DBHelper
import minhhoang.d.db_demo.dialog.DialogStudent
import minhhoang.d.db_demo.model.Student
import java.util.*

class MainActivity : AppCompatActivity(), IClickEvent {


    var dialog: DialogStudent? = null
    var dbHelper: DBHelper? = null
    var students: ArrayList<Student>? = null
    var adapterMain: AdapterMain? = null
    var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarMain)
        supportActionBar?.title = "SQLite"
        dbHelper = DBHelper(this)
        students = ArrayList<Student>()

        adapterMain = AdapterMain(students, this)
        rvMain.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvMain.adapter = adapterMain

        getStudents()
    }

    override fun itemClicked(student: Student?) {
        isEditing = true
        dialog = DialogStudent(this, this, student)
        dialog?.setTitle("Edit Student")
        dialog?.show()
    }

    override fun addEvent(student: Student?) {
        if (!isEditing){
            dbHelper?.runQuery("INSERT INTO $TABLE_NAME VALUES(null, '${student?.name}', '${student?.address}', '${if(student?.sexual == true) "true" else "false"}')")
        } else {
            dbHelper?.runQuery("UPDATE $TABLE_NAME SET $COL_STUDENT_NAME = '${student?.name}', $COL_STUDENT_ADDRESS = '${student?.address}', $COL_STUDENT_SEXSUAL = '${if (student?.sexual == true) "true" else "false"}' WHERE $COL_ID == ${student?.id}")
        }
        isEditing = false
        getStudents()
        dialog?.cancel()

    }

    override fun delete(student: Student?) {
        dbHelper?.runQuery("DELETE FROM $TABLE_NAME WHERE $COL_ID == ${student?.id}")
        dialog?.cancel()
        isEditing = false
        getStudents()
    }

    override fun cancleDialog() {
        dialog?.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu_addnew, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.action_add_new -> {
                dialog = DialogStudent(this, this, null)
                dialog?.setTitle("Add Student")
                dialog?.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getStudents(){
        val cursor: Cursor? = dbHelper?.getData("SELECT * FROM $TABLE_NAME")
        if (cursor != null){
            students?.clear()
            while (cursor.moveToNext()){
                val student = Student(cursor.getInt(cursor.getColumnIndex(COL_ID)), cursor.getString(cursor.getColumnIndex(COL_STUDENT_NAME)), cursor.getString(cursor.getColumnIndex(COL_STUDENT_ADDRESS)), cursor.getString(cursor.getColumnIndex(COL_STUDENT_SEXSUAL)).equals("true"))
                students?.add(student)
            }
            adapterMain?.notifyDataSetChanged()
            cursor.close()
        } else {
            Toast.makeText(this, "data not found", Toast.LENGTH_SHORT).show()
        }

    }
}
