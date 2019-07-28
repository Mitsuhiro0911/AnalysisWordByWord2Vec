package word2vec


import java.io.BufferedReader
import java.io.File
import java.io.FileReader

//TODO:コーパスが大きすぎるので、名詞のみに限定するといった方法で削減する
//TODO:マップに大規模コーパスの情報すべてを格納は難しい。入力した単語だけマップにputし、数回コーパスを走査する仕様にする

fun main(args: Array<String>) {
    val br = BufferedReader(FileReader(File("data/corpas/model_201907.vec")))
    var str = br.readLine()
    str = br.readLine()
    val vectorMap = LinkedHashMap<String, ArrayList<Double>>()
    while(str != null){
        println(str)
        val split = str.split(" ")
        if(split[0] == "パズドラ" || split[0] == "ゲーム") {
            val scoreList = ArrayList<Double>()
            for (i in 1 until split.size - 1) {
                scoreList.add(split.get(i).toDouble())
            }
            vectorMap.put(split[0], scoreList)
        }
        str = br.readLine()
    }
    println(vectorMap)
}