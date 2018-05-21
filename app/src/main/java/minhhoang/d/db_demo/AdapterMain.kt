package minhhoang.d.db_demo

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_student.view.*
import minhhoang.d.db_demo.model.Student

class AdapterMain(var students: List<Student>?, var clicked: IClickEvent) : RecyclerView.Adapter<AdapterMain.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return students?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.item?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                clicked.itemClicked(students?.get(position))
            }
        })
        holder.bindData(students?.get(position))
    }

    class ViewHolder(var item: View?) : RecyclerView.ViewHolder(item) {
        fun bindData(student: Student?){
            item?.tvName?.text = student?.name
            item?.tvAddress?.text = student?.address
            if (student?.sexual ?: true){
                item?.imSexual?.setImageResource(R.drawable.ic_male)
            } else{
                item?.imSexual?.setImageResource(R.drawable.ic_female)
            }
        }
    }

}