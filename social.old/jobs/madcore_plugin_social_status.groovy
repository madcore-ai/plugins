pipelineJob('madcore.plugin.social.status') {
    parameters {
	    stringParam('RELEASE_NAME', 'social', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('helm chart status') {
                  build job: 'madcore.helm.status', parameters: [string(name: 'RELEASE_NAME', value: params.RELEASE_NAME)]
                }
                stage ('zookeeper clients status') {
                  build job: 'madcore.plugin.kafka.test', parameters: [string(name: 'RELEASE_NAME', value: params.RELEASE_NAME)]
                }
              }
            """.stripIndent())
	    }
    }
}
