pipelineJob('madcore.plugin.neo4j.status') {
    parameters {
	    stringParam('APP_NAME', 'neo4j', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Neo4jCluster kubernetes: status') {
                    build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'neo4j/kub')]
                }
                stage('Neo4jCluster nodes: status') {
                    build job: 'madcore.kubectl.cluster.nodes.status', parameters: [string(name: 'NODE_LABEL', value: 'cluster=neo4j')]
                }
                stage('Neo4j cluster status') {
                    build job: 'madcore.plugin.neo4j.selftest'
                }
                }
            """.stripIndent())
	    }
    }
}
