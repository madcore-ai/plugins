pipelineJob('madcore.plugin.neo4j.deploy') {
    parameters {
	    stringParam('APP_NAME', 'neo4j', '')
      stringParam('S3BUCKETNAME', '', 'S3 bucket name for backup')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Ingress: cloud formation install') {
                }
                stage('Neo4j: create volume') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'neo4j/kub/neo4j.yaml')]
                }
                stage('Neo4j: create services') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'neo4j/kub/services')]
                }
                stage('Neo4j: create core') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'neo4j/kub/neo4j-core.yaml')]
                }
                stage('Neo4j: wait for neo4j cluster to start') {
                    build job: 'madcore.kubectl.wait.service.up', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'neo4j-main:7474'), string(name: 'SERVICE_NAMESPACE', value: 'neo4j-cluster')]
                }
                stage('Update HAproxy') {
                    build job: 'madcore.ssl.letsencrypt.getandinstall', parameters: [string(name: 'S3BucketName', value: params.S3BUCKETNAME)]
                }
              }
            """.stripIndent())
	    }
    }
}
