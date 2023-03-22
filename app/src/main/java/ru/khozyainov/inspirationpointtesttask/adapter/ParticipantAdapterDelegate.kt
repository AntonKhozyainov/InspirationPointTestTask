package ru.khozyainov.inspirationpointtesttask.adapter

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.khozyainov.inspirationpointtesttask.R
import ru.khozyainov.inspirationpointtesttask.databinding.FragmentItemBinding
import ru.khozyainov.inspirationpointtesttask.model.Participant
import ru.khozyainov.inspirationpointtesttask.utils.onNext


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

                getTextForEditText(participantEditText1, participant.field1)
                getTextForEditText(participantEditText2, participant.field2)
                getTextForEditText(participantEditText3, participant.field3)
                getTextForEditText(participantEditText4, participant.field4)
                getTextForEditText(participantEditText5, participant.field5)
                getTextForEditText(participantEditText6, participant.field6)
                getTextForEditText(participantEditText7, participant.field7)

                setClickListeners(participant)

                when (participant.id) {
                    1 -> disableEditText(participantEditText1)
                    2 -> disableEditText(participantEditText2)
                    3 -> disableEditText(participantEditText3)
                    4 -> disableEditText(participantEditText4)
                    5 -> disableEditText(participantEditText5)
                    6 -> disableEditText(participantEditText6)
                    else -> disableEditText(participantEditText7)
                }

                val amountOfPoints = getAmountOfPoints(participant)
                if (amountOfPoints != 0) {
                    onItemClicked(participant.copy(amountOfPoints = amountOfPoints))
                    participantAmountOfPoints.text = amountOfPoints.toString()
                } else {
                    participantAmountOfPoints.text = ""
                }

                participantAmountOfPoints.background.setTint(Color.WHITE)

                participantRanked.text = if (participant.ranked != 0) participant.ranked.toString() else ""
                participantRanked.background.setTint(Color.WHITE)
            }
        }

        private fun getAmountOfPoints(participant: Participant): Int {
            var filled = 0
            var result = 0

            var pair = checkField(participant.field1)
            filled += pair.first
            result += pair.second

            pair = checkField(participant.field2)
            filled += pair.first
            result += pair.second

            pair = checkField(participant.field3)
            filled += pair.first
            result += pair.second

            pair = checkField(participant.field4)
            filled += pair.first
            result += pair.second

            pair = checkField(participant.field5)
            filled += pair.first
            result += pair.second

            pair = checkField(participant.field6)
            filled += pair.first
            result += pair.second

            pair = checkField(participant.field7)
            filled += pair.first
            result += pair.second

            return if (filled == 6) result
            else 0
        }

        private fun checkField(field: Int?): Pair<Int, Int> =
            if (field != null && field in 0..5) 1 to field
            else 0 to 0

        private fun getTextForEditText(participantEditText: EditText, fieldValue: Int?) {
            when (fieldValue) {
                null -> {
                    participantEditText.hint = "x"
                    participantEditText.background.setTint(Color.WHITE)
                }
                !in 0..5 -> {
                    participantEditText.background.setTint(Color.RED)
                    participantEditText.hint = fieldValue.toString()
                }
                else -> participantEditText.hint = fieldValue.toString()
            }
        }

        private fun disableEditText(participantEditText: EditText) {
            participantEditText.isEnabled = false
            participantEditText.background.setTint(Color.BLACK)
        }

        private fun setClickListener(
            participant: Participant,
            participantEditText: EditText,
            numbOfField: Int
        ) {
            participantEditText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    participantEditText.setText("")
                }
            }

            participantEditText.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        val point = s.toString().toIntOrNull()
                        if (point != null && point !in 0..5) {
                            participantEditText.background.setTint(Color.RED)
                            Toast.makeText(
                                itemView.context,
                                itemView.context.getText(R.string.incorrect_value),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        participantEditText.onNext {
                            onItemClicked(updateParticipant(numbOfField, participant, point))
                            s?.clear()
                        }
                    }
                }
            )
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

        private fun setClickListeners(participant: Participant) {
            setClickListener(participant, binding.participantEditText1, 1)
            setClickListener(participant, binding.participantEditText2, 2)
            setClickListener(participant, binding.participantEditText3, 3)
            setClickListener(participant, binding.participantEditText4, 4)
            setClickListener(participant, binding.participantEditText5, 5)
            setClickListener(participant, binding.participantEditText6, 6)
            setClickListener(participant, binding.participantEditText7, 7)
        }

        private fun refresh() {
            listOf(
                binding.participantEditText1,
                binding.participantEditText2,
                binding.participantEditText3,
                binding.participantEditText4,
                binding.participantEditText5,
                binding.participantEditText6,
                binding.participantEditText7,
            ).forEach { editText ->
                editText.isEnabled = true
                editText.background.setTint(Color.WHITE)
                editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI)
            }
        }
    }
}