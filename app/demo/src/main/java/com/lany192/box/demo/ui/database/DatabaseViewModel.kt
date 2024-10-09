package com.lany192.box.demo.ui.database

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.github.lany192.extension.toast
import com.lany192.box.database.entity.SearchHistory
import com.lany192.box.database.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(val repository: DatabaseRepository) :
    LifecycleViewModel() {
    val results = MutableStateFlow<List<String>>(mutableListOf())

    fun insert() {
        viewModelScope.launch {
            val record = SearchHistory()
            record.keyword = "测试" + System.currentTimeMillis()
            val records = repository.saveSearchHistory(record)
            records.collect {
                log.i("插入测试：" + it.size)
                toast("数据：" + it.size)
            }
        }
    }

    fun query() {
        viewModelScope.launch {
            val records = repository.getSearchHistories()
            records.collect { it ->
                results.value = it.map { it.keyword!! }
            }
        }
    }
}