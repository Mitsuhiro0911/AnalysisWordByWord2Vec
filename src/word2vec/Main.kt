package word2vec


import java.io.BufferedReader
import java.io.File
import java.io.FileReader

//TODO:コーパスが大きすぎるので、名詞のみに限定するといった方法で削減する
//TODO:マップに大規模コーパスの情報すべてを格納は難しい。入力した単語だけマップにputし、数回コーパスを走査する仕様にする

fun main(args: Array<String>) {
    val cal = Calculator()

    // 入力した単語の素性ベクトル情報をサーチ
    val searchBr = BufferedReader(FileReader(File("data/corpas/model_201907.vec")))
    var searchStr = searchBr.readLine()
    searchStr = searchBr.readLine()
    val vectorMap = LinkedHashMap<String, ArrayList<Double>>()
    while(searchStr != null){
        println(searchStr)
        val split = searchStr.split(" ")
        if(split[0] == "パズドラ" || split[0] == "ゲーム") {
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
    for(i in 0 until 300){
        calculatedScore.add(0.0)
    }
    for(vector in vectorMap) {
        joinedWord = joinedWord + vector.key
        for(i in 0 until vector.value.size) {
            calculatedScore[i] = calculatedScore[i] +  vector.value.get(i)
        }
    }
    calculatedVector.put(joinedWord, calculatedScore)
    println(calculatedVector)

    // 入力した単語と他の単語のコサイン類似度を計算
    val cosBr = BufferedReader(FileReader(File("data/corpas/model_201907.vec")))
    var cosStr = cosBr.readLine()
    cosStr = cosBr.readLine()
    while(cosStr != null){
        val scoreList = ArrayList<Double>()
        val split = cosStr.split(" ")
        for (i in 1 until split.size - 1) {
            scoreList.add(split.get(i).toDouble())
        }
        val word = calculatedVector.get(joinedWord)!!.toDoubleArray()
        val compareWord = scoreList.toDoubleArray()
        val cosSimilarity = cal.calCosSimilarity(word, compareWord)
        println(cosSimilarity)
        cosStr = cosBr.readLine()
    }
    cosBr.close()
}