gnome-terminal -x bash -c "rm -f *.class;
javac -classpath .:../../gridworld.jar CircleBugRunner.java;
java -classpath .:../../gridworld.jar CircleBugRunner;
cd ~/Desktop/GridWorldCode/projects/CircleBug;
~/sonar-scanner-2.6.1/bin/sonar-scanner;
read"
