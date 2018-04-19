pipelineJob('madcore.plugin.pdfkit.status') {
    parameters {
	    stringParam('APP_NAME', 'pdfkit', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Kubernetes: status') {
                  build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'pdfkit/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
