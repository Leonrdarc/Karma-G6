package com.pmovil.karmag6.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.type.DateTime
import com.pmovil.karmag6.R
import com.pmovil.karmag6.model.Message
import kotlinx.android.synthetic.main.my_message.view.*
import kotlinx.android.synthetic.main.other_message.view.*
import java.util.*

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2

class MessageRVAdapter (val context: Context, private val init_messages: List<Message>, val email: String) : RecyclerView.Adapter<MessageViewHolder>() {
    private val messages: MutableList<Message> = init_messages.toMutableList()

    inner class MyMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtMyMessage
        private var timeText: TextView = view.txtMyMessageTime

        override fun bind(message: Message) {
            messageText.text = message.message
            timeText.text = message.sendDate
        }
    }

    inner class OtherMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtOtherMessage
        private var userText: TextView = view.txtOtherUser
        private var timeText: TextView = view.txtOtherMessageTime

        override fun bind(message: Message) {
            messageText.text = message.message
            userText.text = message.sentBy
            timeText.text = message.sendDate.toString()
        }
    }

    fun addMessage(message: Message){
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]

        return if(email == message.sentBy) {
            VIEW_TYPE_MY_MESSAGE
        }
        else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if(viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.my_message, parent, false)
            )
        } else {
            OtherMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.other_message, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder?.bind(message)
    }

}

open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message:Message) {}
}