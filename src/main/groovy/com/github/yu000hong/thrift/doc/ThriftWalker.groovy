package com.github.yu000hong.thrift.doc

import com.github.yu000hong.thrift.antlr.ThriftBaseListener
import com.github.yu000hong.thrift.antlr.ThriftParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode

class ThriftWalker extends ThriftBaseListener {
    private String currentComment
    private String currentBlockComment
    private Gson gson = new GsonBuilder().setPrettyPrinting().create()

    @Override
    public void enterInclude(ThriftParser.IncludeContext ctx) {
        println('\n=======================================')
        println('Include')
        if (currentComment != null) {
            println("comment with include: " + currentComment)
            currentComment = null
        }
        if (currentBlockComment != null) {
            println("block comment with include: " + currentBlockComment)
            currentBlockComment = null
        }
    }

    @Override
    public void enterNamespace(ThriftParser.NamespaceContext ctx) {
        println('\n=======================================')
        println('Namespace')
        if (currentComment != null) {
            println("comment with namespace: " + currentComment)
            currentComment = null
        }
        if (currentBlockComment != null) {
            println("block comment with namespace: " + currentBlockComment)
            currentBlockComment = null
        }
    }

    @Override
    public void enterComment(ThriftParser.CommentContext ctx) {
        if (ctx.BlockComment()) {
            currentBlockComment = ctx.BlockComment().text
        }
        if (ctx.LineComment()) {
            currentComment = ctx.LineComment().text
        }
    }

    @Override
    public void enterConstDefinition(ThriftParser.ConstDefinitionContext ctx) {
        println('\n=======================================')
        println('Const')
        if (currentComment != null) {
            println("comment with const: " + currentComment)
            currentComment = null
        }
        if (currentBlockComment != null) {
            println("block comment with const: " + currentBlockComment)
            currentBlockComment = null
        }

        def identifier = ctx.Identifier().text
        println("Constant identifier: " + identifier)
        def filedTypeContext = ctx.fieldType()
        println("Constant Type: " + getFiledType(filedTypeContext))
        def constValueContext = ctx.constValue()
        println("Constant Value: " + gson.toJson(getConstValue(constValueContext)))

    }

    @Override
    public void enterTypedefDefinition(ThriftParser.TypedefDefinitionContext ctx) {
        println('\n=======================================')
        println('Typedef')
        println(getFiledType(ctx.fieldType()))
        println(ctx.Identifier().text)
    }

    @Override
    public void enterEnumDefinition(ThriftParser.EnumDefinitionContext ctx) {
        println('\n=======================================')
        println('Enum')
        println(ctx.Identifier().text)
        println('-------')
        ctx.enumItem().each {
            if (it.Identifier()) {
                print(it.Identifier().text)
                if (it.NumberConstant()) {
                    print("(" + it.NumberConstant().text + ")")
                }
                if (currentBlockComment) {
                    print(": has comment($currentBlockComment)")
                }
                println()
            }
        }
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
//        println("every")
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
//        println(node.text)
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
//        currentComment = null
    }

    private static String getFiledType(ThriftParser.FieldTypeContext ctx) {
        if (ctx.BaseType()) {
            return ctx.BaseType().text
        } else if (ctx.mapType()) {
            return "map<" + getFiledType(ctx.mapType().fieldType(0)) +
                    "," + getFiledType(ctx.mapType().fieldType(1)) + ">"
        } else if (ctx.setType()) {
            return "set<" + getFiledType(ctx.setType().fieldType()) + ">"
        } else if (ctx.listType()) {
            return "list<" + getFiledType(ctx.listType().fieldType()) + ">"
        } else if (ctx.Identifier()) {
            return ctx.Identifier().text
        } else {
            throw new RuntimeException('should not go here')
        }
    }

    private static Object getConstValue(ThriftParser.ConstValueContext ctx) {
        if (ctx.NumberConstant()) {
            return ctx.NumberConstant().text
        } else if (ctx.String()) {
            return ctx.String().text
        } else if (ctx.constList()) {
            return getListValue(ctx.constList())
        } else if (ctx.constMap()) {
            return getMapValue(ctx.constMap())
        } else if (ctx.Identifier()) {
            return ctx.Identifier().text
        } else {
            throw new RuntimeException()
        }
    }

    private static List getListValue(ThriftParser.ConstListContext ctx) {
        def list = []
        ctx.constValue().each {
            list << getConstValue(it)
        }
        return list
    }

    private static Map getMapValue(ThriftParser.ConstMapContext ctx) {
        def map = [:]
        def len = ctx.constValue().size() / 2
        for (int i = 0; i < len; i++) {
            def key = getConstValue(ctx.constValue(2 * i))
            def value = getConstValue(ctx.constValue(2 * i + 1))
            map[key] = value
        }
        return map
    }

}
