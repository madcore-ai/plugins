pipelineJob('madcore.plugin.GPU.deploy') {
    parameters {
	    stringParam('APP_NAME', 'GPU', '')
      stringParam('S3BUCKETNAME', '', 'S3 bucket name for backup')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('GPU: create kubernetes items') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'GPU/kub')]
                }
                }
            """.stripIndent())
	    }
    }
}
