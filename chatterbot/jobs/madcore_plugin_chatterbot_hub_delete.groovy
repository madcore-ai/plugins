pipelineJob('madcore.plugin.chatterbot.delete') {
    parameters {
	    stringParam('APP_NAME', 'flasker', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Kubernetes: delete') {
                  build job: 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', value: 'chatterbot/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
