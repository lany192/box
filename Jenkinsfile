pipeline {
    agent any

    environment {// 环境变量
        //BRANCH_NAME = 'master'  // 或者其他分支名
    }

    options {
        // 构建保留配置。配置丢弃旧的构建：策略logRotator，保持构建的天数3天，保持构建的最大个数3个
        buildDiscarder(logRotator(numToKeepStr: '3', artifactNumToKeepStr: '3'))
        // 时间戳
        timestamps()
        // 禁止管道的并发执行
        disableConcurrentBuilds()
        //流水线超时设置
        timeout(time: 1, unit: "HOURS")
    }

    triggers {
        pollSCM('H/10 * * * *') // 每5分钟轮询一次
    }

    parameters {
        choice(
            name: 'BUILD_APP',
            choices: ['hello','demo','avatar'],
            description: '选择需要构建的app'
        )

        string(
            name: 'BRANCH_NAME',
            defaultValue: 'master',
            description: '填入需要执行的Git分支名称'
        )

        choice(
            name: 'BUILD_TYPES',
            choices: ['Debug','Develop','Release'],
            description: '选择需要构建的类型:develop开发测试模式 release发布模式 debug开发模式'
        )

        booleanParam(
            name: 'BUILD_CLEAN',
            defaultValue: true,
            description: '是否需要执行clean'
        )
    }
    stages {
        stage('Initialize') {
            steps {
                echo '正在初始化...'
                echo "构建app：${params.BUILD_APP}"
                echo "Git分支名称：${params.BRANCH_NAME}"
                echo "需要构建的类型：${params.BUILD_TYPES}"
                echo "是否需要执行clean：${params.BUILD_CLEAN}"
            }
        }

        stage('Git Checkout') {
            steps {
                echo '拉取代码'
                checkout scm
            }
        }

        stage('Get Git Commit ID') {
            steps {
                script {
                    env.COMMIT_ID = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
                    echo "当前 Git Commit ID: ${env.COMMIT_ID}"
                }
            }
        }

        stage('Build Apk') {
            steps {
                script {
                    sh "chmod +x gradlew"
//                     sh "./gradlew clean assemble${API_ENV}${BUILD_TYPE} -PisJenkins=true -PjenkinsOpsTaskId=$opsTaskId"
                    if(params.BUILD_CLEAN ==  true){
                        sh "./gradlew clean assemble${params.BUILD_TYPES} --debug --stacktrace"
                    }else{
                        sh "./gradlew assemble${params.BUILD_TYPES} --debug --stacktrace"
                    }
                }
            }
        }

        stage('Archive Result') {
            steps {
                echo '归档生成结果'
                archiveArtifacts artifacts: 'app/build/outputs/apk/**/*.apk', fingerprint: true
                script {
                    // 获取Jenkins任务URL
                    def jobUrl = env.BUILD_URL
                    // 设置下载链接
                    env.DOWNLOAD_URL = "${jobUrl}artifact/app/build/outputs/apk/debug/app-debug.apk"
                }
            }
        }
    }

    post {
        always {
            echo '清理工作'
            cleanWs()  // 需要安装"Workspace Cleanup Plugin"
        }
    }
}
