package com.example.rama.utils

import java.util.*
import kotlin.properties.Delegates

object Content {

        var listOfProcessTypes= mutableListOf("seleccion un tipo", "tutela", "divorcio", "ni idea", "de que","poner")
        var listOfProcessTypesParaAdapter= listOfProcessTypes

        var monthProcess by Delegates.notNull<Int>()
        var dayProcess by Delegates.notNull<Int>()
        var yearProcess by Delegates.notNull<Int>()
        var dateManualProcess by Delegates.notNull<String>()
        var dateManualProcessDate by Delegates.notNull<Date>()

        var processType by Delegates.notNull<String>()
        var diasHabiles=0

        var monthTramite by Delegates.notNull<Int>()
        var dayTramite by Delegates.notNull<Int>()
        var yearTramite by Delegates.notNull<Int>()
        var hourTramite by Delegates.notNull<Int>()
        var minuteTramite by Delegates.notNull<Int>()

        var processFileSelected by Delegates.notNull<String>()

        //proceso seleccionado para fichero

        var openFicheroRadicado by Delegates.notNull<String>()
        var openFicheroAccionante by Delegates.notNull<String>()
        var openFicheroAccionado by Delegates.notNull<String>()
        var openFicheroJuzgado by Delegates.notNull<String>()
        var openFicheroDate by Delegates.notNull<String>()
        var openFicheroProcessType by Delegates.notNull<String>()
        var openFicheroDescription by Delegates.notNull<String>()



}