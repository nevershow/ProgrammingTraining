gnome-terminal -x bash -c "
rm -f *.class;
javac -classpath .:../../gridworld.jar ZBugRunner.java;
java -classpath .:../../gridworld.jar ZBugRunner;
cd ~/Desktop/GridWorldCode/projects/ZBug;
~/sonar-scanner-2.6.1/bin/sonar-scanner;
read"
