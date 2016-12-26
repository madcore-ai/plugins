pipelineJob('madcore.plugin.spark.deploy') {
    parameters {
	    stringParam('APP_NAME', 'spark', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Helm: install'
                build job: 'madcore.helm.install', parameters: [string(name: 'CHART', value: params.APP_NAME + '/chart'), string(name: 'RELEASE_NAME', value: params.APP_NAME), string(name: 'NAMESPACE', value: params.APP_NAME + '-plugin')]
                }
            """.stripIndent())
	    }
    }
}
