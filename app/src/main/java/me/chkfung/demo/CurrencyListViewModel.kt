package me.chkfung.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.chkfung.demo.data.model.CurrencyInfo
import me.chkfung.demo.data.source.CurrencyRepo
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    data class ErrorMessage(val id: Long, val message: String)

    data class CurrencyInfoState(
        val currencyList: List<CurrencyInfo> = emptyList(),
        val sortAsc: Boolean = true,
        val errorMessage: List<ErrorMessage> = emptyList()
    )

    private val _uiState: MutableStateFlow<CurrencyInfoState> = MutableStateFlow(CurrencyInfoState())
    val uiState: StateFlow<CurrencyInfoState> = _uiState
    private val _currencyItemClicked = MutableSharedFlow<CurrencyInfo>(replay = 0)
    val currencyItemClicked: SharedFlow<CurrencyInfo> = _currencyItemClicked

    private val mutex = Mutex()
    private var initJob: Job? = null

    fun toggleSorting() {
        viewModelScope.launch(ioDispatcher) {
            mutex.withLock {
                val currState = _uiState.value
                val sorted = sorting(currState.currencyList, !currState.sortAsc)
                _uiState.update {
                    it.copy(currencyList = sorted, sortAsc = !currState.sortAsc)
                }
            }
        }
    }

    fun initData() {
        initJob?.cancel()
        initJob = viewModelScope.launch(ioDispatcher) {
            try {
                val list = currencyRepo.getCurrency()
                _uiState.update { currencyInfoState -> currencyInfoState.copy(currencyList = list, sortAsc = true) }
            } catch (e: Exception) {
                _uiState.update { currentUiState ->
                    val messages = currentUiState.errorMessage + ErrorMessage(
                        id = UUID.randomUUID().mostSignificantBits,
                        message = e.message.toString()
                    )
                    currentUiState.copy(errorMessage = messages)
                }
            }
        }
    }

    fun onCurrencyItemClicked(item: CurrencyInfo) {
        viewModelScope.launch {
            _currencyItemClicked.emit(item)
        }
    }

    fun messageShown(messageId : Long) {
        _uiState.update {
            it.copy(errorMessage = it.errorMessage.filterNot { it.id == messageId })
        }
    }

    private fun sorting(list: List<CurrencyInfo>, sortAsc: Boolean = true): List<CurrencyInfo> {
        return list.sortedWith { o1, o2 -> if (sortAsc) o1.name.compareTo(o2.name) else o2.name.compareTo(o1.name) }
    }
}