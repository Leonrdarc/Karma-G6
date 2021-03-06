package com.pmovil.karmag6.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.pmovil.karmag6.MainActivity
import com.pmovil.karmag6.R
import com.pmovil.karmag6.viewmodel.AuthViewModel
import com.pmovil.karmag6.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_app_drawer.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.nav_header.view.*


class AppDrawerFragment : Fragment() {
    lateinit var drawer: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_app_drawer, container, false)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        val authViewModel: AuthViewModel by activityViewModels()
        val userViewModel: UserViewModel by activityViewModels()

        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val currentUser = authViewModel.getCurrentUser()
        userViewModel.findOneByEmail(currentUser.value?.email!!)
        userViewModel.userInfo.observe(viewLifecycleOwner, Observer { userInfo ->
            if(userInfo!=null){
                view.name_drawer.text = userInfo.karma.toString()
                view.karma_drawer.text = userInfo.name + " " + userInfo.lastname
            }
        })
        drawer = view.findViewById(R.id.drawer_layout)
        var toggle = ActionBarDrawerToggle((activity as AppCompatActivity), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        view.findViewById<NavigationView>(R.id.navigationView).setNavigationItemSelectedListener {
            drawer.closeDrawer(GravityCompat.START)
            when (it.itemId) {
                R.id.profile -> {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, ProfileFragment())?.commit()
                    true
                }
                R.id.todo -> {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, FavoresPendientesFragment())?.commit()
                    true
                }
                R.id.favor -> {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, FavorFragment())?.commit()
                    true
                }
                else -> {false}
            }
        }
        view.findViewById<NavigationView>(R.id.navigationView).setCheckedItem(R.id.profile)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            (activity as AppCompatActivity).supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, ProfileFragment())?.commit()
    }

}