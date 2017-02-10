pipelineJob('madcore.plugin.elasticsearch.cleanup') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('SparkCluster: nodes cleanup') {
                    build job: 'madcore.kubectl.cluster.nodes.cleanup', parameters: [string(name: 'NODE_LABEL', value: 'cluster=elasticsearch')]
                }
                }
            """.stripIndent())
	    }
    }
}
