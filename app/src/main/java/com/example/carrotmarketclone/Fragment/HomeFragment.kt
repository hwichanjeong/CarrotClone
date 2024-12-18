package com.example.carrotmarketclone.Fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.carrotmarketclone.R
import com.example.carrotmarketclone.databinding.ActivityLoginBinding
import com.example.carrotmarketclone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.setContentView(context as Activity, R.layout.fragment_home)
        initSpinner()

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun initSpinner(){
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.category_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = adapter
            }
        }

        binding.spinner.onItemSelectedListener  =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //if (parent != null)
                    //Toast.makeText(baseContext, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    return
                }

            }
    }

}