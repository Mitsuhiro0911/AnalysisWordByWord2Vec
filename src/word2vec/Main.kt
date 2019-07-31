package word2vec

fun main(args: Array<String>) {
    val cal = Calculator()
    // 高性能モード・高速モードを選択し、それに応じたパラメータを設定
//    Setting.mode = "HIGH_PERFORMANCE"
    Setting.mode = "HIGH_SPEED"
    if(Setting.mode == "HIGH_PERFORMANCE") {
        Setting.model = "data/corpas/model_201907.vec"
        Setting.vectorSize = 300
    } else if(Setting.mode == "HIGH_SPEED") {
        Setting.model = "data/corpas/model_abstract_201907.vec"
        Setting.vectorSize = 200
    }

    // 入力する単語情報をセット
    val inputWord = linkedMapOf<String, String>()
    inputWord.put("感情", "Negative")
    inputWord.put("人間", "Positive")

    val vectorMap = Parser().searchInputWordVector(inputWord)

    val (calculatedVector, joinedWord) = cal.calWordVector(inputWord, vectorMap)

    val cosRank = cal.getWord2Vec(inputWord, joinedWord, calculatedVector)

    Sort().sortCosRank(cosRank)
}
