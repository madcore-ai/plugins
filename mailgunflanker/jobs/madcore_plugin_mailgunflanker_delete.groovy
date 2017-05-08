pipelineJob('madcore.plugin.mailgunflanker.delete') {
    parameters {
	    stringParam('APP_NAME', 'mailgunflanker', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Docker: registry delete') {
                  build job: 'madcore.docker.registry.delete', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                }
                stage ('Docker: registry status') {
                  build job: 'madcore.docker.registry.status', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                }
                stage ('Kubernetes: delete') {
                  build job: 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', value: 'mailgunflanker/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
