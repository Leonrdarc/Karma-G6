package com.pmovil.karmag6

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.pmovil.karmag6.interfaces.Communicator
import com.pmovil.karmag6.ui.FavorDetalleFragment
import com.pmovil.karmag6.ui.ProfileFragment
import kotlinx.android.synthetic.main.fragment_app_drawer.*

class MainActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}