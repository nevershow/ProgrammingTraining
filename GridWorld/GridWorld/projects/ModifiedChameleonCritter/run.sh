gnome-terminal -x bash -c "
rm -f *.class;
javac -classpath .:../../gridworld.jar ModifiedChameleonCritterRunner.java;
java -classpath .:../../gridworld.jar ModifiedChameleonCritterRunner;
read"
