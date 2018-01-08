package com.u1fukui.bbs.ui.notification


import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.u1fukui.bbs.R
import com.u1fukui.bbs.customview.BindingHolder
import com.u1fukui.bbs.customview.ObservableListRecyclerAdapter
import com.u1fukui.bbs.databinding.ActivityNotificationListBinding
import com.u1fukui.bbs.databinding.ViewNotificationCellBinding
import com.u1fukui.bbs.ui.BaseActivity
import javax.inject.Inject

class NotificationListActivity : BaseActivity() {

    @Inject
    lateinit var bindingModel: NotificationListBindingModel

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityNotificationListBinding>(this, R.layout.activity_notification_list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bindingModel = bindingModel

        initToolbar(binding.toolbar, true)
        initViews()

        bindingModel.start()
    }

    private fun initViews() {
        binding.recyclerView.apply {
            adapter = Adapter(bindingModel.notificationBindingModelList)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    override fun onDestroy() {
        bindingModel.destroy()
        binding.unbind()
        super.onDestroy()
    }

    private class Adapter(list: ObservableList<NotificationBindingModel>) : ObservableListRecyclerAdapter<NotificationBindingModel, BindingHolder<ViewNotificationCellBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingHolder<ViewNotificationCellBinding>(parent.context, parent, R.layout.view_notification_cell)

        override fun onBindViewHolder(holder: BindingHolder<ViewNotificationCellBinding>, position: Int) {
            holder.binding.apply {
                bindingModel = getItem(position)
                executePendingBindings()
            }
        }
    }
}
