gnome-terminal -x bash -c "rm -f *.class;
javac -classpath .:../../gridworld.jar JumperRunner.java;
java -classpath .:../../gridworld.jar JumperRunner;
cd ~/Desktop/GridWorldCode/projects/Jumper;
javac -classpath .:../../gridworld.jar:/usr/lib/jvm/jdk1.8.0_91/jre/lib/junit-4.9.jar *.java;
java -classpath .:../../gridworld.jar::/usr/lib/jvm/jdk1.8.0_91/jre/lib/junit-4.9.jar -ea org.junit.runner.JUnitCore JumperTest;
read"
