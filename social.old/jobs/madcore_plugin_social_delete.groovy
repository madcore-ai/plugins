pipelineJob('madcore.plugin.social.delete') {
    parameters {
	    stringParam('RELEASE_NAME', 'social', '')
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
