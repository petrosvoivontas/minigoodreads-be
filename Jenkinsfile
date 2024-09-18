pipeline {
    agent any

    environment {
        DOCKER_USERNAME = 'petrosvoivontas'
        GHCR_TOKEN = credentials('ghcr-token')
        DOCKER_REGISTRY_URL = 'ghcr.io'
        BE_DOCKER_IMAGE_PREFIX = 'ghcr.io/petrosvoivontas/minigoodreads-be'
        MYSQL_DOCKER_IMAGE_PREFIX = 'ghcr.io/petrosvoivontas/minigoodreads-db'
    }

    stages {
        stage('Docker build and push minigoodreads-be') {
            steps {
                sh '''
                    HEAD_COMMIT=$(git rev-parse --short HEAD)
                    TAG=$HEAD_COMMIT-$BUILD_ID
                    IMAGE_TAG=$BE_DOCKER_IMAGE_PREFIX:$TAG
                    docker build --rm -t $IMAGE_TAG -f docker/minigoodreads/Dockerfile .
                    echo $GHCR_TOKEN | docker login $DOCKER_REGISTRY_URL -u $DOCKER_USERNAME --password-stdin
                    docker push $IMAGE_TAG
                '''
            }
        }
        stage('Docker build and push minigoodreads-db') {
            steps {
                sh '''
                    HEAD_COMMIT=$(git rev-parse --short HEAD)
                    TAG=$HEAD_COMMIT-$BUILD_ID
                    IMAGE_TAG=$MYSQL_DOCKER_IMAGE_PREFIX:$TAG
                    docker build --rm -t $IMAGE_TAG -f docker/mysql/Dockerfile docker/mysql
                    echo $GHCR_TOKEN | docker login $DOCKER_REGISTRY_URL -u $DOCKER_USERNAME --password-stdin
                    docker push $IMAGE_TAG
                '''
            }
        }
        stage('Install ansible prerequisites') {
            steps {
                sh '''
                    ansible-galaxy collection install kubernetes.core
                '''
            }
        }
        stage('Init ansible workspace') {
            steps {
                build job: 'minigoodreads-ansible'
            }
        }
        stage('Deploy to k8s') {
            environment {
                MINIGOODREADS_VERSION = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim().concat('-').concat(env.BUILD_ID.toString())
            }
            steps {
                ansiblePlaybook(
                        playbook: '/var/lib/jenkins/workspace/minigoodreads-ansible/playbooks/deploy-minigoodreads-db-k8s.yaml',
                        inventory: '/var/lib/jenkins/workspace/minigoodreads-ansible/hosts.yaml',
                        extraVars: [
                                minigoodreads_db_version: env.MINIGOODREADS_VERSION
                        ]
                )
                ansiblePlaybook(
                        playbook: '/var/lib/jenkins/workspace/minigoodreads-ansible/playbooks/deploy-minigoodreads-be-k8s.yaml',
                        inventory: '/var/lib/jenkins/workspace/minigoodreads-ansible/hosts.yaml',
                        extraVars: [
                                minigoodreads_be_version: env.MINIGOODREADS_VERSION
                        ]
                )
            }
        }
    }
}
