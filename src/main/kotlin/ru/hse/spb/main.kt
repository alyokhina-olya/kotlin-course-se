package ru.hse.spb

enum class stage{
    WAS_NOT_NOUN, WAS_NOUN
}

fun isAdjective(word: String): Boolean {
    return word.endsWith("lios") || word.endsWith("liala")
}


fun isNoun(word: String): Boolean {
    return word.endsWith("etr") || word.endsWith("etra")
}


fun isVerb(word: String): Boolean {
    return word.endsWith("initis") || word.endsWith("inites")
}

fun isMale(word: String): Boolean {
    return word.endsWith("lios") || word.endsWith("etr") || word.endsWith("initis")
}


fun isFemale(word: String): Boolean {
    return word.endsWith("liala") || word.endsWith("etra") || word.endsWith("inites")
}

fun run(wordsInSentence: List<String>): Boolean {
    if (wordsInSentence.size == 1) {
        return isFemale(wordsInSentence[0]) || isMale(wordsInSentence[0])
    }

    val isFirstMale = isMale(wordsInSentence[0])
    var type = stage.WAS_NOT_NOUN
    for (word in wordsInSentence) {
        val correctType = if (isFirstMale) isMale(word) else isFemale(word)
        if (!correctType) {
            return correctType
        }
        when (type) {
            stage.WAS_NOT_NOUN -> {
                type = when {
                    isAdjective(word) -> stage.WAS_NOT_NOUN
                    isNoun(word) -> stage.WAS_NOUN
                    else -> return false
                }

            }
            stage.WAS_NOUN -> if (!isVerb(word)) return isVerb(word)
        }
    }
    return type == stage.WAS_NOUN
}


fun main(args: Array<String>) {
    val wordsInSentence = readLine()!!.split(" ")
    print(if (run(wordsInSentence)) "YES" else "NO")
}
