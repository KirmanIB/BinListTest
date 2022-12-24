package smart.ib.corp.binlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import smart.ib.corp.binlist.app.App
import smart.ib.corp.binlist.databinding.FragmentHistoryBinListBinding
import smart.ib.corp.binlist.mvvm.BinListViewModel
import smart.ib.corp.binlist.rcView.Adapter
import smart.ib.corp.binlist.room.BinListDao

class HistoryBinListFragment : Fragment() {

    private var _binding: FragmentHistoryBinListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHistoryBinListBinding.inflate(inflater, container, false)
        return binding.root

    }

    //Инициализация viewModel BinListViewModel
    private val viewModel: BinListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val binListDao: BinListDao = (requireActivity().application as App).db.binListDao()
                return BinListViewModel(binListDao, resources) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcBinList.layoutManager = LinearLayoutManager(requireContext())

        //Подписка на flow  для отображения истории запросов из базы данных.
        lifecycleScope.launchWhenStarted {
            viewModel.allBinList.collect {
                adapter = Adapter(it, resources)
                binding.rcBinList.adapter = adapter
                binding.listEmptySql.isVisible = it.isEmpty()
            }
        }

        //Кнопка очистки всех результатов из базы данных
        binding.buttonClear.setOnClickListener {
            viewModel.dellButton()
            binding.listEmptySql.isVisible = true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}