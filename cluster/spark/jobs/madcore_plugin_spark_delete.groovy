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
                stage('Spark: wait for spark cluster to stop') {
                    build job: 'madcore.kubectl.wait.service.down', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'spark-master:8080'), string(name: 'SERVICE_NAMESPACE', value: 'spark-cluster')]
                    build job: 'madcore.kubectl.wait.service.down', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'zeppelin'), string(name: 'SERVICE_NAMESPACE', value: 'spark-cluster')]
                }
                }
            """.stripIndent())
	    }
    }
}
