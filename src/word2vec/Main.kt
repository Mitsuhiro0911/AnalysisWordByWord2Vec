package word2vec

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    val br = BufferedReader(FileReader(File("data/corpas/model_201907.vec")))
    var str = br.readLine()
    while(str != null){
        println(str)
        str = br.readLine()
    }
}