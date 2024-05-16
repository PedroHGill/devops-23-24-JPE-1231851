# CA3, Part 2 - Virtualization with Vagrant

## Introduction
The goal of this assignment is to use Vagrant to setup a virtual environment to execute the tutorial spring boot 
application, gradle ”basic” version (developed in CA2, Part2).

## Vagrant Configuration:

The Vagrant file defines the configuration settings, such as the
Vagrant version and the settings of the virtual machines to be created.

```bash
Vagrant.configure("2") do |config|
config.vm.box = "ubuntu/focal64"
config.ssh.insert_key = false
```

### Explanation:
- `config.vm.box = "ubuntu/focal64"` - Specifies the base box to use for the virtual machines, in this case, it's "ubuntu/focal64", an Ubuntu 20.04 LTS box.
- `config.ssh.insert_key = false:` - Disables automatic SSH key insertion into the virtual machines to avoid conflicts.

## Common Provision Settings:
The common provisioning settings for all the virtual machines are defined within the Vagrant file.

```bash
config.vm.provision "shell", inline: <<-SHELL
sudo apt-get update -y
sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
openjdk-17-jdk-headless
# ifconfig
SHELL
```
### Explanation:
`config.vm.provision "shell"` - Specifies shell provisioning for the virtual machines.
Installs necessary packages and dependencies using apt-get:     
`iputils-ping` -  Provides network utilities for troubleshooting.       
`avahi-daemon` - Implements mDNS/DNS-SD service discovery.          
`libnss-mdns` Provides an NSS module for Multicast DNS (mDNS) name resolution.
`openjdk-17-jdk-headless` - Installs the Java Development Kit (JDK) version 17 without GUI components.

## Database VM Configuration:
The configuration settings for the database virtual machine are defined within the Vagrant file.

```bash
config.vm.define "db" do |db|
db.vm.box = "ubuntu/focal64"
db.vm.hostname = "db"
db.vm.network "private_network", ip: "192.168.56.11"
```
### Explanation:
-Defines a VM named "db" with Ubuntu 20.04 LTS.      
-Sets the hostname to "db".  
-Assigns the IP address 192.168.56.11 on a private network.

## Database Provisioning:
The provisioning settings for the database virtual machine are defined within the Vagrant file.

```bash
 db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL
 db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
```

### Explanation:
-Downloads the H2 database using wget.      
-Starts the H2 server process in the background.

## Webserver VM Configuration:
The configuration settings for the web server virtual machine are defined within the Vagrant file.

```bash
config.vm.define "web" do |web|
    web.vm.box = "ubuntu/focal64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"
```

### Explanation:
-Defines a VM named "web" with Ubuntu 20.04 LTS.        
-Sets the hostname to "web".        
-Assigns the IP address 192.168.56.10 on a private network.

## Webserver Provisioning:
The provisioning settings for the web server virtual machine are defined within the Vagrant file.

```bash
web.vm.provision "shell", inline: <<-SHELL, privileged: false
  sudo apt install -y tomcat9 tomcat9-admin

  git clone https://github.com/PedroHGill/devops-23-24-JPE-1231851.git
  cd devops-23-24-JPE-1231851/CA2/part2
  chmod u+x gradlew
  ./gradlew clean build
  nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
SHELL
```

### Explanation:
-Installs Tomcat 9 and Tomcat admin.        
-Clones a Git repository containing a Spring Boot application.      
-Builds and runs the Spring Boot application in the background.

## Running the Virtual Environment:

1. Install Vagrant locally.
2. Run the command `vagrant up` to create the virtual machines and run the application. After the command is executed, the virtual machines are created via VirtualBox.
3. Access the web server by opening a browser and navigating to the IP address of the web server virtual machine followed by the port number 8080.
4. Access the H2 database by opening a browser and navigating to the IP address of the database virtual machine followed by the port number 8082 or 9092.
 
Other relevant commands that vagrant provides are:

1. **`vagrant init`**: Initializes a new Vagrant environment by creating a `Vagrantfile` in the current directory.

2. **`vagrant halt`**: Stops the running VMs.

3. **`vagrant suspend`**: Suspends the running VMs, saving their state to be resumed later.

4. **`vagrant resume`**: Resumes the VMs from a suspended state.

5. **`vagrant reload`**: Restarts the VMs and re-provisions them, applying any changes in the `Vagrantfile`.

6. **`vagrant provision`**: Runs the provisioners on the VMs without restarting them.

7. **`vagrant status`**: Displays the status of the VMs defined in the `Vagrantfile`.

8. **`vagrant destroy`**: Stops and removes the VMs defined in the `Vagrantfile`.

9. **`vagrant box`**: Manages Vagrant box resources (list, add, remove, etc.).

10. **`vagrant global-status`**: Lists all active Vagrant environments across different directories.

## Conclusion:
A detailed guide was provided on setting up a virtual environment using Vagrant for executing a Spring Boot application, specifically the Gradle "basic" version developed in a previous assignment.

We began by explaining the Vagrant configuration, including the base box selection. Then, we delved into the common provisioning settings, which included installing necessary packages and dependencies for both the database and web server virtual machines.

For the database VM, we outlined the configuration settings and provisioning steps required to set up the H2 database server. Similarly, for the web server VM, we detailed the configuration settings and provisioning steps for installing Tomcat 9, cloning the Spring Boot application from a Git repository, and running the application.

Finally, we provided instructions on running the virtual environment using Vagrant, along with additional Vagrant commands for managing the virtual machines.

