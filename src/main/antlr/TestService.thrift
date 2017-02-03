//kekek   ?ff ffg

/* 哈哈
不错哦
 */

 /*
 lllllllll
  */

namespace java com.github.yu000hong//haha

//haha, bucuo o

//shi me?

/*
hello block comment
haha
 */

//include 'hello world' /*
//**/

//haha

const string MESSAGE = 'hello world'  const i32 C_I =8

const i64 CONST_LONG = 8

const string HAHA = "haha"

const list<i64> LIST_LONG = [9 13 21]

const i64 TMP = CONST_LONG

typedef i64 long

typedef TweetType haha

enum TweetType

{
/*  不错哦 */
    TWEET,       // 1
    /*
    hfhdfhd
    dfdf
     */
    RETWEET=2, // 2
    DM = 0,    // 3
    REPLY
}

struct Person {//haha
/*
*/
    1:required i64 age

}

struct Tweet

{
    1: required i32 userId;
    2: required string userName;
    3: required string text;
    5: optional string tweetType  // 5
    16: optional string language = "english"
}

union Student

{
  1:string haha
}

exception Error{
    1: string cause
    2: string message
}

service TestService{

    /**
    * 添加站点
    */
    void add(
        /**
         * 站点对象
        */
        1:i32 siteId) throws (1:Error xce)
}