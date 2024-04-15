# CA2, Part 1 - Build Tools with Gradle

## Create a new directory called CA1.
```bash
cd 
mkdir CA2/part1
```

- ## 1. Download and commit to your repository the example application.
```bash
git clone https://bitbucket.org/pssmatos/gradle_basic_demo/
rm ~/CA2/part1/.git
git commit -am "[Feat] Close #6 Cloned repository." 
git push
```

- ## 2. Read the instructions available in the readme.md file and experiment with the application.
```bash
cd gradle_basic_demo
```

- ## Build
### To build a .jar file with the application:

```bash
./gradlew build
```

- ## Run the server
### Open a terminal and execute the following command from the project's root directory:
```bash
java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp 59001
```

- ## Run a client
### Open another terminal and execute the following gradle task from the project's root directory:
```bash
./gradlew runClient
```
(After executing the build command, the server and clients need to be launched in separate terminal windows. Initially, the server should be started first.
Subsequently, when opening windows for the clients, they will prompt the users to input their names. Following this, communication between clients can be initiated, allowing messages to be exchanged.)

### Commit and push the changes:
```bash
git commit -am "[Feat] Close #8 Added a task that executes the server"
git push
```

- ## 3. Add a new task to execute the server.
On the build.gradle file, add the following task to the server:
```bash
task runServer(type:JavaExec, dependsOn: classes){
    group = "DevOps"
    description = "Launches a chat server that listens on port 59001"

    classpath = sourceSets.main.runtimeClasspath

    mainClass = 'basic_demo.ChatServerApp'

    args '59001'
}
```

### From now on, the server can be run by using the following command.
```bash
./gradlew runServer
```
(We can use this command in one terminal to start the server. Then, we can use 2 different terminals to start a chat between two users)

### Commit and push the changes:
```bash
git commit -am "[Test] Close #7 Add a simple unit test in AppTest class."
git push
```

- ## 4. Add a simple unit test and update the gradle script so that it is able to execute the
test.
Create a test directory on the src directory and add the following test to the Apptest.java file:
```java
@Test
public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
        }
}
```

###  The unit tests require junit 4.12 to execute. Dot not forget to add this dependency in gradle.
```java
dependencies {
    // Use Apache Log4J for logging
    implementation group: 'org.apache.logging.log4j', name: 'log4j-api', version: '2.11.2'
    implementation group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.11.2'
    testImplementation 'junit:junit:4.12'
}
```

### Run the test with the following command.
```bash
./gradlew test
```
### Commit and push the changes:
```bash
git commit -am "[Feat] Close #8 Added a task that executes the server"
git push
```
- ## 5. Add a new task of type Copy to be used to make a backup of the sources of the application.
### On the build.gradle file, add the following task to copy the src directory to a backup directory
```java
task copySources (type: Copy){
    group = "DevOps"
    description = "Copy source files to the target directory"

    from 'src/'
    into 'backup/'
```
### To do the backup use the following command:
```bash
./gradlew backupSources
```
(I didn't want to commit duplicated code, so I removed the back up source)
```bash
./backup rm
```
### Commit and push the changes:
```bash
git commit -am "[Feat] Close #9 Added a task that makes a backup of the sources of the application"
git push
```

- ## 6. Add a new task of type Zip to be used to make an archive (i.e., zip file) of the sources of the application.
### On the build.gradle file, add the following task to zip the src directory
```java
task copySources (type: Copy){
    group = "DevOps"
    description = "Copy source files to the target directory"

    from 'src/'
    into 'backup/'
```
### To zip use the following command:
```bash
./gradlew zip
```
### Commit and push the changes:
```bash
git commit -am "[Feat] Close #9 Added a task that makes a backup of the sources of the application"
git push
```
- ## 7.At the end of the part 1 of this assignment mark your repository with the tag ca2-part1.
```bash
git tag ca2-part1
git push origin ca2-part1
```