package minhhoang.d.db_demo.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.dialog.*
import minhhoang.d.db_demo.IClickEvent
import minhhoang.d.db_demo.R
import minhhoang.d.db_demo.model.Student

class DialogStudent(mContext: Context, event: IClickEvent, student: Student?) : Dialog(mContext) {
    var sexsual: Boolean? = true
    init {
        setContentView(R.layout.dialog)
        setCanceledOnTouchOutside(false)
        rdbBoy_Dialog.isChecked = true
        imgSexsual.setImageResource(R.drawable.ic_male)

        if (student != null){
            tvDelete.visibility = View.VISIBLE
            txtYes_Dialog.text = "Edit"
            edtAddress_Dialog.setText(student.address)
            edtName_Dialog.setText(student.name)
            if (student.sexual == true){
                rdbBoy_Dialog.isChecked = true
                imgSexsual.setImageResource(R.drawable.ic_male)
            } else{
                rdbGirl_Dialog.isChecked = true
                imgSexsual.setImageResource(R.drawable.ic_female)
            }
        }

        rdgGender_Dialog.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId){
                    R.id.rdbBoy_Dialog -> {
                        imgSexsual.setImageResource(R.drawable.ic_male)
                        sexsual = true
                    }
                    R.id.rdbGirl_Dialog -> {
                        imgSexsual.setImageResource(R.drawable.ic_female)
                        sexsual = false
                    }
                }

            }
        })

        txtYes_Dialog.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val std = Student(student?.id, edtName_Dialog.text.toString(), edtAddress_Dialog.text.toString(), sexsual)
                if  (!std.address.isNullOrEmpty() && !std.name.isNullOrEmpty()){
                    event.addEvent(std)
                }
            }
        })

        tvDelete.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                event.delete(student)
            }
        })

        txtCancel_Dialog.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                event.cancleDialog()
            }
        })
    }
}