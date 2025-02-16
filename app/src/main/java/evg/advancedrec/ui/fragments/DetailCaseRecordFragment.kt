package evg.advancedrec.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import evg.advancedrec.R
import evg.advancedrec.data.model.CaseRecords
import evg.advancedrec.databinding.FragmentDetailCaseRecordBinding
import evg.advancedrec.other.showSnackToast
import evg.advancedrec.ui.viewModel.DetailCaseRecordViewModel

@AndroidEntryPoint
class DetailCaseRecordFragment : Fragment() {
    private var _binding: FragmentDetailCaseRecordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailCaseRecordViewModel by viewModels()
    private var item: CaseRecords? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailCaseRecordBinding.inflate(inflater, container, false)

        val gson = Gson()
        val jsonString = arguments?.getString("jsonItem")
        item = gson.fromJson(jsonString, CaseRecords::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.action_detailCaseRecordFragment_to_toDoListFragment)
        }
        binding.btnComplete.setOnClickListener {
            viewModel.saveNewCase(
                binding.etNameCase.text.toString(),
                binding.etDescriptionCase.text.toString(),
                binding.cbCompleted.isChecked,
                binding.tvDateDetail.text.toString()
            )
        }
        viewModel.observeSaveCase.observe(viewLifecycleOwner) {
            when (it) {
                true -> findNavController().navigate(R.id.action_detailCaseRecordFragment_to_toDoListFragment)
                false -> showSnackToast(getString(R.string.add_data_case))
            }

        }
    }

    override fun onResume() {
        super.onResume()

        setDataOnItem()
    }

    private fun setDataOnItem() {

        item?.let { itemCase ->
            binding.etNameCase.apply {
                setText(itemCase.name)
                requestFocus()
                setSelection(itemCase.name.length)
            }
            binding.etDescriptionCase.apply {
                setText(itemCase.description)
                setSelection(itemCase.description.length)
            }
            binding.cbCompleted.isChecked = itemCase.completed
            binding.tvDateDetail.text = itemCase.date

        }

    }

    override fun onDestroy() {
        item = null
        _binding = null
        super.onDestroy()
    }

}