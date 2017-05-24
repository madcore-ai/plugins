pipelineJob('madcore.plugin.neo4j.deploy') {
    parameters {
	    stringParam('APP_NAME', 'neo4j', '')
      stringParam('NODES_IPS', '', 'Comma separated list of IPs.')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Neo4j: cloud formation install') {
                }
                stage ('Wait nodes for neo4j') {
                    build job: 'madcore.kubectl.cluster.wait.nodes.up', parameters: [string(name: 'NODES_LABEL', value: 'cluster=neo4j'), string(name: 'NODES_IPS', value: params.NODES_IPS)]
                }
                stage('Neo4j: create services') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'neo4j/kub/services')]
                }
                stage('Neo4j: create core') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'neo4j/kub/neo4j-core.yaml')]
                }
                stage('Neo4j: wait for neo4j cluster to start') {
                    build job: 'madcore.kubectl.wait.service.up', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'neo4j:7474'), string(name: 'SERVICE_NAMESPACE', value: 'neo4j-cluster')]
                }
              }
            """.stripIndent())
	    }
    }
}
