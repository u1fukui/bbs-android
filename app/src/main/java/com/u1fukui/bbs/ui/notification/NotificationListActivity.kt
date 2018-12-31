package com.u1fukui.bbs.ui.notification


import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.u1fukui.bbs.R
import com.u1fukui.bbs.customview.BindingHolder
import com.u1fukui.bbs.customview.ObservableListRecyclerAdapter
import com.u1fukui.bbs.databinding.ActivityNotificationListBinding
import com.u1fukui.bbs.databinding.ViewNotificationCellBinding
import com.u1fukui.bbs.repository.NotificationListRepository
import com.u1fukui.bbs.ui.BaseActivity

class NotificationListActivity : BaseActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityNotificationListBinding>(this, R.layout.activity_notification_list)
    }

    private val viewModel: NotificationListViewModel by lazy {
        ViewModelProviders
                .of(this, NotificationListViewModel.Factory(
                        NotificationListRepository()
                ))
                .get(NotificationListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        initToolbar(binding.toolbar, true)
        initViews()

        viewModel.start()
    }

    private fun initViews() {
        binding.recyclerView.apply {
            adapter = Adapter(viewModel.notificationBindingModelList)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

    private class Adapter(list: ObservableList<NotificationBindingModel>) : ObservableListRecyclerAdapter<NotificationBindingModel, BindingHolder<ViewNotificationCellBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingHolder<ViewNotificationCellBinding>(parent.context, parent, R.layout.view_notification_cell)

        override fun onBindViewHolder(holder: BindingHolder<ViewNotificationCellBinding>, position: Int) {
            holder.binding?.apply {
                bindingModel = getItem(position)
                executePendingBindings()
            }
        }
    }
}
