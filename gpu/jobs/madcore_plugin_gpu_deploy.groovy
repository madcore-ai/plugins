pipelineJob('madcore.plugin.gpu.deploy') {
    parameters {
	    stringParam('APP_NAME', 'gpu', '')
      stringParam('S3BUCKETNAME', '', 'S3 bucket name for backup')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('gpu: create kubernetes items') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'gpu/kub')]
                }
                }
            """.stripIndent())
	    }
    }
}
