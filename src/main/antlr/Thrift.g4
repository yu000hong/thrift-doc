/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

grammar Thrift;

@header {
    package com.github.yu000hong.thrift.antlr;
}

document
    :   (statement NewLine*)* EOF
    ;

statement
    :   comment
    |   header comment?
    |   definition comment?
    ;

header
    :   include
    |   namespace
    ;

include
    :   'include' String
    ;

namespace
    :   'namespace' ('java'|'*') Identifier
    ;

definition
    :   constDefinition
    |   typedefDefinition
    |   enumDefinition
    |   unionDefinition
    |   exceptionDefinition
    |   structDefinition
    |   serviceDefinition
    ;

constDefinition
    :   'const' fieldType Identifier Equal constValue ListSeparator?
    ;

typedefDefinition
    :   'typedef' fieldType Identifier
    ;

enumDefinition
    :   'enum' Identifier '{' enumItem* '}'
    ;

enumItem
    :   Identifier ('=' NumberConstant)? ListSeparator? comment?
    |   comment
    |   NewLine
    ;

unionDefinition
    :   'union' Identifier '{' fieldItem* '}'
    ;

exceptionDefinition
    :   'exception' Identifier '{' fieldItem* '}'
    ;

structDefinition
    :   'struct' Identifier '{' fieldItem* '}'
    ;

serviceDefinition
    :   'service' Identifier ('extends' Identifier)? '{' serviceItem* '}'
    ;

serviceItem
    :   function
    |   comment
    |   NewLine
    ;

function
    :   Oneway? functionType Identifier '(' fieldItem* ')' (Throws '(' fieldItem* ')')? ListSeparator?
    ;

functionType
    :   fieldType
    |   Void
    ;

fieldItem
    :   field
    |   comment
    |   NewLine
    ;

field
    :   NumberConstant Colon FieldReq? fieldType Identifier (Equal constValue)? ListSeparator?
    ;

comment
    :   LineComment
    |   BlockComment
    ;

LineComment
    :   '//' (~'\n')*
    ;

BlockComment
    :   ('/*' .*? '*/')
    ;

BaseType
    :   'bool'
    |   'byte'
    |   'i8'
    |   'i16'
    |   'i32'
    |   'i64'
    |   'double'
    |   'string'
    |   'binary'
    ;

Void
    :   'void'
    ;

mapType
    :   'map' '<' fieldType Comma fieldType '>'
    ;

setType
    :   'set' '<' fieldType '>'
    ;

listType
    :   'list' '<' fieldType '>'
    ;

fieldType
    :   BaseType
    |   mapType
    |   setType
    |   listType
    |   Identifier
    ;

//constant

constValue
    :   NumberConstant
    |   String
    |   constList
    |   constMap
    |   Identifier
    ;

NumberConstant
    :   IntConstant ('.' Digit+)? ( ('E' | 'e') IntConstant )?
    ;

fragment
IntConstant
    :   (Positive|Negative)? Digit+
    ;

constList
    :   LeftSquareBracket (constValue ListSeparator?)* RightSquareBracket
    ;

constMap
    :   LeftBracket (constValue Colon constValue ListSeparator?)* RightBracket
    ;

FieldReq
    :   'required'
    |   'optional'
    ;

Oneway
    :   'oneway'
    ;

Throws
    :   'throws'
    ;

NewLine
    :   '\n'
    ;

Identifier
    :   ( Letter | '_' ) ( Letter | Digit | '.' | '_' )*
    ;

String
    :   '"' (~'"')* '"'
    |   '\'' (~'\'')* '\''
    ;

Letter
    :   [A-Z] | [a-z]
    ;

Digit
    :   [0-9]
    ;

ListSeparator
    :   Comma
    |   Semicolon
    ;

LeftSquareBracket
    :   '['
    ;

RightSquareBracket
    :   ']'
    ;

LeftBracket
    :   '{'
    ;

RightBracket
    :   '}'
    ;

Colon
    :   ':'
    ;

Semicolon
    :   ';'
    ;

Equal
    :   '='
    ;

Comma
    :   ','
    ;

Positive
    :   '+'
    ;

Negative
    :   '-'
    ;

WS
    :   [ |\t\r\n]+ -> skip
    ;
