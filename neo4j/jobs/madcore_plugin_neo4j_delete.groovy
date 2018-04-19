pipelineJob('madcore.plugin.neo4j.delete') {
    parameters {
	    stringParam('APP_NAME', 'neo4j', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Kubernetes: delete') {
                    build job: 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', value: 'neo4j/kub')]
                }
                stage('Neo4j: wait for neo4j cluster to stop') {
                    build job: 'madcore.kubectl.wait.service.down', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'neo4j:7474'), string(name: 'SERVICE_NAMESPACE', value: 'neo4j-cluster')]
                }
                stage('Neo4jCluster: nodes cleanup') {
                    build job: 'madcore.kubectl.cluster.nodes.cleanup', parameters: [string(name: 'NODE_LABEL', value: 'cluster=k8s')]
                }
                }
            """.stripIndent())
	    }
    }
}
