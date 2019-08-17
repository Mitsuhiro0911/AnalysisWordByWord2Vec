package word2vec

/**
 * 実行モード(高性能モード・高速モード)によって変動するパラメータのクラス
 */
class Setting {
    companion object {
        // 高性能モード・高速モードを区別する
        var mode = ""
        // 読み込む素性ベクトルのファイルパス
        var model = ""
        // モデルの単語情報のみを記載したファイルのパス
        var modelWordText = ""
        // 読み込む素性ベクトルの次元数
        var vectorSize = 0
        // 分割されたモデルファイルの行数
        var splitModelLineNum = 0
        // 分割されたモデルファイルの数
        var splitFileNum = 0
    }
}
