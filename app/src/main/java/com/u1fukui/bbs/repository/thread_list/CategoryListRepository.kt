package com.u1fukui.bbs.repository.thread_list


import android.os.SystemClock
import com.u1fukui.bbs.model.Category
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class CategoryListRepository @Inject constructor(

) {

    fun fetchCategoryList(): Single<List<Category>> {
        //TODO: API実装
        return Single.create { e ->
            SystemClock.sleep(1000)

            val list = ArrayList<Category>()
            for (i in 0..9) {
                list.add(Category(i, "カテゴリ" + i))
            }
            e.onSuccess(list)
        }
    }
}
