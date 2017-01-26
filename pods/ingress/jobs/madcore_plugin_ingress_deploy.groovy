pipelineJob('madcore.plugin.ingress.deploy') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Ingress: cloud formation install') {
                }
                stage('Ingress: deploy pods') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'pods/ingress/kub')]
                }
                }
            """.stripIndent())
	    }
    }
}
