package ru.spb.sspk.ssdmd.phonebook_test.service

import org.springframework.stereotype.Service

@Service
interface PersonService {

    fun performingSearchByAllParameters(answer: String): String

}