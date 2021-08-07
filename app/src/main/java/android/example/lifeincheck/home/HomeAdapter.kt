package android.example.lifeincheck.home

import android.example.lifeincheck.R
import android.example.lifeincheck.database.Routine
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter(private val listener: ViewClickAction): RecyclerView.Adapter<HomeAdapter.RoutineViewHolder>() {

    var routines= emptyList<Routine>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val inflater =LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.single_routine_view, parent, false)
        //val viewHolder =  RoutineViewHolder(view)

        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        var currentItem = routines[position]
        holder.startTime.text = currentItem.startTime
        holder.endTime.text = currentItem.endTime
        holder.habitTitle.text = currentItem.HabitTitle
        holder.description.text = currentItem.description
        //holder.day_cnt.text  = currentItem.days.toString()

        holder.itemView.setOnClickListener{
            listener.onClickView(routines[position])
        }
    }

    override fun getItemCount(): Int {
        return routines.size
    }

    fun updateList(newlist: List<Routine>)
    {
        this.routines = newlist
//        routines.clear()
//        routines.addAll(newlist)

        notifyDataSetChanged()
    }

    class RoutineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var startTime = itemView.findViewById<TextView>(R.id.routineStartTime)
        var endTime = itemView.findViewById<TextView>(R.id.routineEndTime)
        var habitTitle = itemView.findViewById<TextView>(R.id.habitTitle)
        var description = itemView.findViewById<TextView>(R.id.routineDescription)
        //var day_cnt = itemView.findViewById<TextView>(R.id.day_no)
    }
}

interface ViewClickAction{
    fun onClickView(routine: Routine)
}