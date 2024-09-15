pipeline {
    agent any

    environment {
        DOCKER_USERNAME = 'petrosvoivontas'
        GHCR_TOKEN = credentials('ghcr-token')
        DOCKER_REGISTRY_URL = 'ghcr.io'
        DOCKER_IMAGE_PREFIX = 'ghcr.io/petrosvoivontas/minigoodreads-be'
    }

    stages {
        stage('Docker build and push') {
            steps {
                sh '''
                    HEAD_COMMIT=$(git rev-parse --short HEAD)
                    TAG=$HEAD_COMMIT-$BUILD_ID
                    IMAGE_TAG=$DOCKER_IMAGE_PREFIX:$TAG
                    docker build --rm -t $IMAGE_TAG -f docker/minigoodreads/Dockerfile .
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
                MINIGOODREADS_VERSION = env.HEAD_COMMIT.toString().concat('-').concat(env.BUILD_ID.toString())
            }
            steps {
                ansiblePlaybook('~/workspace/minigoodreads-ansible/playbooks/deploy-minigoodreads-be-k8s.yaml') {
                    inventory('~/workspace/minigoodreads-ansible/hosts.yaml')
                    extraVars {
                        extraVar('minigoodreads_be_version', env.MINIGOODREADS_VERSION)
                    }
                }
            }
        }
    }
}
