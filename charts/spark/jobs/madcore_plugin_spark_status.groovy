pipelineJob('madcore.plugin.spark.status') {
    parameters {
	    stringParam('APP_NAME', 'spark', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Helm: status'
                build job: 'madcore.helm.status', parameters: [string(name: 'RELEASE_NAME', value: params.APP_NAME)]
                }
            """.stripIndent())
	    }
    }
}
