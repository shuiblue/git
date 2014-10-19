SOURCE_FILES = \
client/ChatLineListener.java \
client/Client.java \
client/CmdClient.java \
server/Auth.java \
server/Connection.java \
server/Server.java \
common/Base64Encrypt.java \
common/CallBack.java \
common/EncryptHelper.java \
common/ReverseEncrypt.java \
common/ProfanityFilter.java \
common/TextMessage.java \
common/User.java \
start/MainServer.java

JAVAC = javac

vpath %.class bin
vpath %.java src

build: $(SOURCE_FILES:.java=.class)

#pattern rule
%.class: %.java
	$(JAVAC) -d bin/ src/client/*.java src/server/*.java src/common/*.java src/start/*.java


clean:
	-rm -frv bin/*
run:
	java -classpath bin start.MainServer

