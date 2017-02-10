pipelineJob('madcore.plugin.elasticsearch.deploy') {
    parameters {
	    stringParam('APP_NAME', 'elasticsearch', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Spark: install') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'cluster/elasticsearch/kub')]
                }
                stage('Spark: wait for elasticsearch cluster to start') {
                    build job: 'madcore.kubectl.wait.service.up', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'elasticsearch-master:9200'), string(name: 'SERVICE_NAMESPACE', value: 'elasticsearch-cluster')]
                }
                }
            """.stripIndent())
	    }
    }
}
