package evg.advancedrec.repository

import dagger.hilt.android.scopes.ViewModelScoped
import evg.advancedrec.data.model.CaseRecords
import javax.inject.Inject

@ViewModelScoped
class MainRepository @Inject constructor(
    private val localRepository: LocalRepository
) {
    fun getListCaseRecords(): List<CaseRecords>  {
      return  localRepository.getListCaseRecords()
    }

    fun saveNewCase(newCase: CaseRecords) {
        localRepository.saveNewCase(newCase)
    }

    fun getListCaseRecordsWithOldSearch(oldSearch: String): List<CaseRecords> {
        return  localRepository.getListCaseRecordsWithOldSearch(oldSearch)
    }
}