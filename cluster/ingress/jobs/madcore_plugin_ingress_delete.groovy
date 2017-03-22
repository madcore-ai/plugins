pipelineJob('madcore.plugin.ingress.delete') {
    parameters {
	    stringParam('APP_NAME', 'ingress', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Kubernetes: delete') {
                    build job: 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', value: 'cluster/ingress/kub')]
                }
                }
            """.stripIndent())
	    }
    }
}
