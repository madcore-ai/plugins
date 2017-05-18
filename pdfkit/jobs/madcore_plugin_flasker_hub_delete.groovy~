pipelineJob('madcore.plugin.pdfkit.delete') {
    parameters {
	    stringParam('APP_NAME', 'pdfkit', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Kubernetes: delete') {
                  build job: 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', value: 'pdfkit/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
