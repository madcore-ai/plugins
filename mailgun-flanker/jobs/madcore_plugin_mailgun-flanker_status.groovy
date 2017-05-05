pipelineJob('madcore.plugin.mailgun-flanker.status') {
    parameters {
	    stringParam('APP_NAME', 'mailgun-flanker', '')
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
                  build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'mailgun-flanker/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
