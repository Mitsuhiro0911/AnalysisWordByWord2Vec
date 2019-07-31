package word2vec

class Sort {
    fun sortCosRank(cosRank: LinkedHashMap<String, Double>): Unit{
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
}