package word2vec

//TODO:Calculator.ktのgetWord2Vecを高速化したい。ファイル読み込みをコルーチンで非同期処理。非同期処理内部でcosRankにputすると情報が落ちそうなので、asyncでputする情報を返し、awaitで受け取る
fun main(args: Array<String>) {
    val cal = Calculator()
    // 高性能モード・高速モードを選択し、それに応じたパラメータを設定
    Setting.mode = "HIGH_PERFORMANCE"
//    Setting.mode = "HIGH_SPEED"
    if (Setting.mode == "HIGH_PERFORMANCE") {
        Setting.model = "data/corpas/high_performance_model/model_201907.vec"
        Setting.modelWordText = "data/corpas/model_201907_word.txt"
        Setting.vectorSize = 300
        Setting.splitModelLineNum = 10000
        Setting.splitFileNum = 101
    } else if (Setting.mode == "HIGH_SPEED") {
        Setting.model = "data/corpas/high_speed_model/model_abstract_201907.vec"
        Setting.modelWordText = "data/corpas/model_abstract_201907_word.txt"
        Setting.vectorSize = 200
        Setting.splitModelLineNum = 2000
        Setting.splitFileNum = 79
    }

    // 入力する単語情報をセット
    val inputWord = linkedMapOf<String, String>()
    inputWord.put("感情", "Negative")
    inputWord.put("人間", "Positive")
    // 入力した単語の素性ベクトル情報をサーチ
    val vectorMap = Parser().searchInputWordVector(inputWord)
    // 単語ベクトルの演算
    val (calculatedVector, joinedWord) = cal.calWordVector(inputWord, vectorMap)
    // 入力した単語と他の単語のコサイン類似度を計算
    val cosRank = cal.getWord2Vec(inputWord, joinedWord, calculatedVector)
    // コサイン類似度の上位10位まで出力
    Sort().sortCosRank(cosRank)
}
