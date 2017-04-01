pipelineJob('madcore.plugin.spark.deploy') {
    parameters {
	    stringParam('APP_NAME', 'spark', '')
      stringParam('S3BucketName', '', 'S3 bucket name for backup')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Spark: install') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'spark/kub')]
                }
                stage('Spark: wait for spark cluster to start') {
                    build job: 'madcore.kubectl.wait.service.up', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'spark-master:8080'), string(name: 'SERVICE_NAMESPACE', value: 'spark-cluster')]
                    build job: 'madcore.kubectl.wait.service.up', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'zeppelin'), string(name: 'SERVICE_NAMESPACE', value: 'spark-cluster')]
                }
                stage('Update HAproxy') {
                    build job: 'madcore.ssl.letsencrypt.getandinstall', parameters: [string(name: 'S3BucketName', value: params.S3BucketName)]
                }
                }
            """.stripIndent())
	    }
    }
}
