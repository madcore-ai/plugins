pipelineJob('madcore.plugin.chatterbot.status') {
    parameters {
	    stringParam('APP_NAME', 'chatterbot', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Kubernetes: status') {
                  build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'chatterbot/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
