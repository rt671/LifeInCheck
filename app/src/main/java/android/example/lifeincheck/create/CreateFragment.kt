package android.example.lifeincheck.create

import android.app.TimePickerDialog
import android.example.lifeincheck.R
import android.example.lifeincheck.database.Routine
import android.example.lifeincheck.databinding.FragmentCreateBinding
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.DialogTitle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

class CreateFragment: Fragment() {

    private lateinit var binding: FragmentCreateBinding
    private lateinit var viewModel: CreateViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false)

        val application = requireNotNull(this.activity).application
        var args = CreateFragmentArgs.fromBundle(requireArguments())
        Log.i("context", "argument received is $args" )
        val viewModelFactory = CreateViewModelFactory(args.routineID, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateViewModel::class.java)



        var whichbutton: String=""

        viewModel.timeString.observe(viewLifecycleOwner, Observer {newtime->
            if(whichbutton == "start")
                binding.startTimeSelected.text = newtime
            else if(whichbutton =="end")
                binding.endTimeSelected.text = newtime

        })

        binding.btnPickStartTime.setOnClickListener {
            whichbutton = "start"
            val timePicker: TimePickerDialog =
                TimePickerDialog(activity, viewModel.timePickerDialogListener, 12, 10, false)
            timePicker.show()
        }
        binding.btnPickEndTime.setOnClickListener {
            whichbutton ="end"
            val timePicker: TimePickerDialog =
                TimePickerDialog(activity, viewModel.timePickerDialogListener, 12, 10, false)
            timePicker.show()
        }

        viewModel.routineClickedCompleted.observe(viewLifecycleOwner, Observer{
            if(it == true) {
                binding.habitTitle.setText(viewModel.routineClicked.value?.HabitTitle)
                binding.habitDescription.setText(viewModel.routineClicked.value?.description)
                binding.startTimeSelected.text = viewModel.routineClicked.value?.startTime
                binding.endTimeSelected.text = viewModel.routineClicked.value?.endTime
                setHasOptionsMenu(true)
            }
        })



        binding.btnConfirm.setOnClickListener{
            val titleVal = binding.habitTitle.text.toString()
            val descriptionVal = binding.habitDescription.text.toString()
            val startTimeVal = binding.startTimeSelected.text.toString()
            val endTimeVal = binding.endTimeSelected.text.toString()

            if(viewModel.routineClickedCompleted.value == false)
            { viewModel.onConfirm(Routine(startTime = startTimeVal, endTime = endTimeVal, description = descriptionVal,
            HabitTitle = titleVal))}
            else
            {
                viewModel.onConfirm(Routine(routineId = args.routineID, startTime = startTimeVal, endTime = endTimeVal, description = descriptionVal,
                    HabitTitle = titleVal))
            }

            view?.findNavController()?.navigate(R.id.action_createFragment_to_homeFragment)
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_create, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.delete ->viewModel.deleteRoutine()
        }
        view?.findNavController()?.navigate(R.id.action_createFragment_to_homeFragment)
        return super.onOptionsItemSelected(item)
    }

}