package com.example.resecondsense_v01

import android.net.Uri
import java.io.Serializable
import java.util.Date

data class data_Entries(var entry_Title: String, var hoursSpent: String, var entryDate: String,
                        var UserID: String, var imageData: String):
    Serializable {


}
