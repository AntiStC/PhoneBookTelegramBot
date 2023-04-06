package ru.spb.sspk.ssdmd.phonebook_test.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var firstname: String = ""
    var middleName: String = ""
    var lastname: String = ""
    var block: String = ""
    var department: String = ""
    var sector: String = ""
    var phone: String = ""
    var mobilPhone: String = ""
}