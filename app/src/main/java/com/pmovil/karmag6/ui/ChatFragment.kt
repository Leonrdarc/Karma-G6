package com.pmovil.karmag6.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmovil.karmag6.R
import com.pmovil.karmag6.adapters.MessageRVAdapter
import com.pmovil.karmag6.interfaces.Communicator
import com.pmovil.karmag6.interfaces.IOnBackPressed
import com.pmovil.karmag6.model.Message
import com.pmovil.karmag6.viewmodel.AuthViewModel
import com.pmovil.karmag6.viewmodel.ChatViewModel
import kotlinx.android.synthetic.main.fragment_chat.view.*
import java.time.Instant
import java.util.*

class ChatFragment : Fragment(), IOnBackPressed {
    private lateinit var adapter: MessageRVAdapter
    private var customer: Boolean? = null
    lateinit var comm: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        var view = inflater.inflate(R.layout.fragment_chat, container, false)
        val authViewModel: AuthViewModel by activityViewModels()
        val chatViewModel: ChatViewModel by activityViewModels()
        val currentUser = authViewModel.getCurrentUser()
        comm = activity as Communicator
        customer = arguments?.getBoolean("customer")

        chatViewModel.messagesLiveData.observe(viewLifecycleOwner, Observer { messages ->
            view.messageList.layoutManager = LinearLayoutManager(context)
            adapter = MessageRVAdapter(requireContext(), messages ,currentUser.value?.email!! )
            view.messageList.adapter = adapter
        })
        view.btnSend.setOnClickListener {
            if(view.txtMessage.text.toString() != ""){
                val message = Message()
                message.message = view.txtMessage.text.toString()
                message.sentBy = currentUser.value?.email!!
                message.orderId = "randomUUID"
                message.sendDate = (chatViewModel.messagesList.size+1).toString();
                chatViewModel.create(message)

            }

        }
        return view
    }

    override fun onBackPressed(): Boolean {
        comm.passDataSetOrderToState(customer!!)
        return true
    }

}