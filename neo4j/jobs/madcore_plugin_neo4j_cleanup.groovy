pipelineJob('madcore.plugin.neo4j.cleanup') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Neo4jCluster: nodes cleanup') {
                    build job: 'madcore.kubectl.cluster.nodes.cleanup', parameters: [string(name: 'NODE_LABEL', value: 'cluster=neo4j')]
                }
                }
            """.stripIndent())
	    }
    }
}
