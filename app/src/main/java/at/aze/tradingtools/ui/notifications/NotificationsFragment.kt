package at.aze.tradingtools.ui.notifications

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import at.aze.tradingtools.R
import at.aze.tradingtools.TradingApplication
import at.aze.tradingtools.databinding.FragmentNotificationsBinding
import at.aze.tradingtools.persistence.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddUser.setOnClickListener {
            addUser()
        }

        binding.btnRefreshList.setOnClickListener {
            refreshList()
        }

        binding.btnDeleteAll.setOnClickListener {
            showConfirmationDialog()
        }

        refreshList()
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmation")
            .setMessage("Do you really want to erase database?")
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, whichButton ->
                    deleteAll()
                })
            .setNegativeButton("No", null).show()
    }

    private fun deleteAll() {
        // execute work on worker thread to avoid blocking main thread
        val source: Single<Unit> = Single.just(Unit)
        source.observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    val db = TradingApplication.getDatabase(requireContext())
                    db.userDao().deleteAll()
                },
                { error ->
                    Timber.e(error)
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG)
                        .show()
                }
            )
    }

    private fun refreshList() {
        val db = TradingApplication.getDatabase(requireContext())
        db.userDao().getAll()
            .observeOn(AndroidSchedulers.mainThread()) // observe on main thread since this will update UI interface
            .subscribeOn(Schedulers.io())
            .subscribe(
                { users ->
                    try {
                        if (users.isEmpty()) {
                            binding.labelNoUser.isVisible = true
                            binding.rvUsers.isVisible = false
                        } else {
                            binding.labelNoUser.isVisible = false
                            binding.rvUsers.isVisible = true
                        }
                        binding.rvUsers.adapter = UserAdapter(users)
                    } catch (e: Exception) {
                        Timber.e(e)
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG)
                            .show()
                    }
                },
                { error ->
                    Timber.e(error)
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG)
                        .show()
                }
            )
    }

    @SuppressLint("SetTextI18n")
    private fun addUser() {
        val lastName = binding.edtLastName.text.toString()
        val firstName = binding.edtFirstName.text.toString()
        if (lastName.isBlank()) {
            binding.txtErrorMessage.text = "please enter your last name"
            return
        }
        if (firstName.isBlank()) {
            binding.txtErrorMessage.text = "please enter your first name"
            return
        }
        binding.txtErrorMessage.text = ""
        val uid = System.currentTimeMillis().toString()
        val newUser = User(uid = uid, firstName = firstName, lastName = lastName)

        // execute work on worker thread to avoid blocking main thread
        val source: Single<User> = Single.just(newUser)
        source.observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    val db = TradingApplication.getDatabase(requireContext())
                    db.userDao().insertAll(newUser)
                },
                { error ->
                    Timber.e(error)
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG)
                        .show()
                }
            )
    }
}