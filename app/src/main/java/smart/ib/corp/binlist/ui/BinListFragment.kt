package smart.ib.corp.binlist.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import smart.ib.corp.binlist.R
import smart.ib.corp.binlist.api.BinListItem
import smart.ib.corp.binlist.app.App
import smart.ib.corp.binlist.databinding.FragmentBinListBinding
import smart.ib.corp.binlist.mvvm.BinListViewModel
import smart.ib.corp.binlist.mvvm.State
import smart.ib.corp.binlist.room.BinListDao


class BinListFragment : Fragment() {

    private var _binding: FragmentBinListBinding? = null
    private val binding get() = _binding!!

    //Инициализация viewModel BinListViewModel
    private val viewModel: BinListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val binListDao: BinListDao = (requireActivity().application as App).db.binListDao()
                return BinListViewModel(binListDao, resources) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBinListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    //Метод инициализации
    private fun init() {
        binding.apply {
            //Кнопка поика и инициализации запроса
            searchButtonBinList.setOnClickListener {
                if (editTextBinList.text.toString() == "") {
                    Snackbar.make(
                        binding.root,
                        resources.getText(R.string.hint_edit_text_bin_list),
                        Snackbar.LENGTH_LONG
                    ).show()
                } else
                    viewModel.getBinListViewModel(id = binding.editTextBinList.text.toString())
            }

            //Кнопка перехода во фрагмент истории запросов.
            buttonHistory.setOnClickListener {
                findNavController().navigate(R.id.SecondFragment)
                binding.editTextBinList.text = null
            }
        }

        //Подписка на stateFlow
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    State.Load -> {
                        settingLoadResult()
                    }
                    State.Results -> {
                        if (binding.loadBinList.isVisible) {
                            settingTypeCheck()
                        }
                    }
                }
            }
        }
    }

    //Метод настройки и проверки получения необходимого типа данных для отображения результата
    private fun settingTypeCheck() = with(binding) {
        when (State.Results.list) {
            is BinListItem -> {
                initBinList(State.Results.list as BinListItem)
                viewModel.addButton(
                    State.Results.list as BinListItem,
                    editTextBinList.text.toString().toLong().toInt()
                )
                loadBinList.isVisible = false
                lottiCard.isVisible = true
                notFoundBinList.isVisible = false
            }
            is String -> {
                Snackbar.make(
                    binding.root,
                    resources.getText(R.string.hint_edit_text_bin_list),
                    Snackbar.LENGTH_LONG
                ).show()
                notFountBinList()
                loadBinList.isVisible = false
                lottiCard.isVisible = true
                notFoundBinList.isVisible = true
            }
            else -> {
                notFountBinList()
                loadBinList.isVisible = false
                lottiCard.isVisible = true
                notFoundBinList.isVisible = true
            }
        }
    }

    //Метод визульной настройки отображения элементов закрузки
    private fun settingLoadResult() = with(binding) {
        loadBinList.isVisible = true
        lottiCard.isVisible = true
        notFoundBinList.isVisible = false
    }

    //Метод инициализации и отображения полученных элементов
    @SuppressLint("SetTextI18n")
    private fun initBinList(bankBinList: BinListItem) = with(binding.includeBinList) {
        textViewBIN.text = binding.editTextBinList.text.toString()
        bankBinList.apply {
            if (bank == null){
                textViewBank.text = resources.getText(R.string.not_result)
                textViewUrlBank.text = resources.getText(R.string.not_result)
                textViewPhoneBank.text = resources.getText(R.string.not_result)
            }
            else {
                textViewBank.text =
                    "${bank.name} ${bank.city}"
                textViewUrlBank.text = bank.url
                textViewPhoneBank.text = bank.phone
            }

            if (country == null) {
                textViewCountry.text = resources.getText(R.string.not_result)
                textViewMapBank.text = resources.getText(R.string.not_result)
            }
            else {
                textViewMapBank.text = resources.getText(R.string.maps_bin_list_bank)
                textViewCountry.text =
                    "${country.emoji} ${country.name}, ${country.currency}"
                val mapsLocation = ArrayList<Int>()
                mapsLocation.add(country.latitude)
                mapsLocation.add(country.longitude)
                val bundle = Bundle().apply {
                    putIntegerArrayList(
                        "maps_location",
                        mapsLocation
                    )
                }
                textViewMapBank.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_FirstFragment_to_mapsFragmentBinList,
                        bundle
                    )
                }
            }

            if (number == null) {
                textViewCNLength.text = resources.getText(R.string.not_result)
                textViewCNLuhn.text = resources.getText(R.string.not_result)
            } else {
                textViewCNLength.text = number.length.toString()
                textViewCNLuhn.text =
                    if (number.luhn) resources.getText(R.string.bin_list_yes) else resources.getText(
                        R.string.bin_list_no
                    )
            }

            if (brand == null) textViewBrand.text = resources.getText(R.string.not_result)
            else textViewBrand.text = brand

            if (prepaid == null) textViewPrepaid.text = resources.getText(R.string.not_result)
            else textViewPrepaid.text =
                if (bankBinList.prepaid!!) resources.getText(R.string.bin_list_yes) else resources.getText(
                    R.string.bin_list_no
                )

            if (scheme == null) textViewScheme.text = resources.getText(R.string.not_result)
            else textViewScheme.text = scheme

            if (type == null) textViewType.text = resources.getText(R.string.not_result)
            else textViewType.text = type
        }
    }

    //Метод обнуления значений при отсутствии результатов
    private fun notFountBinList() = with(binding.includeBinList) {
        textViewBIN.text = binding.editTextBinList.text.toString()
        textViewBank.text = resources.getText(R.string.not_result)
        textViewUrlBank.text = resources.getText(R.string.not_result)
        textViewPhoneBank.text = resources.getText(R.string.not_result)
        textViewMapBank.text = resources.getText(R.string.not_result)
        textViewBrand.text = resources.getText(R.string.not_result)
        textViewCNLength.text = resources.getText(R.string.not_result)
        textViewCountry.text = resources.getText(R.string.not_result)
        textViewPrepaid.text = resources.getText(R.string.not_result)
        textViewScheme.text = resources.getText(R.string.not_result)
        textViewType.text = resources.getText(R.string.not_result)
        textViewCNLuhn.text = resources.getText(R.string.not_result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}