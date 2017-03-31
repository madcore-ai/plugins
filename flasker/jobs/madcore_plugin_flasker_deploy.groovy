pipelineJob('madcore.plugin.flasker.deploy') {
    parameters {
      stringParam('REPO_URL', 'https://github.com/madcore-ai/containers', '')
      stringParam('REPO_BRANCH', 'master', '')
      stringParam('APP_NAME', 'flasker', '')
      stringParam('SERVICE_PORT', '9019', '')
      stringParam('DOCKERFILE_PATH', 'flasker', 'Specify path to docker file relative to root repo.')
      stringParam('S3BucketName', '', 'S3 bucket name for backup')
      stringParam('APP_NAMESPACE', 'flasker-plugin', 'Plugin namespase')
      stringParam('APP_SERVICE_NAME', 'flasker-service', 'Plugin service name')
      booleanParam('MADCORE_INGRESS_FLAG', false, 'ingress flag ')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Docker: build container') {
                  build job: 'madcore.docker.image.build', parameters: [string(name: 'REPO_URL', value: params.REPO_URL), string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'DOCKERFILE_PATH', value: params.DOCKERFILE_PATH)]
                }
                stage ('Docker: registry publish') {
                  build job: 'madcore.docker.registry.publish', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                }
                stage ('Docker: registry status') {
                  build job: 'madcore.docker.registry.status', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                }
                stage ('Kubernetes: create') {
                  build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'flasker/kub')]
                }
                stage ('Update app base') {
                  build job: 'madcore.redis.app.update', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_PORT', value: params.SERVICE_PORT), string(name: 'APP_NAMESPACE', value: params.APP_NAMESPACE), string(name: 'APP_SERVICE_NAME', value: params.APP_SERVICE_NAME) ]
                }
                stage ('Update CSR') {
                  build job: 'madcore.ssl.csr.generate'
                }
                stage ('Update certificate and haproxy') {
                  build job: 'madcore.ssl.letsencrypt.getandinstall', parameters: [string(name: 'S3BucketName', value: params.S3BucketName)]
                }
                stage ('add to ingress controller') {
                  if (params.MADCORE_INGRESS_FLAG == true) {
                    build job: 'madcore.plugin.ingress.add.service', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: params.APP_SERVICE_NAME, string(name: 'SERVICE_PORT', value: params.SERVICE_PORT), string(name: 'SERVICE_NAMESPACE', value: params.APP_NAMESPACE) ]
                  }
                  else {println "not need add to ingress controller"}
                }
              }
              }
            """.stripIndent())
	    }
    }
}
