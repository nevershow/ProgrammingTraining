gnome-terminal -x bash -c "
rm -f *.class;
javac -classpath .:../../gridworld.jar *.java;
java -classpath .:../../gridworld.jar SparseGridRunner;
~/sonar-scanner-2.6.1/bin/sonar-scanner;
read"
