package evg.advancedrec.repository

import evg.advancedrec.data.dao.DaoCaseRecords
import evg.advancedrec.data.model.CaseRecords
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val daoCaseRecords: DaoCaseRecords
) {
    fun getListCaseRecords(): List<CaseRecords> {
        return daoCaseRecords.getListCaseRecords()
    }

    fun saveNewCase(newCase: CaseRecords) {
        val record = daoCaseRecords.getRecord(newCase.name)
        if (record != null) daoCaseRecords.updateNewCase(newCase)
        else daoCaseRecords.saveNewCase(newCase)
    }

    fun getListCaseRecordsWithOldSearch(oldSearch: String): List<CaseRecords> {
        val newQuery = "$oldSearch%"
        return daoCaseRecords.getListCaseRecordsWithOldSearch(newQuery)
    }
}