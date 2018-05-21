package minhhoang.d.db_demo

import minhhoang.d.db_demo.model.Student

interface IClickEvent{
    fun addEvent(student: Student?)
    fun delete(student: Student?)
    fun itemClicked(student: Student?)
    fun cancleDialog()
}