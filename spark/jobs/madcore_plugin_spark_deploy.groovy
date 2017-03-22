pipelineJob('madcore.plugin.spark.deploy') {
    parameters {
	    stringParam('APP_NAME', 'spark', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Spark: install') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'cluster/spark/kub')]
                }
                stage('Spark: wait for spark cluster to start') {
                    build job: 'madcore.kubectl.wait.service.up', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'spark-master:8080'), string(name: 'SERVICE_NAMESPACE', value: 'spark-cluster')]
                    build job: 'madcore.kubectl.wait.service.up', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'zeppelin'), string(name: 'SERVICE_NAMESPACE', value: 'spark-cluster')]
                }
                }
            """.stripIndent())
	    }
    }
}
