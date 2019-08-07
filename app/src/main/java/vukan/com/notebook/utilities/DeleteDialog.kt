package vukan.com.notebook.utilities

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import vukan.com.notebook.R

class DeleteDialog : DialogFragment() {
    private var yesNoClick: OnYesNoClick? = null

    fun setOnYesNoClick(yesNoClick: OnYesNoClick) {
        this.yesNoClick = yesNoClick
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.dialog_message))
                .setPositiveButton(
                    getString(R.string.dialog_confirm)
                ) { _, _ ->
                    yesNoClick?.onYesClicked()
                }
                .setNegativeButton(
                    getString(R.string.dialog_cancel)
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