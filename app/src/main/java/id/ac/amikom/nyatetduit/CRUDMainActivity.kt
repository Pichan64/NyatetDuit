package id.ac.amikom.nyatetduit

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CRUDMainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var edDate: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    //var edDate: EditText? = null
    var datePickerDialog: DatePickerDialog? = null
    var dateFormatter: SimpleDateFormat? = null

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: CRUDAdapter? = null
    private var std:CRUDModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudmain)
        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener{ addStudent() }
        btnView.setOnClickListener{ getStudent() }
        btnUpdate.setOnClickListener{ updateStudent()}

        //calender

        edDate = findViewById<View>(R.id.edDate) as EditText
        dateFormatter = SimpleDateFormat("dd-MM-yyyy")
        edDate!!.setOnClickListener { showDateDialog() }
//        val c = Calendar.getInstance()
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH)
//        val day = c.get(Calendar.DAY_OF_MONTH)

        //
//        edDate.setOnClickListener {
//            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener(view, mYear, MMonth, mDay ->
//            edDate.setText(""+ mDay + "/" + mMonth + "/" + mYear))
//        }, year, month, day)

        //


        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            edName.setText(it.name)
            edEmail.setText(it.email)
            edDate.setText(it.date)
            std = it
        }

        adapter?.setOnClickDeleteItem {
            deleteStudent(it.id)
        }
    }

    private fun showDateDialog() {
        val calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(this, { view, year, month, dayOfMonth ->
            val newDate = Calendar.getInstance()
            newDate[year, month] = dayOfMonth
            edDate!!.setText(dateFormatter!!.format(newDate.time))
        }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
        datePickerDialog!!.show()
    }

    private fun getStudent(){
        val stdList = sqLiteHelper.getAllStudent()
        Log.e("pppp", "${stdList.size}")

        adapter?.addItems(stdList)
    }
    private fun addStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val date = edDate.text.toString()

        if(name.isEmpty() || email.isEmpty() ){
            Toast.makeText(this, "Please enter requried field", Toast.LENGTH_SHORT).show()
        } else {
            val std = CRUDModel(name =name, email = email, date = date)
            val status = sqLiteHelper.insertStudent(std)
            //check insert success or not success
            if(status > -1){
                Toast.makeText(this, "Student Added....", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudent()
            } else {
                Toast.makeText(this, "Record not saved....", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun updateStudent(){
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val date = edDate.text.toString()

        if(name ==std?.name && email == std?.email){
            Toast.makeText(this, "Record not Changed...", Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null) return

        val std = CRUDModel(id = std!!.id, name = name, email = email, date = date)
        val status = sqLiteHelper.updateStudent(std)
        if(status > -1){
            clearEditText()
            getStudent()
        }else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun deleteStudent(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog, _ ->
            sqLiteHelper.deleteStudentById(id)
            getStudent()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){dialog, _ ->

            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText(){
        edName.setText("")
        edEmail.setText("")
        edDate.setText("")
        edName.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CRUDAdapter()
        recyclerView.adapter = adapter
    }


    private fun initView(){
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        edDate = findViewById(R.id.edDate)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}