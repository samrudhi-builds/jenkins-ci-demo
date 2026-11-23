# Jenkins CI Demo

A small Java command-line project for practicing Jenkins CI. `NumberAnalysis.java` checks whether an integer is prime and reports its factorial, the number of digits in the factorial, and their sum.

## Run locally

Install a JDK and run these commands from the repository root:

```sh
javac NumberAnalysis.java
java NumberAnalysis 7 output.txt
```

The report prints to the console and is also saved to `output.txt` when a filename is provided. The maximum factorial input is 1,000.

## Run in Jenkins

1. Create a Pipeline job and select **Pipeline script from SCM**.
2. Select **Git**, enter this repository's URL, set the branch to `*/main`, and use `Jenkinsfile` as the script path.
3. Run the job on a Unix agent with `java` and `javac` on its `PATH`.

The pipeline compiles the program, checks console and file output, and verifies that invalid input and `1001` are rejected. After a successful build, `output.txt` is available under **Artifacts**.
