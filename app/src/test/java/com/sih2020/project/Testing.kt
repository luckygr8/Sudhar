package com.sih2020.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

fun main(){
    Scanner(System.`in`).next()
}

private fun gotResults(result:String) = print(result)


private fun doInBackground(){
    var result = "raw"
    CoroutineScope(Dispatchers.Default).launch {
        delay(5000)
        result = "fetched 100 records from the server"
        gotResults(result)
    }

}