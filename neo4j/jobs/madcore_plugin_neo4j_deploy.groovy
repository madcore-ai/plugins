pipelineJob('madcore.plugin.neo4j.deploy') {
    parameters {
	    stringParam('APP_NAME', 'neo4j', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Neo4j: cloud formation install') {
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
