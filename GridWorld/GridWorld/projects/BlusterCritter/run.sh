gnome-terminal -x bash -c "
rm -f *.class;
javac -classpath .:../../gridworld.jar BlusterCritterRunner.java;
java -classpath .:../../gridworld.jar BlusterCritterRunner;
read"
