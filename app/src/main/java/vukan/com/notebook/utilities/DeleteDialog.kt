package vukan.com.notebook.utilities

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DeleteDialog : DialogFragment() {
    private var yesNoClick: OnYesNoClick? = null

    fun setOnYesNoClick(yesNoClick: OnYesNoClick) {
        this.yesNoClick = yesNoClick
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Are you sure?")
                .setPositiveButton(
                    "Yes"
                ) { _, _ ->
                    yesNoClick?.onYesClicked()
                }
                .setNegativeButton(
                    "No"
                ) { _, _ ->
                    yesNoClick?.onNoClicked()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface OnYesNoClick {
        fun onYesClicked()
        fun onNoClicked()
    }
}