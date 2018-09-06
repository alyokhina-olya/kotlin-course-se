package ru.hse.spb


fun getGreeting(): String {
    val words = mutableListOf<String>()
    words.add("Hello,")
    words.add("world!")

    return words.joinToString(separator = " ")
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

fun run(wordsInSentence: List<String>): String {
    if (wordsInSentence.size == 1) {
        return if (isFemale(wordsInSentence[0]) || isMale(wordsInSentence[0])) "YES" else "NO"
    }

    val gender = isMale(wordsInSentence[0])
    var type = 0
    for (word in wordsInSentence) {
        val correctType = if (gender) isMale(word) else isFemale(word)
        if (!correctType) {
            return "NO"
        }
        when (type) {
            0 -> {
                type = when {
                    isAdjective(word) -> 0
                    isNoun(word) -> 1
                    else -> return "NO"
                }

            }
            1 -> if (!isVerb(word)) return "NO"
        }
    }
    return "YES"
}


fun main(args: Array<String>) {
    val wordsInSentence = readLine()!!.split(" ")
    print(run(wordsInSentence))
}
