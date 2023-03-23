package ru.khozyainov.inspirationpointtesttask.adapter

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.khozyainov.inspirationpointtesttask.R
import ru.khozyainov.inspirationpointtesttask.databinding.FragmentItemBinding
import ru.khozyainov.inspirationpointtesttask.model.Participant
import ru.khozyainov.inspirationpointtesttask.utils.updateText


class ParticipantAdapterDelegate(
    private val onItemClicked: (participant: Participant) -> Unit
) : AbsListItemAdapterDelegate<Participant, Participant, ParticipantAdapterDelegate.ParticipantHolder>() {

    override fun isForViewType(
        item: Participant,
        items: MutableList<Participant>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): ParticipantHolder {
        return ParticipantHolder(
            binding = FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked
        )
    }

    override fun onBindViewHolder(
        item: Participant,
        holder: ParticipantHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ParticipantHolder(
        val binding: FragmentItemBinding,
        private val onItemClicked: (participant: Participant) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(participant: Participant) {

            refresh()
            binding.apply {
                titleTextView.text =
                    itemView.context.getString(R.string.participant, participant.id.toString())
                titleTextView.background.setTint(Color.WHITE)
                idTextView.text = participant.id.toString()
                idTextView.background.setTint(Color.WHITE)

                regTextWatcher(participantEditText1, participant, 1)
                regTextWatcher(participantEditText2, participant, 2)
                regTextWatcher(participantEditText3, participant, 3)
                regTextWatcher(participantEditText4, participant, 4)
                regTextWatcher(participantEditText5, participant, 5)
                regTextWatcher(participantEditText6, participant, 6)
                regTextWatcher(participantEditText7, participant, 7)

                setTextToEditText(participantEditText1, participant.field1)
                setTextToEditText(participantEditText2, participant.field2)
                setTextToEditText(participantEditText3, participant.field3)
                setTextToEditText(participantEditText4, participant.field4)
                setTextToEditText(participantEditText5, participant.field5)
                setTextToEditText(participantEditText6, participant.field6)
                setTextToEditText(participantEditText7, participant.field7)

                when (participant.id) {
                    1 -> disableEditText(participantEditText1)
                    2 -> disableEditText(participantEditText2)
                    3 -> disableEditText(participantEditText3)
                    4 -> disableEditText(participantEditText4)
                    5 -> disableEditText(participantEditText5)
                    6 -> disableEditText(participantEditText6)
                    else -> disableEditText(participantEditText7)
                }

                if (participant.amountOfPoints != 0) {
                    participantAmountOfPoints.text = participant.amountOfPoints.toString()
                } else {
                    participantAmountOfPoints.text = ""
                }

                participantAmountOfPoints.background.setTint(Color.WHITE)

                participantRanked.text =
                    if (participant.ranked != 0) participant.ranked.toString() else ""
                participantRanked.background.setTint(Color.WHITE)
            }
        }

        private fun setTextToEditText(participantEditText: EditText, fieldValue: Int?) {
            when (fieldValue) {
                null -> {
                    participantEditText.updateText("x")
                    participantEditText.background.setTint(Color.WHITE)
                }
                !in 0..5 -> {
                    participantEditText.background.setTint(Color.RED)
                    participantEditText.updateText(fieldValue.toString())
                }
                else -> participantEditText.updateText(fieldValue.toString())
            }
        }

        private fun regTextWatcher(
            editText: EditText,
            participant: Participant,
            numberOfET: Int
        ) {

            editText.tag?.let {
                editText.removeTextChangedListener(it as TextWatcher)
            }

            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (editText.hasFocus()) {
                        val point = s.toString().toIntOrNull()
                        if (point != null && point !in 0..5) {
                            editText.background.setTint(Color.RED)
                            Toast.makeText(
                                itemView.context,
                                itemView.context.getText(R.string.incorrect_value),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            refreshEditText(editText)
                        }
                        onItemClicked(updateParticipant(numberOfET, participant, point))
                    }
                }
            }.also {
                editText.addTextChangedListener(it)
                editText.tag = it
            }
        }

        private fun updateParticipant(
            numbOfField: Int,
            participant: Participant,
            value: Int?
        ): Participant {
            return when (numbOfField) {
                1 -> participant.copy(field1 = value)
                2 -> participant.copy(field2 = value)
                3 -> participant.copy(field3 = value)
                4 -> participant.copy(field4 = value)
                5 -> participant.copy(field5 = value)
                6 -> participant.copy(field6 = value)
                else -> participant.copy(field7 = value)
            }
        }

        private fun getEditTextList(): List<EditText> =
            listOf(
                binding.participantEditText1,
                binding.participantEditText2,
                binding.participantEditText3,
                binding.participantEditText4,
                binding.participantEditText5,
                binding.participantEditText6,
                binding.participantEditText7,
            )

        private fun refresh() =
            getEditTextList().forEach { editText ->
                refreshEditText(editText)
            }


        private fun refreshEditText(editText: EditText) {
            editText.isEnabled = true
            editText.background.setTint(Color.WHITE)
        }

        private fun disableEditText(participantEditText: EditText) {
            participantEditText.isEnabled = false
            participantEditText.background.setTint(Color.BLACK)
        }
    }
}