pipelineJob('madcore.plugin.elasticsearch.status') {
    parameters {
	    stringParam('APP_NAME', 'elasticsearch', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('ElasticsearchCluster kubernetes: status') {
                    build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'cluster/elasticsearch/kub')]
                }
                stage('ElasticsearchCluster nodes: status') {
                    build job: 'madcore.kubectl.cluster.nodes.status', parameters: [string(name: 'NODE_LABEL', value: 'cluster=elasticsearch')]
                }
                }
            """.stripIndent())
	    }
    }
}
