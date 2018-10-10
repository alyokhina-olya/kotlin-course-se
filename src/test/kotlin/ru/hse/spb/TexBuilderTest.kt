import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.hse.spb.document
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class TexTests {
    private val byteOutputStream = ByteArrayOutputStream()
    private val printStream = PrintStream(byteOutputStream)

    @Before
    fun clear() {
        byteOutputStream.flush()
        printStream.flush()

    }

    @Test
    fun exampleTest() {
        document {
            documentClass("beamer")
            usepackage("babel", "russian" /* varargs */)
            frame("frametitle", "arg1" to "arg2") {
                itemize {
                    for (row in listOf(1, 2, 3, 4)) {
                        item { + "$row text" }
                    }
                }

                // begin{pyglist}[language=kotlin]...\end{pyglist}
                customTag("pyglist", "language" to "kotlin") {
                    +"val a = 1"
                }
            }
        }.toOutputStream(printStream)
        val expected = """
            |\documentclass{beamer}
            |\usepackage[russian]{babel}
            |\begin{document}
            |\begin{frame}[arg1=arg2]
            |\frametitle{frametitle}
            |\begin{itemize}
            |\item
            |1 text
            |\item
            |2 text
            |\item
            |3 text
            |\item
            |4 text
            |\end{itemize}
            |\begin{pyglist}[language=kotlin]
            |val a = 1
            |\end{pyglist}
            |\end{frame}
            |\end{document}
            |""".trimMargin()
        assertEquals(expected, String(byteOutputStream.toByteArray()))
    }


    @Test
    fun testTextPosition() {
        document {
            documentClass("beamer")
            usepackage("babel", "russian" /* varargs */)
            left {
                +"text 1"
                center { +"text 2" }
            }
            right { +"text 3" }

        }.toOutputStream(printStream)

        assertEquals(String(byteOutputStream.toByteArray()), """
            |\documentclass{beamer}
            |\usepackage[russian]{babel}
            |\begin{document}
            |\begin{flushleft}
            |text 1
            |\begin{center}
            |text 2
            |\end{center}
            |\end{flushleft}
            |\begin{flushright}
            |text 3
            |\end{flushright}
            |\end{document}
            |""".trimMargin())
    }


    @Test
    fun testFormulas() {
        document {
            documentClass("article")
            center {
                math("x_i = x^2 \\cdot 3")
            }

        }.toOutputStream(printStream)

        assertEquals(String(byteOutputStream.toByteArray()), """
            |\documentclass{article}
            |\begin{document}
            |\begin{center}
            |${'$'}x_i = x^2 \cdot 3${'$'}
            |\end{center}
            |\end{document}
            |""".trimMargin())
    }

    @Test
    fun testItemize() {
        document {
            documentClass("article")
            center {
                +"lalala"

                enumerate {
                    for (row in listOf(1, 2, 3, 4)) {
                        item { +"$row" }
                    }
                    item { +"test" }
                }
            }
        }.toOutputStream(printStream)

        assertEquals(String(byteOutputStream.toByteArray()), """
            |\documentclass{article}
            |\begin{document}
            |\begin{center}
            |lalala
            |\begin{enumerate}
            |\item
            |1
            |\item
            |2
            |\item
            |3
            |\item
            |4
            |\item
            |test
            |\end{enumerate}
            |\end{center}
            |\end{document}
            |""".trimMargin())
    }


    @Test
    fun testFrame() {
        document {
            documentClass("beamer")
            frame(frameTitle = "test") {
                +"lalala"
            }
        }.toOutputStream(printStream)

        assertEquals(String(byteOutputStream.toByteArray()), """
            |\documentclass{beamer}
            |\begin{document}
            |\begin{frame}
            |\frametitle{test}
            |lalala
            |\end{frame}
            |\end{document}
            |""".trimMargin())
    }
}