gnome-terminal -x bash -c "
rm -f *.class;
javac -classpath .:../../gridworld.jar DancingBugRunner.java;
java -classpath .:../../gridworld.jar DancingBugRunner;
cd ~/Desktop/GridWorldCode/projects/DancingBug;
~/sonar-scanner-2.6.1/bin/sonar-scanner;
read"
