pipelineJob('madcore.plugin.kafka.delete') {
    parameters {
	    stringParam('RELEASE_NAME', 'kafka', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('HELM delete chart') {
                  build job: 'madcore.helm.delete', parameters: [string(name: 'RELEASE_NAME', value: params.RELEASE_NAME)]
                }
              }
            """.stripIndent())
	    }
    }
}
