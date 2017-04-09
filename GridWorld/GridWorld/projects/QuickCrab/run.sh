gnome-terminal -x bash -c "
rm -f *.class;
javac -classpath .:../../gridworld.jar QuickCrabRunner.java;
java -classpath .:../../gridworld.jar QuickCrabRunner;
read"
