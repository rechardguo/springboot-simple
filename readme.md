> 简单的一个用户的rest crud
### 如何调整到一个页面


### mybatis generator
pom.xml 里配置 ,注意version选1.3.5 选1.3.7 会报错，官方文档里写的就是1.3.7不知道为什么
注意的是在pom.xml里平时为注释掉状态，上传上去的不要包含非注释掉的
```xml
 <plugins>
       <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.5</version>
                <executions>
                    <execution>
                        <id>Generate MyBatis Artifacts</id>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                            <groupId>mysql</groupId>
                            <artifactId>mysql-connector-java</artifactId>
                        <version>${mysql.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
```
建立resources/genertorConfig.xml 在里面配置即可
有些参数配置可以加入到pom.xml里的properties里，例如
 <mybatis.generator.overwrite>true</mybatis.generator.overwrite>
更多的参数参看
http://www.mybatis.org/generator/running/runningWithMaven.html

```xml
<table catalog="miaosha"   tableName="goods" domainObjectName="Good" >
            <generatedKey column="id" sqlStatement="mysql" identity="true" />
</table>
```
注意 catalog 需要指定库，由于mysql没有schema，所以使用catalog

### 集成thymeleaf


### docker 私有仓库 建立
在我的百度云上106.12.131.132 操作
1. 建立了一个私有仓库
```
docker run -it -d \
-p 5000:5000 \
-v /data/registry:/var/lib/registry \
--name registry registry
```
2.
`vi /etc/docker/daemon.json `
```
{  
  "insecure-registries":["106.12.131.132:5000"] 
}
```
3. 重启 docker
`systemctl restart docker`

4. 将一个本地的镜像redis上传到私有仓库
```
docker tag redis:3.2 106.12.131.132:5000/redis:3.2 
docker push 12.131.132:5000/redis:3.2
```

5. 验证是否成功，看
`http://106.12.131.132:5000/v2/redis/tags/list`

任何的客户端想要访问

`vi /etc/docker/daemon.json `

```
{  
  "insecure-registries":["106.12.131.132:5000"],
  "registry-mirrors":  ["106.12.131.132:5000"]  
 }
  ```
  
重启docker后使用
`docker pull 106.12.131.132:5000/redis:3.2` 拉取镜像



### Automatic Restart
1. pom.xml 加上spring-boot-devtools
2. idea里需要手动trigger, 方法是右键project->build module

###  简单的操作
- postman做CRUD

- swagger 做CRUD
http://127.0.0.1:9898/swagger-ui.html


### war 包的启动步骤
1. Bootstrap 上加上SpringBootServletInitializer
2. pom.xml里改成的<package>war</package>
3. mvn clean package -Dmaven.test.skip=true
4. 将生成的war 包放到tomcat目录下
5. pom.xml里的spring-boot-starter-web要exclude tomcat，注意exlcude后就不能再通过main启动，会报servlet包找不到

### docker 部署启动
集成docker



### Jenkins 自动化部署
#### 新建项目 构建一个maven的项目
- 碰到的问题如下
- - 没看到maven选项
>找maven intergration 插件

- - 插件timeout无法更新

找插件管理 - advance - Update Site
url 填写成
http://mirror.esuni.jp/jenkins/updates/update-center.json

- - 构建中maven_home找不到，由于是docker 建立的jenkins,jenkins里需要使用宿主机器的maven路径，需要将宿主机器的maven映射到docker里，如下的-v /usr/share/maven3:/usr/share/maven3,这样在jenkins的全局工具配置里里可以不设置maven_home
```
    docker run \
      --name jenkins\
      -d \
      -u root \
      -p 8686:8080 \
      -v /data/jenkins/jenkins-data:/var/jenkins_home \
      -v /run/docker.sock:/var/run/docker.sock \
      -v /usr/share/maven3:/usr/share/maven3 \
      -v /data/jenkins/home:/home \
      jenkinsci/blueocean
```

#### 建立基于Jenkinsfile的pipeline方式的构建

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