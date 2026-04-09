# Jarvis Linux Cluster Monitoring

# Introduction
(about 100-150 words)
Discuss the design of the project. What does this project/product do? Who are the users? What are the technologies you have used? (e.g. bash, docker, git, etc..)

The Jarvis Linux Cluster Monitoring project is designed to provide the user with the tools nessesary to easily record the hardware and real time usage data of 10 nodes/servers. The project collects the data from all 10 nodes/servers and inserts it into an RDMS hosted on one of the machines. This project was done with the intention to make it quick and simple to setup aswell as being easily scalable for future expansions. To achive this bash scripts were used to colects data and do first time setup, docks was used to run postgres sql, git was used for version control, and crontab was used to automation.

# Quick Start
Use markdown code block for your quick-start commands
1. Start a psql instance using psql_docker.sh (done on psql host machine)
    ``` 
    docker pull postgres
    ./linux_sql/scripts/psql_docker.sh create [db_username] [db_password]
    ```
2. Create tables using ddl.sql (done on psql host machine)
    ```
    psql -h localhost -U postgres -d host_agent -f linux_sql/sql/ddl.sql
    ```
3.  Insert hardware specs data into the DB using host_info.sh (done on each machine once | [psql_host] will be localhost of done on host machine)
    ```
    ./linux_sql/scripts/host_info.sh [psql_host] 5432 host_agent [db_username] [db_password]
    ```
4. Insert hardware usage data into the DB using host_usage.sh (this inserts the usage data once, to automate look at the next step | [psql_host] will be localhost of done on host machine)
    ```
    ./linux_sql/scripts/host_usage.sh [psql_host] 5432 host_agent [db_username] [db_password]
    ```
5. Crontab setup (done on each machine | runs every minute | inserts output to log file local to each machine)
    ```
    crontab -e

    * * * * * bash [location of project]/jarvis_data_eng_RonaldSin/linux_sql/scripts/host_usage.sh [psql_host] 5432 host_agent [db_username] [db_password] > /tmp/host_usage.log
    ```



# Implemenation
## Architecture
![cluster_diagram] (./assets/cluster_diagram.png)

## Scripts
### psql_docker.sh
This sets up the psql server in a docker container and a volume to store the data and provides an easy way to start and stop the container

#### Commands
#### create
Creates and runs the docker container and volume (make sure you have pulled the postgres image before running - see [quick start](#quick-start))     
**[db_username]** - set a username used to login to postgres  
**[db_password]** - sets a password used to login to postgres
```
./linux_sql/scripts/psql_docker.sh create [db_username] [db_password] 
```

#### start
This starts the cointainer
```
./linux_sql/scripts/psql_docker.sh start
```

#### stop
This stops the cointainer
```
./linux_sql/scripts/psql_docker.sh start
```

### host_info.sh
Collects and inserts hardware info into the psql database - intended to be run only once on each machine  
**[psql_host]** - ip where the psql is located (localhost if on host machine)  
**[psql_port]** - port opened for the psql database (5432 default)  
**[db_name]** - name of the database (host_agent default)  
**[db_username]** - username set with [psql_docker.sh create](#psql_dockersh)  
**[db_password]** - password set with [psql_docker.sh create](#psql_dockersh)

```
./linux_sql/scripts/host_info.sh [psql_host] [psql_port] [db_name] [db_username] [db_password]
```

### host_usage.sh
Collects and inserts current usage info into the psql database only run after running host_info or it will fail - intended to be run once a minute using crontab on each machine  
**[psql_host]** - ip where the psql is located (localhost if on host machine)  
**[psql_port]** - port opened for the psql database (5432 default)  
**[db_name]** - name of the database (host_agent default)  
**[db_username]** - username set with [psql_docker.sh create](#psql_dockersh)  
**[db_password]** - password set with [psql_docker.sh create](#psql_dockersh)

```
./linux_sql/scripts/host_usage.sh [psql_host] [psql_port] [db_name] [db_username] [db_password]
```
#### crontab
Used to run host_usage.sh once a minute and sends output to a log file  
**[location of project]** - where you saved the root of the project
**[psql_host]** - ip where the psql is located (localhost if on host machine)  
**[psql_port]** - port opened for the psql database (5432 default)  
**[db_name]** - name of the database (host_agent default)  
**[db_username]** - username set with [psql_docker.sh create](#psql_dockersh)  
**[db_password]** - password set with [psql_docker.sh create](#psql_dockersh)
```
crontab -e

* * * * * bash [location of project]/jarvis_data_eng_RonaldSin/linux_sql/scripts/host_usage.sh [psql_host] [psql_port] [db_name] [db_username] [db_password] > /tmp/host_usage.log
```

## Database Modeling
### host_info
|name | type| 
| :---- | :---- |
| id | serial - unique id number for each machine
| hostname | char - unique hostname from each machine
| cpu_number | int - number of cpus
| cpu_architecture | char - architecture of cpu
| cpu_model | char - cpu model name
| cpu_mhz | float - cpu clock speed in mhz
| l2_cache | int - total l2 cache size in kB
| timestamp | timestamp - when the data was recorded
| total_memory | int - total memory size in kB




### host_usage
|name | type| 
| :---- | :---- |
| timestamp | timestamp - when the data was recorded
| host_id | serial - unique id number for each machine taken from [host_info](#host_info)
| memory_free | int - ammount of memory currently free in MB
| cpu_idle | int - percentage of cpu currently idle
| cpu_kernel | int - percentage of cpu currently on kernel processes
| disk_io | int - current disk io 
| disk_available | int - current available space in root directory disk in MB 




# Testing
All testing was done on a single machine running rocky 9 on google cloud. It was assumed that all firewalls, networking and connections were setup properly.
First I ran each script the way it was ment to be run and verified I got the intended result in the database. Then I ran each script in an improper way to make sure I got the proper error messages and error codes
## Some sample test
|script | test| result |
| :---- | :---- | :---- |
| psql_docker create | proper | intended container, volume and database created
| psql_docker create | invalid number of args | error message
| psql_docker create | container already exist | error message
| host_usage | wrong db_username/db_password | error message 
| host_info | wrong port | error message 
| ddl | database not created | error message 


# Deployment
How did you deploy your app? (e.g. Github, crontab, docker)
The project is hosted on github following gitflow standard, having a main, develop and features branch for each new feature.
Postgres server is running in a docker container on the host machine.
Crontab is used to automate host_usage.sh every minute and insert the data into the database.
The machines are vms running rocky 9 deployed on google cloud.

# Improvements
- script to automate running all the scripts to speed up deployment to more machines
- script to automate deploying to multiple machines useful for when this project is scaled up
- setup alerts if a machines usage stat passes a certain value there maybe something wrong with that machine