pipelineJob('madcore.plugin.spark.status') {
    parameters {
	    stringParam('APP_NAME', 'spark', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Kubernetes: status') {
                    build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'spark/kub')]
                }
                }
            """.stripIndent())
	    }
    }
}
