package word2vec

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Parser {
    fun searchInputWordVector(inputWord: LinkedHashMap<String, String>): LinkedHashMap<String, ArrayList<Double>> {
        // 入力した単語の素性ベクトル情報をサーチ
        val vectorMap = LinkedHashMap<String, ArrayList<Double>>()
        val searchBr = BufferedReader(FileReader(File(Setting.model)))
        var searchStr = searchBr.readLine()
        searchStr = searchBr.readLine()
        while (searchStr != null) {
//        println(searchStr)
            val split = searchStr.split(" ")
//        if(split[0] == "アンドロイド" || split[0] == "感情") {
            if (inputWord.containsKey(split[0])) {
                val scoreList = ArrayList<Double>()
                for (i in 1 until split.size - 1) {
                    scoreList.add(split.get(i).toDouble())
                }
                vectorMap.put(split[0], scoreList)
            }
            searchStr = searchBr.readLine()
        }
        println(vectorMap)
        searchBr.close()
        return vectorMap
    }
}