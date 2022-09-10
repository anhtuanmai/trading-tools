package at.aze.tradingtools.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import at.aze.tradingtools.databinding.ItemUserBinding
import at.aze.tradingtools.persistence.User

class UserAdapter(
    var users: List<User>,
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(users[position]) {
                binding.txtFirstName.text = this.firstName
                binding.txtLastName.text = this.lastName
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}
