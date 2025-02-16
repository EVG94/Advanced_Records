package evg.advancedrec.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import evg.advancedrec.data.model.CaseRecords
import evg.advancedrec.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val observerCaseList = MutableLiveData<List<CaseRecords>>()


    fun getDataRecyclerView(oldSearch: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listCase = if (oldSearch.isNotEmpty()) {
                repository.getListCaseRecordsWithOldSearch(oldSearch)
            } else {
                repository.getListCaseRecords()
            }
            observerCaseList.postValue(listCase)
        }
    }


}