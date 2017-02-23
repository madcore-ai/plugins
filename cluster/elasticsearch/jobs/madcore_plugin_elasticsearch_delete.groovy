pipelineJob('madcore.plugin.elasticsearch.delete') {
    parameters {
	    stringParam('APP_NAME', 'elasticsearch', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Kubernetes: delete') {
                    build job: 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', value: 'cluster/elasticsearch/kub')]
                }
                stage('Elasticsearch: wait for elasticsearch cluster to stop') {
                    build job: 'madcore.kubectl.wait.service.down', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'es-master:9200'), string(name: 'SERVICE_NAMESPACE', value: 'es-cluster')]
                }
                stage('ElasticsearchCluster: nodes cleanup') {
                    build job: 'madcore.kubectl.cluster.nodes.cleanup', parameters: [string(name: 'NODE_LABEL', value: 'cluster=k8s')]
                }
                }
            """.stripIndent())
	    }
    }
}
