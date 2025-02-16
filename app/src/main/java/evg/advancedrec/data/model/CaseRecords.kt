package evg.advancedrec.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CaseRecords")
data class CaseRecords(
    @PrimaryKey
    val name:String,
    val description:String,
    val completed:Boolean,
    val date:String
)
