pipelineJob('madcore.plugin.mailgunflanker.deploy') {
    parameters {
      stringParam('REPO_URL', 'https://github.com/madcore-ai/containers', '')
      stringParam('REPO_BRANCH', 'master', '')
      stringParam('APP_NAME', 'mailgunflanker', '')
      stringParam('DOCKERFILE_PATH', 'mailgunflanker', 'Specify path to docker file relative to root repo.')
      stringParam('S3BUCKETNAME', '', 'S3 bucket name for backup')
      stringParam('APP_NAMESPACE', 'mailgunflanker-plugin', 'Plugin namespase')

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
                  build job: 'madcore.plugin.mailgun-flanker.render.template', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                }
                stage ('Kubernetes: create') {
                  build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'mailgunflanker/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
