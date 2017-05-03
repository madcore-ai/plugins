pipelineJob('madcore.plugin.mailinput.status') {
    parameters {
	    stringParam('APP_NAME', 'mailinput', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('docker registry status') {
                  build job: 'madcore.docker.registry.status', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                }
                stage ('Kubernetes: status') {
                  build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'mailinput/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
