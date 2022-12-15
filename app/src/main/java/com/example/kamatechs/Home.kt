package com.example.kamatechs

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.NavigationUI
import com.example.kamatechs.databinding.FragmentHomeBinding


class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "HOME"

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false
        )
        binding.btnLogin.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_home2_to_listFragment)
        }
        binding.btnReg.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_home2_to_weather)
        }

        setHasOptionsMenu(true)

        binding.bottomNavigation.setSelectedItemId(R.id.home2)
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.about -> {
                    replaceFragment(About())
                    true
                }
                R.id.home2 -> {
                    replaceFragment(Home())
                    true
                }
                R.id.FAQ -> {
                    replaceFragment(FAQ())
                    true
                }
                else -> false
            }
        }

        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private  fun replaceFragment(fragment: Fragment){
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homelayout,fragment)
        fragmentTransaction.addToBackStack(null).commit()
    }

}