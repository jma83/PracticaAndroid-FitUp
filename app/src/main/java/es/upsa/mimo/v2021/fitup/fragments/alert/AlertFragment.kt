package es.upsa.mimo.v2021.fitup.fragments.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentAlertBinding


class AlertFragment: DialogFragment() {
    companion object {

        val ALERT_TITLE: String = "alertTitle"
        val ALERT_MESSAGE: String = "alertMessage"

        fun newInstance(title: String, message: String): AlertFragment {
            val alertFragment = AlertFragment()
            val args = Bundle()
            args.putString(ALERT_TITLE, title)
            args.putString(ALERT_MESSAGE, message)
            alertFragment.setArguments(args)

            return alertFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAlertBinding.bind(view)
        binding.alertTitle.text  = getArguments()?.getString(ALERT_TITLE)
        binding.alertMessage.text  = getArguments()?.getString(ALERT_MESSAGE)
    }
}