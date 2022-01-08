package me.chkfung.demo


import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.chkfung.demo.data.model.CurrencyInfo
import me.chkfung.demo.data.source.FakeCurrencyRepo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CurrencyListViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var currencyListViewModel: CurrencyListViewModel
    private lateinit var currencyRepo: FakeCurrencyRepo

    @Before
    fun setup() {
        currencyRepo = FakeCurrencyRepo()
        currencyListViewModel = CurrencyListViewModel(currencyRepo, Dispatchers.Main)
    }

    @Test
    fun loadCurrency_dbSuccess_returnsAllCurrencyASC() = mainCoroutineRule.runBlockingTest {
        currencyListViewModel.initData()
        val currencyInfoState = currencyListViewModel.uiState.value
        assertThat(currencyInfoState.sortAsc).isTrue()
        assertThat(currencyInfoState.currencyList.toTypedArray()).isEqualTo(ASC_LIST)
    }

    @Test
    fun loadCurrency_dbFailure_returnsErrorMessage() = mainCoroutineRule.runBlockingTest {
        currencyRepo.shouldReturnError = true
        currencyListViewModel.initData()
        val currencyInfoState = currencyListViewModel.uiState.value
        assertThat(currencyInfoState.errorMessage.size).isEqualTo(1)
    }

    @Test
    fun loadCurrency_errorMessageConsume_returnZeroMessage() = mainCoroutineRule.runBlockingTest {
        currencyRepo.shouldReturnError = true
        currencyListViewModel.initData()
        var currencyInfoState = currencyListViewModel.uiState.value
        assertThat(currencyInfoState.errorMessage.size).isEqualTo(1)
        currencyListViewModel.messageShown(currencyInfoState.errorMessage.first().id)
        currencyInfoState = currencyListViewModel.uiState.value
        assertThat(currencyInfoState.errorMessage.size).isEqualTo(0)
    }

    @Test
    fun loadCurrency_dbEmpty_returnsZeroCurrency() = mainCoroutineRule.runBlockingTest {
        currencyRepo.shouldReturnEmpty = true
        currencyListViewModel.initData()
        val currencyInfoState = currencyListViewModel.uiState.value
        assertThat(currencyInfoState.currencyList.size).isEqualTo(0)
        assertThat(currencyInfoState.errorMessage.size).isEqualTo(0)
    }

    @Test
    fun sortCurrencyASCtoDSC_currASC_returnASCtoDSC() = mainCoroutineRule.runBlockingTest {
        currencyListViewModel.initData()
        currencyListViewModel.toggleSorting()
        val currencyInfoState = currencyListViewModel.uiState.value
        assertThat(currencyInfoState.sortAsc)
        assertThat(currencyInfoState.currencyList.toTypedArray()).isEqualTo(DSC_LIST)
    }

    @Test
    fun sortCurrencyDSCtoASC_currDSC_returnDSCtoASC() = mainCoroutineRule.runBlockingTest {
        currencyListViewModel.initData()
        currencyListViewModel.toggleSorting()
        currencyListViewModel.toggleSorting()
        val currencyInfoState = currencyListViewModel.uiState.value
        assertThat(currencyInfoState.sortAsc)
        assertThat(currencyInfoState.currencyList.toTypedArray()).isEqualTo(ASC_LIST)
    }

    @After
    fun clear() {
    }

    companion object {
        private val DSC_LIST = arrayOf(
            CurrencyInfo(id = "USDC", name = "USD Coin", symbol = "USDC"),
            CurrencyInfo(id = "CUC", name = "Cucumber", symbol = "CUC"),
            CurrencyInfo(id = "BTC", name = "Bitcoin", symbol = "BTC"),
        )
        private val ASC_LIST = arrayOf(
            CurrencyInfo(id = "BTC", name = "Bitcoin", symbol = "BTC"),
            CurrencyInfo(id = "CUC", name = "Cucumber", symbol = "CUC"),
            CurrencyInfo(id = "USDC", name = "USD Coin", symbol = "USDC"),
        )
    }
}