package me.chkfung.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.chkfung.demo.databinding.ActivityDemoBinding
import kotlin.time.ExperimentalTime

@ExperimentalTime
@AndroidEntryPoint
class DemoActivity : AppCompatActivity() {
    private val model: CurrencyListViewModel by viewModels()

    private lateinit var binding: ActivityDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragment()
        initView()
        initEventObserve()
    }

    private fun initFragment() {
        supportFragmentManager.apply {
            var fragment = findFragmentByTag(CurrencyListFragment::class.simpleName)
            if (fragment == null) {
                val ft: FragmentTransaction = beginTransaction()
                fragment = CurrencyListFragment()
                ft.add(R.id.fl_container, fragment, fragment::class.simpleName)
                ft.commit()
            }
        }
    }

    @ExperimentalTime
    private fun initView() {
        binding.btnLoadData.setOnClickListener {
            model.initData()
        }

        binding.btnSortToggle.setOnClickListener {
            model.toggleSorting()
        }
    }

    private fun initEventObserve() {
        lifecycleScope.apply {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    model.uiState.collect {
                        binding.tvSortState.text = if (it.sortAsc) "Sorted ASC" else "Sorted DSC"
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    model.currencyItemClicked.collect {
                        Toast.makeText(this@DemoActivity, it.name + " clicked", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}