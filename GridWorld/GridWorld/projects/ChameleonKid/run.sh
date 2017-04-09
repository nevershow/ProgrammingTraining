gnome-terminal -x bash -c "
rm -f *.class;
javac -classpath .:../../gridworld.jar ChameleonKidRunner.java;
java -classpath .:../../gridworld.jar ChameleonKidRunner;
read"
