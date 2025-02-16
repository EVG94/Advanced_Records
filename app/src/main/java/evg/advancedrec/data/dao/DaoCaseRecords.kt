package evg.advancedrec.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import evg.advancedrec.data.model.CaseRecords

@Dao
interface DaoCaseRecords {

    @Query("select * from CaseRecords")
    fun getListCaseRecords(): List<CaseRecords>

    @Insert
    fun saveNewCase(newCase: CaseRecords)

    @Query("select * from CaseRecords where name=:name")
    fun getRecord(name: String): CaseRecords?

    @Update
    fun updateNewCase(newCase: CaseRecords)
    @Query("select * from CaseRecords where name like :oldSearch")
    fun getListCaseRecordsWithOldSearch(oldSearch: String): List<CaseRecords>


}