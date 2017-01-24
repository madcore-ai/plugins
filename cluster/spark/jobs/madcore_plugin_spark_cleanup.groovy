pipelineJob('madcore.plugin.spark.cleanup') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('SparkCluster: nodes cleanup') {
                    build job: 'madcore.kubectl.cluster.nodes.cleanup', parameters: [string(name: 'NODE_LABEL', value: 'cluster=spark')]
                }
                }
            """.stripIndent())
	    }
    }
}
