package com.pmovil.karmag6

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.pmovil.karmag6.interfaces.Communicator
import com.pmovil.karmag6.interfaces.IOnBackPressed
import com.pmovil.karmag6.ui.*
import kotlinx.android.synthetic.main.fragment_app_drawer.*

class MainActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val fragment =
        this.supportFragmentManager.findFragmentById(R.id.fragment_container)
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let { toLogin ->
            if(toLogin){
                super.onBackPressed()
            }
        }
    }

    fun openCloseNavigationDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun passDataCom(orderIndex: Int) {
        val bundle = Bundle()
        bundle.putInt("orderId", orderIndex)

        val transaction = this.supportFragmentManager.beginTransaction()
        val favorDetail = FavorDetalleFragment()
        favorDetail.arguments = bundle

        transaction.replace(R.id.fragment_container, favorDetail)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun passDataSetOrderToState(customer: Boolean) {
        val bundle = Bundle()
        bundle.putBoolean("customer", customer)

        val transaction = this.supportFragmentManager.beginTransaction()
        val favorSeguir = SeguirFavorFragment()
        favorSeguir.arguments = bundle

        transaction.replace(R.id.fragment_container, favorSeguir)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun passDataToChat(orderId: String, customer: Boolean) {
        val bundle = Bundle()
        bundle.putString("orderId", orderId)


        val transaction = this.supportFragmentManager.beginTransaction()
        val chat = ChatFragment()
        chat.arguments = bundle

        transaction.replace(R.id.fragment_container, chat)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }
}