package id.ac.amikom.nyatetduit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CRUDAdapter : RecyclerView.Adapter<CRUDAdapter.StudentViewHolder>() {
    private var stdList: ArrayList<CRUDModel> = ArrayList()
    private var onClickItem: ((CRUDModel) -> Unit)? = null
    private var onClickDeleteItem: ((CRUDModel) -> Unit)? = null

    fun addItems(items: ArrayList<CRUDModel>){
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (CRUDModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (CRUDModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = StudentViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener {onClickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener{onClickDeleteItem?.invoke(std)}

    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class StudentViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        private  var id = view.findViewById<TextView>(R.id.tvId)
        private  var name = view.findViewById<TextView>(R.id.tvName)
        private  var email = view.findViewById<TextView>(R.id.tvEmail)
        private var date = view.findViewById<TextView>(R.id.tvDate)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std: CRUDModel){
            id.text = std.id.toString()
            name.text = std.name
            email.text = std.email
            date.text = std.date
        }

    }
}