package evg.advancedrec.data.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import evg.advancedrec.R
import evg.advancedrec.data.model.CaseRecords

class ToDoListAdapter(
    private var list: List<CaseRecords>,
    val listener: (CaseRecords) -> Unit
) : RecyclerView.Adapter<ToDoListAdapter.MyViewHolder>(), Filterable {
    private var textHighLight = ""
    private var filterData: List<CaseRecords> = list

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNum: TextView = itemView.findViewById(R.id.tv_num)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvDate:TextView = itemView.findViewById(R.id.tv_date)
        val imgCompleted: ImageView = itemView.findViewById(R.id.img_completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_for_rv_to_do_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (textHighLight.isNotEmpty()) {
            val stringName = filterData[position].name
            val index = stringName.indexOf(textHighLight, 0, true)
            val spannedName = SpannableString(stringName)
            if (index > -1) {
                spannedName.setSpan(
                    ForegroundColorSpan(Color.BLUE),
                    index, index + textHighLight.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                holder.tvName.text = spannedName
            }
        } else {
            holder.tvName.text = filterData[position].name
        }
        if (filterData[position].completed) {
            holder.imgCompleted.visibility = View.VISIBLE
        } else {
            holder.imgCompleted.visibility = View.INVISIBLE
        }
        val num = (position + 1).toString()
        holder.tvNum.text = num
        holder.tvDate.text = filterData[position].date

        val item = filterData[position]
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount() = filterData.size

    fun setNewDataAdapter(listNewData: List<CaseRecords>) {
        list = listNewData
        filterData = listNewData
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(findText: CharSequence): FilterResults {
                val result = FilterResults()
                if (findText.isBlank()) {
                    result.values = list
                    result.count = list.size
                    textHighLight = ""
                } else {
                    var filteredData: MutableList<CaseRecords> = ArrayList()
                    filteredData = list.filter {
                        it.name.startsWith(
                            findText.toString(),
                            true
                        ) /*|| it.Name.contains(findText.toString(), true)*/
                    }.toMutableList()

                    result.values = filteredData
                    result.count = filteredData.size
                    textHighLight = findText.toString()
                }
                return result

            }

            override fun publishResults(findText: CharSequence, filterResult: FilterResults) {
                filterData = filterResult.values as List<CaseRecords>
                notifyDataSetChanged()
            }

        }
    }
}