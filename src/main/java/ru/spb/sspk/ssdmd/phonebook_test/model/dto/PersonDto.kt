package ru.spb.sspk.ssdmd.phonebook_test.model.dto

data class PersonDto(
    val firstname: String,
    val middleName: String,
    val lastname: String,
    val block: String,
    val department: String,
    val sector: String,
    val phone: String,
    val mobilPhone: String
)
