package evg.advancedrec.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import evg.advancedrec.R
import evg.advancedrec.data.adapter.ToDoListAdapter
import evg.advancedrec.data.model.CaseRecords
import evg.advancedrec.databinding.FragmentToDoListBinding
import evg.advancedrec.other.showSnackToast
import evg.advancedrec.ui.viewModel.ToDoListViewModel

@AndroidEntryPoint
class ToDoListFragment : Fragment() {
    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ToDoListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listData = mutableListOf<CaseRecords>()


        val adapter = ToDoListAdapter(listData) { itemCase ->
            val gson = Gson()
            val jsonString = gson.toJson(itemCase)
            val bundle = Bundle()
            bundle.putString("jsonItem", jsonString)
            binding.searchView.setQuery("", false)
            binding.searchView.clearFocus()
            findNavController().navigate(
                R.id.action_toDoListFragment_to_detailCaseRecordFragment,
                bundle
            )

        }

        binding.rvToDoList.adapter = adapter
        binding.rvToDoList.layoutManager = LinearLayoutManager(requireContext())
        viewModel.observerCaseList.observe(viewLifecycleOwner) {
            it?.let { list ->
                if (list.isNotEmpty()) adapter.setNewDataAdapter(list)
                else showSnackToast(getString(R.string.empty_case))
            }
        }

        binding.btnAddNewCase.setOnClickListener {
            findNavController().navigate(R.id.action_toDoListFragment_to_detailCaseRecordFragment)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        updateDataFragment()
    }

    private fun updateDataFragment() {
        val oldSearch = binding.searchView.query.toString()
        viewModel.getDataRecyclerView(oldSearch)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}