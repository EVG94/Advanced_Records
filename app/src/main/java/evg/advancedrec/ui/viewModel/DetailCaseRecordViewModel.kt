package evg.advancedrec.ui.viewModel

import android.annotation.SuppressLint
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import evg.advancedrec.data.model.CaseRecords
import evg.advancedrec.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DetailCaseRecordViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {
    val observeSaveCase = MutableLiveData<Boolean>()

    fun saveNewCase(name: String, description: String, checked: Boolean, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isNotBlank() && description.isNotBlank()){
                val realDate = date.ifEmpty { getDate() }
                val newCase = CaseRecords(name,description,checked,realDate)
                repository.saveNewCase(newCase)
                observeSaveCase.postValue(true)
            } else observeSaveCase.postValue(false)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        var timeAndDate = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val pattern = "yyyy.MM.dd hh:mm:ss"
            val sdf = SimpleDateFormat(pattern)
            timeAndDate = sdf.format(Date())
        } else {
            val currentDate = Date()
            val dateFormat: DateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            val dateText: String = dateFormat.format(currentDate)
            val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val timeText: String = timeFormat.format(currentDate)
            timeAndDate = dateText + "" + timeText
        }
        return timeAndDate
    }
}