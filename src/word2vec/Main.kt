package word2vec


import java.io.BufferedReader
import java.io.File
import java.io.FileReader


fun main(args: Array<String>) {
    val cal = Calculator()
    // 高性能モード・高速モードを選択し、それに応じたパラメータを設定
    Setting.mode = "HIGH_PERFORMANCE"
//    Setting.mode = "HIGH_SPEED"
    if(Setting.mode == "HIGH_PERFORMANCE") {
        Setting.model = "data/corpas/model_201907.vec"
        Setting.vectorSize = 300
    } else if(Setting.mode == "HIGH_SPEED") {
        Setting.model = "data/corpas/model_abstract_201907.vec"
        Setting.vectorSize = 200
    }

    // 入力する単語情報をセット
    val inputWord = linkedMapOf<String, String>()
    inputWord.put("人間", "Positive")
    inputWord.put("感情", "Negative")

    // 入力した単語の素性ベクトル情報をサーチ
    val searchBr = BufferedReader(FileReader(File(Setting.model)))
    var searchStr = searchBr.readLine()
    searchStr = searchBr.readLine()
    val vectorMap = LinkedHashMap<String, ArrayList<Double>>()
    while(searchStr != null){
//        println(searchStr)
        val split = searchStr.split(" ")
//        if(split[0] == "アンドロイド" || split[0] == "感情") {
        if(inputWord.containsKey(split[0])) {
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

    // 単語ベクトルの演算
    val calculatedVector = LinkedHashMap<String, ArrayList<Double>>()
    var joinedWord = ""
    val calculatedScore = ArrayList<Double>()
    for(i in 0 until Setting.vectorSize){
        calculatedScore.add(0.0)
    }
    for(vector in vectorMap) {
        joinedWord = joinedWord + vector.key
        // Positiveの場合、ベクトルを加算。Nevativeの場合、ベクトルを減算。
        if(inputWord.get(vector.key) == "Positive") {
            for (i in 0 until vector.value.size) {
                calculatedScore[i] = calculatedScore[i] + vector.value.get(i)
            }
        } else if(inputWord.get(vector.key) == "Negative"){
            for (i in 0 until vector.value.size) {
                calculatedScore[i] = calculatedScore[i] - vector.value.get(i)
            }
        }
    }
    calculatedVector.put(joinedWord, calculatedScore)
    println(calculatedVector)

    // 入力した単語と他の単語のコサイン類似度を計算
    val cosBr = BufferedReader(FileReader(File(Setting.model)))
    var cosRank = LinkedHashMap<String, Double>()
    var cosStr = cosBr.readLine()
    cosStr = cosBr.readLine()
    while(cosStr != null){
        val scoreList = ArrayList<Double>()
        val split = cosStr.split(" ")
        // 演算に用いた単語はコサイン類似度計算の対象から除外
//        if (split[0] != "アンドロイド" && split[0] != "感情") {
        if (!inputWord.containsKey(split[0])) {
            for (i in 1 until split.size - 1) {
                scoreList.add(split.get(i).toDouble())
            }
            val word = calculatedVector.get(joinedWord)!!.toDoubleArray()
            val compareWord = scoreList.toDoubleArray()
            val cosSimilarity = cal.calCosSimilarity(word, compareWord)
//            println(cosSimilarity)
            cosRank.put(split[0], cosSimilarity)
        }
//        cosRank = cosRank.toList().sortedByDescending { it.second }.toMap()
        cosStr = cosBr.readLine()
    }
    cosBr.close()
    val sortedAnswer = cosRank.toList().sortedByDescending { it.second }.toMap()
    var i = 0
    for(ans in sortedAnswer){
        i = i.plus(1)
        println("${i}位：${ans}")
        if(i > 9) {
            break
        }
    }
}

class Setting {
    companion object {
        // 高性能モード・高速モードを区別する
        var mode = ""
        // 読み込む素性ベクトルのファイルパス
        var model = ""
        // 読み込む素性ベクトルの次元数
        var vectorSize = 0
    }
}