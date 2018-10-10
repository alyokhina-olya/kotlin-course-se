package ru.hse.spb


open class ItemizedTag(private val name: String,
                       private val args: MutableList<String>) : TexElement {
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

    fun item(vararg args: String, init: Item.() -> Unit) {
        val item = Item(args.toMutableList())
        item.init()
        children.add(item)
    }
}

class Itemize(args: MutableList<String>) : ItemizedTag("itemize", args)

class Enumerate(args: MutableList<String>) : ItemizedTag("enumerate", args)