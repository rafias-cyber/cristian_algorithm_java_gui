cd pac1/
rm *.class
javac --module-path /usr/share/openjfx/lib --add-modules=javafx.controls,javafx.fxml,javafx.base,javafx.media,javafx.web,javafx.swing *.java
cd ..
java -classpath . -Djava.rmi.server.codebase=file:. -Djava.security.manager -Djava.security.policy=./my.policy --module-path /usr/share/openjfx/lib --add-modules=javafx.controls -Dprism.forceGPU=true pac1.FxSample
