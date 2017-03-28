pipelineJob('madcore.plugin.flasker-hub.status') {
    parameters {
	    stringParam('APP_NAME', 'flasker-hub', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Kubernetes: status') {
                  build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'flasker-hub/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
