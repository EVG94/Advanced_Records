package evg.advancedrec.data

import androidx.room.Database
import androidx.room.RoomDatabase
import evg.advancedrec.data.dao.DaoCaseRecords
import evg.advancedrec.data.model.CaseRecords


@Database(
    entities = [CaseRecords::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoCaseRecords(): DaoCaseRecords
}