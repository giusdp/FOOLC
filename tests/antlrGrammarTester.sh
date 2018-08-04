shopt -s expand_aliases
antlrPath="$1"
grammar="$2"
testfile="$3"
startRule="$4"

filename=$(basename -- "$2")
extension=$([[ "$filename" = *.* ]] && echo ".${filename##*.}" || echo '')
filename="${filename%.*}"
unset CLASSPATH
export CLASSPATH='.:'"$antlrPath"':$CLASSPATH'
echo $CLASSPATH
echo 'java -Xmx500M -cp /usr/local/lib/antlr-4.6-complete.jar org.antlr.v4.Tool'
alias antlr4='java -Xmx500M -cp /usr/local/lib/antlr-4.6-complete.jar org.antlr.v4.Tool'
alias grun='java org.antlr.v4.gui.TestRig'
mkdir /tmp/antlrGrammar
antlr4 -o /tmp/antlrGrammar "$2"
cd /tmp/antlrGrammar
javac "$filename"*.java
grun "$filename" "$startRule"  "$testfile" -gui
cd ~
rm -r /tmp/antlrGrammar
