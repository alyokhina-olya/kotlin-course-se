package ru.hse.spb

@DslMarker
annotation class TexElementMarker

@TexElementMarker
interface TexElement {
    fun addToStringBuilder(builder: StringBuilder)
}


fun addToStringBuilder(name: String, arg: String, args: MutableList<String>, builder: StringBuilder) {
    builder.append("\\$name")

    if (args.size > 0) {
        builder.append(args.joinToString(",", "[", "]"))
    }

    builder.append("{$arg}\n")
}


class DocumentClass(private val mainArg: String, private val args: MutableList<String>) : TexElement {
    override fun addToStringBuilder(builder: StringBuilder) = addToStringBuilder("documentclass", mainArg, args, builder)
    operator fun String.unaryPlus() = args.add(this)

}

class UsePackage(private var packageName: String, private var args: MutableList<String>) : TexElement {
    override fun addToStringBuilder(builder: StringBuilder) = addToStringBuilder("usepackage", packageName, args, builder)
    operator fun String.unaryPlus() = args.add(this)
}


class Math(private var formula: String, private var args: MutableList<String>) : TexElement {
    override fun addToStringBuilder(builder: StringBuilder) {
        builder.append("$").append(formula).append("$\n")
    }

    operator fun String.unaryPlus() = args.add(this)
}

class FrameTitle(private var title: String) : TexElement {
    override fun addToStringBuilder(builder: StringBuilder) {
        builder.append("\\frametitle").append("{$title}\n")
    }
}


fun document(init: Document.() -> Unit): Document {
    val document = Document()
    document.init()
    return document
}