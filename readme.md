> 简单的一个用户的rest crud
###  简单的操作
- postman做CRUD

- swagger 做CRUD
http://127.0.0.1:9898/swagger-ui.html


### Jenkins 自动化部署
> 在项目里建立一个Jenkinsfile的文件，参看项目里的Jenkinsfile
**注意不要有一开头不要有Jenkinsfile (Declarative Pipeline)**

例如：我一开始建立如下
```
Jenkinsfile (Declarative Pipeline)
pipeline {
    agent { docker 'maven:3.3.3' }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}
```
直接报错，去掉Jenkinsfile (Declarative Pipeline)就好了

#### agent
agent 就是环境相关，例如 agent { docker 'maven:3.3.3' }
就是建立 maven 的环境。其实我的主机里是有将MAVEN_HOME加入到全局路径里的，但不知道
Jenkins怎么使用？


### 遇到的问题
问题 1.

Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?

解决：在主机里找到docker.sock
```
cd /
ls -ll|find -name 'docker.sock'
```
我的docker.sock 在./run/docker.sock下

而我在使用docker建立jenkins容器的时候，
指定错了`/data/jenkins/docker.sock:/var/run/docker.sock`

修正成为`/run/docker.sock:/var/run/docker.sock`
``` 
    docker run \
      --name jenkins\
      -d \
      -u root \
      -p 8686:8080 \
      -v /data/jenkins/jenkins-data:/var/jenkins_home \
      -v /run/docker.sock:/var/run/docker.sock \
      -v /data/jenkins/home:/home \
      jenkinsci/blueocean
```

### 参考
- https://jenkins.io/zh/doc/ 