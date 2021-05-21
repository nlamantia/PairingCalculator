# Pairing Calculator

This project is a Java console application that facilitates creating
optimal pairs given list(s) of preferences between two different
entities. This project was initially created for the SEP mentor-mentee
pairing use case, however it was designed to be generic and extendable
to other use cases which we find fit. 

To make this project fully independent of what entities are being 
paired, some of the generic classes should be extracted in a 
separate artifact and included as a dependency.

The pairing calculator accepts two input files: one for the mentees and
their preferences and one that represents the mentors list. If we are
trying to run the stable marriage algorithm, the mentor list must include
preferences for each mentor as well. However, if we are trying to use
the hungarian algorithm (e.g. using the mentor profile slide approach),
we only need their name, SID, and max number of partners. (Please see
the sample input files in the `docs` directory)

## Setup
This project requires Java 8+. If you do not have Java 8+ installed, please
do install a version of the Java 8 JDK. You will know it's installed if you
can open a terminal and run:
```shell script
java -version
```

Expected output should include something like:
```shell script
java version "1.8.*"
```

When we compile the code, the `.class` files should be stored in a separate
directory to avoid cluttering up the code base. Therefore, from the project
root directory, run:
```shell script
mkdir out
```

## Running the Code
After running the aforementioned setup, run the following command to compile
the code:
```shell script
javac -classpath src -d out src/com/chase/sep/columbus/mentoring/Main.java
```

Then, to run the code:
```shell script
java -classpath out com.chase.sep.columbus.mentoring.Main
```

It will then start asking for the appropriate input. Please ensure that
all file name inputs are the ***full*** paths to the files.