package ant.vit.corsokotlin

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ant.vit.corsokotlin.tools.implementingInterface
import ant.vit.corsokotlin.tools.systemService
import kotlinx.android.synthetic.main.dialog_custom_alert.*

/**
 * Created by Antonio Vitiello
 */
class CustomAlertDialog : DialogFragment() {
    private var accepted: Boolean = false

    companion object {
        const val TAG = "CustomAlertDialog"
        private const val TITLE_BUNDLE_KEY = "title_key"
        private const val MESSAGE_BUNDLE_KEY = "message_key"

        fun newInstance(title: String, message: String) = CustomAlertDialog().apply {
            arguments = Bundle().apply {
                putString(TITLE_BUNDLE_KEY, title)
                putString(MESSAGE_BUNDLE_KEY, message)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return inflater.inflate(R.layout.dialog_custom_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        with(requireArguments()) {
            alertTitleText.text = getString(TITLE_BUNDLE_KEY)
            alertBodyText.text = getString(MESSAGE_BUNDLE_KEY)
            isCancelable = false
        }
        acceptButton.setOnClickListener {
            vibrate()
            accepted = true
            dismiss()
        }
        cancelButton.setOnClickListener {
            accepted = false
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        implementingInterface<ICustomAlertDialog>()?.isAccepted(accepted)
    }

    @Suppress("DEPRECATION")
    private fun vibrate() {
        val vibrator = requireContext().systemService<Vibrator>()
        //same as:
        //val vibrator = requireContext().getSystemService(VIBRATOR_SERVICE) as? Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(1000, DEFAULT_AMPLITUDE))
        } else {
            vibrator?.vibrate(1000)
        }
    }

}