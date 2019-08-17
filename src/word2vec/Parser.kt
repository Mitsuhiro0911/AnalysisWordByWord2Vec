package word2vec

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * モデルファイル(Word2Vecで学習した素性ベクトル)をパースするクラス
 */
class Parser {
    /**
     * 入力した単語の素性ベクトルの情報を素性ベクトルファイルからサーチ
     */
    fun searchInputWordVector(inputWord: LinkedHashMap<String, String>): LinkedHashMap<String, ArrayList<Double>> {
        val vectorMap = LinkedHashMap<String, ArrayList<Double>>()
        val searchLocationBr = BufferedReader(FileReader(File(Setting.modelWordText)))
        var searchLocationStr = searchLocationBr.readLine()
        searchLocationStr = searchLocationBr.readLine()
        var i = 1
        val corpasIndexList = ArrayList<Int>()
        while (searchLocationStr != null) {
            if (inputWord.containsKey(searchLocationStr)) {
                corpasIndexList.add(i)
            }
            searchLocationStr = searchLocationBr.readLine()
            i = i.plus(1)
        }
        searchLocationBr.close()

        for (corpasIndex in corpasIndexList) {
            val targetFile = (corpasIndex / Setting.splitModelLineNum)
            val targetRow = corpasIndex % Setting.splitModelLineNum + 1
            println("${targetFile}")
            println("${targetRow}")
            println()
            val searchVectorBr = BufferedReader(
                FileReader(File("${Setting.model.substring(0, Setting.model.length - 4)}_${String.format("%03d", targetFile)}.vec")
                )
            )
            var searchVectorStr = searchVectorBr.readLine()
            var i = 1
            while (searchVectorStr != null) {
                if (i == targetRow) {
                    val split = searchVectorStr.split(" ")
                    val scoreList = ArrayList<Double>()
                    for (i in 1 until split.size - 1) {
                        scoreList.add(split.get(i).toDouble())
                    }
                    vectorMap.put(split[0], scoreList)
                }
                searchVectorStr = searchVectorBr.readLine()
                i = i.plus(1)
            }
        }
        println(vectorMap)
        return vectorMap
//        val vectorMap = LinkedHashMap<String, ArrayList<Double>>()
//        val searchBr = BufferedReader(FileReader(File(Setting.model)))
//        var searchStr = searchBr.readLine()
//        searchStr = searchBr.readLine()
//        while (searchStr != null) {
//            val split = searchStr.split(" ")
//            if (inputWord.containsKey(split[0])) {
//                val scoreList = ArrayList<Double>()
//                for (i in 1 until split.size - 1) {
//                    scoreList.add(split.get(i).toDouble())
//                }
//                vectorMap.put(split[0], scoreList)
//            }
//            searchStr = searchBr.readLine()
//        }
//        println(vectorMap)
//        searchBr.close()
//        return vectorMap
    }
}