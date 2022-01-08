package me.chkfung.demo.data.source


import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.chkfung.demo.MainCoroutineRule
import me.chkfung.demo.data.source.local.FakeLocalDataSource
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CurrencyRepoTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var fakeLocalDataSource: FakeLocalDataSource
    lateinit var currencyRepo: CurrencyRepo
    @Before
    fun setup(){
        fakeLocalDataSource = FakeLocalDataSource()
        currencyRepo = CurrencyRepoImpl(fakeLocalDataSource)
    }

    @Test
    fun getLocalData_success_returnAll(){
        assertThat(currencyRepo.getCurrency()).hasSize(3)
    }

    @Test
    fun getLocalData_empty_returnEmpty(){
        fakeLocalDataSource.emptyData = true
        assertThat(currencyRepo.getCurrency()).hasSize(0)
    }

    @Test(expected = Exception::class)
    fun getLocalData_failure_returnException(){
        fakeLocalDataSource.dataSourceFailure = true
        currencyRepo.getCurrency()
    }
}