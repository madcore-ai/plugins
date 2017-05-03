pipelineJob('madcore.plugin.mailinput.deploy') {
    parameters {
      stringParam('REPO_URL', 'https://github.com/madcore-ai/containers', '')
      stringParam('REPO_BRANCH', 'master', '')
      stringParam('APP_NAME', 'mailinput', '')      
      stringParam('DOCKERFILE_PATH', 'mailinput', 'Specify path to docker file relative to root repo.')
      stringParam('S3BUCKETNAME', '', 'S3 bucket name for backup')
      stringParam('APP_NAMESPACE', 'mailinput-plugin', 'Plugin namespase')
      stringParam('APP_SERVICE_NAME', 'mailinput-service', 'Plugin service name')
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
                stage ('Generate rc yaml') {
                  build job: 'madcore.plugin.mailinput.render.template', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                }
                stage ('Kubernetes: create') {
                  build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'mailinput/kub')]
                }
                stage ('Update app base') {
                  APP_SERVICE_NAME = params.APP_NAME + "-service"
                  build job: 'madcore.redis.app.update', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_PORT', value: params.SERVICE_PORT), string(name: 'APP_NAMESPACE', value: params.APP_NAMESPACE), string(name: 'APP_SERVICE_NAME', value: APP_SERVICE_NAME) ]
                }
              }
            """.stripIndent())
	    }
    }
}
