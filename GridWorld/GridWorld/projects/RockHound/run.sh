gnome-terminal -x bash -c "
rm -f *.class;
javac -classpath .:../../gridworld.jar RockHoundRunner.java;
java -classpath .:../../gridworld.jar RockHoundRunner;
read"
