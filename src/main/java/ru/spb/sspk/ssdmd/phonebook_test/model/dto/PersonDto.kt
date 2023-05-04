package ru.spb.sspk.ssdmd.phonebook_test.model.dto

data class PersonDto(
    var firstname: String = "",
    var middleName: String = "",
    var lastname: String = "",
    var block: String? = "",
    var department: String? = "",
    var sector: String? = "",
    var phone: String = "",
    var mobilPhone: String = ""
) {
    override fun toString(): String {
        return "$lastname $firstname $middleName\n     $department\n     #$phone\n +7$mobilPhone"
    }
}

