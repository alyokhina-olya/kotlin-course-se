package ru.hse.spb


import java.io.PrintStream

open class Tag(private val name: String, private val args: MutableList<String>) :TexElement {
    private val children = mutableListOf<TexElement>()
    override fun addToStringBuilder(builder: StringBuilder) {
        builder.append("\\begin{").append(name).append("}")

        if (args.size > 0) {
            builder.append(args.joinToString(",", "[", "]"))
        }
        builder.append("\n")
        children.forEach { it.addToStringBuilder(builder) }
        builder.append("\\end{").append(name).append("}\n")
    }

    operator fun String.unaryPlus() {
        val text=this
        children.add(object : TexElement {
            override fun addToStringBuilder(builder: StringBuilder) {
                builder.append("$text\n")
            }
        })
    }



    fun math(formula: String, vararg args: String) {
        children.add(Math(formula, args.toMutableList()))
    }

    fun itemize(vararg args: String, init: Itemize.() -> Unit) {
        val itemize = Itemize(args.toMutableList())
        itemize.init()
        children.add(itemize)
    }

    fun enumerate(vararg args: String, init: Enumerate.() -> Unit) {
        val enumerate = Enumerate(args.toMutableList())
        enumerate.init()
        children.add(enumerate)
    }


    fun customTag(name: String, vararg args: Pair<String, String>, init: CustomTag.() -> Unit)  {
        val customTag=CustomTag(name,args.map {it.first + "=" + it.second}.toMutableList())
        customTag.init()
        children.add(customTag)
    }

    fun left(init: Left.() -> Unit)  {
        val left = Left()
        left.init()
        children.add(left)
    }

    fun center(init: Center.() -> Unit) {
        val center = Center()
        center.init()
        children.add(center)
    }

    fun right(init: Right.() -> Unit) {
        val right = Right()
        right.init()
        children.add(right)
    }


    fun getChildren(): MutableList<TexElement> {
        return children
    }

    fun addChild(child:TexElement) {
        children.add(child)
    }
}


class Item(args: MutableList<String>) : Tag("item", args) {
    override fun addToStringBuilder(builder: StringBuilder) {
        builder.append("\\item\n")
        this.getChildren().forEach { it.addToStringBuilder(builder) }
    }
}

class Left : Tag("flushleft", mutableListOf())

class Center : Tag("center", mutableListOf())

class Right : Tag("flushright", mutableListOf())


class Frame(frameTitle: String, args: MutableList<String>) : Tag("frame", args) {
    init {
        this.addChild(FrameTitle(frameTitle))
    }
}

class CustomTag(name: String, args: MutableList<String>) : Tag(name, args)


class Document : Tag("document", mutableListOf()) {
    private val usedPackages = mutableListOf<UsePackage>()
    private var documentClass: DocumentClass? = null

    override fun addToStringBuilder(builder: StringBuilder) {
        documentClass!!.addToStringBuilder(builder)
        usedPackages.forEach { it.addToStringBuilder(builder) }
        super.addToStringBuilder(builder)
    }

    fun frame(frameTitle: String, vararg args: Pair<String, String>, init: Frame.() -> Unit) {
        val frame = Frame(frameTitle, args.map {it.first + "=" + it.second}.toMutableList())
        frame.init()
        this.addChild(frame)
    }

    fun documentClass(
            mainArg: String,
            vararg args: String,
            init: DocumentClass.() -> Unit = {}) {

        assert(documentClass == null) {
            "More than one document class found"
        }

        val documentClass = DocumentClass(mainArg, args.toMutableList())
        documentClass.init()
        this.documentClass = documentClass
    }

    fun usepackage(
            mainArg: String,
            vararg args: String,
            init: UsePackage.() -> Unit = {}
    ) {
        val usePackage = UsePackage(mainArg, args.toMutableList())
        usePackage.init()
        usedPackages.add(usePackage)
    }

    fun toOutputStream(outStream: PrintStream) {
        outStream.append(buildString(this::addToStringBuilder))
    }
}