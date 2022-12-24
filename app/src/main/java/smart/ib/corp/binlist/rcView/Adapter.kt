package smart.ib.corp.binlist.rcView

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import smart.ib.corp.binlist.R
import smart.ib.corp.binlist.databinding.ShablonBinListBinding
import smart.ib.corp.binlist.room.BinList

//Адаптер RecyclerView для отображения списка истории binlist
class Adapter(private val bankBinList: List<BinList>, private val resources: Resources) :
    RecyclerView.Adapter<MyViewHolderBinList>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderBinList {
        val item = ShablonBinListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolderBinList(item)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolderBinList, position: Int) {
        with(holder.binding) {
            textViewBIN.text = bankBinList[position].bin_number.toString()
            textViewBank.text =
                "${bankBinList[position].nameBank} ${bankBinList[position].city}"
            textViewUrlBank.text = bankBinList[position].url
            textViewPhoneBank.text = bankBinList[position].phone
            textViewMapBank.text = resources.getText(R.string.maps_bin_list_bank)
            if (bankBinList[position].latitude != 0 && bankBinList[position].longitude != 0) {
                val mapsLocation = ArrayList<Int>()
                mapsLocation.add(bankBinList[position].latitude)
                mapsLocation.add(bankBinList[position].longitude)
                val bundle = Bundle().apply {
                    putIntegerArrayList(
                        "maps_location",
                        mapsLocation
                    )
                }
                textViewMapBank.setOnClickListener {
                    findNavController(holder.binding.root).navigate(
                        R.id.action_SecondFragment_to_mapsFragmentBinList,
                        bundle
                    )
                }
            }
            textViewBrand.text = bankBinList[position].brand
            textViewCNLength.text = bankBinList[position].length.toString()
            textViewCountry.text =
                "${bankBinList[position].emoji} ${bankBinList[position].nameCountry}"
            textViewPrepaid.text =
                if (bankBinList[position].prepaid) resources.getText(R.string.bin_list_yes) else resources.getText(
                    R.string.bin_list_no
                )
            textViewScheme.text = bankBinList[position].scheme
            textViewType.text = bankBinList[position].type
            textViewCNLuhn.text =
                if (bankBinList[position].luhn) resources.getText(R.string.bin_list_yes) else resources.getText(
                    R.string.bin_list_no
                )
        }

    }

    override fun getItemCount(): Int {
        return bankBinList.count()
    }

}

class MyViewHolderBinList(val binding: ShablonBinListBinding) : ViewHolder(binding.root)