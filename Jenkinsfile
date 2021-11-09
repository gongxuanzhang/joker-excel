pipeline {
    agent any

    stages {
        // 编译
        stage('compile') {
            steps {
                echo 'compile'
            }
        }

        // 打包
        stage('package1') {
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v /var/jenkins_home/appconfig/maven/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean install -s "/var/jenkins_home/appconfig/maven/settings.xml" -Dmaven.test.skip=true'
                echo 'package finish'
            }
        }
        //  部署
        stage('deploy') {
            steps {
                echo 'deploy'
            }
        }
    }

}
