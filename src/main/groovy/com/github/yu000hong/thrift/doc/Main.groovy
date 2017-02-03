package com.github.yu000hong.thrift.doc

import com.github.yu000hong.thrift.antlr.ThriftLexer
import com.github.yu000hong.thrift.antlr.ThriftParser
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker

public class Main {

    public static void main(String[] args) {
        def filename = 'src/main/antlr/TestService.thrift'
        parse(filename)
    }

    private static void parse(String filename) {
        def lexer = new ThriftLexer(new ANTLRFileStream(filename))
        def tokens = new CommonTokenStream(lexer)
        def parser = new ThriftParser(tokens)
        def tree = parser.document()
        def walker = new ParseTreeWalker()
        walker.walk(new ThriftWalker(), tree)
    }

}
