pipelineJob('madcore.plugin.spark.delete') {
    parameters {
	    stringParam('APP_NAME', 'spark', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Helm: delete'
                build job: 'madcore.helm.delete', parameters: [string(name: 'RELEASE_NAME', value: params.APP_NAME)]
                }
            """.stripIndent())
	    }
    }
}
