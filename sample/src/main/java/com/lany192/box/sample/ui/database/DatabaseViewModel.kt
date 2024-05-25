package com.lany192.box.sample.ui.database

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.hjq.toast.Toaster
import com.lany192.box.database.entity.SearchHistory
import com.lany192.box.database.repository.DatabaseRepository
import com.lany192.box.network.data.api.ApiService
import com.lany192.box.network.data.bean.ViewPagerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(val repository: DatabaseRepository) :
    LifecycleViewModel() {

    fun insert() {
        viewModelScope.launch {
            val record = SearchHistory()
            record.keyword = "测试"
            val records = repository.saveSearchHistory(record)
            records.collect {
                log.i("插入测试：" + it.size)
                Toaster.show("数据：" + it.size)
            }
        }
    }

    fun query() {
        viewModelScope.launch {
            val records = repository.getSearchHistories()
            records.collect {
                Toaster.show("数据：" + it.size)
            }
        }
    }
}