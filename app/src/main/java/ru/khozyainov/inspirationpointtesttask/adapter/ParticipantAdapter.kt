package ru.khozyainov.inspirationpointtesttask.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.khozyainov.inspirationpointtesttask.model.Participant


class ParticipantAdapter(
    onItemClicked: (participant: Participant) -> Unit
): AsyncListDifferDelegationAdapter<Participant>(ParticipantDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ParticipantAdapterDelegate(onItemClicked))
    }

    class ParticipantDiffUtilCallback: DiffUtil.ItemCallback<Participant>(){

        override fun areItemsTheSame(oldItem: Participant, newItem: Participant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Participant, newItem: Participant): Boolean {
            return oldItem == newItem
        }

    }
}