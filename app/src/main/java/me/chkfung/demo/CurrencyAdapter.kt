package me.chkfung.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.chkfung.demo.CurrencyAdapter.*
import me.chkfung.demo.databinding.ItemCurrencyInfoBinding
import me.chkfung.demo.data.model.CurrencyInfo

class CurrencyAdapter(private val onItemClick: (CurrencyInfo) -> Unit) : RecyclerView.Adapter<VH>() {

    private var currencyList: List<CurrencyInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vh = VH(ItemCurrencyInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        vh.itemView.setOnClickListener {
            val position = vh.bindingAdapterPosition
            onItemClick.invoke(currencyList[position])
        }
        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    fun updateCurrency(newList: List<CurrencyInfo>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return currencyList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return currencyList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return currencyList[oldItemPosition] == newList[newItemPosition]
            }
        }, true)
        currencyList = newList
        result.dispatchUpdatesTo(this)
    }

    class VH(private var binding: ItemCurrencyInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyInfo: CurrencyInfo) {
            binding.tvName.text = currencyInfo.name
            binding.tvSymbol.text = currencyInfo.symbol
        }
    }
}