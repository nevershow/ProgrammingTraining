gnome-terminal -x bash -c "
rm -f *.class;
javac -classpath .:../../gridworld.jar SpiralBugRunner.java;
java -classpath .:../../gridworld.jar SpiralBugRunner;
cd ~/Desktop/GridWorldCode/projects/SpiralBug;
~/sonar-scanner-2.6.1/bin/sonar-scanner;
read"
