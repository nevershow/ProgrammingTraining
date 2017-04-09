gnome-terminal -x bash -c "
rm -f *.class;
javac -classpath .:../../gridworld.jar KingCrabRunner.java;
java -classpath .:../../gridworld.jar KingCrabRunner;
read"
