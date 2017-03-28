pipelineJob('madcore.plugin.spark.status') {
    parameters {
	    stringParam('APP_NAME', 'spark', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('SparkCluster kubernetes: status') {
                    build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'spark/kub')]
                }
                stage('SparkCluster nodes: status') {
                    build job: 'madcore.kubectl.cluster.nodes.status', parameters: [string(name: 'NODE_LABEL', value: 'cluster=spark')]
                }
                }
            """.stripIndent())
	    }
    }
}
