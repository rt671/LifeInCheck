package android.example.lifeincheck.home

import android.example.lifeincheck.R
import android.example.lifeincheck.database.Routine
import android.example.lifeincheck.databinding.FragmentHomeBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

class HomeFragment: Fragment(), ViewClickAction {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.RoutineRecyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = HomeAdapter(this)
        binding.RoutineRecyclerView.adapter =adapter

        val application = requireNotNull(this.activity).application
        val viewModelFactory = HomeViewModelFactory(application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        viewModel.allRoutines.observe(viewLifecycleOwner, Observer{list->
            list?.let{
                adapter.updateList(it)
                adapter.routines = it
            }
        })

        binding.lifecycleOwner = this
        binding.homeViewModel = viewModel

        binding.fabAdd.setOnClickListener{view->
            NavigateToCreate(view, -1)
        }
        return binding.root
    }

    private fun NavigateToCreate(view:View, Id: Long)
    {
        val action = HomeFragmentDirections.actionHomeFragmentToCreateFragment(Id)
        view.findNavController().navigate(action)
    }

    override fun onClickView(routine: Routine) {
        view?.let { NavigateToCreate(it, routine.routineId) }
    }
}