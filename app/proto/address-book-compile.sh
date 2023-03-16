cd $(dirname $0)
SRC_DIR=.
DST_DIR=../scr/main/java
protoc -I=SRC_DIR --java_out=DST_DIR --kotlin_out=$DST_DIR $SRC_DIR/addressbook.proto