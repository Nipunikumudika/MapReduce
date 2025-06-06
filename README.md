# 🧮 Diabetes Outcome Counter using Hadoop MapReduce (Java)

This project counts the number of diabetic (Outcome = 1) and non-diabetic (Outcome = 0) cases in a dataset using Hadoop MapReduce written in Java. Further it checks for gender based glucode levels.

---
## 📦 Project Structure

```
DiabetesMapReduce/
├── src/
│   ├── OutcomeMapper.java
│   ├── OutcomeReducer.java
│   └── OutcomeDriver.java
├── build/                  # Compiled .class files
├── DiabetesCount.jar       # Output JAR
├── diabetes_dataset.csv    # Input CSV data
```


## 🖥 Prerequisites

- Java JDK 8+
- Hadoop 2.x or 3.x (single-node setup)
- Windows 10/11 (Admin access)
- Command Prompt or PowerShell

---

## 🛠 Installing Hadoop on Windows

### Step 1: Download and Extract Hadoop
    1. Download Hadoop binary using https://hadoop.apache.org/release/3.2.4.html and place it in C:\hadoop

    2. Download winutils file (e.g., [WinUtils Hadoop binary](https://github.com/cdarlint/winutils) for Windows) and replace bin folder in C:\hadoop.
    

### Step 2: Configure Environment Variables
Set the following environment variables:

- HADOOP_HOME = C:\hadoop
- Add %HADOOP_HOME%\bin to the PATH

### Step 3: Configure Hadoop Files

Edit the following files in C:\hadoop\etc\hadoop:

#### core-site.xml
xml
<configuration>
  <property>
    <name>fs.defaultFS</name>
    <value>hdfs://localhost:9000</value>
  </property>
</configuration>

#### hdfs-site.xml

xml
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property><property>
        <name>dfs.namenode.name.dir</name>
        <value>C:\hadoop\data\namenode</value>
    </property><property>
        <name>dfs.datanode.data.dir</name>
        <value>C:\hadoop\data\datanode</value>
    </property>
</configuration>

#### mapred-site.xml
xml
<configuration>
  <property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
  </property>
</configuration>


#### yarn-site.xml
xml
<configuration>
  <property>
    <name>yarn.nodemanager.aux-services</name>
    <value>mapreduce_shuffle</value>
  </property>
  <property>
    <name>yarn.nodemanager.auxservices.mapreduce.shuffle.class</name>
    <value>org.apache.hadoop.mapred.ShuffleHandler</value>
</property>
</configuration>


Then Type hadoop and hadoop version and confirm it installed successfully.


🚀 Running the MapReduce Project


Step 1: Compile Java Code
Open Command Prompt (Admin) and run:

cd path\to\DiabetesMapReduce\src

javac -classpath "path\to\hadoop\share\hadoop\common\.jar;path\to\hadoop\share\hadoop\mapreduce\.jar" -d ../build *.java


Step 2: Create JAR File

cd ..
jar -cvf DiabetesCount.jar -C build/ .


Step 3: Start Hadoop
From Hadoop’s sbin directory:

start-dfs.cmd
start-yarn.cmd


Step 4: Upload Data to HDFS

hdfs dfs -mkdir /diabetes
hdfs dfs -put diabetes_dataset.csv /diabetes/input.csv

Step 5: Run the MapReduce Job

hadoop jar DiabetesCount.jar OutcomeDriver /diabetes /diabetes_output


Step 6: View the Output

hdfs dfs -cat /diabetes_output/part-r-00000s
