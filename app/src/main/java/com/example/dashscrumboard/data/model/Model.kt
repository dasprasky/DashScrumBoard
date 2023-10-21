package com.example.dashscrumboard.data.model

import java.io.Serializable

enum class Status(val value: String) {
    TODO("todo"),
    IN_PROGRESS("progress"),
    DONE("done")
}

class TicketItem(
    var title: String,
    var description: String,
    var status: String,
    var id: String,
    var assignedTo: String,
) : Serializable {

    constructor():this("","","","", "")

}