pipelineJob('madcore.plugin.spark.delete') {
    parameters {
	    stringParam('APP_NAME', 'spark', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Kubernetes: delete') {
                    build job: 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', value: 'spark/kub')]
                }
                }
            """.stripIndent())
	    }
    }
}
