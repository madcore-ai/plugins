pipelineJob('madcore.plugin.flasker-hub.delete') {
    parameters {
	    stringParam('APP_NAME', 'flasker', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Kubernetes: delete') {
                  build job: 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', value: 'flasker-hub/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
