pipelineJob('madcore.plugin.kafka.status') {
    parameters {
	    stringParam('RELEASE_NAME', 'kafka', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('docker registry status') {
                  build job: 'madcore.helm.status', parameters: [string(name: 'RELEASE_NAME', value: params.RELEASE_NAME)]
                }
              }
            """.stripIndent())
	    }
    }
}
