# CA3, Part 2 - Virtualization with Vagrant and Hyper-V
## Introduction

This README provides instructions for setting up a virtual environment using Vagrant with Hyper-V as the provider. The goal is to execute a Spring Boot application tutorial developed in a previous assignment (CA2, Part 2) within this virtualized environment.

## Vagrant Configuration:

The Vagrant file defines the configuration settings for creating virtual machines with Hyper-V.

```bash
Vagrant.configure("2") do |config|
# Specify the base box compatible with Hyper-V
config.vm.box = "hypervv/Ubuntu2004"

# Default provider configuration for Hyper-V
config.vm.provider "hyperv" do |hv|
hv.cpus = 2          # Number of CPUs
hv.memory = 1024     # Memory size in MB
hv.maxmemory = 2048  # Maximum dynamic memory in MB
hv.linked_clone = true # Use linked clones to speed up VM creation
end

# General provisioning script that runs on all VMs
config.vm.provision "shell", inline: <<-SHELL
sudo apt-get update -y
sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip openjdk-17-jdk-headless
SHELL

# Configuration for the database VM
config.vm.define "db" do |db|
db.vm.hostname = "db"
db.vm.network "private_network", ip: "192.168.56.11"

    # Specific provisioning for the database VM
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
      java -cp h2-1.4.200.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/h2-server.log 2>&1 &
    SHELL
end

# Configuration for the webserver VM
config.vm.define "web" do |web|
web.vm.hostname = "web"
web.vm.network "private_network", ip: "192.168.56.10"

    # Webserver specific provisioning
    web.vm.provision "shell", inline: <<-SHELL
      sudo apt install -y tomcat9 tomcat9-admin
      # Clone the repository
      git clone https://github.com/PedroHGill/devops-23-24-JPE-1231851.git
      sudo chown -R vagrant:vagrant /home/vagrant/devops-23-24-JPE-1231851/

      cd devops-23-24-JPE-1231851/CA2/part2
      chmod +x ./gradlew
      ./gradlew build
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
    SHELL
end
end
```

## Explanation:

- `config.vm.box`: Specifies the base box compatible with Hyper-V.
- `config.vm.provider "hyperv"`: Defines provider-specific settings for Hyper-V, including CPU, memory, and linked clone configurations.
- General Provisioning: Installs necessary packages and dependencies using `apt-get` for all VMs.
- Database VM Configuration: Defines settings for the database VM, including hostname and IP address.
- Database Provisioning: Downloads and sets up the H2 database server on the database VM.
- Webserver VM Configuration: Defines settings for the web server VM, including hostname and IP address.
- Webserver Provisioning: Installs Tomcat 9, clones the Spring Boot application repository, builds, and runs the application on the web server VM.


## Running the Virtual Environment:

1. Ensure Vagrant is installed and Hyper-V is enabled on your system.
2. Navigate to the directory containing the Vagrantfile.
3. Run the command vagrant up to create the virtual machines and provision them.
4. Access the web server by opening a browser and navigating to the IP address of the web server virtual machine followed by the port number 8080.
5. Access the H2 database by opening a browser and navigating to the IP address of the database virtual machine followed by the port number 8082 or 9092.

Additional Vagrant commands can be used to manage the virtual environment as described in the previous README.

## Conclusion:
This README provides a guide for setting up a virtual environment using Vagrant with Hyper-V as the provider. It outlines the configuration settings and provisioning steps required to execute a Spring Boot application tutorial within this environment. By following the provided instructions, users can efficiently deploy and manage the virtualized infrastructure for development and testing purposes.